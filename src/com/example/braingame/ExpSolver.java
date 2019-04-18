/******************************
 * ExpSolver.java
 * Class to solve the random expression
 * Author: Praveen Naresh
 */
package com.example.braingame;

import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * 
 * @author Praveen
 */
public class ExpSolver {

	private int answer;

	public ExpSolver() {

	}

	// function to solve the expression
	public int Solve(String exp) {
		Scanner input = new Scanner(exp);//stream string into the scanner
		answer = input.nextInt();//iterator through the scanner
		while (input.hasNext()) {//while there are terms
			String operator = input.next();//assigning the operator to the string
			int num = input.nextInt();//assigning numbers to int
			if (operator.equals("+")) {//addition
				answer += num;
			} else if (operator.equals("-")) {//subtraction
				answer -= num;
			} else if (operator.equals("*")) {//multiplication
				answer *= num;
			} else if (operator.equals("/")) {//division
				answer /= num;
			}
		}
		return answer;
	}
}
