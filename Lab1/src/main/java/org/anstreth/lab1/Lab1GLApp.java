package org.anstreth.lab1;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import org.anstreth.common.AbstractOpenGLApp;
import org.anstreth.common.primitives.Cube;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import static com.jogamp.opengl.fixedfunc.GLPointerFunc.GL_VERTEX_ARRAY;
import static com.jogamp.opengl.glu.GLU.GLU_FILL;
import static com.jogamp.opengl.glu.GLU.GLU_SMOOTH;

/**
 * Created by roman on 09.09.2016.
 */
public class Lab1GLApp extends AbstractOpenGLApp {
    private float angle = 0;
    private volatile int currentTask = 1;

    private int verticalAngle = 0;
    private float horisontalAngle = 0;
    private double state = 0;
    private double add = 0.001;

    public Lab1GLApp(String name) {
        super(name);
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

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        currentTask++;
                        break;
                    case KeyEvent.VK_DOWN:
                        verticalAngle--;
                        break;
                    case KeyEvent.VK_UP:
                        verticalAngle++;
                        break;
                    case KeyEvent.VK_LEFT:
                        horisontalAngle--;
                        break;
                    case KeyEvent.VK_RIGHT:
                        horisontalAngle++;
                        break;
                }
            }
        });
    }

    private Texture earthTexture;

    @Override
    public void init(GLAutoDrawable drawable) {
        super.init(drawable);

        try {
            earthTexture = TextureIO.newTexture(getTextureFile(), true);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        super.display(drawable);
        GL2 gl2 = drawable.getGL().getGL2();

        switch (currentTask) {
            case 1:
                task1(gl2);
                break;
            case 2:
                task2(gl2, -angle);
                if (angle < 180) {
                    angle += 0.1;
                }
                break;
            case 3:
                task3(gl2);
                break;
            case 4:
                task4(gl2);
                break;
            case 5:
                task5(gl2);
                break;
            default:
                task3(gl2);
        }
    }

    private void task1(GL2 gl2) {
        task2(gl2, 0);
    }

    private void task2(GL2 gl2, float angle) {
        gl2.glColor3f(1, 1, 1);

        gl2.glPushMatrix();
        gl2.glRotatef(angle, 0, 0, 1);
        gl2.glTranslatef(10, 10, 0);

        int slices = 10;
        int stacks = 10;
        glut.glutWireCone(10, 15, slices, stacks);

        gl2.glTranslatef(0, 0, 15);
        glut.glutWireSphere(5, slices, stacks);
        gl2.glPopMatrix();
    }

    private void task3(GL2 gl2) {
        gl2.glEnable(GL_BLEND);
        gl2.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        float alpha = 0.4f;
        gl2.glColor4f(1, 1, 1, alpha);
        gl2.glPushMatrix();
        {
            int cubeSize = 20;

            int sphereRadius = 7;
            int slices = 20;
            int stacks = 20;

            glut.glutSolidCube(cubeSize);
            gl2.glTranslatef(cubeSize / 2, cubeSize / 2, cubeSize / 2);
            glut.glutSolidSphere(sphereRadius, slices, stacks);
        }
        gl2.glPopMatrix();
    }

    private File getTextureFile() throws URISyntaxException {
        return new File(getClass().getResource("/earth.jpg").toURI());
    }

    private void task4(GL2 gl) {
        gl.glEnable(GL_TEXTURE_2D);

        earthTexture.enable(gl);
        earthTexture.bind(gl);

        GLUquadric quad = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(quad, GLU_FILL);
        glu.gluQuadricTexture(quad, true);
        glu.gluQuadricNormals(quad, GLU_SMOOTH);

        gl.glPushMatrix();
        gl.glRotatef(verticalAngle, 0, 1, 0);
        gl.glPushMatrix();
        gl.glRotatef(horisontalAngle, 0, 0, 1);

        glu.gluCylinder(quad, 10, 10, 10, 4, 1);

        gl.glPopMatrix();
        gl.glPopMatrix();

        earthTexture.disable(gl);

        glu.gluDeleteQuadric(quad);
    }

    private void task5(GL2 gl) {

        gl.glEnableClientState(GL_VERTEX_ARRAY);
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        gl.glPushMatrix();
        gl.glRotatef(horisontalAngle, 0, 0, 1);
        gl.glPushMatrix();
        gl.glTranslatef(10, 10, 0);
        new Cube(20, verticalAngle).draw(gl, 1 - state);
        gl.glTranslatef(-20, -20, 0);
        new Cube(20, verticalAngle).draw(gl, state);
        gl.glPopMatrix();
        gl.glPopMatrix();

        if (state < 0) {
            add = -add;
        }

        horisontalAngle += 0.1;
        state += add;

        gl.glDisableClientState(GL_VERTEX_ARRAY);
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

}

