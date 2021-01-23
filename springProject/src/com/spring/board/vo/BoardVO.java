package com.spring.board.vo;

import org.springframework.web.multipart.MultipartFile;

import com.spring.common.vo.CommonVO;

public class BoardVO extends CommonVO {

	private String b_num;			// 글번호
	private String b_name;			// 작성자
	private String b_title;			// 제목
	private String b_content;		// 내용
	private String b_pwd;			// 패스워드
	private MultipartFile file;		// 첨부파일
	private String b_file;			// 첨부파일 : 실제 서버에 저장한 파일명
	private String b_delyn;			// 삭제여부
	private String b_insertdate;	// 입력일
	private String b_updatedate;	// 변경일

	
	public BoardVO() {}
	
	public BoardVO(String b_num,
				   String b_name,
				   String b_title,
				   String b_content,
				   String b_pwd,
				   String b_file,
				   String b_delyn,
				   String b_insertdate,
				   String b_updatedate) {
		this.b_num=b_num;
		this.b_name=b_name;
		this.b_title=b_title;
		this.b_content=b_content;
		this.b_pwd=b_pwd;
		this.b_file=b_file;
		this.b_delyn=b_delyn;
		this.b_insertdate=b_insertdate;
		this.b_updatedate=b_updatedate;
	}

	public String getB_num() {
		return b_num;
	}

	public void setB_num(String b_num) {
		this.b_num = b_num;
	}

	public String getB_name() {
		return b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

	public String getB_title() {
		return b_title;
	}

	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	public String getB_content() {
		return b_content;
	}

	public void setB_content(String b_content) {
		this.b_content = b_content;
	}

	public String getB_pwd() {
		return b_pwd;
	}

	public void setB_pwd(String b_pwd) {
		this.b_pwd = b_pwd;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getB_file() {
		return b_file;
	}

	public void setB_file(String b_file) {
		this.b_file = b_file;
	}

	public String getB_delyn() {
		return b_delyn;
	}

	public void setB_delyn(String b_delyn) {
		this.b_delyn = b_delyn;
	}

	public String getB_insertdate() {
		return b_insertdate;
	}

	public void setB_insertdate(String b_insertdate) {
		this.b_insertdate = b_insertdate;
	}

	public String getB_updatedate() {
		return b_updatedate;
	}

	public void setB_updatedate(String b_updatedate) {
		this.b_updatedate = b_updatedate;
	}
	
	
}
