package org.aren.particlefactory;

import org.aren.particlefactory.shapes.Shape;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ParticleAnimator extends BukkitRunnable {

    List<ParticleAnimation> particleAnimations = new ArrayList<>();

    private JavaPlugin plugin;
    private long period;

    private ParticleObject particleObject;

    private int index = 0;
    private int delayed = 0;

    public ParticleAnimator(JavaPlugin plugin, ParticleObject particleObject, long period) {
        this.plugin = plugin;
        this.particleObject = particleObject;
        this.period = period;
    }

    public void playAnimation() {
        this.runTaskTimer(plugin, 0, period);
    }

    public void addAnimation(ParticleAnimation particleAnimation) {
        particleAnimations.add(particleAnimation);
    }

    public void removeAnimation(int index) {
        particleAnimations.remove(index);
    }
    public void removeIfAnimation(Predicate<? super ParticleAnimation> removeIf) {
        particleAnimations.removeIf(removeIf);
    }

    private List<ParticleAnimation> getParticleAnimations() {
        return particleAnimations;
    }

    @Override
    public void run() {
        if (delayed > 0) {
            delayed--;
            return;
        }

        if (index >= particleAnimations.size() - 1)
            this.cancel();

        ParticleAnimation pa = particleAnimations.get(index);
        Shape shape = pa.getChangedShape();

        particleObject.setShape(shape);
        delayed += pa.getDelay();

        index++;
    }
}
