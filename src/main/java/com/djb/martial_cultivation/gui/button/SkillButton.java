package com.djb.martial_cultivation.gui.button;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.gui.screen.SkillScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class SkillButton extends ImageButton {
    private static final ResourceLocation skill_button = new ResourceLocation(Main.MOD_ID, "textures/gui/skills_button.png");
    private static int width = 20;

    public SkillButton(Minecraft mcIn, int parentHeight) {
        super(
                (mcIn.currentScreen.width - width) / 2 + 60,
                parentHeight/2 - 22,
                width,
                18,
                0,
                0,
                19,
                skill_button,
                (button) -> {
                    Minecraft.getInstance().displayGuiScreen(new SkillScreen());
                    Main.LOGGER.debug("Skill button pressed");
                });

        //
    }
}
