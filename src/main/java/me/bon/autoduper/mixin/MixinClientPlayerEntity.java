package me.bon.autoduper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.bon.autoduper.AutoDuper;
import net.minecraft.client.network.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

	@Inject(method = "tick()V", at = @At("HEAD"))
	public void onTick(CallbackInfo ci) {
		if(AutoDuper.DUPING) {
			AutoDuper.onUpdate();
		}
	}
	
	@Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
	public void sendChatMessage(String message, CallbackInfo ci) {
		if(message.toLowerCase().equals(".autodupe start")) {
			AutoDuper.onEnable();
			ci.cancel();
		}
		if(message.toLowerCase().equals(".autodupe stop")) {
			AutoDuper.onDisable();
			ci.cancel();
		}
	}
	
}
