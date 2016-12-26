package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import org.anstreth.coursework.common.Triple;

/*
координаты, скорость, размер, цвет
(с возможным добавлением текстуры и прозрачности),
время жизни, след от частицы.
Параметры могут зависеть друг от друга
(например, цвет частицы изменяется в зависимости
от времени жизни).
По окончании времени жизни частица исчезает
и порождается новая частица.
Тем самым система становится цикличной.

 */
abstract class SystemObject implements GLDrawable {
    Triple position;

    Triple speed;

    void setPosition(Triple position) {
        this.position = position;
    }

    void setSpeed(Triple speed) {
        this.speed = speed;
    }

    void timeStep() {
        position = position.add(speed);
    }

}
