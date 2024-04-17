package com.plummetstudios.gatorcountry.event;

import com.plummetstudios.gatorcountry.GatorCountry;
import com.plummetstudios.gatorcountry.entity.ModEntityTypes;
import com.plummetstudios.gatorcountry.entity.custom.AlligatorEntity;
import com.plummetstudios.gatorcountry.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = GatorCountry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ModEventBusEvents {
            @SubscribeEvent
            public static void registerAttributes(EntityAttributeCreationEvent event) {
                event.put(ModEntityTypes.Alligator.get(), AlligatorEntity.setAttributes());

            }}
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event)
    {
        if(event.getType() == VillagerProfession.BUTCHER)
        {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.SLAB_OF_GATOR.get(), 3),
                    new ItemStack(Items.EMERALD, 15),
                    10, 8, 0.02f
            ));

        }

    }
}

