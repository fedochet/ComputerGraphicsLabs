package org.anstreth.lab2;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.DebugGL4bc;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import org.anstreth.common.AbstractOpenGLApp;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES1.GL_LIGHT_MODEL_TWO_SIDE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/**
 * Created by roman on 05.10.2016.
 */
public class Lab2GLApp extends AbstractOpenGLApp {

    private int verticalAngle;
    private int horisontalAngle;

    public Lab2GLApp() {
        super("Lab 2");
    }


    public static float[] floats(float... floats) {
        return floats;
    }

    /* параметры материала тора */
    float mat1_dif[]={0.8f,0.8f,0.0f, 1f};
    float mat1_amb[]= {0.2f,0.2f,0.2f, 1f};
    float mat1_spec[]={0.6f,0.6f,0.6f, 1f};
    float mat1_shininess=0.1f*128;

    /* параметры материала конуса */
    float mat2_dif[]={0.0f,0.0f,0.8f};
    float mat2_amb[]= {0.2f,0.2f,0.2f};
    float mat2_spec[]={0.6f,0.6f,0.6f};
    float mat2_shininess=0.7f*128;

    /* параметры материала шара */
    float mat3_dif[]={0.9f,0.2f,0.0f, 0f};
    float mat3_amb[]= {0.2f,0.2f,0.2f};
    float mat3_spec[]={0.6f,0.6f,0.6f};
    float mat3_shininess=0.1f*128;

    @Override
    public void init(GLAutoDrawable drawable) {
//      /* устанавливаем параметры источника света */
        GL2 gl2 = drawable.getGL().getGL2();

        float[] light_ambient = { 0.0f, 0.0f, 0.0f, 1.0f };
        float[] light_diffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
        float[] light_specular = { 1.0f, 1.0f, 1.0f, 1.0f };
        float[] light_position = { 1.0f, 1.0f, 1.0f, 0.0f };

        gl2.glLightfv (GL_LIGHT0, GL_AMBIENT, light_ambient, 0);
        gl2.glLightfv (GL_LIGHT0, GL_DIFFUSE, light_diffuse, 0);
        gl2.glLightfv (GL_LIGHT0, GL_SPECULAR, light_specular, 0);
        gl2.glLightfv (GL_LIGHT0, GL_POSITION, light_position, 0);

   /* включаем освещение и источник света */
        gl2.glEnable (GL_LIGHTING);
        gl2.glEnable (GL_LIGHT0);

   /* включаем z-буфер */
        gl2.glEnable(GL_DEPTH_TEST);
//        gl2.glEnable(GL_NORMALIZE);

//        float[] material_diffuse = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
//        gl2.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, material_diffuse, 0);

//        gl2.glMaterialfv(GL_FRONT, GL_SHININESS, new float[]{1f},0);

//        gl2.glLightfv(GL_LIGHT0, GL_AMBIENT, new float[]{1f,0f,1f,1f}, 0);
//        gl2.glLightfv(GL_LIGHT0, GL_DIFFUSE, new float[]{1f, 1f, 1f, 0f}, 0);

//        gl2.glEnable(GL_LIGHT0);
        gl2.glEnable(GL_BLEND);
        gl2.glBlendFunc(GL_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl2.glEnable(GL_COLOR_MATERIAL);

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
        glu.gluLookAt(-2, -2, 5, 0, 0, 0, 0, 0, 1);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        gl2.glPushMatrix ();
        gl2.glRotatef (20.0f, 1.0f, 0.0f, 0.0f);

   /* отображаем тор */
        gl2.glMaterialfv (GL_FRONT,GL_AMBIENT,mat1_amb,0);
        gl2.glMaterialfv (GL_FRONT,GL_DIFFUSE,mat1_dif,0);
        gl2.glMaterialfv (GL_FRONT,GL_SPECULAR,mat1_spec,0);
        gl2.glMaterialf  (GL_FRONT,GL_SHININESS,mat1_shininess);

        gl2.glPushMatrix ();
        gl2.glTranslatef (-0.75f, 0.5f, 0.0f);
        gl2.glRotatef (90.0f, 1.0f, 0.0f, 0.0f);
        glut.glutSolidTorus (0.275, 0.85, 15, 15);
        gl2.glPopMatrix ();

   /* отображаем конус */
        gl2.glMaterialfv (GL_FRONT,GL_AMBIENT,mat2_amb,0);
        gl2.glMaterialfv (GL_FRONT,GL_DIFFUSE,mat2_dif,0);
        gl2.glMaterialfv (GL_FRONT,GL_SPECULAR,mat2_spec,0);
        gl2.glMaterialf  (GL_FRONT,GL_SHININESS,mat2_shininess);

        gl2.glPushMatrix ();
        gl2.glTranslatef (-0.75f, -0.5f, 0.0f);
        gl2.glRotatef (270.0f, 1.0f, 0.0f, 0.0f);
        glut.glutSolidCone (1.0, 2.0, 15, 15);
        gl2.glPopMatrix ();

   /* отображаем шар */
        gl2.glMaterialfv (GL_FRONT,GL_AMBIENT,mat3_amb,0);
        gl2.glMaterialfv (GL_FRONT,GL_DIFFUSE,mat3_dif,0);
        gl2.glMaterialfv (GL_FRONT,GL_SPECULAR,mat3_spec,0);
        gl2.glMaterialf  (GL_FRONT,GL_SHININESS,mat3_shininess);

        gl2.glPushMatrix ();
        gl2.glTranslatef (0.75f, 0.0f, -1.0f);
        gl2.glColor4f(1,0,0,1);
        glut.glutSolidSphere (1.0, 15, 15);
        gl2.glPopMatrix ();

        gl2.glPopMatrix ();
   /* выводим сцену на экран */
        gl2.glFlush ();

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
}
