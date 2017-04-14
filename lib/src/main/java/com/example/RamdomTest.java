package com.example;

/**
 * Created by 肖稳华 on 2017/3/25.
 */

public class RamdomTest {
	public static void main(String[] args) {
		java.util.Random r=new java.util.Random();
		for(int i=0;i<10;i++){
			System.out.println(r.nextInt(10));
		}
	}
}
