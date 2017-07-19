package com.itao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itao.common.utils.FastDFSClient;
import com.itao.common.utils.JsonUtils;
import com.itao.service.ItemService;

@Controller
public class PictureController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@Autowired
	public ItemService itemService;
	
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile){
		try {
			//把图片上传到图片服务器
			FastDFSClient client=new FastDFSClient("classpath:conf/client.conf");
			//取文件扩展名
			String originalFileName=uploadFile.getOriginalFilename();
			String extName=originalFileName.substring(originalFileName.lastIndexOf('.')+1);
			//得到一个图片的地址和文件名
			String url=client.uploadFile(uploadFile.getBytes());
			//补充为完整url
			url=IMAGE_SERVER_URL+url;
			
			//封装到Map
			Map result=new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			// TODO: handle exception
			Map result=new HashMap<>();
			result.put("error", 1);
			result.put("message", "upload error");
			return JsonUtils.objectToJson(result);
		}
	}
}
