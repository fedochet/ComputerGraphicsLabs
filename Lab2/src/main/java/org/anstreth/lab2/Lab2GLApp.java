package org.anstreth.lab2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import org.anstreth.common.AbstractOpenGLApp;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;

/**
 * Created by roman on 05.10.2016.
 */
public class Lab2GLApp extends AbstractOpenGLApp {
    public Lab2GLApp() {
        super("Lab 2");
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        super.display(drawable);
        GL2 gl2 = drawable.getGL().getGL2();

//        gl2.glEnable(GL_BLEND);
//        gl2.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        float[] material_diffuse = new float[]{1.0f, 1.0f, 1.0f, 0.0f};
        gl2.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, material_diffuse, 0);

        gl2.glEnable(GL_LIGHT0);
        gl2.glLightfv(GL_LIGHT0, GL_AMBIENT, new float[]{1f,1f,1f,0.0f}, 0);
        gl2.glLightfv(GL_LIGHT0, GL_DIFFUSE, new float[]{0.4f, 0.7f, 0.2f, 1f}, 0);

        gl2.glColor4f(1,0,0, 0.1f);
        glut.glutSolidTorus(10, 30, 100, 100);
    }

}
