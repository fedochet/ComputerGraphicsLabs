package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import java.util.ArrayList;
import java.util.List;

public class SystemController implements GLDrawable {
    private int particlesLimit = 100;
    private SphereEmitter sphereEmitter = new SphereEmitter();
    private List<Particle> particleList = new ArrayList<>();

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        removeDeadParticles();
        if (canGenerateNewParticle()) {
            particleList.add(sphereEmitter.generateParticle());
        }
        sphereEmitter.draw(gl2, glu, glut);
        particleList.forEach(this::accelerate);
        particleList.forEach(Particle::timeStep);
        particleList.forEach(p -> p.draw(gl2, glu, glut));
    }

    private void accelerate(Particle particle) {
        double[] accelerationAtPoint = sphereEmitter.getAccelerationAtPoint(particle.x, particle.y, particle.z);
        particle.xSpeed += accelerationAtPoint[0];
        particle.ySpeed += accelerationAtPoint[1];
        particle.zSpeed += accelerationAtPoint[2];
    }

    private boolean canGenerateNewParticle() {
        return particleList.size() < particlesLimit;
    }

    private void removeDeadParticles() {
        particleList.removeIf(Particle::isDead);
    }
}
