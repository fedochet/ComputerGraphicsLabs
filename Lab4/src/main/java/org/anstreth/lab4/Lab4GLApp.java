package org.anstreth.lab4;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import org.anstreth.common.AbstractOpenGLApp;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/*
4. Изобразить текстурированные конус и сферу.
Положение конуса – основание параллельно плоскости ZoX (конус «лежит на боку» на плоскости XoY)
5. Реализовать освещение (один источник).
6. Рассчитать и изобразить перекатывание конуса вокруг сферы.
 */

class Lab4GLApp extends AbstractOpenGLApp {

    Lab4GLApp() {
        super("Lab 3");
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glEnable(GL_BLEND);
        gl2.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl2.glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glViewport(0, 0, w, h);

        final float hh = (float) w / (float) h;

        gl2.glMatrixMode(GL_PROJECTION);
        gl2.glLoadIdentity();
        int size = 20;
        gl2.glOrtho(-size, size, -size / hh, size / hh, -1000, 1000);
        glu.gluLookAt(2, 2, 2, 0, 0, 0, 0, 0, 1);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl2.glClearColor(1, 1, 1, 0);

        drawAxes(gl2);
        drawCone(gl2);
    }

    private void drawCone(GL2 gl2) {
        gl2.glColor4f(0, 0, 1, 1);

        withCleanState(gl2, () -> {
            double base = 5;
            double height = 10;
            double coneSideLength = getHypotenuse(base / 2, height);
            gl2.glTranslatef(0, 0, (float)-height);
            glut.glutSolidCone(base, height, 100, 100);
        });
    }

    private void drawAxes(GL2 gl2) {
        float tip = 0;
        float turn = 0;
        float[] ORG = {0, 0, 0};
        float[] XP = {1, 0, 0};
        float[] YP = {0, 1, 0};
        float[] ZP = {0, 0, 1};

        withCleanState(gl2, () -> {
            gl2.glRotatef(tip, 1, 0, 0);
            gl2.glRotatef(turn, 0, 1, 0);
            float scaleFactor = 4f;
            gl2.glScalef(scaleFactor, scaleFactor, scaleFactor);

            gl2.glLineWidth(2.0f);

            gl2.glBegin(GL_LINES);
            gl2.glColor3f(1, 0, 0); // X axis is red.
            gl2.glVertex3fv(ORG, 0);
            gl2.glVertex3fv(XP, 0);
            gl2.glColor3f(0, 1, 0); // Y axis is green.
            gl2.glVertex3fv(ORG, 0);
            gl2.glVertex3fv(YP, 0);
            gl2.glColor3f(0, 0, 1); // z axis is blue.
            gl2.glVertex3fv(ORG, 0);
            gl2.glVertex3fv(ZP, 0);
            gl2.glEnd();
        });
    }

    private double getHypotenuse(double catet1, double catet2) {
        return Math.sqrt(Math.pow(catet1, 2) + Math.pow(catet2, 2));
    }

    private void withCleanState(GL2 gl2, Runnable drawer) {
        gl2.glPushMatrix();
        drawer.run();
        gl2.glPopMatrix();
    }

    @Override
    public void start() {
        super.start();
    }
}
