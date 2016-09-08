package org.anstreth.lab1;

import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import org.anstreth.lab1.tasks.AbstractTask;
import org.anstreth.lab1.tasks.Task1;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Consumer;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.GL2GL3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/**
 * Created by roman on 03.09.2016.
 */

public class Lab1 {

    private float angle = 0;

//    static private String[] tasks = {"Конус и шар", "Поворот", "Куб и сфера", "Текстура", "Морфинг"};
    static private AbstractTask[] tasks = {new Task1()};

    public static void main(String[] args) {
        System.out.println("It's #1 lab. Choose task to demonstrate.");
        for (int i = 0; i < tasks.length; i++) {
            System.out.println((i+1) + ". " + tasks[i].getName());
        }

        Scanner scanner = new Scanner(System.in);

        int n = -1;

        while (true) {
            try {
                n = scanner.nextInt() - 1;
                if (n < 0 || n >= tasks.length)
                    throw new IllegalArgumentException();
                else break;
            } catch (InputMismatchException e) {
                System.out.println("Input error! Try again.");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println("Wront task number! Try again.");
            }
        }

        System.out.printf("Task #%d has been chosen for demonstration.", n+1);

        tasks[n].start();
    }
}
