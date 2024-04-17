package com.plummetstudios.gatorcountry.entity;

import com.plummetstudios.gatorcountry.GatorCountry;
import com.plummetstudios.gatorcountry.entity.custom.AlligatorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GatorCountry.MOD_ID);


    public static final RegistryObject<EntityType<AlligatorEntity>> Alligator = ENTITY_TYPES.register("alligator",
            () -> EntityType.Builder.of(AlligatorEntity::new, MobCategory.CREATURE)
                    .sized(1.5f,1.5f)
                    .build(new ResourceLocation(GatorCountry.MOD_ID, "alligator").toString()));

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}
