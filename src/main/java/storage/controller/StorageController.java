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
		String ext=null;
		String exts[]= {"jpg", "jpeg", "png", "gif", "bmp"};
		  //찾는 이미지가 없을 때 처리
		 String imgpath = request.getSession().getServletContext().getRealPath("/") + "storage\\onstore\\noImage.jpg";		
		 String uploadPath = request.getSession().getServletContext().getRealPath("/")+"storage\\onstore\\";
		  //System.out.println(uploadPath);	

		  //확장자명에 따라 response type 결정
		  int post = product_name_image.lastIndexOf(".");
		  if(post==-1) {//productID처럼 확장자가 없는 경우
			  for(String type:exts) {
				  ext = type;
				  product_name_image = product_name_image+"."+ext;
				  File fileImage = new File(uploadPath,product_name_image);//생성
				   if(fileImage.exists()){
					    imgpath = uploadPath+product_name_image; 
					    response.setContentType("image/"+ext);
					    break;
					   } 
				   else {fileImage.delete();}//지움
			  }
		  }
		  else {
			  ext = product_name_image.substring(post + 1).toLowerCase();//확장자명
			  response.setContentType("image/"+ext);
			  File fileImage = new File(uploadPath);
			   if(fileImage.exists()){
				    imgpath = request.getSession().getServletContext().getRealPath("/") + "storage\\onstore\\"+ product_name_image; 
				   } 
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
