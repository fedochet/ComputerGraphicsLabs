package org.anstreth.lab1;

/**
 * Created by roman on 03.09.2016.
 */

public class Lab1 {

    private float angle = 0;

    //    static private String[] tasks = {"Конус и шар", "Поворот", "Куб и сфера", "Текстура", "Морфинг"};

    static private TaskExecutor taskExecutor = new TaskExecutor("Lab 1");

    public static void main(String[] args) {
        System.out.println("It's #1 lab.");
        taskExecutor.start();
    }
}
