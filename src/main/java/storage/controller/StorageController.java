package storage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/*
 * 서버 관련 제어를 수행하는 클래스
 */
@Controller
@RequestMapping(value="/storage/**")
public class StorageController {

	//1. 서버 이미지 호출하기
	@RequestMapping(value="/showProduct.do")
	@ResponseBody	
	public void showProduct(@RequestParam String product_name_image,HttpServletResponse response,HttpServletRequest request){
	
		int length =0; 
		int post; 
		String ext;
		String[] exts;
		
		String imgpath = request.getSession().getServletContext().getRealPath("/") + "storage\\onstore\\noImage.jpg";		
		String uploadPath = request.getSession().getServletContext().getRealPath("/")+"storage\\onstore\\";

		ServletOutputStream bout =null;
		FileInputStream fis =null;	
		
		  		//(1) 확장자명에 따라 response type 결정
			  	post = product_name_image.lastIndexOf(".");
			  	ext="";
			  	exts= new String[]{"jpg", "jpeg", "png", "gif", "bmp"};
		 if(post==-1) { 
			  for(String type:exts) {
				  	ext = type;
				  	product_name_image = product_name_image+"."+ext;
				  	
				  File fileImage = new File(uploadPath,product_name_image);
				  if(fileImage.exists()){
					    imgpath = uploadPath+product_name_image; 
					    response.setContentType("image/"+ext);
					   break;} 
				  else {fileImage.delete();}}}
		  else {
			  	ext = product_name_image.substring(post + 1).toLowerCase();
			  	response.setContentType("image/"+ext);
			  	
			  File fileImage = new File(uploadPath);
			   if(fileImage.exists()){
				    	imgpath = request.getSession().getServletContext().getRealPath("/") + "storage\\onstore\\"+ product_name_image;}}  	   		 
		
		 try {
			 	bout = response.getOutputStream();
			 	fis = new FileInputStream(imgpath);  		
			  byte[] buffer = new byte[10];
			  while ( ( length = fis.read( buffer ) ) != -1 ) 
				  bout.write( buffer, 0, length );} 
		 catch (IOException e) {e.printStackTrace();}
		 finally {
			try {fis.close();}
			catch (IOException e) {e.printStackTrace();}}	
	}
	
}
