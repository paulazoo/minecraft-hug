package com.paulazhu.hugmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.ItemInHandRenderer;

import com.paulazhu.hugmod.PlayerAnimationTrigger;

@EventBusSubscriber(value = Dist.CLIENT)
public class FirstPersonRenderer {

    //private static final ResourceLocation ARM_PNG_LOCATION =
    //        Create.asResource("textures/models/arm_png.png");

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPlayerHand(RenderArmEvent event) {
        System.out.println(PlayerAnimationTrigger.isHugging);
        if (!PlayerAnimationTrigger.isHugging)
            return;
        System.out.println("detected is hugging");

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
        // rendering whatever arm is not the main arm, so that ultimately both arms are rendered for hugging
        //ModelPart armPart = event.getArm() == HumanoidArm.LEFT ? model.rightSleeve : model.leftSleeve;
        ModelPart leftArmPart = model.leftSleeve;
        ModelPart rightArmPart = model.rightSleeve;
        System.out.println(leftArmPart.visible);
        System.out.println(rightArmPart.visible);
        System.out.println(event.getPoseStack());
        System.out.println(event.getMultiBufferSource().getBuffer(RenderType.solid()));
        System.out.println(event.getPackedLight());
        //armPart.xRot = 0.0F;
        // armPart.render(event.getPoseStack(), buffer.getBuffer(RenderType.entitySolid(ARM_PNG_LOCATION)),
        //        LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        leftArmPart.render(event.getPoseStack(), event.getMultiBufferSource().getBuffer(RenderType.lines()), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        event.setCanceled(true);
        rightArmPart.render(event.getPoseStack(), event.getMultiBufferSource().getBuffer(RenderType.lines()), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        event.setCanceled(true);
    }

}