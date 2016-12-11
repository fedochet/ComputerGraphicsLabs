package org.anstreth.lab3;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import org.anstreth.common.AbstractOpenGLApp;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static com.jogamp.opengl.GL.GL_SRC_ALPHA;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
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

        gl2.glEnable(GL_LIGHTING);
        gl2.glEnable(GL_LIGHT0);
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
        int size = 40;
        gl2.glOrtho(-size, size, -size / hh, size / hh, -1000, 1000);
        setUpCameraLookAt(gl2);

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        setUpCameraLookAt(gl2);

        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl2.glClearColor(1, 1, 1, 0);

        drawRoom(gl2);
    }

    private void drawRoom(GL2 gl2) {
        int roomSize = 10;

        glut.glutSolidCube(roomSize);

        drawCubeWithCenterIn(gl2, roomSize);

        gl2.glPushMatrix();
        gl2.glTranslatef(0, roomSize, 0);
        glut.glutSolidCube(roomSize);
        gl2.glPopMatrix();

        gl2.glPushMatrix();
        gl2.glTranslatef(0, -roomSize, 0);
        glut.glutSolidCube(roomSize);
        gl2.glPopMatrix();

        gl2.glPushMatrix();
        gl2.glTranslatef(0, 0, roomSize);
        glut.glutSolidCube(roomSize);
        gl2.glPopMatrix();

        gl2.glPushMatrix();
        gl2.glTranslatef(0, 0, -roomSize);
        glut.glutSolidCube(roomSize);
        gl2.glPopMatrix();


    }

    private void drawCubeWithCenterIn(GL2 gl2, int roomSize) {
        gl2.glPushMatrix();
        gl2.glTranslatef(-roomSize, 0, 0);
        glut.glutSolidCube(roomSize);
        gl2.glPopMatrix();
    }

    private void setUpCameraLookAt(GL2 gl2) {
        gl2.glMatrixMode(GL_MODELVIEW);
        gl2.glLoadIdentity();
        glu.gluLookAt(2, cameraYcoord, cameraZcoord, 0, 0, 0, 0, 0, 1);
    }

    @Override
    public void start() {
        super.start();
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
