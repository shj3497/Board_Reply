package com.spring.common.chaebun;

public abstract class ChaebunUtil {

	public static final String BIZ_GUBUN_B = "B"; // Board
	public static final String BIZ_GUBUN_R = "R"; // ReplyBoard
	
	// t = DateFormatUtil 에서의 Type
	// c = chaebun 쿼리의 결과
	public static String numPad(String t, String c) {
		
		for(int i=c.length(); i<4; i++) {
			c = "0" + c;
		}
		String ymd = DateFormatUtil.ymdFormats(t);
		
		return ymd.concat(c);
	}
	
	public static String getBoardChaebun(String type, String chaebun) {
		return BIZ_GUBUN_B.concat(ChaebunUtil.numPad(type, chaebun));
	}
	
	public static String getBoardChaebun_r(String type, String chaebun) {
		return BIZ_GUBUN_R.concat(ChaebunUtil.numPad(type, chaebun));
	}
}
