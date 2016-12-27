package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import org.anstreth.coursework.common.Triple;

import java.util.LinkedList;
import java.util.List;

class Particle extends SystemObject {
    int life;
    private final double[] color = {0, 1, 0};
    private final int particleRadius = 1;
    private final int slices = 10;
    private final int stacks = 10;
    private final ParticleTrajectory trajectory = new ParticleTrajectory();

    @Override
    public void draw(GL2 gl2, GLU glu, GLUT glut) {
        trajectory.draw(gl2, glu, glut);
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
        trajectory.addPoint(position);
        super.timeStep();
        if (isDead()) {
            throw new IllegalStateException("This particle is dead!");
        }
        life--;
    }

    private static class ParticleTrajectory implements GLDrawable {
        private static final double trajectoryPointRadius = 0.5;
        private static final int maxNymberOfPositions = 5;
        private static final double[] trajectoryPointColor = {0,0,1};
        List<Triple> previousPositions = new LinkedList<>();

        void addPoint(Triple point) {
            while (previousPositions.size() >= maxNymberOfPositions) {
                previousPositions.remove(0);
            }
            previousPositions.add(point);
        }

        @Override
        public void draw(GL2 gl2, GLU glu, GLUT glut) {
            for (Triple previousPosition : previousPositions) {
                gl2.glPushMatrix();
                gl2.glColor3dv(trajectoryPointColor, 0);
                gl2.glTranslated(previousPosition.x, previousPosition.y, previousPosition.z);
                glut.glutSolidSphere(trajectoryPointRadius, 5, 5);
                gl2.glPopMatrix();
            }
        }
    }
}
