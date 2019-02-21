package com.txusballesteros.demo;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void aa(){
        String ruo = "(?=.{6,}).*";
        String zhong = "^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$";
        String qiang = "^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$";

        Scanner scanner = new Scanner(System.in);
        System.out.println("写一个java正则表达式，判断输入的密码强度，是数字弱搜索，是字母较弱，数字加字母强:");
        while (scanner.hasNextLine()) {
            String pwd = scanner.nextLine();
            if (pwd.matches(qiang)) {
                System.out.println("强密码");
            } else if (pwd.matches(zhong)) {
                System.out.println("中密码");
            } else if (pwd.matches(ruo)) {
                System.out.println("弱密码");
            } else {
                System.out.println("未知错误");
                scanner.close();
                break;
            }
        }

    }
}