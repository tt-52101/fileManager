package cn.cs.fileManager.service;

import java.util.List;

import cn.cs.fileManager.dao.model.FmFile;
import cn.cs.fileManager.dao.model.FmFileType;
import cn.cs.fileManager.dao.model.FmUser;

public interface IUploadService {


	boolean insert(String trueName,long fileSize,String UUIDfileName,long folderid,String account);

	List<FmFileType> getFileTypeList(String suffixName);


}
