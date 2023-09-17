package org.aren.particlefactory;


import org.aren.particlefactory.shapes.Shape;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ParticleScheduler extends BukkitRunnable {

    private JavaPlugin plugin;
//    hashMap 구조를 사용할 필요가 없습니다. 후에 List로 변경해주세요.
    private final HashMap<String, ParticleObject> activatedObjects = new HashMap<>();

    public ParticleScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public HashMap<String, ParticleObject> getObjects() {
        return activatedObjects;
    }

    public synchronized void runTaskTimer(long delay, long period) throws IllegalArgumentException, IllegalStateException {
        super.runTaskTimer(plugin, delay, period);
    }

    public void addObject(String name, ParticleObject particleObject) {
        particleObject.setActivate(true);
        activatedObjects.put(name, particleObject);
    }

    public void removeObject(String name) {
        try {
            ParticleObject particleObject = activatedObjects.get(name);
            particleObject.setActivate(false);
            activatedObjects.remove(name, particleObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeIfDeactivate() {
        try {
            activatedObjects.values().removeIf(val -> !val.isActivate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activateObject(String name) {
        setObjectsMode(name, true);
    }

    public void deactivateObject(String name) {
        setObjectsMode(name, false);
    }

    private void setObjectsMode(String name, boolean activate) {
        activatedObjects.get(name).setActivate(activate);
    }

    @Override
    public void run() {
        if (activatedObjects.isEmpty())
            return;

        for (ParticleObject particleObject : activatedObjects.values()) {

            if (!particleObject.isActivate())
                continue;

            Shape shape = particleObject.getShape();

            Particle particle = shape.getParticle();
            World w = shape.getLocation().getWorld();
            for (Location location : shape.getShapePosition()) {
                w.spawnParticle(particle, location, 1, 0, 0, 0, 0, null, true);
            }
        }

    }
}
