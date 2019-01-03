package cn.cs.fileManager.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.dao.model.FmFile;
@RestController
public class DownloadController {

	
	 @RequestMapping(value= {"/download"},method= {RequestMethod.POST})
	public JSONObject downloadFile(HttpServletResponse response,@RequestBody String canshu,@RequestBody MultipartFile fuleUpload) throws IOException {
		 JSONObject ccan=JSON.parseObject(canshu);
		 String fileName = ccan.getString("filename");
		 String filetruename = ccan.getString("filetruename");
		 System.out.println(fileName);
		 System.out.println(filetruename);
		 
	  if (fileName != null) {
	    //设置文件路径
	    String realPath = ccan.getString("basedir");
	    System.out.println(realPath);
	    File file = new File(realPath , fileName);
	    if (file.exists()) {
		      System.out.println("2");
	      response.setContentType("application/force-download");// 设置强制下载不打开
	      response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
	      response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode(filetruename,"UTF-8"));
	      response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode(realPath,"UTF-8"));
	      byte[] buffer = new byte[1024];
	      FileInputStream fis = null;
	      BufferedInputStream bis = null;
	      try {
	        fis = new FileInputStream(file);
	        bis = new BufferedInputStream(fis);
	        OutputStream os = response.getOutputStream();
	        int i = bis.read(buffer);
	        System.out.println(i);
	        while (i != -1) {
	          os.write(buffer, 0, i);
	          i = bis.read(buffer);
	        }
	        System.out.println(i);
	        System.out.println("success");
	      } catch (Exception e) {
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
	  }
	  return null;
	}
}
