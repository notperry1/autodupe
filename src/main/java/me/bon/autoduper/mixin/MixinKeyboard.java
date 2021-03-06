package me.bon.autoduper.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.bon.autoduper.AutoDuper;
import net.minecraft.client.Keyboard;

@Mixin(Keyboard.class)
public class MixinKeyboard {

	@Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5), cancellable = true)
	private void onKeyEvent(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
		if(AutoDuper.DUPING && key == GLFW.GLFW_KEY_ESCAPE) {
			AutoDuper.onDisable();
		}
	}
	
}
