package cn.cs.fileManager.handler;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import cn.cs.fileManager.common.ResponseUtil;
import cn.cs.fileManager.common.ResultUtil;
import cn.cs.fileManager.enums.ResultEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: al89
 * @date: 2019/4/8 18:25
 * @description:权限不足
 */
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtil.write(response, ResultUtil.error(ResultEnum.ACCESS_NOT));
    }
}
