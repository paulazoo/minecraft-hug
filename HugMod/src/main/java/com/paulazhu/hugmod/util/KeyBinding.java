package com.paulazhu.hugmod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_HUG = "hugmod";
    public static final String KEY_HUG_SOMEONE = "hug someone";

    // o key for now
    public static final KeyMapping HUGGING_KEY = new KeyMapping(KEY_HUG_SOMEONE, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_HUG);
}