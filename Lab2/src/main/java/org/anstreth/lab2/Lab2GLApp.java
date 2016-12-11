package org.anstreth.lab2;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import org.anstreth.common.AbstractOpenGLApp;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import static com.jogamp.opengl.glu.GLU.GLU_FILL;
import static com.jogamp.opengl.glu.GLU.GLU_SMOOTH;

public class Lab2GLApp extends AbstractOpenGLApp {

    private int verticalAngle = 1;
    private float transperency = 0.5f;
    private float firstLightPositionZ = 2;
    private float secondLightAngle = 0;
    private Texture earthTexture;

    public Lab2GLApp() {
        super("Lab 2");
    }


    public static float[] floats(float... floats) {
        return floats;
    }

    /* параметры материала тора */
    float mat1_dif[] = {0.8f, 0.8f, 0.0f, 1f};
    float mat1_amb[] = {0.2f, 0.2f, 0.2f, 1f};
    float mat1_spec[] = {0.6f, 0.6f, 0.6f, 1f};
    float mat1_shininess = 0.1f * 128;

    float mat2_amb[] = {0.2f, 0.2f, 0.2f};
    float mat2_spec[] = {0.6f, 0.6f, 0.6f};
    float mat2_shininess = 0.2f * 128;

    /* параметры материала шара */
    float mat3_dif[] = {1f, 1f, 1f, 1f};
    float mat3_amb[] = {1f, 1f, 1f};
    float mat3_spec[] = {1f, 1f, 1f, 1f};
    float mat3_shininess = 80;

    @Override
    public void init(GLAutoDrawable drawable) {

        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glEnable(GL_DEPTH_TEST);
        lightSetup(gl2);
        gl2.glEnable(GL_BLEND); // Enable the OpenGL Blending functionality
        gl2.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        gl2.glEnable(GL_COLOR_MATERIAL);
        earthTexture = getEarthTexture();

    }

    private Texture getEarthTexture() {
        try {
            return TextureIO.newTexture(getTextureFile("/earth.jpg"), true);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void lightSetup(GL2 gl2) {
        firstLightSetup(gl2);
        secondLightSetup(gl2);
    }

    private void firstLightSetup(GL2 gl2) {
        float[] lightPosition = floats(2, 2, firstLightPositionZ, 1);
        float[] direction = floats(-1, -1, -1, 1);
        float[] lightDiffuse = floats(1, 0, 0, 1);
        float spotCutoff = 12;
        gl2.glEnable(GL_LIGHTING);
        gl2.glEnable(GL_LIGHT0);
        gl2.glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, spotCutoff);
        gl2.glLightfv(GL_LIGHT0, GL_POSITION, lightPosition, 0);
        gl2.glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, direction, 0);
        gl2.glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuse, 0);
        gl2.glLightfv(GL_LIGHT0, GL_SPECULAR, lightDiffuse, 0);
    }

    private void secondLightSetup(GL2 gl2) {
        int radius = 20;
        float x = (float) (radius * Math.cos(secondLightAngle));
        float y = (float) (radius * Math.sin(secondLightAngle));
        float[] lightPosition = floats(x, y, 2, 1);
        float[] lightDiffuse = floats(1, 1, 1, 1);
        gl2.glEnable(GL_LIGHTING);
        gl2.glEnable(GL_LIGHT1);
        gl2.glLightfv(GL_LIGHT1, GL_POSITION, lightPosition, 0);
        gl2.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuse, 0);
        gl2.glLightfv(GL_LIGHT1, GL_SPECULAR, lightDiffuse, 0);
        gl2.glLighti(GL_LIGHT1, GL_SHININESS, 1);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glViewport(0, 0, w, h);

        final float hh = (float) w / (float) h;
        double mul = 0.1;

        gl2.glMatrixMode(GL_PROJECTION);
        gl2.glLoadIdentity();
        int size = 3;
        gl2.glOrtho(-size, size, -size / hh, size / hh, -1000, 1000);
        gl2.glMatrixMode(GL_MODELVIEW);
        gl2.glLoadIdentity();
        glu.gluLookAt(2, 2, 2, 0, 0, 0, 0, 0, 1);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl2.glClearColor(1, 1, 1, 0);
        lightSetup(gl2);
        gl2.glPushMatrix();
        gl2.glRotatef(verticalAngle++, 0, 0, 1);

        drawCone(gl2);
        drawSphere(gl2);
        drawTorus(gl2);

        gl2.glPopMatrix();

    }

    private void drawTorus(GL2 gl2) {
        gl2.glMaterialfv(GL_FRONT, GL_AMBIENT, mat2_amb, 0);
        float[] diffuse = {0.0f, 0.0f, 0.8f, transperency};
        gl2.glMaterialfv(GL_FRONT, GL_DIFFUSE, diffuse, 0);
        gl2.glMaterialfv(GL_FRONT, GL_SPECULAR, mat2_spec, 0);
        gl2.glMaterialf(GL_FRONT, GL_SHININESS, mat2_shininess);

//        gl2.glColor4f(0.0f, 1.0f, 0.0f, transperency);
        glut.glutSolidTorus(0.3f, 1f, 20, 20);
    }

    private void drawCone(GL2 gl2) {
        gl2.glColor4f(1.0f, 0.0f, 0.0f, 1);
        glut.glutSolidCone(0.5, 2, 10, 10);
    }

    private void drawSphere(GL2 gl2) {
        gl2.glMaterialfv(GL_FRONT, GL_AMBIENT, mat3_amb, 0);
        gl2.glMaterialfv(GL_FRONT, GL_DIFFUSE, mat3_dif, 0);
        gl2.glMaterialfv(GL_FRONT, GL_SPECULAR, mat3_spec, 0);
        gl2.glMaterialf(GL_FRONT, GL_SHININESS, mat3_shininess);

        gl2.glEnable(GL_TEXTURE_2D);
        earthTexture.enable(gl2);
        earthTexture.bind(gl2);

        gl2.glPushMatrix();
        gl2.glTranslatef(0, 0, -1);
        gl2.glColor4f(1f, 1f, 1f, 1);
        GLUquadric sphereQuadric = glu.gluNewQuadric();
        glu.gluQuadricTexture(sphereQuadric, true);
        glu.gluQuadricDrawStyle(sphereQuadric, GLU_FILL);
        glu.gluQuadricNormals(sphereQuadric, GLU_SMOOTH);
        glu.gluSphere(sphereQuadric, 0.5, 100, 100);
        gl2.glPopMatrix();

        earthTexture.disable(gl2);
        glu.gluDeleteQuadric(sphereQuadric);
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

                System.out.println(e.getKeyCode());

                double transparencyStep = 0.1;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
//                        transperency += transparencyStep;
                        firstLightPositionZ += transparencyStep;
                        break;
                    case KeyEvent.VK_DOWN:
//                        transperency -= transparencyStep;
                        firstLightPositionZ -= transparencyStep;
                        break;
                    case KeyEvent.VK_RIGHT:
                        secondLightAngle += transparencyStep;
                        break;
                    case KeyEvent.VK_LEFT:
                        secondLightAngle -= transparencyStep;
                        break;
                }
            }
        });
    }


    private File getTextureFile(String fileName) throws URISyntaxException {
        return Objects.requireNonNull(new File(getClass().getResource(fileName).toURI()), "Texture not found");
    }
}
