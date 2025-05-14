package com.airistotal.martial_cultivation.capabilities.skills;

import com.airistotal.martial_cultivation.items.tools.combat.BasicStaff;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;

import java.util.IdentityHashMap;

public class ToolSkillGroupMapper {
    private static IdentityHashMap<Class, ToolSkillGroup> toolSkillGroupMap = new IdentityHashMap<>();
    static {
        toolSkillGroupMap.put(SwordItem.class, ToolSkillGroup.SWORD);
        toolSkillGroupMap.put(AxeItem.class, ToolSkillGroup.AXE);
        toolSkillGroupMap.put(BasicStaff.class, ToolSkillGroup.STAFF);
        toolSkillGroupMap.put(null, ToolSkillGroup.UNARMED);
    }

    public static ToolSkillGroup getToolSkillGroupFromItem(Class _class) {
        ToolSkillGroup tsg = toolSkillGroupMap.get(_class);

        if (tsg == null) {
            return ToolSkillGroup.BLUDGEON;
        } else {
            return tsg;
        }
    }
}
