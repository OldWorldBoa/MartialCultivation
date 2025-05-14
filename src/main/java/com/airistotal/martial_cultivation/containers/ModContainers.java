package com.airistotal.martial_cultivation.containers;

import com.airistotal.martial_cultivation.Registrar;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ModContainers {
    public static final RegistryObject<ContainerType<Container>> SKILL_CONTAINER =
            Registrar.CONTAINERS.register(SkillContainer.name, () -> IForgeContainerType.create(
                    (windowId, inv, data) -> new SkillContainer(windowId, inv.player)));

    public static void register() {}
}
