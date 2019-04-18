/*****************************
 * Expression.java
 * Class used to generate the random expression
 * Author: Praveen Naresh
 */
package com.example.braingame;

import java.util.Random;

import android.widget.Toast;

public class Expression {


	final String length1 = "23";
	private int eterms;
	private int num;
	private String expression;
	Random rand = new Random();//calling the random function

	//default constructor
	public Expression() {
		num = 0;
		expression = "";
	}
	//function to get a random number
	public int getNum() {
		num = rand.nextInt(9) + 1;
		return num;
	}
	//function to set and get expression based on the difficulty
	public String getEx(int difficulty) {
		switch (difficulty) {
		case 0://novice difficulty
			//setting the expression
			expression = getNum() + " " + getOper() + " " + getNum();
			return expression;
		case 1://easy difficulty
			eterms = getEasyTerm();
			expression = "";
			for (int i = 0; i < eterms; i++) {
				if (i == eterms - 1) {//check if expression doesn't finish with operator
					expression += " " + getNum();
				} else {
					expression += " " + getNum() + " " + getOper();
				}
			}
			return expression;

		case 2://medium difficulty
			int mterms = getMediumTerm();
			expression = "";
			for (int i = 0; i < mterms; i++) {
				if (i == mterms - 1) {//check if expression doesn't finish with operator
					expression = expression + " " + getNum();
				} else {
					expression = expression + " " + getNum() + " " + getOper();
				}
			}
			return expression;
		case 3://guru difficulty
			System.out.println("guru is called");
			int gterms = getGuruTerm();
			expression = "";
			for (int i = 0; i < gterms; i++) {
				if (i == gterms - 1) {//check if expression doesn't finish with operator
					expression = expression + " " + getNum();
				} else {
					expression = expression + " " + getNum() + " " + getOper();
				}
			}
			return expression;
		}
		return expression;
	}
	//getting a random number of terms for easy
	public int getEasyTerm() {
		int[] term = { 2, 3 };
		int ranTerm = rand.nextInt(2);
		return term[ranTerm];
	}
	//getting a random number of terms for medium
	public int getMediumTerm() {
		int[] term = { 2, 3, 4 };
		int ranTerm = rand.nextInt(3);
		return term[ranTerm];
	}
	//getting a random number of terms for guru
	public int getGuruTerm() {
		int[] term = { 4, 5, 6 };
		int ranTerm = rand.nextInt(3);
		System.out.println("bla"+term[ranTerm]);
		return term[ranTerm];
	}
	//getting a random operator
	public String getOper() {
		String[] operator = { "+", "-", "*", "/" };
		int randOp = rand.nextInt(4);
		return operator[randOp];
	}
}
