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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

import cn.cs.fileManager.dao.mapper.FmUserMapper;
import cn.cs.fileManager.dao.model.FmUser;
import cn.cs.fileManager.service.IUserService;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;
    private static final Logger logger=LoggerFactory.getLogger(UserController.class);
    

    @RequestMapping(value = {"/login"},method = {RequestMethod.POST})
    @ResponseBody
    //必须加上@responsebody 发现它的作用是将你代码return的值作为http请求的内容发挥客户端
    //如果之前没写这个注解，所以，http请求的内容默认将是一个页面。    
    public JSONObject login(@RequestBody String params,HttpServletRequest request)  {
        
        JSONObject obj=JSON.parseObject(params);
        FmUser u=this.userService.getUser(obj.getString("name"),obj.getString("password"));
        
        if(u!=null && u.getValid().equalsIgnoreCase("1")) {
            HttpSession session=request.getSession();//这就是session的创建
            session.setAttribute("user", u);//session set user          
            logger.info("session创建完毕");
            if(this.userService.updateTime(u))
                logger.info("更新登录时间成功");
            JSONObject o= new JSONObject();
            o.put("type", u.getType());
            return o;            
        }else
        {
            logger.info("用户不存在或者是已经离职");
            return null;
        }          
    }
    
   
    
    @RequestMapping(value = {"/allusers"},method = {RequestMethod.POST})
    @ResponseBody  
    public JSONObject GetAllUsers(@RequestParam int start,
            @RequestParam int length,@RequestParam int draw,
            @RequestParam String searchval,@RequestParam( value="order[column]") int column,
            @RequestParam( value="order[dir]") String dir,
            HttpServletRequest request)  {
            
        HttpSession session = request.getSession(false);
        logger.info("start:"+start+"  length: "+length+"  draw: "+draw+"  searchval: "+searchval+" order by "+column+" "+dir);

        FmUser u=(FmUser) session.getAttribute("user");
        if(u!=null && u.getValid().equals("1") && u.getType().equals("0"))
        {
            logger.info("获得查询所有用户信息权限");
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
     
    @RequestMapping(value = {"/checkloginname"},method = {RequestMethod.POST})
    @ResponseBody 
    public JSONObject checkloginname(@RequestBody String param,HttpServletRequest request)  {
        JSONObject obj=JSON.parseObject(param);
        long count=this.userService.checkUserName(obj.getString("loginname"));
        logger.info("检查"+obj.getString("loginname")+"用户名当前使用数为："+count);
        
        if(count>0)
        {
            JSONObject data=new JSONObject();
            data.put("tip", "用户名已被占用");
            return data;
        }else
        {
            JSONObject data=new JSONObject();
            data.put("tip", "");
            return data;
            
        }          
    }
    @RequestMapping(value = {"/register"},method = {RequestMethod.POST})
    @ResponseBody  
    public FmUser register(@RequestBody String param,HttpServletRequest request)  {
        JSONObject obj=JSON.parseObject(param);
        FmUser u=new FmUser();
        u.setLoginName(obj.getString("loginName"));
        u.setMobileNumber(obj.getString("mobileNumber"));
        u.setPassword(obj.getString("password"));
        u.setLastLoginDate(new Date());     
        u.setType("1");
        u.setValid("1");
        boolean flag=this.userService.register(u);
        if(flag) {
            
           
            
            return u;
        }else
        {
            JSONObject data=new JSONObject();
          
            return null;
        }       
    }
    
    @RequestMapping(value = {"/getpsnlmsg"},method = {RequestMethod.POST})
    @ResponseBody  
    public FmUser getpsnlmsg(HttpServletRequest request)  {         
        HttpSession session = request.getSession();         
        FmUser u=(FmUser) session.getAttribute("user");
        if(u!=null)
        {
        
            return u;
        }
        else
        {
            u=new FmUser();
            u.setId(-1l);
            return u;
        }
                
    }
    
    @RequestMapping(value = {"/update"},method = {RequestMethod.POST})
    @ResponseBody  
    public JSONObject update(@RequestBody String param,HttpServletRequest request)  {
        JSONObject obj=JSON.parseObject(param);
        FmUser record=new FmUser();
        record.setId(Long.parseLong(obj.getString("id")));
        record.setLoginName(obj.getString("loginName"));
        record.setMobileNumber(obj.getString("mobileNumber"));
        record.setPassword(obj.getString("password"));              
        record.setType(obj.getString("type"));
        record.setValid(obj.getString("valid"));
        boolean flag=this.userService.update(record);
        
        
        if(flag) {
            logger.info("数据库中用户信息更新完成");
            JSONObject data=new JSONObject();
            data.put("tip", "成功");          
            //更新session中的user内容
            HttpSession session=request.getSession(false);
            FmUser current=(FmUser) session.getAttribute("user");//session set user 
            if(current.getId()==record.getId())
            {
                FmUser u=this.userService.getUser(obj.getString("loginName"),obj.getString("password"));
                session.setAttribute("user", u);
                logger.info("session中用户信息更新");
            }               
            return data;
        }else
        {
            logger.info("数据库中用户信息更新失败");                        
            return null;
        }           
    }
}
