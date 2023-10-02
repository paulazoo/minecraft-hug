package com.paulazhu.hugmod;

import com.mojang.logging.LogUtils;
import com.paulazhu.hugmod.networking.ModMessages;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import net.minecraftforge.api.distmarker.Dist;

import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.client.model.PlayerModel;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HugMod.MODID)
public class HugMod
{
    public static final String MODID = "hugmod"; // Define mod id in a common place for everything to reference
    private static final Logger LOGGER = LogUtils.getLogger(); // Directly reference a slf4j logger
    public HugMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup); // Register the commonSetup method for modloading
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModMessages.register();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            //Set the player construct callback. It can be a lambda function.
            PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                    new ResourceLocation(MODID, "animation"),
                    42,
                    HugMod::registerPlayerAnimation);
        }
    }

    //This method will set your mods animation into the library.
    private static IAnimation registerPlayerAnimation(AbstractClientPlayer player) {
        //This will be invoked for every new player
        return new ModifierLayer<>();
    }
}