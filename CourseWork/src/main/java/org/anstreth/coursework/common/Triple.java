package org.anstreth.coursework.common;

import java.util.Arrays;

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

    public Triple divide(double divisor) {
        return new Triple(x/divisor, y/divisor, z/divisor);
    }

    public Triple substract(Triple right) {
        return add(right.inverse());
    }

    public Triple inverse() {
        return new Triple(-x, -y, -z);
    }

    public double getLength() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Triple multiply(double multiplyer) {
        return new Triple(x*multiplyer, y*multiplyer, z*multiplyer);
    }
}
