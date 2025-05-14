package com.djb.martial_cultivation.events.client;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.capabilities.Cultivator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.util.Map;

@Mod.EventBusSubscriber
public class KeyHandler {
    private static Field KEYBIND_ARRAY = null;

    // https://forums.minecraftforge.net/topic/59857-112-solved-keyinputevent-get-keypressed/
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientTick(TickEvent.ClientTickEvent event) throws Exception {
        if(KEYBIND_ARRAY == null){
            KEYBIND_ARRAY = KeyBinding.class.getDeclaredField("KEYBIND_ARRAY");
            KEYBIND_ARRAY.setAccessible(true);
        }

        if(event.phase.equals(TickEvent.Phase.END) && event.side == LogicalSide.CLIENT){
            Map<String, KeyBinding> binds = (Map<String, KeyBinding>) KEYBIND_ARRAY.get(null);
            for (String bind : binds.keySet()) {
                KeyBinding keyBinding = binds.get(bind);

                if (keyBinding.getKeyDescription().equals("key." + Main.MOD_ID + ".cultivate")) {
                    if(keyBinding.isKeyDown()) {
                        Cultivator.getCultivatorFrom(Minecraft.getInstance().player).toggleActiveCultivation();
                    }
                }
            }
        }
    }
}
