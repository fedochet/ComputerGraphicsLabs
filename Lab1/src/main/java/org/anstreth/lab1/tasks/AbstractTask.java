package org.anstreth.lab1.tasks;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import org.anstreth.lab1.Lab1;

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
        glWindow.setTitle("Test");
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
}
