package org.anstreth.common;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Created by roman on 05.10.2016.
 */
public abstract class AbstractOpenGLApp implements GLEventListener {
    protected GLU glu = new GLU();
    protected GLUT glut = new GLUT();

    private final String name;

    protected AbstractOpenGLApp(String name) {
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
}
