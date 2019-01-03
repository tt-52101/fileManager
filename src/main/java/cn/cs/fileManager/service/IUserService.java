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
    FmUser getUser(String login_name,String password);
    long checkUserName(String login_name);
    boolean register(FmUser u);	
	boolean updateTime(FmUser u);
	boolean update(FmUser u);
	List<FmUser> getUsersBySth(String attr,String val,int column,String dir);
}
