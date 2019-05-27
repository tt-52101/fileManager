package cn.cs.fileManager.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.cs.fileManager.dao.mapper.FmUserMapper;
import cn.cs.fileManager.dao.model.FmUser;
import cn.cs.fileManager.dao.model.FmUserExample;
import cn.cs.fileManager.dao.model.FmUserExample.Criteria;
import cn.cs.fileManager.dto.FmUserDTO;
import cn.cs.fileManager.filter.JwtLoginFilter;
import cn.cs.fileManager.form.LoginForm;

@Transactional(value = "transactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, timeout = 300)
@Service
public class FmUserServiceDetails implements UserDetailsService {
	  @Autowired
	  private FmUserMapper fmUserMapper;
	  private static final Logger logger=LoggerFactory.getLogger(FmUserServiceDetails.class);
	
	 @Override
	    @Cacheable(value = "cn.cs.fileManager.dao.model.FmUser", key = "#root.targetClass + #root.methodName")
	    public UserDetails loadUserByUsername(String json) throws UsernameNotFoundException {
	        LoginForm loginFrom = JSON.parseObject(json, LoginForm.class);
	        FmUserExample fe = new FmUserExample();
	        Criteria criteria = fe.createCriteria();	       
	        criteria.andLoginNameEqualTo(loginFrom.getUserName());	       
	        List<FmUser> list=fmUserMapper.selectByExample(fe);
	        
	        if(list.size()==1)
	        {
	            FmUser u=list.get(0);
	            logger.info(u.toString());
	            FmUserDTO userDTO = new FmUserDTO();
	            BeanUtils.copyProperties(u,userDTO);
	            userDTO.setRemember(loginFrom.getRemember());	                      
	            userDTO.setUserName(u.getLoginName());
	            userDTO.setUserPassword(u.getPassword());
	            userDTO.setLoginName(u.getLoginName());
	            logger.info("get user entity "+userDTO.toString()+"password :"+userDTO.getPassword());
	            return userDTO;
	        }
	        else
	        {
	            return null;
	        }
	      
	    }


}
