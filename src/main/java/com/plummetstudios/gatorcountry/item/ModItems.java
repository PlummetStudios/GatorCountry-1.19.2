package com.plummetstudios.gatorcountry.item;

import com.plummetstudios.gatorcountry.GatorCountry;
import com.plummetstudios.gatorcountry.entity.ModEntityTypes;
import com.plummetstudios.gatorcountry.item.custom.Banjo;
import com.plummetstudios.gatorcountry.item.custom.GatorChow;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GatorCountry.MOD_ID);

    public static final RegistryObject<Item> ALLIGATOR_SPAWN_EGG = ITEMS.register("alligator_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.Alligator,0X738940,0XA6A15E,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> SLAB_OF_GATOR = ITEMS.register("slab_of_gator",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).stacksTo(16).food(ModFoods.SLAB_OF_GATOR)));

    public static final RegistryObject<Item> GATOR_GUMBO = ITEMS.register("gator_gumbo",
            () -> new BowlFoodItem(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).stacksTo(1).food(ModFoods.GATOR_GUMBO)));

    public static final RegistryObject<Item> BANJO = ITEMS.register("banjo",
            () -> new Banjo(new Item.Properties().tab(CreativeModeTab.TAB_MISC).durability(200)));
    public static final RegistryObject<Item> GATOR_CHOW = ITEMS.register("gator_chow",
            () -> new GatorChow(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
