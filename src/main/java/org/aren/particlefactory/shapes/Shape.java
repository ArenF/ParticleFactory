package org.aren.particlefactory.shapes;

import org.bukkit.Location;
import org.bukkit.Particle;

public abstract class Shape implements ShapeMap {

    protected ShapeType type;
    protected Location location;
    private Particle particle;
    protected int multiply;
    protected double range;

    public Shape(Location location, Particle particle, double range, int multiply) {
        this.location = location;
        this.particle = particle;
        this.range = range;
        this.multiply = multiply;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setMultiply(int multiply) {
        this.multiply = multiply;
    }

    public void setType(ShapeType type) {
        this.type = type;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public ShapeType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public Particle getParticle() {
        return particle;
    }

    public int getMultiply() {
        return multiply;
    }

    public double getRange() {
        return range;
    }
}
