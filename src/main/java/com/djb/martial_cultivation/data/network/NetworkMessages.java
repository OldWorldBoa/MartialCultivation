package com.djb.martial_cultivation.data.network;

import com.djb.martial_cultivation.Main;
import com.djb.martial_cultivation.data.network.messages.QiAmountChanged;

public class NetworkMessages {
    public static void registerMessages() {
        Main.NETWORK_CHANNEL.registerMessage(
            1,
            QiAmountChanged.class,
            QiAmountChanged.getQiAmountChangedEncoder(),
            QiAmountChanged.getQiAmountChangedDecoder(),
            QiAmountChanged::handleMessage
        );
    }
}
