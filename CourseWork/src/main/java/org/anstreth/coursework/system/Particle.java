package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class Particle extends SystemObject {
    private final double[] color = {0, 1, 0};
    private final int particleRadius = 1;
    private final int slices = 10;
    private final int stacks = 10;

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        gl2.glPushMatrix();
        gl2.glColor3dv(color, 0);
        gl2.glTranslated(x, y, z);
        glut.glutSolidSphere(particleRadius, slices, stacks);
        gl2.glPopMatrix();
    }
}
