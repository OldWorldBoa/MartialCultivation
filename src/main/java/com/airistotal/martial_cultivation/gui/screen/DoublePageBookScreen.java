package com.airistotal.martial_cultivation.gui.screen;

import com.airistotal.martial_cultivation.Main;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DoublePageBookScreen extends Screen {
    private final ResourceLocation cultivationIntroBookTexture = new ResourceLocation(Main.MOD_ID, "textures/gui/book_page.png");

    private final int bookSize = 256;
    private final int lineLength = 140;
    private final int maxLinesPerPage = 18;
    private ChangePageButton buttonNextPage;
    private ChangePageButton buttonPreviousPage;

    private int currFirstVirtualPage = 0;
    private int currContentNumber = 0;

    private int currCachedContentNumber = -1;
    private List<IReorderingProcessor> currContentCachedLines = new ArrayList<>();
    private int nextCachedContentNumber = -1;
    private List<IReorderingProcessor> nextContentCachedLines = new ArrayList<>();

    public DoublePageBookScreen() {
        super(new StringTextComponent("Cultivation Intro Book"));
        this.addDoneButton();
        this.addChangePageButtons();
    }

    /**
     * When this a Screen is created internally by Minecraft, this function is called instead of the constructor
     */
    @Override
    protected void init() {
        this.addDoneButton();
        this.addChangePageButtons();

        super.init();
    }

    protected ReadBookScreen.IBookInfo getContentBook() {
        return ReadBookScreen.EMPTY_BOOK;
    }

    protected void addDoneButton() {
        this.addButton(new Button(this.width / 2 - 100, 196, 200, 20, DialogTexts.GUI_DONE,
        (c) -> {
            assert this.minecraft != null;
            this.minecraft.displayGuiScreen(null);
        }));
    }

    protected void addChangePageButtons() {
        int i = (this.width - 192) / 2;

        this.buttonNextPage = this.addButton(
                new ChangePageButton(i + 116, 159, true, (c) -> this.nextPage(), true));
        this.buttonPreviousPage = this.addButton(
                new ChangePageButton(i + 43, 159, false, (c) -> this.previousPage(), true));

        this.updateButtons();
    }

    protected void nextPage() {
        if (this.currFirstVirtualPage < this.getNumVirtualPagesForCurrentContent() - 2) {
            // We are before the second last virtual page for the current content
            this.currFirstVirtualPage += 2;
        } else if (this.currFirstVirtualPage == this.getNumVirtualPagesForCurrentContent() - 1) {
            // We are on the last virtual page
            this.currContentNumber += 1;
            this.currFirstVirtualPage = 1;

            if (this.getNumVirtualPagesForCurrentContent() == 1) {
                // The only virtual page for the next content is already displayed, go to the next one
                this.currContentNumber += 1;
                this.currFirstVirtualPage = 0;
            }
        } else {
            // We are on the second last virtual page
            this.currContentNumber += 1;
            this.currFirstVirtualPage = 0;
        }

        updateButtons();
    }

    protected void previousPage() {
        if (this.currFirstVirtualPage == 0) {
            this.currContentNumber -= 1;
            this.currFirstVirtualPage = this.getNumVirtualPagesForCurrentContent() - 2;

            if (this.currFirstVirtualPage == -1) {
                this.currContentNumber -= 1;
                this.currFirstVirtualPage = this.getNumVirtualPagesForCurrentContent() - 1;
            }
        } else if ( this.currFirstVirtualPage == 1) {
            this.currContentNumber -= 1;
            this.currFirstVirtualPage = this.getNumVirtualPagesForCurrentContent() - 1;
        } else {
            this.currFirstVirtualPage -= 2;
        }

        updateButtons();
    }

    private void updateButtons() {
        this.buttonNextPage.visible = !(this.currContentNumber == this.getContentBook().getPageCount() - 1 &&
                                        this.currFirstVirtualPage >= this.getNumVirtualPagesForCurrentContent() - 2);
        this.buttonPreviousPage.visible = !(this.currContentNumber == 0 && this.currFirstVirtualPage == 0);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        RenderPage(matrixStack);
        RenderPageIndicator(matrixStack);
        RenderText(matrixStack);
        RenderHoverEffects(matrixStack, mouseX, mouseY);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private void RenderPage(MatrixStack matrixStack) {
        assert this.minecraft != null;

        GL11.glPushMatrix();
        double pageScale = 1.1;
        GL11.glScaled(pageScale, pageScale, pageScale);

        this.minecraft.getTextureManager().bindTexture(cultivationIntroBookTexture);

        double xOffset = (this.width - (bookSize * pageScale)) / 2;
        this.blit(matrixStack, (int)(xOffset / pageScale), 2, 0, 0, bookSize, bookSize);

        GL11.glPopMatrix();
    }

    private void RenderText(MatrixStack matrixStack) {
        GL11.glPushMatrix();
        double textScale = 0.8;
        GL11.glScaled(textScale, textScale, textScale);

        double leftMarginFirstPage = ((this.width - bookSize) / 2) + 10;
        RenderPageText(matrixStack, (float) (leftMarginFirstPage * 1/textScale), this.getCurrVirtualPage());

        double leftMarginSecondPage = ((this.width - bookSize) / 2) + (bookSize / 2) + 10;
        RenderPageText(matrixStack, (float) (leftMarginSecondPage * 1/textScale), this.getNextVirtualPage());

        GL11.glPopMatrix();
    }

    private List<IReorderingProcessor> getCurrVirtualPage() {
        return this.getVirtualPageFromLines(this.getCurrContentCachedLines(), this.currFirstVirtualPage);
    }

    private List<IReorderingProcessor> getNextVirtualPage() {
        if (this.currFirstVirtualPage < this.getNumVirtualPagesForCurrentContent() - 1) {
            return getVirtualPageFromLines(this.getCurrContentCachedLines(), this.currFirstVirtualPage + 1);
        } else {
            if (this.currContentNumber < this.getContentBook().getPageCount()) {
                return getVirtualPageFromLines(this.getNextContentCachedLines(), 0);
            } else {
                return new ArrayList<>();
            }
        }
    }

    private List<IReorderingProcessor> getVirtualPageFromLines(List<IReorderingProcessor> lines, int pageNum) {
        List<IReorderingProcessor> returnedLines = new ArrayList<>();

        int pageStart = (this.maxLinesPerPage * pageNum);
        int pageEnd = pageStart + this.maxLinesPerPage;

        for(int i = pageStart; i < pageEnd && i < lines.size(); i++) {
            returnedLines.add(lines.get(i));
        }

        return returnedLines;
    }

    private int getNumVirtualPagesForCurrentContent() {
        return (int)Math.ceil((this.getCurrContentCachedLines().size() / (float)this.maxLinesPerPage));
    }

    private List<IReorderingProcessor> getCurrContentCachedLines() {
        if (this.currContentNumber != this.currCachedContentNumber) {
            ITextProperties itextproperties = this.getContentBook().func_230456_a_(this.currContentNumber);
            this.currContentCachedLines = this.font.trimStringToWidth(itextproperties, lineLength);
            this.currCachedContentNumber = this.currContentNumber;
        }

        return this.currContentCachedLines;
    }

    private List<IReorderingProcessor> getNextContentCachedLines()
    {
        int nextContentNumber = this.currContentNumber + 1;
        if (nextContentNumber != this.nextCachedContentNumber &&
            nextContentNumber > -1 &&
            nextContentNumber < this.getContentBook().getPageCount()) {

            ITextProperties itextproperties = this.getContentBook().func_230456_a_(nextContentNumber);
            this.nextContentCachedLines = this.font.trimStringToWidth(itextproperties, lineLength);
            this.nextCachedContentNumber = nextContentNumber;
        } else if (nextContentNumber == this.nextCachedContentNumber) {
            return this.nextContentCachedLines;
        } else {
            return new ArrayList<>();
        }

        return new ArrayList<>();
    }

    private void RenderPageText(MatrixStack matrixStack, float leftMargin, List<IReorderingProcessor> lines) {
        int maxLinesForPage = Math.min(lines.size(), 18);
        int color = 0;

        for(int l = 0; l < maxLinesForPage; ++l) {
            float lineTopMargin = (36 + (l * 9));

            IReorderingProcessor ireorderingprocessor = lines.get(l);
            this.font.func_238422_b_(matrixStack, ireorderingprocessor, leftMargin, lineTopMargin, color);
        }
    }

    private void RenderPageIndicator(MatrixStack matrixStack) {
        GL11.glPushMatrix();
        double textScale = 0.75;
        GL11.glScaled(textScale, textScale, textScale);

        ITextComponent currentPageIndicator = new TranslationTextComponent(
            "book.martial_cultivation.chapter_indicator",
            this.currContentNumber + 1,
            Math.max(this.getContentBook().getPageCount(), 1));

        float topMargin = 18.0F;
        double leftMargin = ((this.width - bookSize) / 2) + 10;

        this.font.func_243248_b(
            matrixStack,
            currentPageIndicator,
            (float) ( leftMargin * 1/textScale),
            topMargin,
            TextFormatting.DARK_GRAY.getColor());

        GL11.glPopMatrix();
    }

    private void RenderHoverEffects(MatrixStack matrixStack, int mouseX, int mouseY) {
        Style style = this.GetStyleAtCoordinates(mouseX, mouseY);
        if (style != null) {
            this.renderComponentHoverEffect(matrixStack, style, mouseX, mouseY);
        }
    }

    @Nullable
    public Style GetStyleAtCoordinates(double x, double y) {
        assert this.minecraft != null;
        
        if (this.currContentCachedLines.isEmpty()) {
            return null;
        } else {
            int i = MathHelper.floor(x - (double)((this.width - 192) / 2) - 36.0D);
            int j = MathHelper.floor(y - 2.0D - 30.0D);
            if (i >= 0 && j >= 0) {
                int k = Math.min(128 / 9, this.currContentCachedLines.size());
                if (i <= 114 && j < 9 * k + k) {
                    int l = j / 9;
                    if (l < this.currContentCachedLines.size()) {
                        IReorderingProcessor ireorderingprocessor = this.currContentCachedLines.get(l);
                        return this.minecraft.fontRenderer.getCharacterManager().func_243239_a(ireorderingprocessor, i);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else {
            switch(keyCode) {
                case 266:
                    this.buttonPreviousPage.onPress();
                    return true;
                case 267:
                    this.buttonNextPage.onPress();
                    return true;
                default:
                    return false;
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            Style style = this.GetStyleAtCoordinates(mouseX, mouseY);
            if (style != null && this.handleComponentClicked(style)) {
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean handleComponentClicked(Style style) {
        assert style != null;
        assert this.minecraft != null;

        ClickEvent clickevent = style.getClickEvent();
        if (clickevent == null) {
            return false;
        } else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            String s = clickevent.getValue();

            try {
                int i = Integer.parseInt(s) - 1;
                if (i < this.currContentNumber) {
                    this.previousPage();
                } else if ( i > this.currContentNumber) {
                    this.nextPage();
                }

                return true;
            } catch (Exception exception) {
                return false;
            }
        } else {
            boolean flag = super.handleComponentClicked(style);
            if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.minecraft.displayGuiScreen(null);
            }

            return flag;
        }
    }
}
