package me.banana.no_render;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;

public class NoRenderConfig {
    public static NoRenderConfig CONFIG;
    public static ForgeConfigSpec GENERAL_SPEC;

    public static final HashSet<Class<? extends Entity>> hiddenTypes = new HashSet<>();
    public final ForgeConfigSpec.BooleanValue hideAllEntities;
    public final ForgeConfigSpec.BooleanValue hideItems;
    public final ForgeConfigSpec.BooleanValue hideItemframes;
    public final ForgeConfigSpec.BooleanValue hidePassiveMobs;
    public final ForgeConfigSpec.BooleanValue hideVillager;
    public final ForgeConfigSpec.BooleanValue hidePlayer;

    public final ForgeConfigSpec.BooleanValue skipLightUpdates;
    public final ForgeConfigSpec.BooleanValue hideBlocks;
    public final ForgeConfigSpec.BooleanValue hideBlockEntities;
    public final ForgeConfigSpec.BooleanValue hideGlobalBlockEntities;
    public final ForgeConfigSpec.BooleanValue hideSky;
    public final ForgeConfigSpec.BooleanValue hideTerrainFog;
    public final ForgeConfigSpec.BooleanValue hideParticles;

    NoRenderConfig(ForgeConfigSpec.Builder builder) {
        builder.push("General");
        skipLightUpdates = builder.define("skipLightUpdates", false);
        builder.pop();

        builder.push("Entities");
        hideAllEntities = builder.define("hideAllEntities", false);
        hideItems = builder.define("hideItems", false);
        hideItemframes = builder.define("hideItemframes", false);
        hidePassiveMobs = builder.define("hidePassiveMobs", false);
        hideVillager = builder.define("hideVillager", false);
        hidePlayer = builder.define("hidePlayer", false);
        builder.pop();

        builder.push("World");
        hideBlocks = builder.define("hideBlocks", false);
        hideBlockEntities = builder.comment("very specific things like the rotating entity in spawners").define("hideBlockEntities", false);
        hideGlobalBlockEntities = builder.comment("BEs that render offscreen like beacon beams and structure block position marker")
            .define("hideGlobalBlockEntities", false);
        hideSky = builder.define("hideSky", false);
        hideTerrainFog = builder.define("hideTerrainFog", false);
        hideParticles = builder.define("hideParticles", false);
        builder.pop();
    }

    void onConfigReload() {
        // update hidden entity types
        hiddenTypes.clear();
        if (hideItems.get()) {
            hiddenTypes.add(ItemEntity.class);
        }
        if (hideItemframes.get()) {
            hiddenTypes.add(ItemFrame.class);
        }
        if (hidePassiveMobs.get()) {
            hiddenTypes.add(WaterAnimal.class);
            hiddenTypes.add(IronGolem.class);
            hiddenTypes.add(Animal.class);
            hiddenTypes.add(Allay.class);
            hiddenTypes.add(AmbientCreature.class);
        }
        if (hideVillager.get()) {
            hiddenTypes.add(Villager.class);
        }
        if (hidePlayer.get()) {
            hiddenTypes.add(Player.class);
        }
    }

    static {
        Pair<NoRenderConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(NoRenderConfig::new);
        CONFIG = pair.getLeft();
        GENERAL_SPEC = pair.getRight();
    }
}
