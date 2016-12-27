package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import org.anstreth.coursework.common.Triple;

import java.util.ArrayList;
import java.util.List;

public class SystemController implements GLDrawable {
    private int particlesLimit = 100;
    private SphereEmitter sphereEmitter = new SphereEmitter();
    private SphereReflector sphereReflector = new SphereReflector(new Triple(0, 0, 80), 40);
    private List<Particle> particleList = new ArrayList<>();

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        sphereEmitter.draw(gl2, glu, glut);
        sphereReflector.draw(gl2, glu, glut);
        removeDeadParticles();
        if (canGenerateNewParticle()) {
            particleList.add(sphereEmitter.generateParticle());
        }
        particleList.forEach(this::accelerateByEmitter);
        particleList.forEach(sphereReflector::reflectParticle);
        particleList.forEach(Particle::timeStep);
        particleList.forEach(p -> p.draw(gl2, glu, glut));
    }

    private void accelerateByEmitter(Particle particle) {
        Triple accelerationAtPoint = sphereEmitter.getAccelerationAtPoint(particle.position);
        particle.speed = particle.speed.add(accelerationAtPoint);
    }

    private boolean canGenerateNewParticle() {
        return particleList.size() < particlesLimit;
    }

    private void removeDeadParticles() {
        particleList.removeIf(Particle::isDead);
    }
}
