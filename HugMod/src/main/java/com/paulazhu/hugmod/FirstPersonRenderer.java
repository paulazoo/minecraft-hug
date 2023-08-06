package com.paulazhu.hugmod;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraft.client.renderer.LightTexture;

import com.paulazhu.hugmod.PlayerAnimationTrigger;

@Mod.EventBusSubscriber(modid = HugMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class FirstPersonRenderer {
    private static int tickTime = 0;

    @SubscribeEvent
    public static void onTick(TickEvent event) {
        if (!PlayerAnimationTrigger.isHugging) {
            return;
        } else {
            //otherwise increment counter
            tickTime++; // 1 minecraft tick = 50 ms supposedly
            if (tickTime > 200) {
                PlayerAnimationTrigger.isHugging = false;
                tickTime = 0;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPlayerHand(RenderArmEvent event) {
        if (!PlayerAnimationTrigger.isHugging || Minecraft.getInstance().options.getCameraType() != CameraType.FIRST_PERSON)
            return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        MultiBufferSource buffer = event.getMultiBufferSource();
        if (!(mc.getEntityRenderDispatcher()
                .getRenderer(player) instanceof PlayerRenderer pr))
            return;

        PlayerModel<AbstractClientPlayer> model = pr.getModel();
        //model.setAllVisible(true);
        model.attackTime = 0.0F;
        model.crouching = false;
        model.swimAmount = 0.0F;
        model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        ModelPart leftArmPart = model.leftArm;
        ModelPart rightArmPart = model.rightArm;

        leftArmPart.x = 5.0F; // negative is up
        leftArmPart.y = -5.0F; // positive is outward
        leftArmPart.z = -20.0F; // positive is rightward
        leftArmPart.yRot = 2.2F; // positive is clockwise
        rightArmPart.yRot = -1.0F;

        leftArmPart.render(event.getPoseStack(), event.getMultiBufferSource().getBuffer(RenderType.entitySolid(player.getSkinTextureLocation())), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        rightArmPart.render(event.getPoseStack(), event.getMultiBufferSource().getBuffer(RenderType.entitySolid(player.getSkinTextureLocation())), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        event.setCanceled(true);
    }

}