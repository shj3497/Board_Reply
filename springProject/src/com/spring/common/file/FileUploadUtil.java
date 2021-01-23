package com.spring.common.file;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	static Logger logger = Logger.getLogger(FileUploadUtil.class);
			
	// 파일 업로드 메소드
	public static String fileUpload(MultipartFile file, HttpServletRequest request) throws IOException{
		
		logger.info("FileUploadUtil fileUpload() 호출 성공");
		String real_name = null;
		
		// MultipartFile 클래스의 getFile() 메소드로 클라이언트가 업로드한 파일명
		String org_name = file.getOriginalFilename();
		logger.info("org_name >>> : " + org_name);
		
		// 파일명 변경(중복되지 않게)
		if(org_name != null && (!org_name.equals(""))) {
			real_name = "board" + System.currentTimeMillis() + "_" + org_name;
			
			String docRoot = request.getSession().getServletContext().getRealPath("/uploadStorage");
			
			File fileDir = new File(docRoot);
			if(!fileDir.exists()) {
				fileDir.mkdir();
			}
			
			File fileAdd = new File(docRoot + "/" + real_name);
			logger.info("fileAdd >>> : " + fileAdd);
			
			file.transferTo(fileAdd);
		}
		
		return real_name;
	}// end of fileUpload()
	
	// 파일 삭제 메소드
	public static void fileDelete(String fileName, HttpServletRequest request) throws IOException {
		
		logger.info("fileUploadUtil fileDelete() 호출 성공");
		boolean result = false;
		String docRoot = request.getSession().getServletContext().getRealPath("/uploadStorage");
		
		File fileDelete = new File(docRoot + "/" + fileName);
		logger.info("fileDelete >>> : " + fileDelete);
		
		if(fileDelete.exists() && fileDelete.isFile()) {
			result = fileDelete.delete();
			// 흠.. 나는 현재까지 db에 경로만을 이용해서 업로드만 했었는데
			// 여기서는 실제 파일을 삭제까지하네
		}
		
		logger.info("result >>> : " + result);
	}// end of fileDelete()
}// end of fileUploadUtil.class
