package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import org.anstreth.coursework.common.Triple;

class SphereReflector extends SystemObject {

    SphereReflector(Triple position) {
        setPosition(position);
        setSpeed(new Triple(0,0,0));
    }

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {

    }
}
