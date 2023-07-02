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

import com.paulazhu.hugmod.PlayerData;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

@Mod.EventBusSubscriber(modid = HugMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PlayerAnimationTrigger {

    @SubscribeEvent
    public static void onHugKeyPressed(InputEvent.Key event) {
        if (Minecraft.getInstance().options.keySwapOffhand.isDown()) {

            var selfPlayer = Minecraft.getInstance().player;
            if (selfPlayer == null) return; //The player can be null because it was a system message or because it is not loaded by this player.

            double d = 1;//range
            Vec3 vec3 = selfPlayer.getEyePosition(0);
            Vec3 vec32 = selfPlayer.getViewVector(1.0F);
            Vec3 vec33 = vec3.add(vec32.x * d, vec32.y * d, vec32.z * d);
            AABB aABB = selfPlayer.getBoundingBox().expandTowards(vec32.scale(d)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult entHit = ProjectileUtil.getEntityHitResult(selfPlayer, vec3, vec33, aABB,
                    en -> (!en.isSpectator()), d);
            if(entHit != null && (entHit.getEntity().getType() == EntityType.VILLAGER)) {
                //var otherPlayer = Minecraft.getInstance().level.getPlayerByUUID();
                //if (otherPlayer == null) return; //The player can be null because it was a system message or because it is not loaded by this player.

                var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) selfPlayer).get(new ResourceLocation(HugMod.MODID, "animation"));
                if (animation != null) {
                    animation.setAnimation(new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation("hugmod", "hugging"))));
                    //You might use  animation.replaceAnimationWithFade(); to create fade effect instead of sudden change
                    //See javadoc for details
                }
            }
        }

    }

    //For server-side animation playing, see Emotecraft API
}


