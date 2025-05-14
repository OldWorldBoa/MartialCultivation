package com.airistotal.martial_cultivation.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilities {
    @CapabilityInject(Cultivator.class)
    public static final Capability<Cultivator> CULTIVATOR_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(Cultivator.class, new CultivatorStorage(), FoundationCultivator::new);
    }
}
