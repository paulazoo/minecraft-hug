package com.paulazhu.hugmod;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.item.Item;

@Mod.EventBusSubscriber(modid = HugMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerAnimationTrigger {
    public static boolean isHugging = false;

    @SubscribeEvent
    public static void onHugKeyPressed(InputEvent.Key event) {
        if (Minecraft.getInstance().options.keySwapOffhand.isDown()) {

            var selfPlayer = Minecraft.getInstance().player;
            if (selfPlayer == null) return; //The player can be null because it was a system message or because it is not loaded by this player.
            if (selfPlayer.getOffhandItem().getItem() != Item.byId(0) && selfPlayer.getOffhandItem().getItem() != null) return;

            double d = 1; // distance range for hugging hit box
            Vec3 vec3 = selfPlayer.getEyePosition(0);
            //if (selfPlayer.isCrouching()) {
            //    vec3 = selfPlayer.getEyePosition(0);
            //    System.out.println(vec3);
            //}
            Vec3 vec32 = selfPlayer.getViewVector(1.0F);
            Vec3 vec33 = vec3.add(vec32.x * d, vec32.y * d, vec32.z * d);
            AABB aABB = selfPlayer.getBoundingBox().expandTowards(vec32.scale(d)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult entHit = ProjectileUtil.getEntityHitResult(selfPlayer, vec3, vec33, aABB,
                    en -> (!en.isSpectator()), d);

            // entity is within hugging hit box
            if(entHit != null) {
                isHugging = true;

                // increase other player's health
                if (entHit.getEntity().getType() == EntityType.PLAYER) {
                    var otherPlayerUUID = entHit.getEntity().getUUID();
                    var otherPlayer = Minecraft.getInstance().level.getPlayerByUUID(otherPlayerUUID);
                    if (otherPlayer == null)
                        return; //The player can be null because it was a system message or because it is not loaded by this player.
                    if (otherPlayer.getHealth() == 20) {
                    } else {
                        int heartHealAmount = 2;
                        int health = (int) (otherPlayer.getHealth() + heartHealAmount);
                        otherPlayer.setHealth(Math.min(health, 20));
                    }
                }

                // increase your own health
                if (selfPlayer.getHealth() == 20) {
                } else {
                    int heartHealAmount = 1;
                    int health = (int) (selfPlayer.getHealth() + heartHealAmount);

                    selfPlayer.setHealth(Math.min(health, 20));
                }

                // run third person hugging animation
                var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) selfPlayer).get(new ResourceLocation(HugMod.MODID, "animation"));
                if (animation != null) {
                    animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("hugmod", "hugging"))));
                    //You might use animation.replaceAnimationWithFade(); to create fade effect instead of sudden change
                    //See javadoc for details
                }
            }


        }

    }

    //For server-side animation playing, see Emotecraft API
}


