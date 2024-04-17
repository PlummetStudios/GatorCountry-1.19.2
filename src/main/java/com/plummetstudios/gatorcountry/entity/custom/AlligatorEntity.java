package com.plummetstudios.gatorcountry.entity.custom;

import com.plummetstudios.gatorcountry.entity.custom.ai.BaskingGoal;
import com.plummetstudios.gatorcountry.item.ModItems;
import com.plummetstudios.gatorcountry.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Random;
import java.util.UUID;

public class AlligatorEntity extends Animal implements IAnimatable {
    int Variant;
    String attackMob;
    private final Random rand = new Random();
    private int ticksSinceLastDecrement;
    UUID playerUUID;
    int happinessMeter;

    private final EntityDataAccessor<Integer> baskingCooldownTimer = SynchedEntityData.defineId(AlligatorEntity.class, EntityDataSerializers.INT);

    boolean basking;


    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.CHICKEN.asItem());

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);


    public AlligatorEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
        this.setGatorTexture();
        this.entityData.define(baskingCooldownTimer, 0); // Assuming initial value is 0


    }

    public void setGatorTexture() {
        float changeVariant = this.getRandom().nextFloat();

        if (changeVariant >= 0.35) {
            if (changeVariant >= 1) {
                Variant = 2;
            } else {
                Variant = 0;
            }
        } else if (changeVariant <= 0.35) {
            Variant = 1;
        }
    }
    public int getBaskingCooldownTimer() {
        return this.entityData.get(baskingCooldownTimer);
    }

    public void setBaskingCooldownTimer(int newTime) {
        this.entityData.set(baskingCooldownTimer, newTime);
        if (newTime <= 0) {
            basking = true;
        }
    }
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    // Set and Read NBT Data
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.Variant);
        if (this.attackMob != null) {
            compound.putString("attackMob", this.attackMob);
        }
        if (this.playerUUID != null) {
            compound.putUUID("playerUUID", this.playerUUID);
        }
        compound.putInt("happinessMeter", this.happinessMeter);
        compound.putInt("ticksSinceLastDecrement", this.ticksSinceLastDecrement);
        compound.putInt("baskingCooldownTimer", this.getBaskingCooldownTimer());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.Variant = compound.getInt("Variant");
        if (this.attackMob != null) {
            this.attackMob = compound.getString("attackMob");
        }
        this.happinessMeter = compound.getInt("happinessMeter");
        if (this.attackMob != null) {
            this.playerUUID = compound.getUUID("playerUUID");
        }
        this.ticksSinceLastDecrement = compound.getInt("ticksSinceLastDecrement");
        this.setBaskingCooldownTimer(compound.getInt("baskingCooldownTimer"));
    }

    // Set Attributes
    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0f)
                .add(Attributes.FOLLOW_RANGE, 15)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9F)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // Swim randomly when in water
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0D, 1) {
            @Override
            public boolean canUse() {
                return isInWater() && super.canUse();
            }
        });
        // Stroll on land
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.7D) {
            @Override
            public boolean canUse() {
                return isOnGround() && getBaskingCooldownTimer() > 0 && super.canUse();
            }
        });

        this.goalSelector.addGoal(4, new BaskingGoal(this));

        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Skeleton.class, 10, true, false, (entity) -> !this.isBaby()) {
            @Override
            public boolean canUse() {
                return attackMob == "skeleton" && super.canUse();
            }
        });

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie.class, 10, true, false, (entity) -> !this.isBaby()) {
            @Override
            public boolean canUse() {
                return attackMob == "zombie" && super.canUse();
            }
        });

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (entity) -> !this.isBaby() && !entity.getUUID().equals(playerUUID)) {
            @Override
            public boolean canUse() {
                return attackMob == "player" && super.canUse();
            }
        });

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, WitherSkeleton.class, 10, true, false, (entity) -> !this.isBaby()) {
            @Override
            public boolean canUse() {
                return attackMob == "wither_skeleton" && super.canUse();
            }
        });

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Creeper.class, 10, true, false, (entity) -> !this.isBaby()) {
            @Override
            public boolean canUse() {
                return attackMob == "creeper" && super.canUse();
            }
        });


    }


    public int gatorTexture() {
        return this.Variant;
    }

    @Override
    public void tick() {
        super.tick();
        System.out.println(this.getBaskingCooldownTimer());

        if (this.getBaskingCooldownTimer() > 0) {
            this.setBaskingCooldownTimer(this.getBaskingCooldownTimer() - 1);
        }

        // Gradually decrease happiness based on environmental conditions
        BlockPos blockPos = this.blockPosition();
        Biome biome = this.level.getBiome(blockPos).value();
        if (!this.level.canSeeSky(blockPos)) {
            // If not exposed to sunlight, decrease by 5
            if (this.happinessMeter > 0 && ticksSinceLastDecrement >= 400) { // 20 ticks * 60 seconds = 1200 ticks
                this.happinessMeter -= 5;
                ticksSinceLastDecrement = 0;
            }
        }
        if (!isInWarmBiome()) {
            // If in a cold biome, decrease by 10
            if (this.happinessMeter > 0 && ticksSinceLastDecrement >= 400) {
                this.happinessMeter -= 10;
                ticksSinceLastDecrement = 0;
            }
        }



        // Increment the tick counter
        ticksSinceLastDecrement++;
    }

    private boolean isInWarmBiome() {
        int i = Mth.floor(this.getX());
        int j = Mth.floor(this.getY());
        int k = Mth.floor(this.getZ());
        BlockPos blockpos = new BlockPos(i, j, k);
        Biome biome = this.level.getBiome(blockpos).value();
        return !biome.coldEnoughToSnow(blockpos);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));

        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));


    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            if (this.isInWater()) {

                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alligator.swim", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (this.getTarget() != null) {

                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alligator.charge", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else {

                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alligator.walk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        } else if (isInWater() && !event.isMoving()) {

            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alligator.swimidle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;

        }

        if (this.getBaskingCooldownTimer() <= 0)
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alligator.basking_with_mouth_open", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.alligator.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().playOnce("animation.alligator.thrash"));

            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }


    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (pPlayer.getItemInHand(pHand).getItem() == ModItems.BANJO.get()) {

            // Spawn note particles at the alligator's position
            for (int i = 0; i < happinessMeter / 10; i++) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.NOTE, this.getX() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getY() + 1.0D + (double) (this.random.nextFloat() * this.getBbHeight()), this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), d0, d1, d2);
            }
            this.level.playSound(null, pPlayer.blockPosition(), SoundEvents.NOTE_BLOCK_BANJO, SoundSource.PLAYERS, 1f, 0.5F + rand.nextFloat());

            // Check if happiness meter is below 100 before incrementing
            if (happinessMeter < 100) {
                happinessMeter += 10;

                // Check if happiness meter reaches 100 after increment
                if (happinessMeter >= 100) {
                    if (happinessMeter <= 200) {
                        happinessMeter += 10;
                    }
                    // Spawn heart particles
                    for (int i = 0; i < 10; i++) {
                        double d0 = this.random.nextGaussian() * 0.02D;
                        double d1 = this.random.nextGaussian() * 0.02D;
                        double d2 = this.random.nextGaussian() * 0.02D;
                        this.level.addParticle(ParticleTypes.HEART, this.getX() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), this.getY() + 1.0D + (double) (this.random.nextFloat() * this.getBbHeight()), this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(), d0, d1, d2);
                    }
                }
            }

            return InteractionResult.SUCCESS;
        }

        if (pPlayer.getItemInHand(pHand).getItem() == Items.ZOMBIE_HEAD && happinessMeter >= 100) {
            attackMob = "zombie";
            pPlayer.level.playSound(null, pPlayer.blockPosition(), SoundEvents.ZOMBIE_DEATH, SoundSource.PLAYERS, 1.5f, 1);
            return InteractionResult.SUCCESS;
        } else if (pPlayer.getItemInHand(pHand).getItem() == Items.SKELETON_SKULL && happinessMeter >= 100) {
            attackMob = "skeleton";
            pPlayer.level.playSound(null, pPlayer.blockPosition(), SoundEvents.SKELETON_DEATH, SoundSource.PLAYERS, 1.5f, 1);
            return InteractionResult.SUCCESS;
        } else if (pPlayer.getItemInHand(pHand).getItem() == Items.CREEPER_HEAD && happinessMeter >= 100) {
            attackMob = "creeper";
            pPlayer.level.playSound(null, pPlayer.blockPosition(), SoundEvents.CREEPER_DEATH, SoundSource.PLAYERS, 1.5f, 1);
            return InteractionResult.SUCCESS;
        } else if (pPlayer.getItemInHand(pHand).getItem() == Items.WITHER_SKELETON_SKULL && happinessMeter >= 100) {
            attackMob = "wither_skeleton";
            pPlayer.level.playSound(null, pPlayer.blockPosition(), SoundEvents.WITHER_SKELETON_DEATH, SoundSource.PLAYERS, 1.5f, 1);
            return InteractionResult.SUCCESS;
        } else if (pPlayer.getItemInHand(pHand).getItem() == Items.PLAYER_HEAD && happinessMeter >= 100) {
            attackMob = "player";
            playerUUID = pPlayer.getUUID();
            pPlayer.level.playSound(null, pPlayer.blockPosition(), SoundEvents.PLAYER_DEATH, SoundSource.PLAYERS, 1.5f, 1);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
    // Sound methods remain unchanged

    public boolean isPushedByFluid() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.ALLIGATOR_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.ALLIGATOR_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ALLIGATOR_HURT.get();
    }

    public boolean isSwampy() {
        String name = this.getName() != null ? this.getName().getString() : "";
        return name != null && name.toLowerCase().contains("swampy");
    }
}