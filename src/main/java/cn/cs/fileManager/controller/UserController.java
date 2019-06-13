package cn.cs.fileManager.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.cs.fileManager.common.ResultUtil;
import cn.cs.fileManager.dao.mapper.FmUserMapper;
import cn.cs.fileManager.dao.model.FmUser;
import cn.cs.fileManager.dao.model.FmUserExample;
import cn.cs.fileManager.dao.model.FmUserExample.Criteria;
import cn.cs.fileManager.dto.FmUserDTO;
import cn.cs.fileManager.form.LoginForm;
import cn.cs.fileManager.form.NewForRegister;
import cn.cs.fileManager.service.FmUserServiceDetails;
import cn.cs.fileManager.service.IUserRoleService;
import cn.cs.fileManager.service.IUserService;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private FmUserServiceDetails userServiceDetails;
    @Autowired
    private IUserRoleService roleService;
    
    private static final Logger logger=LoggerFactory.getLogger(UserController.class);
    
    private FmUserDTO currentUserDetails;
    
    @RequestMapping(value = {"/allusers"},method = {RequestMethod.POST})
    public JSONObject GetAllUsers(@RequestParam int start,
            @RequestParam int length,@RequestParam int draw,
            @RequestParam String searchval,@RequestParam( value="order[column]") int column,
            @RequestParam( value="order[dir]") String dir,
            HttpServletRequest request)  {
    
        currentUserDetails =  (FmUserDTO) SecurityContextHolder.getContext()
	    	    .getAuthentication()
	    	    .getPrincipal();
        List<String> roles=currentUserDetails.getRoles();
    	
		boolean isAdmin=roles.contains("ADMIN");
        
        if(isAdmin)
        {
            PageHelper.startPage(start, length);
            List<FmUser> users;
            if(searchval!="")
            {
                users=this.userService.getUsersBySth("loginName", searchval,column,dir);
            }
            else {
                users=this.userService.getUsersBySth("loginName", null,column,dir);
            }
            
            PageInfo<FmUser> page= new PageInfo<>(users);
            List<FmUser> data=page.getList();
            JSONObject back=new JSONObject();           
            back.put("data", data);
            back.put("draw", draw);
            back.put("recordsTotal",page.getTotal());
            back.put("recordsFiltered",page.getTotal());         
            return back;
        }else
        {
            logger.info("没有查询所有用户信息权限");
            return null;
        }          
    }
    
    
    //@RequestBody接收的是一个Json对象的字符串，而不是一个Json对象
    @RequestMapping(value = {"/checkloginname"},method = {RequestMethod.POST})
    public ResultUtil checkloginname(@RequestBody String loginName,HttpServletRequest request)  {
        long count=this.userService.getNumsOfLoginName(loginName);
        return ResultUtil.success( (count>0l)? 0:1);      
    }
    
    
    @RequestMapping(value = {"/register"},method = {RequestMethod.POST}) 
    public ResultUtil register(@RequestBody FmUser param,HttpServletRequest request)  {              
        if(this.userService.register(param)) {
        	return ResultUtil.success("注册成功");
        }else
        {                     
        	return ResultUtil.error("注册出了问题");
        }       
    }
    
    @RequestMapping(value = {"/isNormal"},method = {RequestMethod.POST}) 
    public ResultUtil isNormal()  {
    	try {
	    	currentUserDetails =  (FmUserDTO) SecurityContextHolder.getContext()
		    	    .getAuthentication()
		    	    .getPrincipal();
	    	List<String> roles=currentUserDetails.getRoles();
	    	System.out.print(roles.toString());
			boolean isNormal=!roles.contains("ADMIN");
			if(isNormal) {                 
	        	return ResultUtil.success("User");
	        }else{                     
	        	return ResultUtil.success("Admin");
	        }
    	}catch(Exception e)
    	{
    		return ResultUtil.error("wrong");
    	}

    }
    
    @RequestMapping(value = {"/getpsnlmsg"},method = {RequestMethod.POST}) 
    public FmUser getpsnlmsg()  {         
    	currentUserDetails =  (FmUserDTO) SecurityContextHolder.getContext()
	    	    .getAuthentication()
	    	    .getPrincipal();
    	FmUser u=new FmUser();
    	BeanUtils.copyProperties(currentUserDetails, u);
        return u;
    }
    
    @RequestMapping(value = {"/updatePsnlInfo"},method = {RequestMethod.POST})  
    public ResultUtil update(@RequestBody FmUser record,HttpServletRequest request)  {
    	boolean flag0=true;
    	if(record.getRoles()!=null && !record.getRoles().isEmpty()) {
    		 flag0=this.roleService.updateRoles(record);
    	}
        boolean flag=this.userService.updateUserInfo(record);
        currentUserDetails =  (FmUserDTO) SecurityContextHolder.getContext()
	    	    .getAuthentication()
	    	    .getPrincipal();
        if(flag && flag0 &&currentUserDetails.getId()==record.getId()) {
        	LoginForm user =new LoginForm();
            BeanUtils.copyProperties(record, user);
            UserDetails userDTO=userServiceDetails.loadUserByUsername(JSON.toJSONString(user));
	        //设置上下文
	        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDTO, null, userDTO.getAuthorities()));
            return ResultUtil.success();
          
        }else
        {                       
            return ResultUtil.error("数据库中用户信息更新失败");
        }           
    }
}
