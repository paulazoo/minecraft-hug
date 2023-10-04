package com.paulazhu.hugmod.event;

import com.paulazhu.hugmod.HugMod;
import com.paulazhu.hugmod.networking.ModMessages;
import com.paulazhu.hugmod.networking.packet.HugC2SPacket;
import com.paulazhu.hugmod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

public class ClientEvents {
    public static boolean isHugging = false;
    public static UUID entityHuggedUUID = null;

    @Mod.EventBusSubscriber(modid = HugMod.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.HUGGING_KEY.consumeClick()) {
                var selfPlayer = Minecraft.getInstance().player;
                double d = 1; // distance range for hugging hit box
                Vec3 vec3 = selfPlayer.getEyePosition(0);
                Vec3 vec32 = selfPlayer.getViewVector(1.0F);
                Vec3 vec33 = vec3.add(vec32.x * d, vec32.y * d, vec32.z * d);
                AABB aABB = selfPlayer.getBoundingBox().expandTowards(vec32.scale(d)).inflate(1.0D, 1.0D, 1.0D);
                EntityHitResult entHit = ProjectileUtil.getEntityHitResult(selfPlayer, vec3, vec33, aABB,
                        en -> (!en.isSpectator()), d);
                if(entHit != null) {
                    isHugging = true;
                    entityHuggedUUID = entHit.getEntity().getUUID();
                    ModMessages.sendToServer(new HugC2SPacket(entityHuggedUUID));
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = HugMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.HUGGING_KEY);
        }
    }
}