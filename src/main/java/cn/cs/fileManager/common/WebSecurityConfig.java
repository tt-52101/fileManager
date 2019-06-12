package cn.cs.fileManager.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cn.cs.fileManager.filter.JwtAuthenticationFilter;
import cn.cs.fileManager.filter.JwtLoginFilter;
import cn.cs.fileManager.handler.AuthEntryPoint;
import cn.cs.fileManager.handler.RestAuthenticationAccessDeniedHandler;
import cn.cs.fileManager.service.FmUserServiceDetails;


/**
 * @author: al89
 * @date: 2019/5/25 
 * @description:security配置
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FmUserServiceDetails userService;
    
    @Override  
    public void configure(WebSecurity web) throws Exception {  
        // 设置不拦截规则  
        web.ignoring().antMatchers("/swagger-resources/**",
        		"/v2/api-docs",
                "/swagger-ui.html**" ,		                
                "/fm/register.html",
                "/fm/login.html",
                "/fm/res/**",
                "/fm/js/**",
                "/fm/image/**",
                "/fm/DataTables/**",
                "/fm/css/**",
                "/webjars/**",
                "/upload",
                "/register",//调试结束后删除
                "/checkloginname");  
  
    }  

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	//ExpressionInterceptUrlRegistry对象，这个对象就一个作用，注册intercept url规则权限匹配信息
    	//设置URL Matcher，antMatchers，mvcMatchers，regexMatchers或者直接设置一个一个或者多个RequestMatcher对象;
    	//上边设置matchers的方法会返回一个AuthorizedUrl对象，用于接着设置符合其规则的URL的权限信息，AuthorizedUrl对象提供了access方法用于设置一个权限表达式，
    	//比如说字符串“hasRole(‘ADMIN’) and hasRole(‘DBA’)”
    	//关闭默认打开的crsf protection
        http.cors().and().csrf().disable().authorizeRequests()
        		.antMatchers("/fm/allusers.html").hasRole("ADMIN")
                .antMatchers("/").hasRole("USER")
                .and()
                .formLogin()// 表单登录  来身份认证
                .loginPage("/fm/login.html")// 自定义登录页面  
                .and().headers().frameOptions().sameOrigin()
                .and()
                .addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .exceptionHandling().accessDeniedHandler(new RestAuthenticationAccessDeniedHandler())
                .authenticationEntryPoint(new AuthEntryPoint());
    }
    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
