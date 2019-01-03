package cn.cs.fileManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.dao.model.FmFile;
import cn.cs.fileManager.dao.model.FmFolder;
import cn.cs.fileManager.service.IFileService;

@RestController
public class FileController{
	 @Autowired
	 private IFileService fileservice;
	 @RequestMapping(value = { "/getAllfile" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public List<FmFile> getAllfile(@RequestBody String canshu){
		 JSONObject ccan=JSON.parseObject(canshu);
		 long id=Long.parseLong(ccan.getString("id"));
		 String owner=ccan.getString("owner");
		 List<FmFile> list=this.fileservice.getAllownerfile(owner, id);
		 return list;
	 }
	 @RequestMapping(value = { "/delefile" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public void shanchu(@RequestBody String canshu) {
		 this.fileservice.gengxin(canshu);
	 }
	 @RequestMapping(value = { "/huanfile" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public void upValid(@RequestBody String canshu) {
		 JSONObject ccan=JSON.parseObject(canshu);
		 ccan.put("valid", "1");
		 String can=JSON.toJSONString(ccan);
		 this.fileservice.gengxin(can);
	 }
	 @RequestMapping(value = { "/huishoufile" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public List<FmFile> huishoufile(@RequestBody String canshu)
	 {
		 JSONObject ccan=JSON.parseObject(canshu);
		 String owner=ccan.getString("owner");
		 List<FmFile> list =this.fileservice.getNovalid(owner);
		 return list;
	 }
	 @RequestMapping(value = { "/getnow" }, method = { RequestMethod.POST },produces = {"application/json; charset=utf-8"})
	 public List<FmFile> getnow(@RequestBody String canshu)
	 {
		 JSONObject ccan=JSON.parseObject(canshu);
		 long id=Long.parseLong(ccan.getString("id"));
		 List<FmFile> list=this.fileservice.getNowfile(id);
		 return list;
	 }
}