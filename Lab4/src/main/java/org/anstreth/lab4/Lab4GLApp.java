package org.anstreth.lab4;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
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
    private AxesDrawer axesDrawer = new AxesDrawer();
    private LyingConeDrawer lyingConeDrawer = new LyingConeDrawer();
    private CoordsPair cameraCoordsPair = new CoordsPair(1, 1);
    private CoordsWatcher coordsWatcher = new CoordsWatcher(cameraCoordsPair);

    Lab4GLApp() {
        super("Lab 3");
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glEnable(GL_BLEND);
        gl2.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        gl2.glEnable(GL_DEPTH_TEST);
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
        setCameraPosition();
    }

    private void setCameraPosition() {
        glu.gluLookAt(cameraCoordsPair.first, 2, cameraCoordsPair.second, 0, 0, 0, 0, 0, 1);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl2.glClearColor(1, 1, 1, 0);

        drawCone(gl2);
        drawAxes(gl2);
    }

    private void drawCone(GL2 gl2) {
        lyingConeDrawer.rotateByAngle(1);
        lyingConeDrawer.draw(gl2);
    }

    private void drawAxes(GL2 gl2) {
        axesDrawer.draw(gl2);
    }

    private class AxesDrawer {
        float tip = 0;
        float turn = 0;
        float[] ORG = {0, 0, 0};
        float[] XP = {1, 0, 0};
        float[] YP = {0, 1, 0};
        float[] ZP = {0, 0, 1};

        float scaleFactor = 4f;
        void draw(GL2 gl2) {
            withCleanState(gl2, () -> {
                gl2.glRotatef(tip, 1, 0, 0);
                gl2.glRotatef(turn, 0, 1, 0);
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

    }

    private class LyingConeDrawer {
        final double baseRadius = 4;
        final double height = 8;
        final double coneSideLength = Math.sqrt(Math.pow(baseRadius, 2) + Math.pow(height, 2));
        final double basePerimeter = 2 * Math.PI * baseRadius;
        final double coneTraectoryRadius = 2 * Math.PI * coneSideLength;
        float coneTurnAngle = 0f;
        float coneTraectoryAngle = 0f;

        void draw(GL2 gl2) {
            gl2.glColor4f(0, 0, 1, 1);

            withCleanState(gl2, () -> {
                double conePeekAngle = getConePeekAngle(baseRadius, height);
                gl2.glRotated(coneTurnAngle, 0, 0, 1);
                gl2.glRotated(conePeekAngle / 2 + 90, 0, 1, 0);
                gl2.glTranslated(0, 0, -height);
                drawCone(gl2);
            });

        }

        private void drawCone(GL2 gl2) {
            withCleanState(gl2, () -> {
                gl2.glRotatef(coneTurnAngle, 0, 0, 1);
                glut.glutWireCone(baseRadius, height, 10, 10);
            });
        }

        void rotateByAngle(double angle) {
            coneTurnAngle += angle;
            double distance = angle / 360 * basePerimeter;
            double coneTraectoryAngleDiff = (distance / coneTraectoryRadius) * 360;
            System.out.println(coneTraectoryAngleDiff);
            coneTraectoryAngle += coneTraectoryAngleDiff;
        }

        private double getConePeekAngle(double base, double height) {
            return 2 * Math.atan(base / height) / (Math.PI) * 180;
        }

    }

    private void withCleanState(GL2 gl2, Runnable drawer) {
        gl2.glPushMatrix();
        drawer.run();
        gl2.glPopMatrix();
    }

    @Override
    public void start() {
        super.start();
        getGlWindow().addMouseListener(coordsWatcher);
    }

    private class CoordsWatcher extends MouseAdapter {
        int x;
        int y;
        CoordsPair pairToWatch;
        CoordsPair startCoordsPair;

        float speedCompensator;

        CoordsWatcher(CoordsPair pairToWatch, float speedCompensator) {
            this.pairToWatch = pairToWatch;
            this.speedCompensator = speedCompensator;
        }

        CoordsWatcher(CoordsPair pairToWatch) {
            this(pairToWatch, 0.001f);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            updateCoords(e);
        }

        private void updateCoords(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            startCoordsPair = new CoordsPair(pairToWatch);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            pairToWatch.setFirst(startCoordsPair.first - (e.getX() - x) * speedCompensator);
            pairToWatch.setSecond(startCoordsPair.second + (e.getY() - y) * speedCompensator);
        }
    }

}
