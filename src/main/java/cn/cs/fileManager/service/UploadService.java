package cn.cs.fileManager.service;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.cs.fileManager.dao.mapper.FmFileMapper;
import cn.cs.fileManager.dao.mapper.FmFileTypeMapper;
import cn.cs.fileManager.dao.model.FmFile;
import cn.cs.fileManager.dao.model.FmFileExample;
import cn.cs.fileManager.dao.model.FmFileType;
import cn.cs.fileManager.dao.model.FmFileTypeExample;
import cn.cs.fileManager.dao.model.FmFileTypeExample.Criteria;
import cn.cs.fileManager.dao.model.FmUser;
import cn.cs.fileManager.dao.model.FmUserExample;
@Service
public  class UploadService implements IUploadService{
    @Autowired
    private FmFileMapper fmFileMapper;
    
    @Autowired
    private FmFileTypeMapper fmFileTypeMapper;
    public static long fm_file_type_id ;    
    public static long limitSize=10000; 
    
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
public boolean insert(String trueName,long fileSize,String UUIDfileName,long folderid,long account) {
     	
        FmFile  file=new FmFile();
        int filesize=(int)fileSize;
		file.setFolderId(folderid);
		file.setFileSize( filesize);
        file.setFileTypeId(fm_file_type_id);
        file.setVer(0);
        file.setFileTrueName(trueName);
        file.setRegAccount(account);
        file.setUpdDate(new Date());
        file.setUpdAccount(account);
        file.setValid("1");
		file.setDescription("无");
		file.setFileName(UUIDfileName);
		file.setRegDate(new Date());	
		fmFileMapper.insert(file);

    	return true;	
}



    @Override
	@Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
	
	public List<FmFileType> getFileTypeList(String suffixName ) {
        System.out.println("测试2");
        System.out.println(suffixName);
		 FmFileTypeExample file = new FmFileTypeExample();
         Criteria criteria = file.createCriteria();
        criteria.andFileExtEqualTo(suffixName);
	    List<FmFileType> list = fmFileTypeMapper.selectByExample(file);
	        
	    fm_file_type_id=list.get(0).getId();
	     
		return list;
}
}
