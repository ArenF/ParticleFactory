package org.aren.particlefactory.shapes;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ShapeFactory {

    private Location location;
    private Particle particle;
    private int multiply;
    private double range;

    public ShapeFactory(Location location, Particle particle, double range, int multiply) {
        this.location = location;
        this.particle = particle;
        this.range = range;
        this.multiply = multiply;
    }

    public Shape create(String type) {
        switch (type) {
            case "CIRCLE":
                return new Circle(location, particle, range, multiply);
            case "TRIANGLE":
                return new Triangle(location, particle, range, multiply);
            case "RECTANGLE":
                return new Rectangle(location, particle, range, multiply);

        }
        return new Circle(location, particle, range, multiply);
    }
}
