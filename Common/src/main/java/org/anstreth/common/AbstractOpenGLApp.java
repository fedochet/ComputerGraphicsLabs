package org.anstreth.common;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL2ES1.GL_LIGHT_MODEL_TWO_SIDE;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/**
 * Created by roman on 05.10.2016.
 */
public abstract class AbstractOpenGLApp implements GLEventListener {
    protected GLU glu = new GLU();
    protected GLUT glut = new GLUT();

    private final String name;

    public AbstractOpenGLApp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public GLWindow getGlWindow() {
        return glWindow;
    }

    private GLWindow glWindow;

    public void start() {
        GLProfile glp = GLProfile.get(GLProfile.GL2);

        GLCapabilities caps = new GLCapabilities(glp);

        glWindow = GLWindow.create(caps);
        glWindow.setTitle(getName());
        glWindow.addGLEventListener(this);
        glWindow.setSize(640, 480);
        glWindow.setVisible(true);

        glWindow.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent e) {
                System.exit(0);
            }
        });

        Animator animator = new Animator(getGlWindow());
        animator.start();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL_DEPTH_TEST);

        gl.glEnable(GL_LIGHTING);
        gl.glLightModelf(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);
        gl.glEnable(GL_NORMALIZE);

        gl.glDepthFunc(GL_LEQUAL);
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glViewport(0, 0, w, h);

        final float hh = (float) w / (float) h;
        double mul = 0.01;

        gl2.glMatrixMode(GL_PROJECTION);
        gl2.glLoadIdentity();
        int size = 80;
        gl2.glOrtho(-size, size, -size / hh, size / hh, -1000, 1000);
        gl2.glMatrixMode(GL_MODELVIEW);
        gl2.glLoadIdentity();
        glu.gluLookAt(-5, -5, 10, 10, 10, 0, 0, 0, 1);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();

//        glu.gluLookAt(-10, -10, verticalAngle, 10, 10, 0, 0, 0, 1);

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
