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

@Controller
@RequestMapping(value="/storage/**")
public class StorageController {//서버에 저장된 파일 등을 불러오는 콘트롤러

	//이미지 뿌리기
	@RequestMapping(value="/showProduct.do")
	@ResponseBody	
	public String showProduct(@RequestParam String product_name_image,HttpServletResponse response,HttpServletRequest request){
		ServletOutputStream bout =null;
		FileInputStream fis =null;		
		int length =0; 
		
		String uploadPath = request.getSession().getServletContext().getRealPath("/")+"\\storage\\onstore\\"+product_name_image;
		  System.out.println(uploadPath);	
		  //찾는 이미지가 없을 때 처리
		  String imgpath = request.getSession().getServletContext().getRealPath("/") + "\\storage\\onstore\\nophoto.jpg";
		  //확장자명에 따라 response type 결정
		  int post = product_name_image.lastIndexOf(".");
		  String ext = product_name_image.substring(post + 1).toLowerCase();//확장자명 
		  response.setContentType("image/"+ext); 
		   File fileImage = new File(uploadPath);
		   if(fileImage.exists()){
		    imgpath = request.getSession().getServletContext().getRealPath("/") + "\\storage\\onstore\\"+ product_name_image; 
		   } 
		  
		 
		try {
			bout = response.getOutputStream();
			  fis = new FileInputStream(imgpath);  		
			  byte[] buffer = new byte[10];
			  while ( ( length = fis.read( buffer ) ) != -1 ) 
			  bout.write( buffer, 0, length );  
		} catch (IOException e) {e.printStackTrace();}
		finally {
			try {fis.close();}
			catch (IOException e) {e.printStackTrace();}
		}	
		  return null;
	}
}
