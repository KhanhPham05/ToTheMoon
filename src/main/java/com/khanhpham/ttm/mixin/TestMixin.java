package com.khanhpham.ttm.mixin;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerMenu.class)
public class TestMixin {

    @Inject(at = @At("HEAD"), method = "addSlot")
    private void addSlot(Slot slot, CallbackInfoReturnable<Slot> info) {
        System.out.print(slot.index + "   ");
    }
}
