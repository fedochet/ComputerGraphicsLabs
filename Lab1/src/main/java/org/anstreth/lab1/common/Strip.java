package org.anstreth.lab1.common;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

import static com.jogamp.opengl.GL2GL3.GL_DOUBLE;

/**
 * Created by roman on 18.09.2016.
 */
public class Strip {
    private final List<Point> points;

    public Strip(Point x0, Point x1, Point x2, Point x3, int slices) {
        points = new ArrayList<>();

        for (int i = 0; i < slices + 1; i++) {
            points.add(new Point(x0.x + i * (x1.x - x0.x) / slices,
                    x0.y + i * (x1.y - x0.y) / slices,
                    x0.z + i * (x1.z - x0.z) / slices));

            points.add(new Point(x3.x + i * (x2.x - x3.x) / slices,
                    x3.y + i * (x2.y - x3.y) / slices,
                    x3.z + i * (x2.z - x3.z) / slices));
        }
    }

    public void draw(GL2 gl) {
        draw(gl, p -> p);
    }

    public void draw(GL2 gl, UnaryOperator<Point> pointMapper) {
        double[] coords = points.stream()
                .map(pointMapper)
                .flatMapToDouble(point -> Arrays.stream(new double[]{point.x, point.y, point.z}))
                .toArray();

        DoubleBuffer coordsBuffers = Buffers.newDirectDoubleBuffer(coords);
        gl.glVertexPointer(3, GL_DOUBLE, 0, coordsBuffers);
        gl.glDrawArrays(GL.GL_TRIANGLE_STRIP, 0, coords.length / 3);
    }
}
