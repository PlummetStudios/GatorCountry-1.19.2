package com.plummetstudios.gatorcountry.entity.custom.ai;

import com.plummetstudios.gatorcountry.entity.custom.AlligatorEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

import java.util.Random;

public class BaskingGoal extends Goal {
    private final AlligatorEntity alligator;
    private float baskingTimer;

    private final Random rand = new Random();

    public BaskingGoal(AlligatorEntity alligator) {
        super();
        this.alligator = alligator; // Store the alligator instance
    }


    @Override
    public boolean canUse() {
        // Only start basking if the cooldown timer is zero and the alligator is not in water
        return alligator.getBaskingCooldownTimer() <= 0 && !alligator.isInWater();
    }

    @Override
    public void start() {
        baskingTimer = 100;
        alligator.getNavigation().stop();

        alligator.level.playSound(null, alligator.blockPosition(), SoundEvents.NOTE_BLOCK_BANJO, SoundSource.PLAYERS, 1f, 0.5F + rand.nextFloat());
    }

    @Override
    public void stop() {
        alligator.setBaskingCooldownTimer(3000);
    }

    @Override
    public void tick() {
        // Print the basking timer value for debugging
        System.out.println("Basking Timer: " + baskingTimer);

        if (baskingTimer > 0) {

            baskingTimer--;
        } else {
            stop();
            // If the basking timer reaches zero, stop basking

        }
    }
}