package cn.cs.fileManager.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.cs.fileManager.dao.model.FmFile;

public interface IFileService {

	
	/*
	 * 获取文件夹fordid下的所有文件
	 * 验证权限：
	 * 如果isNormal为1 代表是普通人需要验证这个userid和folder的reg_account是否一致
	 * 如果isNormal为0，不需要验证
	 * */
	List<FmFile> getAllFile(long userid,long folderid,boolean isNormal);
	
	void updateFile(FmFile fmFile);
	
	//List<FmFile> getNovalid(String owner);
	List<FmFile> getNovalid(long userid,boolean isNormal);
	
	//List<FmFile> getNowfile(long fileid);
	/*
	 * 获取当前fileid这个文件的信息
	 * 不需要校验权限
	 * */
	FmFile getINfoOf(long fileid);
	
	boolean deleteFileRecord(long fileid);
	
	String convertFileFormat2PDF(File sourceFile) throws IOException;

	String readFileContent(String filePath) throws IOException;

	boolean writeFileContent(String fileid, String filePath, String fileContent) ;

}