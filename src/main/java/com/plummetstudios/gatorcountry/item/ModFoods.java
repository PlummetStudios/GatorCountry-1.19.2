package com.plummetstudios.gatorcountry.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties SLAB_OF_GATOR = (new FoodProperties.Builder()).nutrition(14).saturationMod(15.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 0), 1.0F).meat().build();

    public static final FoodProperties GATOR_GUMBO = (new FoodProperties.Builder()).nutrition(20).saturationMod(15.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 0), 1.0F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0),1.0F).meat().build();
}
