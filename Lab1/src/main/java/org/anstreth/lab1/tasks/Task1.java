package org.anstreth.lab1.tasks;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import java.util.function.Consumer;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/**
 * Created by roman on 08.09.2016.
 */
public class Task1 extends AbstractTask {
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

    private float angle = 0;

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

        gl2.glColor3f(1, 0, 0);

        GLUquadric sphereQuadric = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(sphereQuadric, GLU.GLU_LINE);
        gl2.glPushMatrix();
//        gl2.glMatrixMode(GL_MODELVIEW);
//        gl2.glRotatef(angle, 0,0,1);
        gl2.glTranslatef(10, 10, 0);

        int slices = 10;
        int stacks = 10;
        glu.gluCylinder(sphereQuadric, 10, 0, 15, slices, stacks);

        gl2.glTranslatef(0, 0, 15);
        glu.gluSphere(sphereQuadric, 5, slices, stacks);
        gl2.glPopMatrix();

        glu.gluDeleteQuadric(sphereQuadric);

        angle += 0.1;
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

        gl2.glMatrixMode(GL_PROJECTION);
        gl2.glLoadIdentity();
        gl2.glOrtho(-40, 40, -40/hh, 40/hh, -100, 100);
        glu.gluLookAt(0, 0, 10, 10,10,0, 0,0,1);
        gl2.glMatrixMode(GL_MODELVIEW);

    }

    @Override
    public String getName() {
        return "Sphere and cone";
    }
}
