package org.aren.particlefactory;

import org.aren.particlefactory.shapes.Shape;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

public class ParticleManager {

    private static ParticleManager instance;

    public static ParticleManager initialize(JavaPlugin main) {
        if (instance == null)
            instance = new ParticleManager(main);
        return instance;
    }

    public static ParticleManager getInstance() {
        return instance;
    }

    private JavaPlugin main;
    private ParticleScheduler particleScheduler;

    public ParticleManager(JavaPlugin main) {
        this.main = main;
        this.particleScheduler = new ParticleScheduler(main);
    }

    public void createParticleObject(Shape shape, String name) {
        ParticleObject particleObject = new ParticleObject(shape, name);

        particleScheduler.addObject(particleObject.getName(), particleObject);
        if (particleScheduler.isCancelled()) {
            particleScheduler.runTaskTimer(0, 1);
        }
    }

    public JavaPlugin getPlugin() {
        return main;
    }

    public ParticleScheduler getScheduler() {
        return particleScheduler;
    }


}
