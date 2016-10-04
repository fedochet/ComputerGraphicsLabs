package org.anstreth.common.primitives;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Created by roman on 18.09.2016.
 */
public class Side {
    private final List<Strip> strips;

    public Side(Point x0, Point x1, Point x2, Point x3, int slices) {
        strips = new ArrayList<>();

        for (int i = 0; i < slices; i++) {
            Point p1 = new Point(x0.x + i * (x3.x - x0.x) / slices,
                    x0.y + i * (x3.y - x0.y) / slices,
                    x0.z + i * (x3.z - x0.z) / slices);
            Point p2 = new Point(x1.x + i * (x2.x - x1.x) / slices,
                    x1.y + i * (x2.y - x1.y) / slices,
                    x1.z + i * (x2.z - x1.z) / slices);
            Point p3 = new Point(x1.x + (i + 1) * (x2.x - x1.x) / slices,
                    x1.y + (i + 1) * (x2.y - x1.y) / slices,
                    x1.z + (i + 1) * (x2.z - x1.z) / slices);
            Point p4 = new Point(x0.x + (i + 1) * (x3.x - x0.x) / slices,
                    x0.y + (i + 1) * (x3.y - x0.y) / slices,
                    x0.z + (i + 1) * (x3.z - x0.z) / slices);

            strips.add(new Strip(p1, p2, p3, p4, slices));
        }
    }


    public void draw(GL2 gl, UnaryOperator<Point> pointMapper) {
        strips.forEach(strip -> strip.draw(gl, pointMapper));
    }

    public void draw(GL2 gl) {
        draw(gl, p->p);
    }
}
