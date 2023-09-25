package org.aren.particlefactory;

import org.aren.particlefactory.shapes.Shape;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ParticleAnimation {

    private Shape changedShape;
    private int delay;

    public ParticleAnimation(Shape changedShape, int delay) {
        this.changedShape = changedShape;
        this.delay = delay;
    }

    public Shape getChangedShape() {
        return changedShape;
    }

    public int getDelay() {
        return delay;
    }
}
