package com.special;

import java.util.Map;

public class SpecialDTO {
	private int listNum;
	private int specialNum;
	private String userId;
	private String specialSubject;
	private String specialContent;
	private String specialDate;
	private String specialStart;
	private String specialEnd;
	private int specialCount;
	
	private int fileNum; // specialFile_seq
	private String imageFileName;
	
	private Map<String, Long> imageMap;
	
	private long gap;
	
	public Map<String, Long> getImageMap() {
		return imageMap;
	}
	public void setImageMap(Map<String, Long> imageMap) {
		this.imageMap = imageMap;
	}
	
	public long getGap() {
		return gap;
	}
	public void setGap(long gap) {
		this.gap = gap;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getSpecialNum() {
		return specialNum;
	}
	public void setSpecialNum(int specialNum) {
		this.specialNum = specialNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSpecialSubject() {
		return specialSubject;
	}
	public void setSpecialSubject(String specialSubject) {
		this.specialSubject = specialSubject;
	}
	public String getSpecialContent() {
		return specialContent;
	}
	public void setSpecialContent(String specialContent) {
		this.specialContent = specialContent;
	}
	public String getSpecialDate() {
		return specialDate;
	}
	public void setSpecialDate(String specialDate) {
		this.specialDate = specialDate;
	}
	public String getSpecialStart() {
		return specialStart;
	}
	public void setSpecialStart(String specialStart) {
		this.specialStart = specialStart;
	}
	public String getSpecialEnd() {
		return specialEnd;
	}
	public void setSpecialEnd(String specialEnd) {
		this.specialEnd = specialEnd;
	}
	public int getSpecialCount() {
		return specialCount;
	}
	public void setSpecialCount(int specialCount) {
		this.specialCount = specialCount;
	}
	public int getFileNum() {
		return fileNum;
	}
	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
}
