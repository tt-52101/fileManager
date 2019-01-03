package cn.cs.fileManager.service;

import java.util.List;

import cn.cs.fileManager.dao.model.FmFolder;
public interface IFolderService {

	List<FmFolder> getAllownerfolder(String owner,long pid);
	long getFaihui(String owner,long id);
	List<FmFolder> getDangqian(String owner,long id);
	void xinjian(String xulie);
	void gengxin(String canshu);
	List<FmFolder> getNovalid(String owner);
}

