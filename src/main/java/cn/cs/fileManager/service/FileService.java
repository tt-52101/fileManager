package cn.cs.fileManager.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.dao.mapper.FmFileMapper;
import cn.cs.fileManager.dao.model.FmFile;
import cn.cs.fileManager.dao.model.FmFileExample;
import cn.cs.fileManager.dao.model.FmFileExample.Criteria;

@Transactional(value = "transactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, timeout = 300)
@Service
public class FileService implements IFileService{
	 @Autowired
	 private FmFileMapper fmfilemapper;
	 @Override
	 @Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
	 public List<FmFile> getAllFile(long userid,long folderid,boolean isNormal){
		 FmFileExample fe=new FmFileExample();
		 Criteria criteria = fe.createCriteria();
		 criteria.andRegAccountEqualTo(userid);
		 criteria.andFolderIdEqualTo(folderid);
		 List<FmFile> list=fmfilemapper.selectByExample(fe);
		 return list; 
	 }
	 @Override
	 @Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
	 public void gengxin(String canshu)
	 {
		 JSONObject ccanshu=JSON.parseObject(canshu);
		 long id=Long.parseLong(ccanshu.getString("id"));
		 String valid=ccanshu.getString("valid");
		 FmFile file=new FmFile();
		 FmFileExample fe=new FmFileExample();
		 file.setValid(valid);
		 Criteria criteria = fe.createCriteria();
		 criteria.andIdEqualTo(id);
		 fmfilemapper.updateByExampleSelective(file, fe);
	 }
	 @Override
	 @Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
	 public List<FmFile> getNovalid(long userid){
		 FmFileExample fe=new FmFileExample();
		 Criteria criteria = fe.createCriteria();
		 criteria.andRegAccountEqualTo(userid);
		 criteria.andValidEqualTo("0");
		 List<FmFile> list=fmfilemapper.selectByExample(fe);
		 return list;
	 }
	 @Override
	 @Cacheable(value = "cn.cs.fileManager.dao.model.FmFile", key = "#root.targetClass + #root.methodName")
	 public FmFile getNowfile(long fileid){
		 FmFileExample fe=new FmFileExample();
		 Criteria criteria = fe.createCriteria();
		 criteria.andIdEqualTo(fileid);
		 List<FmFile> list=fmfilemapper.selectByExample(fe);
		 return list.get(0); 
	 }
}