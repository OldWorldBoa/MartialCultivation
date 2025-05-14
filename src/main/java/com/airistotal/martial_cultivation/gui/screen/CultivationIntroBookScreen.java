package com.airistotal.martial_cultivation.gui.screen;

import com.airistotal.martial_cultivation.items.books.CultivationIntroBookContent;
import net.minecraft.client.gui.screen.ReadBookScreen;

public class CultivationIntroBookScreen extends DoublePageBookScreen {
    private static final ReadBookScreen.IBookInfo cultivationIntroBook = new CultivationIntroBookContent();

    @Override
    protected ReadBookScreen.IBookInfo getContentBook() {
        return cultivationIntroBook;
    }
}
