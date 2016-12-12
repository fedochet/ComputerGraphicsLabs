package org.anstreth.lab3;

class CoordsPair {
    float first;
    float second;

    CoordsPair(float first, float second) {
        this.first = first;
        this.second = second;
    }

    CoordsPair(CoordsPair pair) {
        this(pair.first, pair.second);
    }
}
