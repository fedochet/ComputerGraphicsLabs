package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class SystemController implements GLDrawable {

    SphereEmitter sphereEmitter = new SphereEmitter();

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        sphereEmitter.draw(gl2, glu, glut);
    }
}
