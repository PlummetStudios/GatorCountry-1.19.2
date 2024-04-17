package com.plummetstudios.gatorcountry.entity.client;

import com.plummetstudios.gatorcountry.GatorCountry;
import com.plummetstudios.gatorcountry.entity.custom.AlligatorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AlligatorModel extends AnimatedGeoModel<AlligatorEntity> {
    private static final ResourceLocation TEXTURE_ORIGINAL = new ResourceLocation("gatorcountry:textures/entity/alligator.png");
    private static final ResourceLocation TEXTURE_SWAMPY = new ResourceLocation("gatorcountry:textures/entity/alligator_swampy.png");
    private static final ResourceLocation TEXTURE_BLACK = new ResourceLocation("gatorcountry:textures/entity/alligator_dark.png");
    private static final ResourceLocation TEXTURE_ALBINO = new ResourceLocation("gatorcountry:textures/entity/alligator_albino.png");
    @Override
    public ResourceLocation getModelResource(AlligatorEntity object) {
        return new ResourceLocation(GatorCountry.MOD_ID, "geo/alligator.geo.json");
    }

    // Get the Gator Texture
    @Override
    public ResourceLocation getTextureResource(AlligatorEntity object) {
        if (object.isSwampy()) {
            return TEXTURE_SWAMPY;
        }

        else {
            switch (object.gatorTexture()) {
                case 1:
                    return TEXTURE_BLACK;

                case 2:
                    return TEXTURE_ALBINO;

                default:
                    return TEXTURE_ORIGINAL;
            }
        }
    }

    @Override
    public ResourceLocation getAnimationResource(AlligatorEntity animatable) {
        return new ResourceLocation(GatorCountry.MOD_ID, "animations/alligator.animation.json");
    }

}
