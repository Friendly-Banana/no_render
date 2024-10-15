package me.banana.no_render;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

public class NoRenderConfig {
    public static NoRenderConfig CONFIG;
    public static ModConfigSpec CONFIG_SPEC;

    public static final HashSet<Class<? extends Entity>> hiddenTypes = new HashSet<>();
    public final ModConfigSpec.BooleanValue hideAllEntities;
    public final ModConfigSpec.BooleanValue hideItems;
    public final ModConfigSpec.BooleanValue hideItemframes;
    public final ModConfigSpec.BooleanValue hidePassiveMobs;
    public final ModConfigSpec.BooleanValue hideVillager;
    public final ModConfigSpec.BooleanValue hidePlayer;
    public final ModConfigSpec.ConfigValue<List<? extends String>> hiddenEntityIds;

    public final ModConfigSpec.BooleanValue skipLightUpdates;
    public final ModConfigSpec.BooleanValue hideBlocks;
    public final ModConfigSpec.BooleanValue hideBlockEntities;
    public final ModConfigSpec.BooleanValue hideGlobalBlockEntities;
    public final ModConfigSpec.BooleanValue hideSky;
    public final ModConfigSpec.BooleanValue hideTerrainFog;
    public final ModConfigSpec.BooleanValue hideParticles;

    public static final Predicate<Object> ENTITY_ID_PREDICATE = o -> o instanceof String entity && BuiltInRegistries.ENTITY_TYPE.containsKey(ResourceLocation.parse(entity));
    public static final Predicate<Entity> HIDE_ENTITY_PREDICATE = entity -> !CONFIG.hiddenEntityIds.get()
        .contains(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString()) && hiddenTypes.stream()
        .noneMatch(type -> type.isInstance(entity));

    NoRenderConfig(ModConfigSpec.Builder builder) {
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
        builder.comment("A list of entity ids to also hide. Entries in the list have the same format as in commands: namespace:id. The default namespace is minecraft.");
        hiddenEntityIds = builder.defineListAllowEmpty("hiddenEntityIds", Collections::emptyList, () -> "minecraft:", ENTITY_ID_PREDICATE);
        builder.pop();

        builder.push("World");
        hideBlocks = builder.define("hideBlocks", false);
        builder.comment("very specific things like the rotating entity in spawners");
        hideBlockEntities = builder.define("hideBlockEntities", false);
        builder.comment("BEs that also render when the block is offscreen like beacon beams or structure block markers");
        hideGlobalBlockEntities = builder.define("hideGlobalBlockEntities", false);
        hideSky = builder.define("hideSky", false);
        hideTerrainFog = builder.define("hideTerrainFog", false);
        hideParticles = builder.define("hideParticles", false);
        builder.pop();
    }

    public void onConfigReload() {
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
        Pair<NoRenderConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(NoRenderConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
