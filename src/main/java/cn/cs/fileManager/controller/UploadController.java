package cn.cs.fileManager.controller;

import java.io.File;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;

import cn.cs.fileManager.dao.model.FmFolder;
import cn.cs.fileManager.service.IFolderService;
import cn.cs.fileManager.service.IUploadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "uploadController")
public class UploadController {
	@Autowired
	private IUploadService uploadService;
	@Autowired
	private IFolderService folderService;
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@ApiOperation(value = "上传文件", notes = "")
	@RequestMapping(value = { "/upload" }, method = { RequestMethod.POST }, produces = {
			"application/json;charset=utf-8" })
	public JSONObject upload(@RequestParam(value = "fileUpload", required = false) MultipartFile fileUpload,
			@RequestParam(value = "pid") long pid) {
		
		FmFolder fmFolder = this.folderService.getINfoOf(pid);
		String fileName = fileUpload.getOriginalFilename();

		

		// String suffixName;
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		long fileSize = fileUpload.getSize();
		fileSize = (int) fileSize / 1000;


		// 重新生成文件名
		String UUIDfileName = UUID.randomUUID() + suffixName;

		// 指定本地文件夹存储图片

		String filePath = fmFolder.getBaseDir() + "/";
		JSONObject result = new JSONObject();
		try {

			try {
				this.uploadService.getFileTypeList(suffixName);
			} catch (Exception e) {
				
				result.put("tip", "a");
				return result;
			}

			if (fileSize <= 10000) {
				
				fileUpload.transferTo(new File(filePath + UUIDfileName));
				uploadService.insert(fileName, fileSize, UUIDfileName, pid, fmFolder.getRegAccount());
				result.put("tip", "b");
				return result;
			} else {
				System.out.println("文件大小超过限制");
				result.put("tip", "c");
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}