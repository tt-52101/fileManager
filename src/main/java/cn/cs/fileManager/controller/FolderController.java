package cn.cs.fileManager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.dao.model.FmFolder;
import cn.cs.fileManager.service.IFolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "FolderController")
@RestController
public class FolderController {
	 @Autowired
	 private IFolderService folderservice;
	 
	 private static final Logger logger=LoggerFactory.getLogger(FolderController.class);
	 
	 @ApiOperation(value=" get all Folders", notes="which pid='pid'and ((isNormal= true and created by userid ) or (isNormall =false))")
	 @RequestMapping(value = { "/getAllFolder" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	    public List<FmFolder> getFolderList(@RequestBody String param) {
		    JSONObject temp=JSON.parseObject(param);
		    long pid=Long.parseLong(temp.getString("id"));
		    long userid=Long.parseLong(temp.getString("userid"));
	        List<FmFolder> list = this.folderservice.getAllFolder(userid, pid, true);
	        return list;
	    }
	 
	 @RequestMapping(value = { "/getPFolder" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public long getParentFolder(@RequestBody String param) {
		    JSONObject temp=JSON.parseObject(param);
		    long id=Long.parseLong(temp.getString("id"));
		    long userid=Long.parseLong(temp.getString("userid"));
	        long pid = this.folderservice.getPFolder(userid,id,true);
	        return pid;
	    }
	 @ApiOperation(value= "在用户打开到一个文件（夹）的时候，列出到这一步的文件夹作为路径",notes="用户点击文件夹过来的时候就应该记录而不是查很多次数据库")
	 @RequestMapping(value = { "/getDangqian" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public FmFolder getDangqian(@RequestBody String pid) {
		    JSONObject ppid=JSON.parseObject(pid);
		    long id=Long.parseLong(ppid.getString("id"));
	        FmFolder list = this.folderservice.getINfoOf(id);
	        return list;
	 }
	 @ApiOperation(value= "新建文件夹",notes="参数应该换成Folder")
	 @RequestMapping(value = { "/xinjian" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public void xinjian(@RequestBody String xulie)
	 {
		 logger.info(xulie);
		 this.folderservice.newFolder(xulie);
	 }
	 @ApiOperation(value= "删除文件夹",notes="参数如果换成Folder会不会把已有信息更新成null")
	 @RequestMapping(value = { "/delefolder" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public void shanchu(@RequestBody String canshu)
	 {
		 this.folderservice.updateFolder(canshu);;
	 }
	 @ApiOperation(value= "回收站",notes="")
	 @RequestMapping(value = { "/huishouzhan" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public List<FmFolder> huishouzhan(@RequestBody String canshu)
	 {
		 logger.info("回收站"+canshu);
		 JSONObject ccan=JSON.parseObject(canshu);
		 long userid=Long.parseLong(ccan.getString("userid"));
		 List<FmFolder> list = this.folderservice.getNovalid(userid, true);
	        return list;
	 }
	 @ApiOperation(value= "文件夹还原",notes="参数有问题")
	 @RequestMapping(value = { "/folhuanyuan" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public void upValid(@RequestBody String canshu)
	 {
		 JSONObject ccan=JSON.parseObject(canshu);
		 logger.info("文件还原的"+canshu);
		 ccan.put("valid", "1");
		 String can=JSON.toJSONString(ccan);
		 this.folderservice.updateFolder(can);
	 }

}
