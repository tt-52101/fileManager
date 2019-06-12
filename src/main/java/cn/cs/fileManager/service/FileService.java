package cn.cs.fileManager.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.sun.star.io.ConnectException;

import cn.cs.fileManager.dao.mapper.FmFileMapper;
import cn.cs.fileManager.dao.model.FmFile;
import cn.cs.fileManager.dao.model.FmFileExample;
import cn.cs.fileManager.dao.model.FmFileExample.Criteria;

@Transactional(value = "transactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, timeout = 300)
@Service
public class FileService implements IFileService{
    private static Logger log = LoggerFactory.getLogger(IFileService.class);

    private static final String TARGET_FORMAT_FILE_LOCATION
            = "E:\\工作\\fileManager\\src\\main\\webapp\\fm\\files";


    private static final String TARGET_FILE_FORMAT_SUFFIX = ".pdf";

    private static final String TEMP_FILE_LOCATION = "\\fm\\files";

    public static final ArrayList<String> ALLOWED_TO_PDF_FILE_FORMAT_LIST = new ArrayList<String>() {{
        add("doc");
        add("docx");
        add("xls");
        add("xlsx");
        add("ppt");
        add("pptx");
        add("txt");
    }};
    
	 @Autowired
	 private FmFileMapper fmfilemapper;
	 
	 @Override
	 @Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
	 public List<FmFile> getAllFile(long userid,long folderid,boolean isNormal){
		 FmFileExample fe=new FmFileExample();
		 Criteria criteria = fe.createCriteria();
		 if(isNormal)
	      {
	        criteria.andRegAccountEqualTo(userid);
	      }  
		 criteria.andFolderIdEqualTo(folderid);
		 List<FmFile> list=fmfilemapper.selectByExample(fe);
		 return list; 
	 }
	 
	 
	 @Override
	 @Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
	 public void updateFile(FmFile fmFile)
	 {
		 FmFileExample fe=new FmFileExample();
		 Criteria criteria = fe.createCriteria();
		 criteria.andIdEqualTo(fmFile.getId());
		 fmfilemapper.updateByExampleSelective(fmFile, fe);
	 }
	 
	 
	 @Override
	 @Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
	 public List<FmFile> getNovalid(long userid,boolean isNormal){
		 FmFileExample fe=new FmFileExample();
		 Criteria criteria = fe.createCriteria();
		 if(isNormal)
		 {
			 criteria.andRegAccountEqualTo(userid);
		 }
		 criteria.andValidEqualTo("0");
		 List<FmFile> list=fmfilemapper.selectByExample(fe);
		 return list;
	 }
	 
	 @Override
	 @Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
	 public FmFile getINfoOf(long fileid){
		 FmFileExample fe=new FmFileExample();
		 Criteria criteria = fe.createCriteria();
		 criteria.andIdEqualTo(fileid);
		 List<FmFile> list=fmfilemapper.selectByExample(fe);
		 return list.get(0); 
	 }
	 
	 @Override
	 @Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
	 public boolean deleteFileRecord(long fileid)
	 {
		 FmFileExample fe=new FmFileExample();
		 Criteria criteria = fe.createCriteria();
		 criteria.andIdEqualTo(fileid);
		 int flag=fmfilemapper.deleteByExample(fe);
		 if(flag>0)
		 {
			 return true;
		 }else {
			 return false;
		 }
	 }
	 /*
	  * 得到PDF格式文件相对路径
	  * */
	 @Override
	    public String convertFileFormat2PDF(File sourceFile) throws IOException {
	        Map<String, String> fileNameAndFormat = getFileFormat(sourceFile.getName());
	        String fileName = fileNameAndFormat.get("fileName");
	        String sourceFileFormat = fileNameAndFormat.get("fileFormat");
	        if (sourceFileFormat == null || !ALLOWED_TO_PDF_FILE_FORMAT_LIST.contains(sourceFileFormat)) {
	            log.warn("不支持转换的文件类型！");
	            return null;
	        }

	        String targetFileName = TARGET_FORMAT_FILE_LOCATION +  File.separator + fileName + 
	                TARGET_FILE_FORMAT_SUFFIX;
	        File targetFile = new File(targetFileName);
	        if (targetFile.exists()) {
	            targetFile.delete();
	            targetFile.createNewFile();
	        }

	        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
	        
	        try {
	            connection.connect();
	        } catch (Exception e) {
	        	e.printStackTrace();
	            System.err.println("文件转换出错，请检查OpenOffice服务是否启动。");
	        }

	        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
	        converter.convert(sourceFile, targetFile);
	        connection.disconnect();
	        String targetFilePathTemp = TEMP_FILE_LOCATION + File.separator + targetFile.getName();
	        return targetFilePathTemp.replace("\\", "/");
	    }

	    @Override
	    public String readFileContent(String filePath) throws IOException {
	        FileReader fileReader = new FileReader(filePath);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        StringBuilder stringBuilder = new StringBuilder("");
	        String line;
	        while ((line = bufferedReader.readLine()) != null) {
	            stringBuilder.append(line).append("\n");
	        }
	        return stringBuilder.toString();
	    }

	    
	    private Map<String, String> getFileFormat(String fileName) {
	        if (StringUtils.isEmpty(fileName) || !fileName.contains(".")) {
	            log.warn("未检测出文件格式");
	            return Collections.emptyMap();
	        }
	        String[] fileNameAndFormat = fileName.split("\\.");
	        Map<String, String> map = new HashMap<>();
	        map.put("fileName", fileNameAndFormat[0]);
	        map.put("fileFormat", fileNameAndFormat[1]);
	        return map;
	    }
	    
	    
	    @Override
	    public boolean writeFileContent(String fileid,String filePath, String content)  {
	    	
			File file=new File(filePath);
	        if (!file.exists() || StringUtils.isEmpty(content)) {
	            log.warn("文件不存在！");
	            return false;
	        }
	        FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(file);
				fileWriter.write(content);
		        fileWriter.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			FmFile fmFile=this.getINfoOf(Long.parseLong(fileid));
			fmFile.setVer(fmFile.getVer()+1);
	        return true;
	    }
	 
}