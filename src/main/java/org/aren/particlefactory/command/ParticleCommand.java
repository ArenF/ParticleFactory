package org.aren.particlefactory.command;

import com.google.common.collect.Lists;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.*;
import dev.jorel.commandapi.wrappers.FloatRange;
import dev.jorel.commandapi.wrappers.ParticleData;
import dev.jorel.commandapi.wrappers.Rotation;
import net.kyori.adventure.text.Component;
import org.aren.particlefactory.*;
import org.aren.particlefactory.shapes.*;
import org.aren.particlefactory.shapes.Shape;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ParticleCommand {


    public static List<CommandAPICommand> commands() {
        List<CommandAPICommand> apiCommands = new ArrayList<>();
        apiCommands.add(particleCommand());
        return apiCommands;
    }

    public static CommandAPICommand particleCommand() {

        List<String> types = Arrays.stream(ShapeType.values()).map(ShapeType::toString).map(String::toUpperCase).collect(Collectors.toList());

        return new CommandAPICommand("particlefactory")
                .withSubcommands(
                        new CommandAPICommand("create")
                                .withSubcommands(
                                        new CommandAPICommand("particle")
                                                .withArguments(
                                                        new StringArgument("shape").replaceSuggestions(ArgumentSuggestions.strings(types)),
                                                        new StringArgument("name"),
                                                        new ParticleArgument("particle"),
                                                        new DoubleArgument("range"),
                                                        new IntegerArgument("multiply"),
                                                        new LocationArgument("location")
                                                ).withOptionalArguments(
                                                        new DoubleArgument("pitch"),
                                                        new DoubleArgument("yaw")
                                                ).executes((player, args) -> {

                                                    String type = (String) args.get("shape");
                                                    String name = (String) args.get("name");
                                                    ParticleData<?> particleData = (ParticleData<?>) args.get("particle");
                                                    Double range = (Double) args.get("range");
                                                    Integer multiply = (Integer) args.get("multiply");
                                                    Location location = (Location) args.get("location");
                                                    Double pitch = (Double) args.getOptional("pitch").orElse(0.0);
                                                    Double yaw = (Double) args.getOptional("yaw").orElse(0.0);

                                                    location.setPitch(pitch.floatValue());
                                                    location.setYaw(yaw.floatValue());

                                                    Shape shape = (new ShapeFactory(location, particleData.particle(), range, multiply)).create(type);

                                                    ParticleManager particleManager = ParticleManager.getInstance();
                                                    particleManager.createParticleObject(shape, name);


                                                }),
                                        new CommandAPICommand("animation")
                                                .withArguments(
                                                        new StringArgument("name"),
                                                        new IntegerArgument("each delay"),
                                                        new ParticleArgument("particle"),
                                                        new DoubleArgument("range"),
                                                        new IntegerArgument("multiply")
                                                ).withOptionalArguments(
                                                        new LocationArgument("to"),
                                                        new RotationArgument("rotate")
                                                ).executes((player, args) -> {
                                                    String name = (String) args.get("name");
                                                    Integer delay = (Integer) args.get("each delay");
                                                    ParticleObject particleObject = ParticleManager.getInstance().getScheduler().getObjects().get(name);
                                                    Particle particle = ((ParticleData<?>) args.get("particle")).particle();
                                                    Double range = (Double) args.get("range");
                                                    Integer multiply = (Integer) args.get("multiply");

                                                    Location objLoc = particleObject.getShape().getLocation();
                                                    Location loc = (Location) args.getOptional("to").orElse(objLoc);
                                                    Rotation rot = (Rotation) args.getOptional("rotate").orElse(new Rotation(objLoc.getPitch(), objLoc.getYaw()));

                                                    @NotNull Vector from = objLoc.toVector();
                                                    @NotNull Vector to = loc.toVector();
                                                    double distance = objLoc.distance(loc);
                                                    Vector togo = to.clone().subtract(from).normalize();

                                                    ParticleAnimator particleAnimator = new ParticleAnimator(ParticleFactory.getPlugin(), particleObject, 2);

                                                    for (double i = 0; i < distance; i += distance/10) {
                                                        float pitch = rot.getPitch()/10;
                                                        float yaw = rot.getYaw()/10;
                                                        Location locatogo = new Location(objLoc.getWorld(),
                                                                objLoc.getX() + (togo.getX()*i),
                                                                objLoc.getY() + (togo.getY()*i),
                                                                objLoc.getZ() + (togo.getZ()*i),
                                                                yaw, pitch);

                                                        Shape shape = (new ShapeFactory(locatogo, particle, range, multiply).create(particleObject.getShape().getType().name()));
                                                        particleAnimator.addAnimation(new ParticleAnimation(shape, delay));
                                                    }

                                                    particleAnimator.playAnimation();

                                                })
                                ),
                        new CommandAPICommand("remove")
                                .withArguments(
                                        new StringArgument("name")
                                )
                                .executes((player, args) -> {
                                    String name = (String) args.get("name");
                                    ParticleScheduler scheduler = ParticleManager.getInstance().getScheduler();
                                    scheduler.removeObject(name);

                                }),
                        new CommandAPICommand("set")
                                .withSubcommands(
                                        new CommandAPICommand("shape")
                                                .withArguments(

                                                        new StringArgument("name"),
                                                        new StringArgument("shape type")
                                                                .replaceSuggestions(ArgumentSuggestions.strings(types)),
                                                        new DoubleArgument("range"),
                                                        new IntegerArgument("multiply")
                                                )
                                                .withOptionalArguments(
                                                        new LocationArgument("location"),
                                                        new ParticleArgument("particle")
                                                )
                                                .executes((player, args) -> {
                                                    try {
                                                        String name = (String) args.get("name");
                                                        String type = ((String) args.get("shape type")).toUpperCase();
                                                        Double range = (Double) args.get("range");
                                                        Integer multiply = (Integer) args.get("multiply");

                                                        ParticleScheduler scheduler = ParticleManager.getInstance().getScheduler();
                                                        ParticleObject particleObject = scheduler.getObjects().get(name);

                                                        Location location = (Location) args.getOptional("location")
                                                                .orElse(particleObject.getShape().getLocation());
                                                        Particle particle = ((ParticleData<?>) args.getOptional("particle")
                                                                .orElse(
                                                                        new ParticleData<>(particleObject.getShape().getParticle(), particleObject.getShape().getParticle().getDataType()
                                                                ))).particle();

                                                        Shape shape = (new ShapeFactory(location, particle, range, multiply)).create(type);
                                                        particleObject.setShape(shape);


                                                    } catch (Exception e) {
                                                        player.sendMessage(Component.text(e.getMessage()));
                                                    }

                                                }),
                                        new CommandAPICommand("rotate")
                                                .withArguments(
                                                        new StringArgument("name")
                                                ).withOptionalArguments(
                                                        new FloatArgument("pitch"),
                                                        new FloatArgument("yaw")
                                                )
                                                .executes((player, args) -> {
                                                    try {
                                                        ParticleScheduler ps = ParticleManager.getInstance().getScheduler();

                                                        String name = (String) args.get("name");

                                                        Shape shape = ps.getObjects().get(name).getShape();
                                                        Location loc = shape.getLocation();

                                                        Float pitch = (Float) args.getOptional("pitch").orElseGet(loc::getPitch);
                                                        Float yaw = (Float) args.getOptional("yaw").orElseGet(loc::getYaw);


                                                        loc.setPitch(pitch);
                                                        loc.setYaw(yaw);
                                                        shape.setLocation(loc);


                                                    } catch (Exception e) {
                                                        player.sendMessage(Component.text(Color.RED + "명령어 처리 중 오류가 발생하였습니다."));
                                                        player.sendMessage(Component.text(e.getMessage()));
                                                    }
                                                })
                                )

                );


    }


}
