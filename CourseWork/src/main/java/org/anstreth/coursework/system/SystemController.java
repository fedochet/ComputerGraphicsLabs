package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class SystemController implements GLDrawable {

    private SphereEmitter sphereEmitter = new SphereEmitter();

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        sphereEmitter.draw(gl2, glu, glut);

        Particle p = new Particle();
        p.setPosition(10, 10, 10);
        p.draw(gl2, glu, glut);
    }
}
