package cn.cs.fileManager.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import cn.cs.fileManager.common.JwtTokenUtil;
import cn.cs.fileManager.common.SpringUtils;
import cn.cs.fileManager.dto.FmUserDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: al89
 * @date: 2019/4/8 15:28
 * @description:token 校验过滤器
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**从http头的Authorization 项读取token数据,然后用jwtTokenUtil （Jwts包提供的方法）校验token的合法性
     * 如果校验通过，就认为这是一个取得授权的合法请求
     * */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (jwtTokenUtil == null) {
            jwtTokenUtil = (JwtTokenUtil) SpringUtils.getBean("jwtTokenUtil");
        }
        //.getTokenHeader()
        String header = request.getHeader(jwtTokenUtil.getTokenHeader());
        //当token为空或格式错误时 直接放行
        if (header == null || !header.startsWith(jwtTokenUtil.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * 这里从token中获取用户信息并新建一个token
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String header) {

        String token = header.replace(jwtTokenUtil.getTokenPrefix(), "");
        String principal = jwtTokenUtil.getUserName(token);
        if (principal != null) {
            FmUserDTO userDTO = jwtTokenUtil.getUserDTO(token);
            return new UsernamePasswordAuthenticationToken(principal, null, userDTO.getAuthorities());
        }
        return null;
    }
}
