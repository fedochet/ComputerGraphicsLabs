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

public class Lab1 implements GLEventListener {

    public static void main(String[] args) {
        GLProfile glp = GLProfile.get(GLProfile.GL2);

        GLCapabilities caps = new GLCapabilities(glp);

        GLWindow glWindow = GLWindow.create(caps);
        glWindow.setTitle("Test");
        glWindow.addGLEventListener(new Lab1());
        glWindow.setSize(960, 480);
        glWindow.setVisible(true);

        glWindow.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent e) {
                System.exit(0);
            }
        });

        Animator animator = new Animator(glWindow);
        animator.start();

    }

    GLU glu = new GLU();
    GLUT glut = new GLUT();

    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glShadeModel(GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    public void dispose(GLAutoDrawable drawable) {

    }

    public void display(GLAutoDrawable drawable) {

        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glShadeModel(GL2.GL_SMOOTH);
        gl2.glClearColor(0f, 0f, 0f, 0f);
        gl2.glClearDepth(1.0f);
        gl2.glEnable(GL_DEPTH_TEST);
        gl2.glDepthFunc(GL_LEQUAL);
        gl2.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        gl2.glClearColor(0, 0, 0, 1);
        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

//        gl2.glMatrixMode(GL_MODELVIEW);
////        gl2.glLoadIdentity();
////        glu.gluLookAt(10, 10, 10, 0, 0, 0, 0, 0, 0);
//
////        gl2.glMatrixMode(GL_MODELVIEW);
////        gl2.glLoadIdentity();
////        glu.gluLookAt(angle, angle, angle, 0, 0, 0, 0, 1.0, 0);

//        gl2.glMatrixMode(GL_PROJECTION);
//        gl2.glLoadIdentity();
//        gl2.glRotatef(angle, 0f, 0f, 0f);

        gl2.glPushMatrix();
        GLUquadric sphereQuadric = glu.gluNewQuadric();

        gl2.glMatrixMode(GL_MODELVIEW);
        gl2.glRotatef(-45, 1, 0, 0);

        gl2.glColor3f(1, 0, 0);

        glu.gluQuadricDrawStyle(sphereQuadric, GLU.GLU_LINE);
        glu.gluSphere(sphereQuadric, 2, 20, 20);

        gl2.glPushMatrix();
        gl2.glTranslatef(5, 0, -2);
        glu.gluCylinder(sphereQuadric, 2, 0, 4, 20, 20);

        glu.gluDeleteQuadric(sphereQuadric);
        gl2.glPopMatrix();
        gl2.glPopMatrix();

    }

    public static void glDraw(GL2 gl, int mode, Consumer<GL2> consumer) {
        gl.glBegin(mode);

        consumer.accept(gl);

        gl.glEnd();
        gl.glFlush();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glViewport(0, 0, w, h);

        final float hh = (float) w / (float) h;
        double mul = 0.01;

        gl2.glMatrixMode(GL_MODELVIEW);
        gl2.glLoadIdentity();
        glu.gluLookAt(0, 10, 10, 0, 0, 0, 0, 10, 0);

        gl2.glMatrixMode(GL_PROJECTION);
        gl2.glLoadIdentity();
        gl2.glOrtho(-w * mul, w * mul, -h * mul, h * mul, 0, 100);
//        glu.gluPerspective(130, hh, 50, 0);
    }
}
