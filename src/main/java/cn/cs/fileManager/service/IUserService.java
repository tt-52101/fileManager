/**
 * 
 */
package cn.cs.fileManager.service;

import java.util.List;

import cn.cs.fileManager.dao.model.FmUser;
import cn.cs.fileManager.dao.model.FmUserExample;

/**
 * @author dac
 *
 */
public interface IUserService {

    List<FmUser> getUserList();
//    FmUser getUser(String login_name,String password);
    long getNumsOfLoginName(String loginName);
    boolean register(FmUser u);	
	boolean updateUserInfo(FmUser record);
	List<FmUser> getUsersBySth(String attr,String val,int column,String dir);
}
