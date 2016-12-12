package org.anstreth.lab3;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import org.anstreth.common.AbstractOpenGLApp;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static com.jogamp.opengl.GL.GL_SRC_ALPHA;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

class Lab3GLApp extends AbstractOpenGLApp {
    private float cameraZcoord = 2;
    private float cameraYcoord = 2;

    Lab3GLApp() {
        super("Lab 3");
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glEnable(GL_BLEND);
        gl2.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl2.glEnable(GL_DEPTH_TEST);
    }

    private float[] lightPosition = {0,0,0,1};
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

        drawRoom(gl2);
    }

    private void drawRoom(GL2 gl2) {
        int roomSize = 10;
        new RoomDrawer(gl2, roomSize).draw();
    }

    private class RoomDrawer {
        GL2 gl2;
        private int roomSize;
        private float[] roomCoords;

        RoomDrawer(GL2 gl2, int roomSize) {
            this(gl2, roomSize, new float[] {0f,0f,0f});
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
        glu.gluLookAt(2, cameraYcoord, cameraZcoord, 0, 0, 0, 0, 0, 1);
    }

    @Override
    public void start() {
        super.start();
        getGlWindow().addMouseListener(new MouseAdapter() {
            int x;
            int y;
            int startCameraY;
            int startCameraZ;

            @Override
            public void mousePressed(MouseEvent e) {
                updateCoords(e);
            }

            private void updateCoords(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                startCameraY = (int) cameraYcoord;
                startCameraZ = (int) cameraZcoord;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                cameraYcoord = startCameraY - (e.getX() - x)/100f;
                cameraZcoord = startCameraZ + (e.getY() - y)/100f;
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
