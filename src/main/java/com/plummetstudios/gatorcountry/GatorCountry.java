package com.plummetstudios.gatorcountry;

import com.mojang.logging.LogUtils;
import com.plummetstudios.gatorcountry.block.ModBlocks;
import com.plummetstudios.gatorcountry.entity.ModEntityTypes;
import com.plummetstudios.gatorcountry.entity.client.AlligatorRenderer;
import com.plummetstudios.gatorcountry.item.ModItems;
import com.plummetstudios.gatorcountry.sound.ModSounds;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GatorCountry.MOD_ID)
public class GatorCountry
{
    public static final String MOD_ID = "gatorcountry";
    private static final Logger LOGGER = LogUtils.getLogger();


    public GatorCountry()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntityTypes.Alligator.get(), AlligatorRenderer::new);
        }
    }
}
