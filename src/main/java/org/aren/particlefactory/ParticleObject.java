package org.aren.particlefactory;

import org.aren.particlefactory.shapes.Shape;
import org.bukkit.Particle;

public class ParticleObject {

    private String name;
    private Shape shape;

    private boolean activate;

    public ParticleObject(Shape shape, String name) {
        this.shape = shape;
        this.name = name;
    }

    public Shape getShape() {
        return shape;
    }

    public String getName() {
        return name;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setActivate(boolean isActivate) {
        this.activate = isActivate;
    }
}
