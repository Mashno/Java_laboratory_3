/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.java_laboratory_3;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Владислав
 */
public class Java_laboratory_3 {

    public static void main(String[] args) {
        try {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.setErr(new PrintStream(System.err, true, "UTF-8"));
        new Controller();
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    }
}
