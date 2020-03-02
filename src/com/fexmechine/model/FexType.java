package com.fexmechine.model;

public class FexType {
	private int id; // 编号
	private String sandNumber;//发送方号码
	private String recieveNumber;//接收方号码

	public FexType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FexType(int id,String sandNumber, String recieveNumber) {
		super();
		this.id = id;
		this.sandNumber = sandNumber;
		this.recieveNumber = recieveNumber;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSandNumber() {
		return sandNumber;
	}
	public void setSandNumber(String sandNumber) {
		this.sandNumber = sandNumber;
	}
	public String getRecieveNumber() {
		return recieveNumber;
	}
	public void setRecieveNumber(String recieveNumber) {
		this.recieveNumber = recieveNumber;
	}
	
	// 交换位置
	public void exchange(){
		String temp = sandNumber;
		sandNumber = recieveNumber;
		recieveNumber = temp;
	}
}
