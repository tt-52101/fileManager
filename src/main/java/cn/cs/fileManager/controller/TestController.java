package cn.cs.fileManager.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "test")
public class TestController {
	@Autowired
	
	@Value("${cn.cs.fileManager.rootFilePath}")
	private String path;
	private static final Logger logger=LoggerFactory.getLogger(TestController.class);
	
	
	@ApiOperation(value="传输文件", notes="返回是blob")
	@RequestMapping(value = { "/test" }, method = { RequestMethod.GET })
	 public void sendfile(HttpServletRequest request,HttpServletResponse response) {
		logger.info("enter function ");
		 File file=new File(path+"//lyuyu","这就是搜索引擎：核心技术详解.pdf");
		 logger.info("读取文件："+file.getAbsolutePath());
		 if(file.exists())
		 {
			 FileInputStream fis=null;
			 BufferedInputStream bis=null;
			 byte[] buffer=new byte[1024];
			 try {
				 fis = new FileInputStream(file);
                 bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
                 int i = bis.read(buffer);
                 while (i != -1) {
                     os.write(buffer, 0, i);
                     i = bis.read(buffer);
                 }
                 
			 }catch (Exception e) {
                 e.printStackTrace();
             } finally {
                 if (bis != null) {
                     try {
                         bis.close();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
                 if (fis != null) {
                     try {
                         fis.close();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
             }
          }
		 else {
			 logger.info("文件不存在");
		 }
	 }
}
