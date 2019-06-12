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
import io.swagger.annotations.Api;
@Api(value = "download Controller")
@Controller
public class DownloadController {

	@Autowired
	private IFolderService folderservice;
	@Autowired
	private IFileService fileservice;

	@RequestMapping(value = { "/download" })
	@ResponseBody
	public void download(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		FmFile fmFile = new FmFile();
		fmFile = this.fileservice.getINfoOf(Long.parseLong(id));
		String fileName = fmFile.getFileName();
		String filetruename = fmFile.getFileTrueName();
		FmFolder fmFolder = this.folderservice.getINfoOf(fmFile.getFolderId());
		String realpath = fmFolder.getBaseDir();
		if (fileName != null) {
			// 设置文件路径
			File file = new File(realpath, fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + filetruename);// 设置文件名
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(filetruename, "UTF-8"));
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



