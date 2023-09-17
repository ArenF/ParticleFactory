package org.aren.particlefactory.shapes;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Triangle extends Shape {

    public Triangle(Location location, Particle particle, double range, int multiply) {
        super(location, particle, range, multiply);
        this.setType(ShapeType.TRIANGLE);
    }

    @Override
    public List<Location> getShapePosition() {

        double height = range + (range/2);
        double base = (2 / Math.sqrt(3)) * height;

        Vector first = new Vector(0, 0, range);
        Vector second = new Vector(base/2, 0, -(range/2));
        Vector third = new Vector(-(base/2), 0, -(range/2));

        first = RotationMatrix.rotateFunction(first, location);
        second = RotationMatrix.rotateFunction(second, location);
        third = RotationMatrix.rotateFunction(third, location);

        Location firstLocation = new Location(location.getWorld(),
                location.getX() + first.getX(), location.getY() + first.getY(), location.getZ() + first.getZ());
        Location secondLocation = new Location(location.getWorld(),
                location.getX() + second.getX(), location.getY() + second.getY(), location.getZ() + second.getZ());
        Location thirdLocation = new Location(location.getWorld(),
                location.getX() + third.getX(), location.getY() + third.getY(), location.getZ() + third.getZ());

        List<Location> pointLocations = Arrays.asList(firstLocation, secondLocation, thirdLocation);

        List<Location> results = new ArrayList<>();

        for (int i = 0; i < pointLocations.size(); i++) {
            Vector from = pointLocations.get(i).clone().toVector();
            Vector to = pointLocations.get(i+1 >= pointLocations.size() ? 0 : i+1).clone().toVector();
            double distance = from.distance(to);
            Vector togo = to.clone().subtract(from).normalize();

            for (double len = 0.1; len < distance; len += distance/(120 / multiply)) {
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
