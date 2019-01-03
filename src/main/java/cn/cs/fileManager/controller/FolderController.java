package cn.cs.fileManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.dao.model.FmFolder;
import cn.cs.fileManager.service.IFolderService;

@RestController
public class FolderController {
	 @Autowired
	 private IFolderService folderservice;
	 @RequestMapping(value = { "/getAllownerfolder" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 
	    public List<FmFolder> getFolderList(@RequestBody String pid) {
		    JSONObject ppid=JSON.parseObject(pid);
		    long id=Long.parseLong(ppid.getString("id"));
		    String owner=ppid.getString("owner");
	        List<FmFolder> list = this.folderservice.getAllownerfolder(owner,id);
	        return list;
	    }
	 @RequestMapping(value = { "/getFaihui" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public long getFaihui(@RequestBody String id) {
		    JSONObject ppid=JSON.parseObject(id);
		    long iid=Long.parseLong(ppid.getString("id"));
		    String owner=ppid.getString("owner");
	        long pid = this.folderservice.getFaihui(owner,iid);
	        return pid;
	    }
	 @RequestMapping(value = { "/getDangqian" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public List<FmFolder> getDangqian(@RequestBody String pid) {
		    JSONObject ppid=JSON.parseObject(pid);
		    String owner="zz";
		    long id=Long.parseLong(ppid.getString("id"));
	        List<FmFolder> list = this.folderservice.getDangqian(owner,id);
	        return list;
	 }
	 @RequestMapping(value = { "/xinjian" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public void xinjian(@RequestBody String xulie)
	 {
		 this.folderservice.xinjian(xulie);
	 }
	 @RequestMapping(value = { "/delefolder" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public void shanchu(@RequestBody String canshu)
	 {
		 this.folderservice.gengxin(canshu);
	 }
	 @RequestMapping(value = { "/huishouzhan" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public List<FmFolder> huishouzhan(@RequestBody String canshu)
	 {
		 JSONObject ccan=JSON.parseObject(canshu);
		 String owner=ccan.getString("owner");
		 List<FmFolder> list = this.folderservice.getNovalid(owner);
	        return list;
	 }
	 @RequestMapping(value = { "/folhuanyuan" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public void upValid(@RequestBody String canshu)
	 {
		 JSONObject ccan=JSON.parseObject(canshu);
		 ccan.put("valid", "1");
		 String can=JSON.toJSONString(ccan);
		 this.folderservice.gengxin(can);
	 }

}
