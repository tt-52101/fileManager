package cn.cs.fileManager.service;

import cn.cs.fileManager.dao.model.FmUser;

public interface IUserRoleService {
	boolean updateRoles(FmUser record);
	boolean addUserRole(FmUser fmUser);
}
