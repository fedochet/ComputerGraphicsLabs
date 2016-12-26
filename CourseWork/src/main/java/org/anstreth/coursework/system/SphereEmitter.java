package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

class SphereEmitter extends SystemObject {

    SphereEmitter() {
        setPosition(0, 0, 0);
        setSpeed(0, 0, 0);
    }

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {

    }
}
