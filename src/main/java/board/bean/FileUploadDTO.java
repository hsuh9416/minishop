package board.bean;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadDTO {
 private String attach_path;
 private String Filename;
 private MultipartFile upload;
 private String CKEditorFuncNum;
}
