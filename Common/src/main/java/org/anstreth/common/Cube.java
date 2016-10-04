package org.anstreth.common;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 19.09.2016.
 */
public class Cube {

    private final List<Side> sides = new ArrayList<>();
    private final int size;

    public Cube(int size, int slices) {
        this.size = size;
        Point x0 = new Point(-0.5, -0.5, -0.5).scale(size);
        Point x1 = new Point(0.5, -0.5, -0.5).scale(size);
        Point x2 = new Point(0.5, 0.5, -0.5).scale(size);
        Point x3 = new Point(-0.5, 0.5, -0.5).scale(size);

        Point x4 = new Point(-0.5, -0.5, 0.5).scale(size);
        Point x5 = new Point(0.5, -0.5, 0.5).scale(size);
        Point x6 = new Point(0.5, 0.5, 0.5).scale(size);
        Point x7 = new Point(-0.5, 0.5, 0.5).scale(size);

        sides.add(new Side(x0, x1, x2, x3, slices));
        sides.add(new Side(x4, x5, x6, x7, slices));

        sides.add(new Side(x0, x1, x5, x4, slices));
        sides.add(new Side(x2, x3, x7, x6, slices));
        sides.add(new Side(x0, x3, x7, x4, slices));
        sides.add(new Side(x2, x1, x5, x6, slices));
    }

    final private Point center = new Point(0, 0, 0);

    public void draw(GL2 gl, double state) {
        for (Side side : sides) {
            side.draw(gl, point -> {
                double initialLength = point.distanceTo(center);
                double finalLength = size / 2d;

                double fixedState = state;
                if (fixedState < 0) fixedState = 0;
                if (fixedState > 1) fixedState = 1;

                return point.scale(fixedState * (finalLength / initialLength - 1) + 1);
            });
        }
    }
}
