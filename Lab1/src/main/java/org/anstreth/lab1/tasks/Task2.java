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
        super.display(drawable);
        GL2 gl = drawable.getGL().getGL2();

        gl.glColor3f(0,1,0);
        GLUquadric quad = glu.gluNewQuadric();
        gl.glPushMatrix();
        gl.glTranslatef(10, 10, 0);
        glut.glutWireCube(5);
        gl.glPopMatrix();
        glu.gluDeleteQuadric(quad);
    }
}
