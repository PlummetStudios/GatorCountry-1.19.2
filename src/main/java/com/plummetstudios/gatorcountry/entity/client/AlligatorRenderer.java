package com.plummetstudios.gatorcountry.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.plummetstudios.gatorcountry.GatorCountry;
import com.plummetstudios.gatorcountry.entity.custom.AlligatorEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class AlligatorRenderer extends GeoEntityRenderer<AlligatorEntity> {
    public AlligatorRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AlligatorModel());
        this.shadowRadius = 0.3f;
    }
    @Override
    public void renderEarly(AlligatorEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        float scale = animatable.isBaby() ? 0.3F : 1F;
        stackIn.scale(scale, scale, scale);

    }
    @Override
    public RenderType getRenderType(AlligatorEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1.0f, 1.0f, 1.0f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}