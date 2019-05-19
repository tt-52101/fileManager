package cn.cs.fileManager.controller;
import java.io.File;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.dao.mapper.FmFileMapper;
import cn.cs.fileManager.dao.mapper.FmFileTypeMapper;
import cn.cs.fileManager.dao.model.FmFile;
import cn.cs.fileManager.dao.model.FmFileType;

import cn.cs.fileManager.dao.model.FmUser;
import cn.cs.fileManager.service.IUploadService;
import cn.cs.fileManager.service.IUserService;
import cn.cs.fileManager.service.UploadService;
@RestController
public class UploadController {
	@Autowired

	private  IUploadService up;
	 public  long  folderid;
	 public  String fmFolder_baseDir;
	 public  String account;
	 
	 @RequestMapping(value= {"/aaa"},method= {RequestMethod.POST}) 
	    public JSONObject  fanhui(@RequestBody String tranmission)
	    {
	    	JSONObject  a=JSON.parseObject(tranmission);
	    	folderid=Long.parseLong(a.getString("id"));

	    	fmFolder_baseDir=a.getString("basedir");
	    	account=a.getString("account");
	    	System.out.println(folderid+fmFolder_baseDir);
	    	JSONObject  b=new JSONObject();
	    	b.put("tip", "II");
	    	return b;
	    }
	 

    @RequestMapping(value= {"/upload"},method= {RequestMethod.POST},produces= {"application/json;charset=utf-8"})
    public JSONObject  upload(@RequestBody MultipartFile fileUpload)
    {
   	    String UUIDfileName;
        String fileName = fileUpload.getOriginalFilename();
        System.out.println(fileName); 
        //String suffixName;
       String suffixName = fileName.substring(fileName.lastIndexOf("."));
        long fileSize =fileUpload.getSize();
        fileSize=(int)fileSize/1000;
        System.out.println(suffixName);
        //重新生成文件名
        UUIDfileName = UUID.randomUUID()+suffixName;
        System.out.println(UUIDfileName);
        //指定本地文件夹存储图片	
        //System.out.println(username);
        String filePath = fmFolder_baseDir+"/";
        JSONObject result=new JSONObject();
        System.out.println(fileSize);
        try {
            
        	try { this.up.getFileTypeList(suffixName); }
        	catch (Exception e) 
        	{
        		System.out.println("文件型号不匹配");         
        	result.put("tip", "a");
        	return result;
        	}
            if (fileSize<=10000) 
            {
            	System.out.println(fileSize);
            	fileUpload.transferTo(new File(filePath+UUIDfileName));
            	up.insert(fileName,fileSize,UUIDfileName,folderid,account);
            	result.put("tip", "b");
            	return result;
            	}
            else
            {
            	System.out.println("文件大小超过限制");
            	result.put("tip", "c");
            	return result;
            	}

                } catch (Exception e) {
            e.printStackTrace();
        //    return new Massage(-1,"fail to upload");
            return null;
        } 
    }

        
        
  
}