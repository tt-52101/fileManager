package cn.cs.fileManager.handler;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import cn.cs.fileManager.common.ResponseUtil;
import cn.cs.fileManager.common.ResultUtil;
import cn.cs.fileManager.enums.ResultEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Lan
 * @date: 2019/4/9 10:11
 * @description:权限认证异常处理器
 */
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.write(response, ResultUtil.error(ResultEnum.TOKEN_IS_NOT_VALID));
    }
}
