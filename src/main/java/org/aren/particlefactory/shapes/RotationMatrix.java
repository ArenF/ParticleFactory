package org.aren.particlefactory.shapes;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class RotationMatrix {

    public static Vector rotateFunction(Vector v, Location loc) {
        double yawR = loc.getYaw()/180.0*Math.PI;
        double pitchR = loc.getPitch()/180.0*Math.PI;

        v = rotateAboutX(v, pitchR);
        v = rotateAboutY(v, yawR);
        return v;
    }

    public static Vector rotateAboutX(Vector vector, double a) {
        double y = Math.cos(a) * vector.getY() - Math.sin(a) * vector.getZ();
        double z = Math.sin(a) * vector.getY() + Math.cos(a) * vector.getZ();
        return vector.setY(y).setZ(z);
    }

    public static Vector rotateAboutY(Vector vector, double b) {
        double x = Math.cos(b) * vector.getX() + Math.sin(b) * vector.getZ();
        double z = - Math.sin(b) * vector.getX() + Math.cos(b) * vector.getZ();
        return vector.setX(x).setZ(z);
    }

    public static Vector rotateAboutZ(Vector vector, double c) {
        double x = Math.cos(c) * vector.getX() - Math.sin(c) * vector.getY();
        double y = Math.sin(c) * vector.getX() + Math.cos(c) * vector.getY();
        return vector.setX(x).setY(y);
    }
}
