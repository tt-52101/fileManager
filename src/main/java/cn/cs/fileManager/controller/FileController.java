package cn.cs.fileManager.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.common.DeleteFileUtil;
import cn.cs.fileManager.common.ResultUtil;
import cn.cs.fileManager.dao.model.FmFile;
import cn.cs.fileManager.dao.model.FmFolder;
import cn.cs.fileManager.dto.FmUserDTO;
import cn.cs.fileManager.service.IFileService;
import cn.cs.fileManager.service.IFolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "File Controller")
@RestController
public class FileController{
	 @Autowired
	 private IFileService fileservice;
	 
	 @Autowired
	 private IFolderService folderservice;
	 
	 private static final Logger logger=LoggerFactory.getLogger(FileController.class);
	 private FmUserDTO currentUserDetails;
	 
	 @RequestMapping(value = { "/getAllfile" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public List<FmFile> getAllfile(@RequestBody String pid){
		 
		 logger.info("get files under "+pid);
			long temp_pid=Long.parseLong(pid);
			 currentUserDetails =  (FmUserDTO) SecurityContextHolder.getContext()
				    	    .getAuthentication()
				    	    .getPrincipal();
			
			logger.info(currentUserDetails.toString());
			
			List<String> roles=currentUserDetails.getRoles();
			boolean isNormal=!roles.contains("ROLE_ADMIN");
			    
			long userid=currentUserDetails.getId();
		    List<FmFile> list = this.fileservice.getAllFile(userid, temp_pid,isNormal);
		   
		 return list;
	 }
	 
	 @RequestMapping(value = { "/deleteFile" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public ResultUtil deletefile(@RequestBody String id) {
		FmFile fmFile= this.fileservice.getINfoOf(Long.parseLong(id));
		FmFolder fmFolder=this.folderservice.getINfoOf(fmFile.getFolderId());
		String filename=fmFolder.getBaseDir()+"\\"+fmFile.getFileName();
		try {
			DeleteFileUtil.delete(filename);
			this.fileservice.deleteFileRecord(Long.parseLong(id));
		}catch(Exception e)
		{
			return ResultUtil.error("刪除文件錯誤");
		}
		return ResultUtil.success();
	 }
	 
	 @RequestMapping(value = { "/setFileValidOne" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public ResultUtil setFileValidOne(@RequestBody String id) {
		
		 FmFile fmFile=new FmFile();		
		 fmFile.setId( Long.parseLong(id));
		 fmFile.setValid("1");
		 try {
		 this.fileservice.updateFile(fmFile);
		 }catch(Exception e)
			{
				return ResultUtil.error("刪除文件錯誤");
			}
			return ResultUtil.success();
	 }
	 
	 
	 @RequestMapping(value = { "/setFileValidZero" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public ResultUtil setFileValidZero(@RequestBody String id) {
		 FmFile fmFile=new FmFile(); 
		 fmFile.setId( Long.parseLong(id));
		 fmFile.setValid("0");
		 logger.info("update file of "+fmFile.getId());
		 try {
			 this.fileservice.updateFile(fmFile);
		 }catch(Exception e)
			{
				return ResultUtil.error("恢复文件錯誤");
			}
			return ResultUtil.success();
	 }
	 
	 @RequestMapping(value = { "/getRecycleFile" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public List<FmFile> recycleFile(@RequestBody String noneUse)
	 {
		 long userid=currentUserDetails.getId();
		 logger.info("get files under "+userid);
		 List<String> roles=currentUserDetails.getRoles();
		 boolean isNormal=!roles.contains("ROLE_ADMIN");
		 List<FmFile> list =this.fileservice.getNovalid(userid,isNormal);
		 return list;
	 }

	 
	 @RequestMapping(value = "/convertFileFormat2PDF", method = {RequestMethod.GET, RequestMethod.POST},
             produces = "application/json;charset=utf-8")
	public String convertFileFormat2PDF(@RequestBody String fileId) throws IOException {
		 Map<String, String> response = new HashMap<>();
		 FmFile fmFile= this.fileservice.getINfoOf(Long.parseLong(fileId));
		 FmFolder fmFolder=this.folderservice.getINfoOf(fmFile.getFolderId()); 		 
		 String PDFFilePath = fileservice.convertFileFormat2PDF(new File(fmFolder.getBaseDir(),fmFile.getFileName()));
		 String result = (StringUtils.isEmpty(PDFFilePath) ? "failed" : "success");
		 response.put("result", result);
		 response.put("fileLocation", PDFFilePath);
		 return JSONObject.toJSONString(response);
	}
	 
	    @RequestMapping(value = "/readMdFileContent", method = {RequestMethod.GET, RequestMethod.POST},
	            produces = "application/json;charset=utf-8")
	    public String readMdFileContent(@RequestBody String fileid) throws IOException {
	        Map<String, String> response = new HashMap<>();
	       FmFile fmFile=this.fileservice.getINfoOf(Long.parseLong(fileid));
	       FmFolder fmFolder=this.folderservice.getINfoOf(fmFile.getFolderId());
	        String filePath =fmFolder.getBaseDir()+"\\"+fmFile.getFileName();
	        String fileContent = fileservice.readFileContent(filePath);
	        response.put("result", "success");
	        response.put("fileContent", fileContent);
	        return JSONObject.toJSONString(response);
	    }
	    
	    @ApiOperation(value="传输文件", notes="返回是blob")
		@RequestMapping(value = { "/readPicContent" }, method = { RequestMethod.GET })
		 public void readPicContent(@RequestParam("fileid") String fileid,HttpServletResponse response) {
	    	FmFile fmFile=this.fileservice.getINfoOf(Long.parseLong(fileid));
		    FmFolder fmFolder=this.folderservice.getINfoOf(fmFile.getFolderId());
		    String filePath =fmFolder.getBaseDir()+"\\"+fmFile.getFileName();
		    System.out.print(filePath);
			 File file=new File(filePath);
			 if(file.exists())
			 {
				 FileInputStream fis=null;
				 BufferedInputStream bis=null;
				 byte[] buffer=new byte[1024];
				 try {
					 fis = new FileInputStream(file);
	                 bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
	                 int i = bis.read(buffer);
	                 while (i != -1) {
	                     os.write(buffer, 0, i);
	                     i = bis.read(buffer);
	                 }
	                 
				 }catch (Exception e) {
	                 e.printStackTrace();
	             } finally {
	                 if (bis != null) {
	                     try {
	                         bis.close();
	                     } catch (IOException e) {
	                         e.printStackTrace();
	                     }
	                 }
	                 if (fis != null) {
	                     try {
	                         fis.close();
	                     } catch (IOException e) {
	                         e.printStackTrace();
	                     }
	                 }
	             }
	          }
			 else {
				 logger.info("文件不存在");
			 }
		 }
	    
	    @RequestMapping(value = "/writeMdFileContent", method = {RequestMethod.GET, RequestMethod.POST},
                produces = "application/json;charset=utf-8")
		public String writeMdFileContent(@RequestBody String fileIdAndContent) throws IOException {
		    JSONObject params = JSONObject.parseObject(fileIdAndContent);
		    String fileId = params.getString("fileid");
		    FmFile fmFile=this.fileservice.getINfoOf(Long.parseLong(fileId));
		    FmFolder fmFolder=this.folderservice.getINfoOf(fmFile.getFolderId());
		    String filePath =fmFolder.getBaseDir()+"\\"+fmFile.getFileName();
		    String fileContent = params.getString("fileContent");
		    fileservice.writeFileContent(fileId,filePath, fileContent);
		    return JSONObject.toJSONString("\"result\": \"success\"");
}
}