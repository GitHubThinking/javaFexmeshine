package com.fexmechine.model;

public class FexType {
	private int id; // ���
	private String sandNumber;//���ͷ�����
	private String recieveNumber;//���շ�����

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
	
	// ����λ��
	public void exchange(){
		String temp = sandNumber;
		sandNumber = recieveNumber;
		recieveNumber = temp;
	}
}
