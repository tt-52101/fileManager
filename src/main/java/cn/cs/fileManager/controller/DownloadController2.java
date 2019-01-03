package cn.cs.fileManager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.dao.model.FmFile;
import cn.cs.fileManager.dao.model.FmFolder;
import cn.cs.fileManager.service.IFileService;
import cn.cs.fileManager.service.IFolderService;
import cn.cs.fileManager.service.UserService;
@Controller
public class DownloadController2 {

	@Autowired
	 private IFolderService folderservice;
	 private static final Logger logger=LoggerFactory.getLogger(DownloadController2.class);
    @RequestMapping(value= {"/download2"})
    @ResponseBody
    public void  download(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

		 String fileName = request.getParameter("fileName");
		 String filetruename = request.getParameter("filetruename");
		 String fid = request.getParameter("fid");


		    String owner="zz";
		    long id=Long.parseLong(fid);
	        List<FmFolder> list = this.folderservice.getDangqian(owner,id);
	        String realpath=list.get(0).getBaseDir();
		 
		 
	  if (fileName != null) {
	    //设置文件路径
		  logger.info("文件名"+fileName);
		  logger.info("文件真实名"+filetruename);
	    //String realPath = "C:\\Users\\zhendong\\Desktop";
	    File file = new File(realpath , fileName);
	    if (file.exists()) {
	    	logger.info("文件真实名路径无误");
	      response.setContentType("application/force-download");// 设置强制下载不打开
	      response.addHeader("Content-Disposition", "attachment;fileName=" + filetruename);// 设置文件名
	      response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filetruename, "UTF-8"));
	      byte[] buffer = new byte[1024];
	      FileInputStream fis = null;
	      BufferedInputStream bis = null;
	      try {
	        fis = new FileInputStream(file);
	        bis = new BufferedInputStream(fis);
	        OutputStream os = response.getOutputStream();
	        int i = bis.read(buffer);
	        while (i != -1) {
	          os.write(buffer, 0, i);
	          i = bis.read(buffer);
	        }
	        
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

	}
}



