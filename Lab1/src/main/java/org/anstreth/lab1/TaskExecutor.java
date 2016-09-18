package org.anstreth.lab1;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import org.anstreth.lab1.common.Point;
import org.anstreth.lab1.common.Side;
import org.anstreth.lab1.common.Strip;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
public class TaskExecutor implements GLEventListener {
    private GLU glu = new GLU();
    private GLUT glut = new GLUT();

    private final String name;
    private float angle = 0;
    private volatile int currentTask = 1;

    private int verticalAngle = 0;
    private int horisontalAngle = 0;

    public TaskExecutor(String name) {
        this.name = name;
    }

    public void start() {
        GLProfile glp = GLProfile.get(GLProfile.GL2);

        GLCapabilities caps = new GLCapabilities(glp);

        GLWindow glWindow = GLWindow.create(caps);
        glWindow.setTitle(name);
        glWindow.addGLEventListener(this);
        glWindow.setSize(640, 480);
        glWindow.setVisible(true);

        glWindow.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent e) {
                System.exit(0);
            }
        });

        glWindow.addKeyListener(new KeyListener() {
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

        Animator animator = new Animator(glWindow);
        animator.start();
    }

    private Texture earthTexture;

    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        gl.glShadeModel(GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        try {
            earthTexture = TextureIO.newTexture(getTextureFile(), true);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

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
        glu.gluLookAt(-5, -5, 10, 10, 10, 0, 0, 0, 1);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {

        GL2 gl2 = drawable.getGL().getGL2();

//        glu.gluLookAt(-10, -10, verticalAngle, 10, 10, 0, 0, 0, 1);

        gl2.glShadeModel(GL2.GL_SMOOTH);
        gl2.glClearColor(0f, 0f, 0f, 0f);
        gl2.glClearDepth(1.0f);
        gl2.glEnable(GL_DEPTH_TEST);
        gl2.glDepthFunc(GL_LEQUAL);
        gl2.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        gl2.glClearColor(0, 0, 0, 1);
        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

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

        glu.gluSphere(quad, 30, horisontalAngle + 1, verticalAngle + 1);

        gl.glPopMatrix();
        gl.glPopMatrix();

        earthTexture.disable(gl);

        glu.gluDeleteQuadric(quad);
    }

    private void task5(GL2 gl) {

        gl.glEnableClientState(GL_VERTEX_ARRAY);
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        int stacks = 4;
        int length = 10;
//
//        DoubleBuffer verticesBuffer = Buffers.newDirectDoubleBuffer(new double[]{
//                0, 0, 0,
//                0, 10, 10,
//                10, 0, 0,
//                15, 15, 15
//        });
//
//        verticesBuffer.rewind();
//        gl.glVertexPointer(3, GL_DOUBLE, 0, verticesBuffer);
//        gl.glDrawArrays(GL.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPushMatrix();
        gl.glRotatef(horisontalAngle, 0, 0, 1);
        new Side(new Point(0, 0, 0), new Point(0, 40, 0), new Point(0, 40, 40), new Point(0, 0, 40), 5).draw(gl);
        gl.glPopMatrix();

        gl.glDisableClientState(GL_VERTEX_ARRAY);
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

}

