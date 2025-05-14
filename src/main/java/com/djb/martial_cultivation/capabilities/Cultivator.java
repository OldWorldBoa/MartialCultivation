package com.djb.martial_cultivation.capabilities;

import com.djb.martial_cultivation.exceptions.NotEnoughQiException;

public interface Cultivator {
    void storeQi(int qi);
    void cultivate();
    void useQi(int qi) throws NotEnoughQiException;
    int getStoredQi();
}
