package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SystemController implements GLDrawable {

    private SphereEmitter sphereEmitter = new SphereEmitter();
    private List<Particle> particleList = new ArrayList<>();
    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        if (particleList.size()<10) {
            particleList.add(sphereEmitter.generateParticle());
        }
        sphereEmitter.draw(gl2, glu, glut);
        particleList.forEach(Particle::timeStep);
        particleList.forEach(p -> p.draw(gl2, glu, glut));
    }
}
