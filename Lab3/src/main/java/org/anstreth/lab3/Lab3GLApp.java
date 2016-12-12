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
    private float cameraZcoord = 2;
    private float cameraYcoord = 2;
    private CoordsPair cameraCoordsPair = new CoordsPair(2, 2);

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

    private float[] lightPosition = {11, 0, 0, 1};
    private float[] lightDirection = {-1, 0, 0};

    private void setUpLight(GL2 gl2) {
        float spotCutoff = 90;
        gl2.glEnable(GL_LIGHTING);
        gl2.glEnable(GL_LIGHT0);
        gl2.glLightfv(GL_LIGHT0, GL_POSITION, lightPosition, 0);
        gl2.glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, lightDirection, 0);
        gl2.glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, spotCutoff);
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
        setUpLight(gl2);
        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl2.glClearColor(1, 1, 1, 0);

        roomDrawer.draw();
        drawCylinder(gl2);
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

    abstract class CoordsWatcher extends MouseAdapter {
        CoordsPair pairToWatch;

        CoordsWatcher(CoordsPair pairToWatch) {
            this.pairToWatch = pairToWatch;
        }
    }

    @Override
    public void start() {
        super.start();
        getGlWindow().addMouseListener(new CoordsWatcher(cameraCoordsPair) {
            int x;
            int y;
            CoordsPair startCoordsPair;

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
                float speedCompensator = 0.01f;
                pairToWatch.first = startCoordsPair.first - (e.getX() - x) * speedCompensator;
                pairToWatch.second = startCoordsPair.second + (e.getY() - y) * speedCompensator;
            }
        });
        getGlWindow().addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        cameraZcoord++;
                        break;
                    case KeyEvent.VK_DOWN:
                        cameraZcoord--;
                        break;
                    case KeyEvent.VK_RIGHT:
                        cameraYcoord++;
                        break;
                    case KeyEvent.VK_LEFT:
                        cameraYcoord--;
                        break;
                }
            }
        });
    }
}
