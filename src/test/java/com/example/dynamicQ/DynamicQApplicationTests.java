package com.example.dynamicQ;

import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dynamicQ.contents.QuesType;

@SpringBootTest
class DynamicQApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(QuesType.SINGLE.getType());
		System.out.println(QuesType.MULTI.getType());
		System.out.println(QuesType.TEXT.getType());
		String str = "文本問題";
		System.out.println(str.equalsIgnoreCase(QuesType.TEXT.getType()));
	}
	
	@Test
	public void errorTest() {
		try {
			int x = 5;
			int y = 0;
			System.out.println(x/y);
		}catch(Exception e) {//系統出現Exception類別異常時，會跑這行
			System.out.println("error");
		}
	}
	
	@Test
	public void errorTest2() {
		Scanner scan = new Scanner(System.in);
		String str = scan.next();
		try(Scanner scan1 = new Scanner(System.in)) {

		}catch(Exception e) {

		}
	}

}
