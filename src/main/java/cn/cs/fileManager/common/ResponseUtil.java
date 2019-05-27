package cn.cs.fileManager.common;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.extern.slf4j.Slf4j;

/** @return The category of the constructed Logger. By default, it will use the type where the annotation is placed. */
@Slf4j
public class ResponseUtil {
	 public static void write(HttpServletResponse response, Object o) {
	        try {
	            response.setContentType("application/json; charset=utf-8");
	            PrintWriter out = response.getWriter();
	            //json返回
	            out.println(JSON.toJSONString(o, SerializerFeature.WriteMapNullValue));
	            out.flush();
	            out.close();
	        } catch (Exception e) {
	            //log.error("e={}", e);
	        }
	    }

}
