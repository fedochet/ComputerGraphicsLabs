package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

class Particle extends SystemObject {
    int life;
    private final double[] color = {0, 1, 0};
    private final int particleRadius = 1;
    private final int slices = 10;
    private final int stacks = 10;

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        gl2.glPushMatrix();
        gl2.glColor3dv(color, 0);
        gl2.glTranslated(position.x, position.y, position.z);
        glut.glutSolidSphere(particleRadius, slices, stacks);
        gl2.glPopMatrix();
    }

    boolean isDead() {
        return life <= 0;
    }

    @Override
    void timeStep() {
        super.timeStep();
        if (isDead()) {
            throw new IllegalStateException("This particle is dead!");
        }
        life--;
    }
}
