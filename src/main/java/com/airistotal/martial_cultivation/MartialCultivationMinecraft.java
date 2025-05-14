package com.airistotal.martial_cultivation;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.InputEvent;

import java.time.Duration;
import java.time.Instant;

public class MartialCultivationMinecraft {
    private static MartialCultivationMinecraft instance = null;

    private boolean isLeftMouseButtonPressed = false;
    private Instant leftMouseButtonPressedInstant = null;
    private boolean isRightMouseButtonPressed = false;
    private Instant rightMouseButtonPressedInstant = null;

    private MartialCultivationMinecraft() {
    }

    public static MartialCultivationMinecraft getInstance() {
        if (instance == null) {
            instance = new MartialCultivationMinecraft();
        }

        return instance;
    }

    public void setRightClickPressed(boolean pressed) {
        if (!isRightMouseButtonPressed) {
            this.isRightMouseButtonPressed = pressed;
            this.rightMouseButtonPressedInstant = Instant.now();
        } else if (!pressed) {
            this.isRightMouseButtonPressed = false;
            Duration pressedLength = Duration.between(this.rightMouseButtonPressedInstant, Instant.now());

            if (pressedLength.toMillis() < 200) {
                // Click
                Main.LOGGER.debug("Click right button");
            } else {
                // Hold
                Main.LOGGER.debug("Hold right button");
            }

            this.rightClickMouse();
        }
    }

    public void setLeftClickPressed(boolean pressed) {
        if (!isLeftMouseButtonPressed) {
            this.isLeftMouseButtonPressed = pressed;
            this.leftMouseButtonPressedInstant = Instant.now();
        } else if (!pressed) {
            this.isLeftMouseButtonPressed = false;
            Duration pressedLength = Duration.between(this.leftMouseButtonPressedInstant, Instant.now());

            if (pressedLength.toMillis() < 200) {
                // Click
                Main.LOGGER.debug("Click left button");
            } else {
                // Hold
                Main.LOGGER.debug("Hold left button");
            }

            this.clickMouse();
        }
    }

    private void clickMouse() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.objectMouseOver == null) {
            Main.LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
        } else {
            assert mc.player != null;
            if (!mc.player.isRowingBoat()) {
                InputEvent.ClickInputEvent inputEvent = ForgeHooksClient.onClickInput(0, mc.gameSettings.keyBindAttack, Hand.MAIN_HAND);
                if (!inputEvent.isCanceled())
                    switch (mc.objectMouseOver.getType()) {
                        case ENTITY:
                            assert mc.playerController != null;
                            mc.playerController.attackEntity(mc.player, ((EntityRayTraceResult) mc.objectMouseOver).getEntity());
                            break;
                        case BLOCK:
                            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) mc.objectMouseOver;
                            BlockPos blockpos = blockraytraceresult.getPos();
                            assert mc.world != null;
                            if (!mc.world.isAirBlock(blockpos)) {
                                assert mc.playerController != null;
                                mc.playerController.clickBlock(blockpos, blockraytraceresult.getFace());
                                break;
                            }
                        case MISS:
                            mc.player.resetCooldown();
                            net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(mc.player);
                    }

                if (inputEvent.shouldSwingHand())
                    mc.player.swingArm(Hand.MAIN_HAND);
            }
        }
    }

    private void rightClickMouse() {
        Minecraft mc = Minecraft.getInstance();

        if (!mc.playerController.getIsHittingBlock()) {
            if (!mc.player.isRowingBoat()) {
                if (mc.objectMouseOver == null) {
                    Main.LOGGER.warn("Null returned as 'hitResult', this shouldn't happen!");
                }

                for(Hand hand : Hand.values()) {
                    InputEvent.ClickInputEvent inputEvent = ForgeHooksClient.onClickInput(1, mc.gameSettings.keyBindUseItem, hand);
                    if (inputEvent.isCanceled()) {
                        if (inputEvent.shouldSwingHand()) mc.player.swingArm(hand);
                        return;
                    }
                    ItemStack itemstack = mc.player.getHeldItem(hand);
                    if (mc.objectMouseOver != null) {
                        switch(mc.objectMouseOver.getType()) {
                            case ENTITY:
                                EntityRayTraceResult entityraytraceresult = (EntityRayTraceResult)mc.objectMouseOver;
                                Entity entity = entityraytraceresult.getEntity();
                                ActionResultType actionresulttype = mc.playerController.interactWithEntity(mc.player, entity, entityraytraceresult, hand);
                                if (!actionresulttype.isSuccessOrConsume()) {
                                    actionresulttype = mc.playerController.interactWithEntity(mc.player, entity, hand);
                                }

                                if (actionresulttype.isSuccessOrConsume()) {
                                    if (actionresulttype.isSuccess()) {
                                        if (inputEvent.shouldSwingHand())
                                            mc.player.swingArm(hand);
                                    }

                                    return;
                                }
                                break;
                            case BLOCK:
                                BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)mc.objectMouseOver;
                                int i = itemstack.getCount();
                                ActionResultType actionresulttype1 = mc.playerController.func_217292_a(mc.player, mc.world, hand, blockraytraceresult);
                                if (actionresulttype1.isSuccessOrConsume()) {
                                    if (actionresulttype1.isSuccess()) {
                                        if (inputEvent.shouldSwingHand())
                                            mc.player.swingArm(hand);
                                        if (!itemstack.isEmpty() && (itemstack.getCount() != i || mc.playerController.isInCreativeMode())) {
                                            mc.gameRenderer.itemRenderer.resetEquippedProgress(hand);
                                        }
                                    }

                                    return;
                                }

                                if (actionresulttype1 == ActionResultType.FAIL) {
                                    return;
                                }
                        }
                    }

                    if (itemstack.isEmpty() && (mc.objectMouseOver == null || mc.objectMouseOver.getType() == RayTraceResult.Type.MISS))
                        net.minecraftforge.common.ForgeHooks.onEmptyClick(mc.player, hand);

                    if (!itemstack.isEmpty()) {
                        ActionResultType actionresulttype2 = mc.playerController.processRightClick(mc.player, mc.world, hand);
                        if (actionresulttype2.isSuccessOrConsume()) {
                            if (actionresulttype2.isSuccess()) {
                                mc.player.swingArm(hand);
                            }

                            mc.gameRenderer.itemRenderer.resetEquippedProgress(hand);
                            return;
                        }
                    }
                }

            }
        }
    }
}
