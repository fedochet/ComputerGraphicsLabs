package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import java.util.Random;

class SphereEmitter extends SystemObject {

    private final int sphereStacks = 20;
    private final int sphereRadius = 10;
    private final int sphereSlices = 20;
    private final double[] color = {1, 0, 0};

    private Random random = new Random();

    SphereEmitter() {
        setPosition(0, 0, 0);
        setSpeed(0, 0, 0);
    }

    Particle generateParticle() {
        Particle particle = new Particle();
        setRandomPositionOnSphere(particle);

        return particle;
    }

    private void setRandomPositionOnSphere(Particle particle) {
        double phi = getRandomAngle();
        double theta = getRandomAngle();
        double particleX = sphereRadius * Math.cos(phi) * Math.sin(theta);
        double particleY = sphereRadius * Math.sin(phi) * Math.sin(theta);
        double particleZ = sphereRadius * Math.cos(theta);
        particle.setPosition(particleX, particleY, particleZ);
    }

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        gl2.glPushMatrix();
        gl2.glTranslated(x, y, z);
        gl2.glColor3dv(color, 0);
        glut.glutWireSphere(sphereRadius, sphereSlices, sphereStacks);
        gl2.glPopMatrix();
    }

    private double getRandomAngle() {
        return random.nextLong();
    }
}
