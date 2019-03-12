package com.reserve;

public class ReserveDTO {
	private String reserveNum;
	private String memberNum;
	private String reserveName;
	private int reserveCount;
	private String reserveMemo;
	private String reserveDate;
	private String startDay;
	private String endDay;
	private int price;
	private String barbecue;
	private String bank;
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getBarbecue() {
		return barbecue;
	}
	public void setBarbecue(String barbecue) {
		this.barbecue = barbecue;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getReserveNum() {
		return reserveNum;
	}
	public void setReserveNum(String reserveNum) {
		this.reserveNum = reserveNum;
	}
	public String getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}
	public String getReserveName() {
		return reserveName;
	}
	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}
	public int getReserveCount() {
		return reserveCount;
	}
	public void setReserveCount(int reserveCount) {
		this.reserveCount = reserveCount;
	}
	public String getReserveMemo() {
		return reserveMemo;
	}
	public void setReserveMemo(String reserveMemo) {
		this.reserveMemo = reserveMemo;
	}
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
}
