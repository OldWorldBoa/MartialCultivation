package com.djb.martial_cultivation.capabilities;

import com.djb.martial_cultivation.exceptions.NotEnoughQiException;
import net.minecraft.entity.player.PlayerEntity;

public interface Cultivator {
    static Cultivator getCultivatorFrom(PlayerEntity player) {
        return player
            .getCapability(CultivatorCapabilityProvider.capability)
            .orElseThrow(IllegalStateException::new);
    }

    void loadCultivator(Cultivator savedCultivator);

    void cultivate();

    void useQi(int qi) throws NotEnoughQiException;
    void setQi(int qi);
    void regenerateQi();

    int getStoredQi();
    int getMaxQi();

    void setEnabled(boolean enabled);
    boolean isEnabled();
}
