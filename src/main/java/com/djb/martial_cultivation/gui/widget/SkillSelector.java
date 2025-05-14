package com.djb.martial_cultivation.gui.widget;

import com.djb.martial_cultivation.capabilities.Cultivator;
import com.djb.martial_cultivation.capabilities.skills.CultivationSkill;
import com.djb.martial_cultivation.capabilities.skills.CultivationSkillType;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.IdentityHashMap;

public class SkillSelector extends Widget {
    private final IdentityHashMap<CultivationSkillType, ArrayList<CultivationSkill>> groupedSkills = new IdentityHashMap<>();

    private final Minecraft mc;
    private final int skillHeight = 18;
    private final int skillWidth = 18;
    private final int padding = 3;

    public SkillSelector(Minecraft mc, int x, int y, int width, int height, PlayerEntity player) {
        super(x, y, width, height, new StringTextComponent(""));

        this.mc = mc;

        setGroupedSkills(Cultivator.getCultivatorFrom(player).getSkills());
    }

    private void setGroupedSkills(ArrayList<CultivationSkill> cultivationSkills) {
        for (CultivationSkill skill : cultivationSkills) {
            if (!groupedSkills.containsKey(skill.getSkillType())) {
                groupedSkills.put(skill.getSkillType(), new ArrayList<>());
            }

            groupedSkills.get(skill.getSkillType()).add(skill);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int currY = y + padding;

        for (CultivationSkillType type : groupedSkills.keySet()) {
            renderSkillsByType(matrixStack, type, currY);

            currY += this.skillHeight + padding;
        }
    }

    private void renderSkillsByType(MatrixStack stack, CultivationSkillType type, int y) {
        int currX = x + padding;

        for(CultivationSkill skill : groupedSkills.get(type)) {
            mc.getTextureManager().bindTexture(skill.getTextureLocation());

            blit(stack, currX, y, 0, 0, this.skillWidth, this.skillHeight);

            currX += this.skillWidth + padding;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
