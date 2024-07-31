/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author MSI
 */
public class a {

    public static void main(String[] args) {

        DateTimeFormatter dttf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dttf.format(now);

        String[] datepart = date.split("/");
        String imonth = datepart[1].toString();
        System.out.println(imonth);

    }

}
