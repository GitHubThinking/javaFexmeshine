package com.fexmechine.model;

import java.util.Comparator;
import java.util.Vector;

public class IssueType {
	private String id; // 编号
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
	// 添加答案
	public void addAnswer(String str){
		answer.add(str);
	}
	
    // 答案随机排序
	@SuppressWarnings("unchecked")
	public void randomsort(){
		MyCompare mycompare = new MyCompare();
		answer.sort(mycompare);
	}
}

class MyCompare implements Comparator //实现Comparator，定义自己的比较方法
{ 
	public int compare(Object o1, Object o2) { 
		double flag = Math.random()-0.5;
		if (flag<0)// 这样比较是降序,如果把-1改成1就是升序.
		{
			return 1;
		} else if ( flag>0 ) {
			return -1;
		} else {
			return 0;
		}
	}
}