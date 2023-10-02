package com.paulazhu.hugmod.localanimations;

import com.paulazhu.hugmod.HugMod;
import com.paulazhu.hugmod.event.ClientEvents;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HugMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ThirdPersonRenderer {

    @SubscribeEvent
    public static void onTick(TickEvent event) {
        if (!ClientEvents.isHugging)
            return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer selfPlayer = mc.player;

        var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) selfPlayer).get(new ResourceLocation(HugMod.MODID, "animation"));
        if (animation != null) {
            animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("hugmod", "hugging"))));
            //You might use animation.replaceAnimationWithFade(); to create fade effect instead of sudden change
            //See javadoc for details
        }

    }

}