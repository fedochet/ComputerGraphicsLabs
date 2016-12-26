package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import org.anstreth.coursework.common.Triple;

import java.util.Random;

class SphereEmitter extends SystemObject {

    private final int sphereStacks = 20;
    private final int sphereRadius = 10;
    private final int sphereSlices = 20;
    private final double[] color = {1, 0, 0};

    private Random random = new Random();

    SphereEmitter() {
        setPosition(new Triple(0, 0, 0));
        setSpeed(new Triple(0, 0, 0));
    }

    Particle generateParticle() {
        Particle particle = new Particle();
        setRandomPositionOnSphere(particle);
        setInitialRandomSpeed(particle);
        setRandomLife(particle);

        return particle;
    }

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        gl2.glPushMatrix();
        gl2.glTranslated(position.x, position.y, position.z);
        gl2.glColor3dv(color, 0);
        glut.glutWireSphere(sphereRadius, sphereSlices, sphereStacks);
        gl2.glPopMatrix();
    }

    Triple getAccelerationAtPoint(Triple position) {
        Triple distanceVector = position.substract(this.position);
        double distance = distanceVector.getLength();
        if (distance < sphereRadius) {
            return new Triple(0, 0, 0);
        }

        return distanceVector
                .divide(distance)
                .divide(Math.exp(distance))
                .inverse();
    }

    private void setRandomPositionOnSphere(Particle particle) {
        double phi = getRandomAngle();
        double theta = getRandomAngle();
        double particleX = sphereRadius * Math.cos(phi) * Math.sin(theta);
        double particleY = sphereRadius * Math.sin(phi) * Math.sin(theta);
        double particleZ = sphereRadius * Math.cos(theta);
        particle.setPosition(new Triple(particleX, particleY, particleZ));
    }

    private void setInitialRandomSpeed(Particle particle) {
        double speed = random.nextDouble() * 0.1;
        Triple speedVector = particle.position.substract(position).multiply(speed);
        particle.setSpeed(speedVector);
    }

    private void setRandomLife(Particle particle) {
        particle.life = 250 + random.nextInt(250);
    }

    private double getRandomAngle() {
        return random.nextLong();
    }
}
