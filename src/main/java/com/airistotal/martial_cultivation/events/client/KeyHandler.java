package com.airistotal.martial_cultivation.events.client;

import com.airistotal.martial_cultivation.Main;
import com.airistotal.martial_cultivation.MartialCultivationMinecraft;
import com.airistotal.martial_cultivation.capabilities.Cultivator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber
public class KeyHandler {
    public static KeyBinding CultivateKeyBinding = new KeyBinding("key." + Main.MOD_ID + ".cultivate", GLFW.GLFW_KEY_Z, "key." + Main.MOD_ID + ".gameplay");
    public static KeyBinding AttackKeyBinding = new KeyBinding("key." + Main.MOD_ID + ".attack", InputMappings.Type.MOUSE, 0, "key." + Main.MOD_ID + ".gameplay");
    public static KeyBinding UseKeyBinding = new KeyBinding("key." + Main.MOD_ID + ".use", InputMappings.Type.MOUSE, 1, "key." + Main.MOD_ID + ".gameplay");

    // https://forums.minecraftforge.net/topic/59857-112-solved-keyinputevent-get-keypressed/
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END) && event.side == LogicalSide.CLIENT) {
            PlayerEntity player = Minecraft.getInstance().player;
            MartialCultivationMinecraft mcmc = MartialCultivationMinecraft.getInstance();

            try {
                if (player != null) {
                    if (CultivateKeyBinding.isKeyDown()) {
                        Cultivator.getCultivatorFrom(Minecraft.getInstance().player).toggleActiveCultivation();
                    }

                    mcmc.setLeftClickPressed(AttackKeyBinding.isKeyDown());
                    mcmc.setRightClickPressed(UseKeyBinding.isKeyDown());
                }
            } catch (IllegalStateException ise) {
                Main.LOGGER.debug("Looks like you died!");
            }
        }
    }
}
