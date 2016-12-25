package org.anstreth.lab4;

class CoordsPair {
    float first;

    public void setFirst(float first) {
        this.first = first;
    }

    public void setSecond(float second) {
        this.second = second;
    }

    float second;

    CoordsPair(float first, float second) {
        this.first = first;
        this.second = second;
    }

    CoordsPair(CoordsPair pair) {
        this(pair.first, pair.second);
    }
}
