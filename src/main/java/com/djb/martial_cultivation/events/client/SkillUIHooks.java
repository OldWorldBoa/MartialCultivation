package com.djb.martial_cultivation.events.client;

import com.djb.martial_cultivation.gui.button.SkillButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber
public class SkillUIHooks {
    @SubscribeEvent
    public static void AddSkillToggleWidget(GuiScreenEvent.InitGuiEvent event) {
        Screen screen = event.getGui();

        if (screen instanceof InventoryScreen) {
            event.addWidget(new SkillButton(Minecraft.getInstance(), screen.height));
        }
    }
}
