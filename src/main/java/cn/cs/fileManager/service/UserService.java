/**
 * 
 */
package cn.cs.fileManager.service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.cs.fileManager.FileManagerApplication;
import cn.cs.fileManager.dao.mapper.FmUserMapper;
import cn.cs.fileManager.dao.mapper.FmUserRoleMapper;
import cn.cs.fileManager.dao.model.FmFolder;
import cn.cs.fileManager.dao.model.FmUser;
import cn.cs.fileManager.dao.model.FmUserExample;
import cn.cs.fileManager.dao.model.FmUserExample.Criteria;
import cn.cs.fileManager.dao.model.FmUserRole;
import cn.cs.fileManager.dao.model.FmUserRoleExample;
import cn.cs.fileManager.dto.FmUserDTO;
import cn.cs.fileManager.form.LoginForm;
import cn.cs.fileManager.form.LoginUser;

/**
 * @author dac
 *
 */
@Transactional(value = "transactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, timeout = 300)
@Service
public class UserService implements IUserService {

    @Autowired
    private FmUserMapper fmUserMapper;
    
    @Autowired
    private FolderService folderService;
    
    @Autowired
    private FmUserRoleMapper fmUserRoleMapper;
    
    private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
  
    private static final Logger logger=LoggerFactory.getLogger(UserService.class);
	
    @Value("${custom.AdminRole}")
    private  long adminRole;
	
    @Value("${custom.NormalRole}")
    private  long normalRole;
	
    @Value("${custom.RootFilePath}")
    private  String rootPath;
   
    
   
    /* (non-Javadoc)
     * @see cn.cs.fileManager.service.IUserService#getUserList()
     */
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public List<FmUser> getUserList() {
        logger.info("查找所有用户信息");
        FmUserExample fe = new FmUserExample();
        List<FmUser> list = fmUserMapper.selectByExample(fe);       
        return list;
    }
    
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public List<FmUser> getUsersBySth(String attr,String val,int column,String dir) {
        
        FmUserExample fe = new FmUserExample();
        switch(column){
        case 0:
            fe.setOrderByClause("id "+dir);
            break;
        case 5:
            fe.setOrderByClause("type "+dir);
            break;          
        case 6:
            fe.setOrderByClause("valid "+dir);          
            break;        
        } 
        Criteria criteria = fe.createCriteria();
       
        if(val!=null)
        {
            logger.info("查找"+attr+" like "+val);
            switch(attr){
            case "loginName":
                criteria.andLoginNameLike('%'+val+'%');
                break;
            case "mobileNumber":
                criteria.andMobileNumberLike('%'+val+'%');
                break;          
            case "valid":
                criteria.andValidEqualTo(val);
                break;
          
         }            
        }
                  
        List<FmUser> list = fmUserMapper.selectByExample(fe);
               
        
        return list;
    }
    
    
    
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public long getNumsOfLoginName(String login_name) {
        FmUserExample fe = new FmUserExample();
        Criteria criteria = fe.createCriteria();
        criteria.andLoginNameEqualTo(login_name);
        long result=fmUserMapper.countByExample(fe);
        
        return result;
        
    }
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public boolean register(FmUser u) {
    	
		String password = passwordEncoder.encode(u.getPassword());
        u.setPassword(password);
        u.setLastLoginDate(new Date());     
        u.setValid("1");
        int result=fmUserMapper.insert(u);
        if(result>0)
        {
        	FmUserExample fe = new FmUserExample();
            Criteria criteria = fe.createCriteria();
            criteria.andLoginNameEqualTo(u.getLoginName());
            List<FmUser> list = fmUserMapper.selectByExample(fe);
            
            //插入用户角色
            logger.info("插入用户角色");
            FmUserRole record=new FmUserRole();
            record.setUserId(list.get(0).getId());
            record.setRoleId(normalRole);
            record.setUserRoleStatus("1");
            int flag=fmUserRoleMapper.insert(record);
            
            //为新用户新建文件夹
            FmFolder folder=new FmFolder();
            folder.setFolderName(u.getLoginName());
            String basedir = rootPath+"\\"+u.getLoginName();
            folder.setBaseDir(basedir);            
            folder.setpId(0l);
            folder.setRegAccount(list.get(0).getId());            
            int flag2=folderService.newFolder(folder);
            
            if(flag>0 && flag2>0)
            {
            	return true;
            }
            else
            	return false;
        }
        else
            return false;
        
    }
    
   
    
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public boolean updateUserInfo(FmUser record) {
        String password=passwordEncoder.encode(record.getPassword());      
        record.setPassword(password);
        FmUserExample fe = new FmUserExample();
        Criteria criteria = fe.createCriteria();
        criteria.andIdEqualTo(record.getId());
        int result=fmUserMapper.updateByExampleSelective(record, fe);
        if(result>0)
            return true;
        else
            return false;
    }

	
	

}
