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
        criteria.andValidEqualTo("1");
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
	/*
	 * requried:foldername pid regaccount basedir     
	 * valid & RegDate not necessary
	 */
	public int newFolder(FmFolder folder)
	{ 			
		folder.setRegDate(new Date());
		folder.setValid("1");	 
		int result= fmfoldermapper.insert(folder);
		File file=new File(folder.getBaseDir());
		
		if(!file.exists()){//如果文件夹不存在
			file.mkdir();//创建文件夹
		}
			return result;
	}
	
	@Override
	@Cacheable(value = "cn.cs.fileManager.dao.model.FmFolder", key = "#root.targetClass + #root.methodName")
	public void updateFolder(FmFolder fmFolder)
	{
		FmFolderExample fe = new FmFolderExample();
        Criteria criteria = fe.createCriteria();
        criteria.andIdEqualTo(fmFolder.getId());
		fmfoldermapper.updateByExampleSelective(fmFolder, fe);
	}
	
	@Override
	@Cacheable(value = "cn.cs.fileManager.dao.model.FmFolder", key = "#root.targetClass + #root.methodName")
	public List<FmFolder> getNovalid(long userid,boolean isNormal)
	{
		FmFolderExample fe = new FmFolderExample();
        Criteria criteria = fe.createCriteria();
        if(isNormal)
        {
        	 criteria.andRegAccountEqualTo(userid);
        }
        criteria.andValidEqualTo("0");
        List<FmFolder> list = fmfoldermapper.selectByExample(fe);
        return list;
	}
	
	@Override
	@Cacheable(value = "cn.cs.fileManager.dao.model.FmFolder", key = "#root.targetClass + #root.methodName")
	public boolean deleteFolderRecord(long folderid) {
		FmFolderExample fe = new FmFolderExample();
        Criteria criteria = fe.createCriteria();
        criteria.andIdEqualTo(folderid);
        criteria.andValidEqualTo("0");
        int flag = fmfoldermapper.deleteByExample(fe);
        if(flag>0)
        {
        	return true;
        }else {
        	return false;
        }
	}

}
