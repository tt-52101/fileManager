package cn.cs.fileManager.service;

import java.util.List;

import cn.cs.fileManager.dao.model.FmFile;

public interface IFileService {

	//List<FmFile> getAllownerfile(String owner,long folderid);
	/*
	 * 获取文件夹fordid下的所有文件
	 * 验证权限：
	 * 如果isNormal为1 代表是普通人需要验证这个userid和folder的reg_account是否一致
	 * 如果isNormal为0，不需要验证
	 * */
	List<FmFile> getAllFile(long userid,long folderid,boolean isNormal);
	
	void gengxin(String canshu);
	//List<FmFile> getNovalid(String owner);
	List<FmFile> getNovalid(long userid);
	//List<FmFile> getNowfile(long fileid);
	/*
	 * 获取当前fileid这个文件的信息
	 * 不需要校验权限
	 * */
	FmFile getNowfile(long fileid);

}