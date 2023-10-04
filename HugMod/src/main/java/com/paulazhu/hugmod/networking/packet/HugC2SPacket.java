package com.paulazhu.hugmod.networking.packet;

import com.paulazhu.hugmod.event.ClientEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class HugC2SPacket {
    private static final String MESSAGE_HUG_SOMEONE = "*hug*";
    private static final String MESSAGE_GOT_HUGGED = "*hug* received";
    public final UUID entityHuggedUUID;

    public HugC2SPacket(UUID clientEntityHuggedUUID) {
        this.entityHuggedUUID = clientEntityHuggedUUID;
    }

    public HugC2SPacket(FriendlyByteBuf buf) {
        this(buf.readUUID());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.entityHuggedUUID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel(); // changed from 1.19 to 1.20 I think

            player.sendSystemMessage(Component.translatable(MESSAGE_HUG_SOMEONE).withStyle(ChatFormatting.YELLOW));

            // increase hugger players own health
            if (player.getHealth() != 20) {
                int heartHealAmount = 1;
                int health = (int) (player.getHealth() + heartHealAmount);

                player.setHealth(Math.min(health, 20));
            }

            // for the hugged entity
            Entity huggedEntity = level.getEntity(this.entityHuggedUUID);

            if (huggedEntity.getType() == EntityType.PLAYER) {
                // increase other player's health
                Player huggedPlayer = (Player) huggedEntity;
                if (huggedPlayer.getHealth() < 20) {
                    float heartHealAmount = 2;
                    float health = (float) (huggedPlayer.getHealth() + heartHealAmount);
                    huggedPlayer.setHealth(Math.min(health, 20));
                    huggedPlayer.sendSystemMessage(Component.translatable(MESSAGE_GOT_HUGGED).withStyle(ChatFormatting.YELLOW));
                }
            } else if (huggedEntity.getType() == EntityType.VILLAGER) {
                // increase villager's health
                Villager huggedVillager = (Villager) huggedEntity;
                if (huggedVillager.getHealth() < 20) {
                    float heartHealAmount = 2;
                    float health = (float) (huggedVillager.getHealth() + heartHealAmount);
                    huggedVillager.setHealth(Math.min(health, 20));
                    player.sendSystemMessage(Component.literal("entity's new health:"));
                    player.sendSystemMessage(Component.literal(String.valueOf(huggedVillager.getHealth())));
                }
            }

        });
        return true;
    }

}