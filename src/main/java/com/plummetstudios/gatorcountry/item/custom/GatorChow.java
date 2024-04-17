package com.plummetstudios.gatorcountry.item.custom;

import com.mojang.authlib.minecraft.TelemetrySession;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.common.Tags;

public class GatorChow extends Item {
    public GatorChow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        LivingEntity livingEntity = (LivingEntity) entity;
        if (livingEntity instanceof TamableAnimal && ((TamableAnimal) livingEntity).getOwner() == player && !(livingEntity instanceof Parrot)) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 10));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 0));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0));
            if (!player.isCreative()) {
                stack.shrink(1);
                ItemStack BOWL = new ItemStack(Items.BOWL);
                if (player.addItem(BOWL)) {

                } else {
                    player.drop(BOWL, false);

                }

            }
            return true;
        }
        return false;
    }

}
