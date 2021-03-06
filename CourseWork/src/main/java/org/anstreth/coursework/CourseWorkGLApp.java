package org.anstreth.coursework;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import org.anstreth.common.AbstractOpenGLApp;
import org.anstreth.coursework.system.SystemController;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/*
5. Эмиттер – сфера
1. Обязательные параметры:
скорость уменьшается с удалением от эмиттера

Остальные параметры устанавливаются
и изменяются по вашему выбору.
2. След: присутствует, длина от 2 до 6
3. Столкновения: сфера
 */

class CourseWorkGLApp extends AbstractOpenGLApp {
    private SystemController systemController = new SystemController();

    CourseWorkGLApp() {
        super("Course Work");
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl2 = drawable.getGL().getGL2();
        gl2.glEnable(GL_BLEND);
        gl2.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl2.glEnable(GL_DEPTH_TEST);
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
        glu.gluLookAt(5, 0, 0, 0, 0, 0, 0, 0, 1);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl2.glClearColor(1, 1, 1, 0);

        systemController.draw(gl2, glu, glut);
    }
}
