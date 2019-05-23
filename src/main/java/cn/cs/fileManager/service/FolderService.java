package cn.cs.fileManager.service;


import java.io.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.controller.UserController;
import cn.cs.fileManager.dao.mapper.FmFolderMapper;
import cn.cs.fileManager.dao.model.FmFolder;
import cn.cs.fileManager.dao.model.FmFolderExample;
import cn.cs.fileManager.dao.model.FmFolderExample.Criteria;


@Transactional(value = "transactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, timeout = 300)
@Service
public class FolderService  implements IFolderService {

	 @Autowired
	 private FmFolderMapper fmfoldermapper;
	 private static final Logger logger=LoggerFactory.getLogger(FolderService.class);
	@Override
	@Cacheable(value = "cn.cs.fileManager.dao.model.FmFolder", key = "#root.targetClass + #root.methodName")
	public List<FmFolder> getAllFolder(long userid,long pid,boolean isNormal) {
		// TODO Auto-generated method stub
		FmFolderExample fe = new FmFolderExample();
        Criteria criteria = fe.createCriteria();
        if(isNormal)
        {
        	criteria.andRegAccountEqualTo(userid);
        }       
        criteria.andPIdEqualTo(pid);
        List<FmFolder> list = fmfoldermapper.selectByExample(fe);
        return list;
	}
	@Override
	@Cacheable(value = "cn.cs.fileManager.dao.model.FmFolder", key = "#root.targetClass + #root.methodName")
	public long getPFolder(long userid,long id,boolean isNormal)
	{
		FmFolderExample fe = new FmFolderExample();
        Criteria criteria = fe.createCriteria();
        if(isNormal)
        {
        	criteria.andRegAccountEqualTo(userid);
        }   
        criteria.andIdEqualTo(id);
        List<FmFolder> li=fmfoldermapper.selectByExample(fe);
        long pid=li.get(0).getpId();
		return pid;
		
	}
	@Override
	@Cacheable(value = "cn.cs.fileManager.dao.model.FmFolder", key = "#root.targetClass + #root.methodName")
	public FmFolder getINfoOf(long id)
	{
		FmFolderExample fe = new FmFolderExample();
        Criteria criteria = fe.createCriteria();
        criteria.andIdEqualTo(id);
        List<FmFolder> list=fmfoldermapper.selectByExample(fe);
         
		return list.get(0);
		
	}
	@Override
	@Cacheable(value = "cn.cs.fileManager.dao.model.FmFolder", key = "#root.targetClass + #root.methodName")
	public void newFolder(String xulie)
	{
		JSONObject xxulie=JSON.parseObject(xulie);
		 String foldername=xxulie.getString("foldername");
		 long pid=Long.parseLong(xxulie.getString("pid"));
		 
		 String basedir=xxulie.getString("basedir");
		 long regaccount=Long.parseLong(xxulie.getString("regaccount"));
		 String description=xxulie.getString("description");
		 String valid=xxulie.getString("valid");
		 basedir+="\\"+foldername;
		 logger.info("创建文件夹路径"+basedir);
		 
		 FmFolder fmf=new FmFolder();
		 fmf.setBaseDir(basedir);
		 fmf.setDescription(description);
		 fmf.setFolderName(foldername);
		 
		 fmf.setRegDate(new Date());
		 fmf.setRegAccount(regaccount);
		 fmf.setValid(valid);
		 fmf.setpId(pid);
		 fmfoldermapper.insert(fmf);
		 File file=new File(basedir);
			if(!file.exists()){//如果文件夹不存在
				file.mkdir();//创建文件夹
			}
			
	}
	@Override
	@Cacheable(value = "cn.cs.fileManager.dao.model.FmFolder", key = "#root.targetClass + #root.methodName")
	public void updateFolder(String canshu)
	{
		JSONObject ccanshu=JSON.parseObject(canshu);
		long id=Long.parseLong(ccanshu.getString("id"));
		String valid=ccanshu.getString("valid");
		FmFolder fmf=new FmFolder();
		fmf.setValid(valid);
		FmFolderExample fe = new FmFolderExample();
        Criteria criteria = fe.createCriteria();
        criteria.andIdEqualTo(id);
		fmfoldermapper.updateByExampleSelective(fmf, fe);
	}
	
	@Override
	@Cacheable(value = "cn.cs.fileManager.dao.model.FmFolder", key = "#root.targetClass + #root.methodName")
	public List<FmFolder> getNovalid(long userid,boolean isNormal)
	{
		FmFolderExample fe = new FmFolderExample();
        Criteria criteria = fe.createCriteria();
        criteria.andRegAccountEqualTo(userid);
        criteria.andValidEqualTo("0");
        List<FmFolder> list = fmfoldermapper.selectByExample(fe);
        return list;
	}

}
