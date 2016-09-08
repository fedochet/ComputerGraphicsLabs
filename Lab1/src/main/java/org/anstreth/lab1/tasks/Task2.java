package org.anstreth.lab1.tasks;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLUquadric;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;

/**
 * Created by roman on 09.09.2016.
 */
public class Task2 extends AbstractTask {
    @Override
    public String getName() {
        return "Rotating";
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glShadeModel(GL2.GL_SMOOTH);
        gl2.glClearColor(0f, 0f, 0f, 0f);
        gl2.glClearDepth(1.0f);
        gl2.glEnable(GL_DEPTH_TEST);
        gl2.glDepthFunc(GL_LEQUAL);
        gl2.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        gl2.glClearColor(0, 0, 0, 1);
        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        gl.glColor3f(0,1,0);
        GLUquadric quad = glu.gluNewQuadric();
        gl.glPushMatrix();
        gl.glTranslatef(10, 10, 0);
        glut.glutWireCube(5);
        gl.glPopMatrix();
        glu.gluDeleteQuadric(quad);
    }
}
