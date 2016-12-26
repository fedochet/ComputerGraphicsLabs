package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

class SphereEmitter extends SystemObject {

    private final int sphereStacks = 20;
    private final int sphereRadius = 10;
    private final int sphereSlices = 20;
    private final double[] color = {1, 0, 0};

    SphereEmitter() {
        setPosition(0, 0, 0);
        setSpeed(0, 0, 0);
    }

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        gl2.glPushMatrix();
        gl2.glTranslated(x, y, z);
        gl2.glColor3dv(color, 0);
        glut.glutSolidSphere(sphereRadius, sphereSlices, sphereStacks);
        gl2.glPopMatrix();
    }
}
