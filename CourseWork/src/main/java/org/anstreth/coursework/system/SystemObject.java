package org.anstreth.coursework.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

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
abstract class SystemObject {
    public double x;
    public double y;
    public double z;

    public double xSpeed;
    public double ySpeed;
    public double zSpeed;

    public void setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setSpeed(double x, double y, double z) {
        xSpeed = x;
        ySpeed = y;
        zSpeed = z;
    }

    public abstract void draw(GL2 gl2, GLU glu, GLUT glut);
}
