package org.anstreth.coursework.common;

public class Triple {
    public final double x;
    public final double y;
    public final double z;

    public Triple(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Triple add(Triple right) {
        return new Triple(x + right.x, y + right.y, z + right.z);
    }
}
