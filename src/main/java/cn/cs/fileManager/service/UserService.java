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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import cn.cs.fileManager.FileManagerApplication;
import cn.cs.fileManager.dao.mapper.FmUserMapper;
import cn.cs.fileManager.dao.model.FmUser;
import cn.cs.fileManager.dao.model.FmUserExample;
import cn.cs.fileManager.dao.model.FmUserExample.Criteria;

/**
 * @author dac
 *
 */
@Transactional(value = "transactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, timeout = 300)
@Service
public class UserService implements IUserService {

    @Autowired
    private FmUserMapper fmUserMapper;
  
    private static final Logger logger=LoggerFactory.getLogger(UserService.class);
    final Base64.Decoder decoder = Base64.getDecoder();
    final Base64.Encoder encoder = Base64.getEncoder();
   

    /* (non-Javadoc)
     * @see cn.cs.fileManager.service.IUserService#getUserList()
     */
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public List<FmUser> getUserList() {
        logger.info("查找所有用户信息");
        FmUserExample fe = new FmUserExample();
        List<FmUser> list = fmUserMapper.selectByExample(fe);
        for(int i=0;i<list.size();i++)
        {
            String password=list.get(i).getPassword();
            password=new String(decoder.decode(password));
            list.get(i).setPassword(password);
        }
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
            case "type":
                criteria.andTypeEqualTo(val);
                break;
         }            
        }
                  
        List<FmUser> list = fmUserMapper.selectByExample(fe);
          
        
        for(int i=0;i<list.size();i++)
        {
            String password=list.get(i).getPassword();
            password=new String(decoder.decode(password));
            list.get(i).setPassword(password);
        }
        
        return list;
    }
    
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public FmUser getUser(String login_name,String password) {
        FmUserExample fe = new FmUserExample();
        Criteria criteria = fe.createCriteria();
        try {
            byte[] textByte = password.getBytes("UTF-8");
            password=encoder.encodeToString(textByte);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            logger.debug("密码base64加密错误");
        }
        criteria.andLoginNameEqualTo(login_name);
        criteria.andPasswordEqualTo(password);
        List<FmUser> list=fmUserMapper.selectByExample(fe);
        
        if(list.size()==1)
        {
            FmUser u=list.get(0);
            u.setPassword(new String(decoder.decode(u.getPassword())));
            logger.info("根据用户名和密码查找到用户");
            return u;
        }
        else
        {
            return null;
        }
    }
    
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public long checkUserName(String login_name) {
        FmUserExample fe = new FmUserExample();
        Criteria criteria = fe.createCriteria();
        criteria.andLoginNameEqualTo(login_name);
        long result=fmUserMapper.countByExample(fe);
        
        return result;
        
    }
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public boolean register(FmUser u) {
        String password=u.getPassword();
        try {
            byte[] textByte = password.getBytes("UTF-8");
            password=encoder.encodeToString(textByte);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            logger.debug("密码base64加密错误");
        }
        u.setPassword(password);
        int result=fmUserMapper.insert(u);
        if(result>0)
            return true;
        else
            return false;
        
    }
    
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public boolean updateTime(FmUser u) {
        FmUser record=new FmUser();
        record.setLastLoginDate(new Date());
        FmUserExample fe = new FmUserExample();
        Criteria criteria = fe.createCriteria();
        criteria.andLoginNameEqualTo(u.getLoginName());
        int result=fmUserMapper.updateByExampleSelective(record, fe);
        if(result>0)
            return true;
        else
            return false;
    }
    
    @Override
    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
    public boolean update(FmUser record) {
        String password=record.getPassword();
        if(password!=null)
        {
            try {
                byte[] textByte = password.getBytes("UTF-8");
                password=encoder.encodeToString(textByte);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                logger.debug("密码base64加密错误");
            }           
        }
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
