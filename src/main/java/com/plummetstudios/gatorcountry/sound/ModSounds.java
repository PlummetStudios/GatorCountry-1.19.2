package com.plummetstudios.gatorcountry.sound;

import com.plummetstudios.gatorcountry.GatorCountry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GatorCountry.MOD_ID);

    public static final RegistryObject<SoundEvent> ALLIGATOR_IDLE = registerSoundEvents("alligator_idle");

    public static final RegistryObject<SoundEvent> ALLIGATOR_HURT = registerSoundEvents("alligator_hurt");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {

        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(GatorCountry.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus)
    {
        SOUND_EVENTS.register(eventBus);
    }
}
