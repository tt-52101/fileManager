package cn.cs.fileManager.service;

import java.util.List;

import cn.cs.fileManager.dao.model.FmFile;

public interface IFileService {

	List<FmFile> getAllownerfile(String owner,long folderid);
	void gengxin(String canshu);
	List<FmFile> getNovalid(String owner);
	List<FmFile> getNowfile(long fileid);

}