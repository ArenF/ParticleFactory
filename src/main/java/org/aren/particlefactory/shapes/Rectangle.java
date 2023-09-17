package org.aren.particlefactory.shapes;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Rectangle extends Shape {


    public Rectangle(Location location, Particle particle, double range, int multiply) {
        super(location, particle, range, multiply);
        this.setType(ShapeType.RECTANGLE);
    }

    @Override
    public List<Location> getShapePosition() {

        List<Vector> points = Arrays.asList(
                new Vector(range, 0, range), new Vector(range, 0, -range),
                new Vector(-range, 0, -range), new Vector(-range, 0, range)
        );

        points.forEach(vector -> {
            vector = RotationMatrix.rotateFunction(vector, location);
        });

        List<Location> locations = new ArrayList<>();
        for (Vector vector : points) {
            locations.add(location.clone().add(vector));
        }

        List<Location> results = new ArrayList<>();

        for (int i = 0; i < locations.size(); i++) {
            Vector from = locations.get(i).toVector();
            Vector to = locations.get(i+1 >= locations.size() ? 0 : i+1).toVector();
            double distance = from.distance(to);
            Vector togo = to.clone().subtract(from).normalize();

            for (double len = 0.1; len < distance; len += distance/(90 / multiply)) {
                results.add(new Location(location.getWorld(),
                        from.getX() + (togo.getX() * len),
                        from.getY() + (togo.getY() * len),
                        from.getZ() + (togo.getZ() * len)
                ));
            }
        }

        return results;

    }
}
