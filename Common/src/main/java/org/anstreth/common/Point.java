package org.anstreth.common;

import static java.lang.Math.*;

/**
 * Created by roman on 18.09.2016.
 */
public class Point {
    public final double x, y, z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point scale(double d) {
        return new Point(x * d, y * d, z * d);
    }

    public double distanceTo(Point p) {
        return sqrt(pow(x - p.x, 2) + pow(y - p.y, 2) + pow(z - p.z, 2));
    }
}
