package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import org.anstreth.coursework.common.Triple;

class SphereReflector extends SystemObject {

    private double radius;

    SphereReflector(Triple position, double radius) {
        this.radius = radius;
        setPosition(position);
        setSpeed(new Triple(0,0,0));
    }

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        gl2.glPushMatrix();
        gl2.glTranslated(position.x, position.y, position.z);
        glut.glutSolidSphere(radius, 10, 10);
        gl2.glPopMatrix();
    }

    void reflectParticle(Particle particle) {
        Triple nextPosition = particle.position.add(particle.speed);
        if (insideReflectorSphere(nextPosition)) {
            Triple vectorFromCenterToParticle = particle.position.substract(position);
            Triple sphereNormal = normalize(vectorFromCenterToParticle).inverse();
            Triple speedNormal = normalize(particle.speed);
            double cosBetweenNormals = scalarMultiply(sphereNormal, speedNormal);
            particle.speed = particle.speed.substract(sphereNormal.multiply(cosBetweenNormals * particle.speed.getLength()).multiply(2));
        }
    }

    private Triple normalize(Triple vector) {
        return vector.divide(vector.getLength());
    }

    private double scalarMultiply(Triple left, Triple right) {
        return left.x * right.x + left.y * right.y + left.z * right.z;
    }

    private boolean insideReflectorSphere(Triple nextPosition) {
        return position.substract(nextPosition).getLength() <= radius;
    }
}
