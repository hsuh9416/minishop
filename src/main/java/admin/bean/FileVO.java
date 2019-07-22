package admin.bean;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Data;

@Data
public class FileVO {

	private String uploadUrl;

	private String fileName;

	private long fileSize;

	private MultipartFile attachFile;

	private List<CommonsMultipartFile> attachFiles;
	
	private List<Integer> _EVQSID;

	private List<String> _text;

	private List<String> _answer;

	@Override
	public String toString() {
		return "FileVO [uploadUrl=" + uploadUrl + ", fileName=" + fileName + ", fileSize=" + fileSize + ", attachFile="
				+ attachFile + ", attachFiles=" + attachFiles + ", _EVQSID=" + _EVQSID + ", _text=" + _text
				+ ", _answer=" + _answer + "]";
	}
}
