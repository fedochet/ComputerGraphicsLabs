package org.anstreth.lab1.tasks;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import org.anstreth.lab1.Lab1;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/**
 * Created by roman on 08.09.2016.
 */
abstract public class AbstractTask implements GLEventListener {
    protected GLU glu = new GLU();
    protected GLUT glut = new GLUT();

    abstract public String getName();

    public void start() {
        GLProfile glp = GLProfile.get(GLProfile.GL2);

        GLCapabilities caps = new GLCapabilities(glp);

        GLWindow glWindow = GLWindow.create(caps);
        glWindow.setTitle(getName());
        glWindow.addGLEventListener(this);
        glWindow.setSize(640, 480);
        glWindow.setVisible(true);

        glWindow.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent e) {
                System.exit(0);
            }
        });

        Animator animator = new Animator(glWindow);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glShadeModel(GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
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
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
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
    }
}
