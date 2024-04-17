package com.plummetstudios.gatorcountry.item.custom;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


import java.util.Random;
import java.util.function.Consumer;

public class Banjo extends Item {
    public Banjo(Properties p_41383_) {
        super(p_41383_);
    }

    Random rand = new Random();

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand Hand) {
        worldIn.playSound(null, playerIn.blockPosition(), SoundEvents.NOTE_BLOCK_BANJO, SoundSource.PLAYERS, 1f, 0.5F + rand.nextFloat());
        addParticleEffect(ParticleTypes.NOTE, worldIn, playerIn.getX() - 0.5, playerIn.getY() + 1, playerIn.getZ() - 0.5);
        return super.use(worldIn, playerIn, Hand);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 150, 3));
        pTarget.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40));
        pAttacker.level.playSound(null, pAttacker.blockPosition(), SoundEvents.NOTE_BLOCK_BANJO, SoundSource.PLAYERS, 1.5f, 0.5F + rand.nextFloat());
        pAttacker.level.playSound(null, pAttacker.blockPosition(), SoundEvents.NOTE_BLOCK_BANJO, SoundSource.PLAYERS, 1.5f, 0.5F + rand.nextFloat());
        pAttacker.level.playSound(null, pAttacker.blockPosition(), SoundEvents.NOTE_BLOCK_BANJO, SoundSource.PLAYERS, 1.5f, 0.5F + rand.nextFloat());
        pAttacker.level.playSound(null, pAttacker.blockPosition(), SoundEvents.NOTE_BLOCK_BANJO, SoundSource.PLAYERS, 1.5f, 0.5F + rand.nextFloat());
        pAttacker.level.playSound(null, pAttacker.blockPosition(), SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, SoundSource.PLAYERS, 0.5f, 1F);
        addParticleEffect(ParticleTypes.NOTE, pTarget.level, pTarget.getX() - 0.5, pTarget.getY() + 1, pTarget.getZ() - 0.5);
        addParticleEffect(ParticleTypes.NOTE, pTarget.level, pTarget.getX() - 0.5, pTarget.getY() + 1, pTarget.getZ() - 0.5);
        addParticleEffect(ParticleTypes.NOTE, pTarget.level, pTarget.getX() - 0.5, pTarget.getY() + 1, pTarget.getZ() - 0.5);
        addParticleEffect(ParticleTypes.NOTE, pTarget.level, pTarget.getX() - 0.5, pTarget.getY() + 1, pTarget.getZ() - 0.5);
        addParticleEffect(ParticleTypes.NOTE, pTarget.level, pTarget.getX() - 0.5, pTarget.getY() + 1, pTarget.getZ() - 0.5);
        pStack.hurtAndBreak(1, pAttacker, (player) -> {
            player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });


        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }


    private void addParticleEffect(SimpleParticleType particleData, Level world, double x, double y, double z) {
        Random random = new Random();
        for (int i = 0; i < 1; ++i) {
            double d2 = random.nextGaussian() * 0.02D;
            double d3 = random.nextGaussian() * 0.02D;
            double d4 = random.nextGaussian() * 0.02D;
            double d6 = x + random.nextDouble();
            double d7 = y + random.nextDouble() * 0.5;
            double d8 = z + random.nextDouble();
            world.addParticle(particleData, d6, d7, d8, d2, d3, d4);
        }
    }
}