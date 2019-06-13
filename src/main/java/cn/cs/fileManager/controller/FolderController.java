package cn.cs.fileManager.controller;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.common.DeleteFileUtil;
import cn.cs.fileManager.common.ResultUtil;
import cn.cs.fileManager.dao.model.FmFile;
import cn.cs.fileManager.dao.model.FmFolder;
import cn.cs.fileManager.dto.FmUserDTO;
import cn.cs.fileManager.form.NewForFolder;
import cn.cs.fileManager.service.IFileService;
import cn.cs.fileManager.service.IFolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "FolderController")
@RestController
public class FolderController {
	 @Autowired
	 private IFolderService folderservice;
	 @Autowired
	 private IFileService fileservice;
	 
	 private static final Logger logger=LoggerFactory.getLogger(FolderController.class);
	 
	 private FmUserDTO currentUserDetails;
	 
	 @ApiOperation(value=" get all Folders", notes="which pid='pid'and ((isNormal= true and created by userid ) or (isNormall =false))")
	 @RequestMapping(value = { "/getAllFolder" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	    public List<FmFolder> getFolderList(@RequestBody String pid) {
		 	logger.info("get folders under "+pid);
			long temp_pid=Long.parseLong(pid);
			if(temp_pid == 0l)
			{
				currentUserDetails =  (FmUserDTO) SecurityContextHolder.getContext()
			    	    .getAuthentication()
			    	    .getPrincipal();		
				logger.info(currentUserDetails.toString());		
			}
			
			List<String> roles=currentUserDetails.getRoles();
			boolean isNormal=!roles.contains("ADMIN");
			    
			long userid=currentUserDetails.getId();
		    List<FmFolder> list = this.folderservice.getAllFolder(userid, temp_pid, isNormal);
		    return list;
	    }
	 
	 @RequestMapping(value = { "/getCurrentFolderInfo" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public FmFolder getCurrentFolderInfo(@RequestBody String pid) {		  
		    long id=Long.parseLong(pid);
		    logger.info("get info of id="+id);
	        FmFolder list = this.folderservice.getINfoOf(id);
	        return list;
	 }
	 
	 @ApiOperation(value= "新建文件夹",notes="参数应该换成Folder")
	 @RequestMapping(value = { "/newFolder" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public ResultUtil newFolder(@RequestBody NewForFolder folderForm)
	 {
		 logger.info("new Folder");
		FmFolder newFo=new FmFolder();
		BeanUtils.copyProperties(folderForm, newFo);
		FmFolder pFolder=this.folderservice.getINfoOf(folderForm.getPId());
		newFo.setBaseDir(pFolder.getBaseDir()+"\\"+folderForm.getFolderName());
		newFo.setRegAccount(currentUserDetails.getId());
		logger.info("sql pid"+newFo.getpId());
		try {
		this.folderservice.newFolder(newFo);
		}catch(Exception e) {
			 return ResultUtil.error("新建文件夹出错");
		}
		 return ResultUtil.success();
	 }
	 
	
	 
	 @ApiOperation(value= "回收站",notes="")
	 @RequestMapping(value = { "/getRecycleFolder" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public List<FmFolder> getRecycleFolder(@RequestBody String noneUse)
	 {
		 long userid=currentUserDetails.getId();
		 List<String> roles=currentUserDetails.getRoles();
		 boolean isNormal=!roles.contains("ADMIN");
		 List<FmFolder> list = this.folderservice.getNovalid(userid, isNormal);
	        return list;
	 }
	 
	 @ApiOperation(value= "文件夹还原",notes="参数有问题")
	 @RequestMapping(value = { "/setFolderValidOne" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public ResultUtil setFolderValidOne(@RequestBody String id)
	 {
		
		 FmFolder fmFolder=new FmFolder();
		
		 fmFolder.setId(Long.parseLong(id));
		 fmFolder.setValid("1");
		 try {
			 this.folderservice.updateFolder(fmFolder);
		 }catch(Exception e) {
			 return ResultUtil.error("还原出错");
		 }
		 return ResultUtil.success();
	 }

	 @ApiOperation(value= "删除文件夹",notes="参数如果换成Folder会不会把已有信息更新成null")
	 @RequestMapping(value = { "/setFolderValidZero" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public ResultUtil setFolderValidzero(@RequestBody String id)
	 {
		 FmFolder fmFolder=new FmFolder();
		 fmFolder.setId(Long.parseLong(id));
		 fmFolder.setValid("0");
		 try {
			 this.folderservice.updateFolder(fmFolder);
		 }catch(Exception e) {
			 return ResultUtil.error("set valid = 0 出错");
		 }
		 return ResultUtil.success();
	 }
	 
	 @RequestMapping(value = { "/deleteFolder" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public ResultUtil deleteFolder(@RequestBody String id) {

		FmFolder fmFolder = this.folderservice.getINfoOf(Long.parseLong(id));
		List<String> roles = currentUserDetails.getRoles();
		boolean isNormal = !roles.contains("ADMIN");
		List<FmFile> list = this.fileservice.getAllFile(currentUserDetails.getId(), Long.parseLong(id), isNormal);
		String basedir = fmFolder.getBaseDir();
		try {
			for (int i = 0; i < list.size(); i++) {
				String fileName = basedir + "\\" + list.get(i).getFileName();
				DeleteFileUtil.delete(fileName);
				this.fileservice.deleteFileRecord(list.get(i).getId());
			}
			this.folderservice.deleteFolderRecord(fmFolder.getId());
		} catch (Exception e) {
			return ResultUtil.error("删除错误");
		}
		return ResultUtil.success();
	 }
	 
}
