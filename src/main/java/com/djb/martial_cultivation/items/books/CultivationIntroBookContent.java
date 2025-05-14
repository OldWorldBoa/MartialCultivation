package com.djb.martial_cultivation.items.books;

import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.util.text.*;

public class CultivationIntroBookContent implements ReadBookScreen.IBookInfo {
    @Override
    public int getPageCount() {
        return 3;
    }

    /**
     * Gets the content at contentNumber, or returns empty if it's not found.
     * This is not necessarily a page, it could span multiple pages!
     * @param contentNumber
     * @return Returns the ITextProperties that represent the content
     */
    @Override
    public ITextProperties func_230456_a_(int contentNumber) {
        return getPageTitle(contentNumber)
                .append(new StringTextComponent("\n\n"))
                .append(getPageContent(contentNumber));
    }

    private static TranslationTextComponent getPageTitle(int contentNumber) {
        TranslationTextComponent title = new TranslationTextComponent("book.cultivation_intro_book.title_" + contentNumber);
        title.mergeStyle(TextFormatting.DARK_BLUE);

        return title;
    }

    private static TranslationTextComponent getPageContent(int contentNumber) {
        TranslationTextComponent page = new TranslationTextComponent("book.cultivation_intro_book.content_" + contentNumber);
        page.mergeStyle(TextFormatting.BLACK);

        return page;
    }
}
