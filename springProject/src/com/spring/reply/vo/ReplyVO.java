package com.spring.reply.vo;

public class ReplyVO {
	
	private String r_num;
	private String b_num;
	private String r_name;
	private String r_content;
	private String r_pwd;
	private String r_date;
	
	public ReplyVO() {}
	
	public ReplyVO(String r_num,
				   String b_num,
				   String r_name,
				   String r_content,
				   String r_pwd,
				   String r_delyn,
				   String r_date) {
		this.r_num=r_num;
		this.b_num=b_num;
		this.r_name=r_name;
		this.r_content=r_content;
		this.r_pwd=r_pwd;
		this.r_date=r_date;
	}

	public String getR_num() {
		return r_num;
	}

	public void setR_num(String r_num) {
		this.r_num = r_num;
	}

	public String getB_num() {
		return b_num;
	}

	public void setB_num(String b_num) {
		this.b_num = b_num;
	}

	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public String getR_content() {
		return r_content;
	}

	public void setR_content(String r_content) {
		this.r_content = r_content;
	}

	public String getR_pwd() {
		return r_pwd;
	}

	public void setR_pwd(String r_pwd) {
		this.r_pwd = r_pwd;
	}

	public String getR_date() {
		return r_date;
	}

	public void setR_date(String r_date) {
		this.r_date = r_date;
	}
	
	
}
