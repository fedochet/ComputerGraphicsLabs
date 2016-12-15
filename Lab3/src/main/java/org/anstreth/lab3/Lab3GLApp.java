package org.anstreth.lab3;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import org.anstreth.common.AbstractOpenGLApp;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

// TODO draw cylinder, sphere on top of it and thorus
class Lab3GLApp extends AbstractOpenGLApp {
    private CoordsPair cameraCoordsPair = new CoordsPair(1, 1);
    private CoordsPair light0CoordsPair = new CoordsPair(0, 0);

    private RoomDrawer roomDrawer;

    Lab3GLApp() {
        super("Lab 3");
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        int roomSize = 20;
        GL2 gl2 = drawable.getGL().getGL2();
        roomDrawer = new RoomDrawer(gl2, roomSize);
        gl2.glEnable(GL_BLEND);
        gl2.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl2.glEnable(GL_DEPTH_TEST);
    }

    private float[] lightDirection = {-1, 0, 0};

    private void setUpLight(GL2 gl2) {
        float[] lightPosition = getLightPosition();
        float spotCutoff = 90;
        gl2.glEnable(GL_LIGHTING);
        gl2.glEnable(GL_LIGHT0);
        gl2.glLightfv(GL_LIGHT0, GL_POSITION, lightPosition, 0);
        gl2.glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, lightDirection, 0);
        gl2.glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, spotCutoff);

        gl2.glPushMatrix();
        gl2.glTranslatef(lightPosition[0], lightPosition[1], lightPosition[2]);
        glut.glutSolidSphere(1, 10, 10);
        gl2.glPopMatrix();
    }

    private float[] getLightPosition() {
        return new float[]{11, -light0CoordsPair.first, -light0CoordsPair.second, 1};
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
        setUpCameraPosition(gl2);

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        setUpCameraPosition(gl2);
        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl2.glClearColor(1, 1, 1, 0);

        setUpLight(gl2);
        roomDrawer.draw();
        drawShadows(gl2);
        drawCylinder(gl2);
    }

    private void drawShadows(GL2 gl2) {
        gl2.glEnable(GL_BLEND);
        gl2.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl2.glDisable(GL_LIGHTING);
        gl2.glColor4f(0.0f, 0.0f, 0.0f, 0.7f);
        float[][] matrix = new float[4][4];
        float[] floorPlane = {0, 0, 1, 9.99f};
        float[] backWall = {1, 0, 0, 9.99f};
        float[] leftWall = {0, 1, 0, 9.99f};
        float[] rightWall = {0, -1, 0, 9.99f};
        float[] lightPos = getLightPosition();

        shadowmatrix(matrix, floorPlane, lightPos);
        float[] stripMatrix = stripMatrix(matrix);
        gl2.glPushMatrix();
        gl2.glMultMatrixf(stripMatrix, 0);
        drawCylinder(gl2);
        gl2.glPopMatrix();

        shadowmatrix(matrix, leftWall, lightPos);
        stripMatrix = stripMatrix(matrix);
        gl2.glPushMatrix();
        gl2.glMultMatrixf(stripMatrix, 0);
        drawCylinder(gl2);
        gl2.glPopMatrix();

        shadowmatrix(matrix, rightWall, lightPos);
        stripMatrix = stripMatrix(matrix);
        gl2.glPushMatrix();
        gl2.glMultMatrixf(stripMatrix, 0);
        drawCylinder(gl2);
        gl2.glPopMatrix();

        shadowmatrix(matrix, backWall, lightPos);
        stripMatrix = stripMatrix(matrix);
        gl2.glPushMatrix();
        gl2.glMultMatrixf(stripMatrix, 0);
        drawCylinder(gl2);
        gl2.glPopMatrix();

        gl2.glEnable(GL_LIGHTING);
        gl2.glDisable(GL_BLEND);
    }

    private void shadowmatrix(float matrix[][], float plane[], float lightpos[]) {
        float dot;

        dot = plane[0] * lightpos[0] +
                plane[1] * lightpos[1] +
                plane[2] * lightpos[2] +
                plane[3] * lightpos[3];

        matrix[0][0] = dot - lightpos[0] * plane[0];
        matrix[1][0] = 0.f - lightpos[0] * plane[1];
        matrix[2][0] = 0.f - lightpos[0] * plane[2];
        matrix[3][0] = 0.f - lightpos[0] * plane[3];

        matrix[0][1] = 0.f - lightpos[1] * plane[0];
        matrix[1][1] = dot - lightpos[1] * plane[1];
        matrix[2][1] = 0.f - lightpos[1] * plane[2];
        matrix[3][1] = 0.f - lightpos[1] * plane[3];

        matrix[0][2] = 0.f - lightpos[2] * plane[0];
        matrix[1][2] = 0.f - lightpos[2] * plane[1];
        matrix[2][2] = dot - lightpos[2] * plane[2];
        matrix[3][2] = 0.f - lightpos[2] * plane[3];

        matrix[0][3] = 0.f - lightpos[3] * plane[0];
        matrix[1][3] = 0.f - lightpos[3] * plane[1];
        matrix[2][3] = 0.f - lightpos[3] * plane[2];
        matrix[3][3] = dot - lightpos[3] * plane[3];
    }

    private float[] stripMatrix(float[][] matrix) {
        int size = matrix[0].length * matrix.length;
        float[] result = new float[size];

        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                result[column*4 + row] = matrix[column][row];
            }
        }

        return result;
    }

    private void drawCylinder(GL2 gl2) {
        gl2.glPushMatrix();
        gl2.glTranslatef(0, 0, -10);
        glut.glutSolidCylinder(3, 7, 20, 20);
        gl2.glPopMatrix();
    }

    private class RoomDrawer {
        GL2 gl2;
        private int roomSize;
        private float[] roomCoords;

        RoomDrawer(GL2 gl2, int roomSize) {
            this(gl2, roomSize, new float[]{0f, 0f, 0f});
        }

        RoomDrawer(GL2 gl2, int roomSize, float[] roomCoords) {
            this.gl2 = gl2;
            this.roomSize = roomSize;
            this.roomCoords = roomCoords;
        }

        void draw() {
            gl2.glPushMatrix();
            gl2.glTranslatef(roomCoords[0], roomCoords[1], roomCoords[2]);
            drawCubeWithCenterIn(-roomSize, 0, 0);
            drawCubeWithCenterIn(0, roomSize, 0);
            drawCubeWithCenterIn(0, -roomSize, 0);
            drawCubeWithCenterIn(0, 0, roomSize);
            drawCubeWithCenterIn(0, 0, -roomSize);
            gl2.glPopMatrix();

        }

        private void drawCubeWithCenterIn(int x, int y, int z) {
            gl2.glPushMatrix();
            gl2.glTranslatef(x, y, z);
            glut.glutSolidCube(roomSize);
            gl2.glPopMatrix();
        }
    }

    private void setUpCameraPosition(GL2 gl2) {
        gl2.glMatrixMode(GL_MODELVIEW);
        gl2.glLoadIdentity();
        glu.gluLookAt(2, cameraCoordsPair.first, cameraCoordsPair.second, 0, 0, 0, 0, 0, 1);
    }

    @Override
    public void start() {
        super.start();
        getGlWindow().addMouseListener(new CoordsWatcher(light0CoordsPair, 0.1f));
        getGlWindow().addKeyListener(new KeyListener() {
            boolean state = true;
            CoordsWatcher cameraWatcher = new CoordsWatcher(cameraCoordsPair, 0.01f);
            CoordsWatcher lightWatcher = new CoordsWatcher(light0CoordsPair, 0.1f);

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        switchWatcher();
                        break;
                    case KeyEvent.VK_UP:
                        break;
                    case KeyEvent.VK_DOWN:
                        break;
                    case KeyEvent.VK_RIGHT:
                        break;
                    case KeyEvent.VK_LEFT:
                        break;
                }
            }

            private void switchWatcher() {
                if (state) {
                    getGlWindow().removeMouseListener(cameraWatcher);
                    getGlWindow().addMouseListener(lightWatcher);
                } else {
                    getGlWindow().removeMouseListener(lightWatcher);
                    getGlWindow().addMouseListener(cameraWatcher);
                }
                state = !state;
            }
        });
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
            pairToWatch.first = startCoordsPair.first - (e.getX() - x) * speedCompensator;
            pairToWatch.second = startCoordsPair.second + (e.getY() - y) * speedCompensator;
        }
    }
}
