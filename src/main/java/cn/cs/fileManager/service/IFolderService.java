package cn.cs.fileManager.service;

import java.util.List;


import cn.cs.fileManager.dao.model.FmFolder;
public interface IFolderService {

	
	/*
	 * get all Folders which pid='pid'and ((isNormal= true and created by userid ) or (isNormall =false))
	 * 验证权限：
	 * 如果isNormal为1 代表是普通人需要验证这个userid和folder的reg_account是否一致
	 * 如果isNormal为0，不需要验证
	 * */
	//List<FmFolder> getAllownerfolder(String owner,long pid);
	List<FmFolder> getAllFolder(long userid,long pid,boolean isNormal);
	
	
	/*
	 * 得到id是‘id’的文件夹的父文件夹的id 
	 * pid以返回值的形式
	 * */
	//long getFaihui(String owner,long id);
	long getPFolder(long userid,long id,boolean isNormal); 
	
	/*
	 * 获得folderid是id的文件夹的信息
	 * */
	//List<FmFolder> getDangqian(String owner,long id);
	FmFolder getINfoOf(long id);
	
	/*???????
	 * 新建的文件夹得信息存在String里面?*/
	int newFolder(FmFolder folder);
	/*
	 * ??????
	 * 参数需要先赋值成原值，改动的再在基础上改
	 */
	void updateFolder(FmFolder fmfolder);
	
	
	//List<FmFolder> getNovalid(String owner);
	List<FmFolder> getNovalid(long userid,boolean isNormal);
	
	boolean deleteFolderRecord(long folderid);
}

