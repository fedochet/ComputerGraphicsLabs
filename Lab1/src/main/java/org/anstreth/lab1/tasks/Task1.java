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

    private float angle = 0;

    @Override
    public void display(GLAutoDrawable drawable) {
        super.display(drawable);

        GL2 gl2 = drawable.getGL().getGL2();
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

    @Override
    public String getName() {
        return "Sphere and cone";
    }
}
