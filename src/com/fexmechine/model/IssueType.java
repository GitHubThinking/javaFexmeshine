package com.fexmechine.model;

import java.util.Comparator;
import java.util.Vector;

public class IssueType {
	private String id; // ���
	private String question;
	private Vector answer = new Vector();
	private String true_ans;
	
	public String getTrue_ans() {
		return true_ans;
	}
	public void setTrue_ans(String true_ans) {
		this.true_ans = true_ans;
	}
	public Vector getAnswer() {
		return answer;
	}
	public void setAnswer(Vector answer) {
		this.answer = answer;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	// ��Ӵ�
	public void addAnswer(String str){
		answer.add(str);
	}
	
    // ���������
	@SuppressWarnings("unchecked")
	public void randomsort(){
		MyCompare mycompare = new MyCompare();
		answer.sort(mycompare);
	}
}

class MyCompare implements Comparator //ʵ��Comparator�������Լ��ıȽϷ���
{ 
	public int compare(Object o1, Object o2) { 
		double flag = Math.random()-0.5;
		if (flag<0)// �����Ƚ��ǽ���,�����-1�ĳ�1��������.
		{
			return 1;
		} else if ( flag>0 ) {
			return -1;
		} else {
			return 0;
		}
	}
}