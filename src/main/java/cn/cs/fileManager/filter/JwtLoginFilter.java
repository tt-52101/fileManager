package cn.cs.fileManager.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.cs.fileManager.common.BlankUtil;
import cn.cs.fileManager.common.JwtTokenUtil;
import cn.cs.fileManager.common.ResponseUtil;
import cn.cs.fileManager.common.ResultUtil;
import cn.cs.fileManager.common.SpringUtils;
import cn.cs.fileManager.common.TimeUtil;
import cn.cs.fileManager.dao.mapper.FmUserMapper;
import cn.cs.fileManager.dao.model.FmUser;
import cn.cs.fileManager.dao.model.FmUserExample;
import cn.cs.fileManager.dao.model.FmUserExample.Criteria;
import cn.cs.fileManager.dto.FmUserDTO;
import cn.cs.fileManager.form.LoginForm;
import cn.cs.fileManager.form.LoginUser;
import cn.cs.fileManager.service.IUserService;
import cn.cs.fileManager.service.UserService;
import cn.cs.fileManager.vo.FmUserVO;




/**
 * @author: al89
 * @date: 2019/5/25
 * @description:处理登录请求
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter{
	 @Autowired
	    private JwtTokenUtil jwtTokenUtil;
	 @Autowired
	    private FmUserMapper fmUserMapper;

	 
	 //身份验证
	    private AuthenticationManager authenticationManager;
	    
	    private static final Logger logger=LoggerFactory.getLogger(JwtLoginFilter.class);

	    public JwtLoginFilter(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	    }

	    /**
	     * 请求登录
	     *
	     * @param request
	     * @param response
	     * @return
	     * @throws AuthenticationException
	     */
	    @Override
	    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
	        try {
	            LoginForm loginForm = new ObjectMapper().readValue(request.getInputStream(), LoginForm.class);
	           
	            LoginUser loginUser = new LoginUser();
	            //source ,target
	            //Copy the property values of the given source bean into the target bean. 
	            BeanUtils.copyProperties(loginForm, loginUser);
	            logger.info("用用户名和密码验证 "+loginUser.toString());
	            /**UsernamePasswordAuthenticationToken 参数说明：
	             * Object principal（主要的身份认证信息），Object credentials（用于证明principal是正确的信息，比如密码）
	             * */ 
	            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(JSON.toJSONString(loginUser), loginForm.getPassword(), new ArrayList<>()));
	        } catch (IOException e) {
	        	logger.info("数据读取错误");
	        	
	            ResponseUtil.write(response, ResultUtil.error("数据读取错误"));
	        }
	        return null;
	    }

	    /**
	     * 当登录成功后，生成一个token，并将token返回给客户端
	     *
	     * @param request
	     * @param response
	     * @param chain
	     * @param authResult
	     * @throws IOException
	     * @throws ServletException
	     */
	    @Override
	    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
	        FmUserDTO userDTO = (FmUserDTO) authResult.getPrincipal();
	        if (jwtTokenUtil == null) {
	            jwtTokenUtil = (JwtTokenUtil) SpringUtils.getBean("jwtTokenUtil");
	        }
	        if(fmUserMapper == null) {
	        	fmUserMapper = (FmUserMapper) SpringUtils.getBean("fmUserMapper");
	        }
	       
	      //更新最近一次登陆时间
	        FmUserExample fe=new FmUserExample();
	        Criteria criteria = fe.createCriteria();
	        criteria.andIdEqualTo(userDTO.getId());
	        FmUser user = new FmUser();
	        user.setLastLoginDate(new Date());
	        int result=fmUserMapper.updateByExampleSelective(user,fe);
	       
	        if(result>0)
	        {
	        	logger.info("update time correctly");
	        }
	        String token = jwtTokenUtil.createToken(userDTO);
	        //设置上下文
	        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDTO, null, userDTO.getAuthorities()));
	        //将token放置请求头返回
	        response.addHeader(jwtTokenUtil.getTokenHeader(), jwtTokenUtil.getTokenPrefix() + token);

	        ResponseUtil.write(response, ResultUtil.success());
	    }

	    /**
	     * 登录失败
	     *
	     * @param request
	     * @param response
	     * @param failed
	     * @throws IOException
	     * @throws ServletException
	     */
	    @Override
	    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
	        ResponseUtil.write(response, ResultUtil.error(failed.getMessage()));
	    }

	    /**
	     * 校验参数
	     *
	     * @param loginForm
	     */
	    private void checkLoginForm(LoginForm loginForm, HttpServletResponse response) {
	        if (BlankUtil.isBlank(loginForm.getPassword())) {
	            ResponseUtil.write(response, ResultUtil.error("密码不能为空"));
	            return;
	        }
	       
	    }

}
