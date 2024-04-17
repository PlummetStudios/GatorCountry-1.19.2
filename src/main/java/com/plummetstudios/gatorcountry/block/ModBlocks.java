package com.plummetstudios.gatorcountry.block;

import com.plummetstudios.gatorcountry.GatorCountry;
import com.plummetstudios.gatorcountry.block.custom.GatorEggBlock;
import com.plummetstudios.gatorcountry.block.custom.ModFlammableRotatedPillarBlock;
import com.plummetstudios.gatorcountry.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GatorCountry.MOD_ID);


    public static final RegistryObject<Block> GATOR_NEST_BLOCK = registerBlock("gator_nest_block",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.ROOTED_DIRT)),
                    CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> GATOR_EGG = registerBlock("gator_egg",
            () -> new GatorEggBlock(BlockBehaviour.Properties.of(Material.EGG)
                    .strength(0f)), CreativeModeTab.TAB_MISC);

public static final RegistryObject<Block> GATOR_MEAT_BLOCK = registerBlock("gator_meat_block",
        () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.CALCITE)), CreativeModeTab.TAB_MISC);






    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus eventBus)
    {
BLOCKS.register(eventBus);
    }
}
