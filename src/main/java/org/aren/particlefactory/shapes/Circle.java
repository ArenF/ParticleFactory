package org.aren.particlefactory.shapes;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.LinkedList;
import java.util.List;

public class Circle extends Shape {

    public Circle(Location location, Particle particle, double range, int multiply) {
        super(location, particle, range, multiply);
        setType(ShapeType.CIRCLE);

    }

    @Override
    public List<Location> getShapePosition() {

        List<Location> positions = new LinkedList<>();
        for (int i = 0; i < 360; i += multiply) {
            double x = range * Math.cos(Math.toRadians(i));
            double y = 0;
            double z = range * Math.sin(Math.toRadians(i));
            Vector vec = new Vector(x, y, z);
            vec = RotationMatrix.rotateFunction(vec, location);
            Location loc = new Location(location.getWorld(),
                    location.getX() + vec.getX(),
                    location.getY() + vec.getY(),
                    location.getZ() + vec.getZ());

            positions.add(loc);

        }

        return positions;
    }

}

