package me.bon.autoduper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

import static net.minecraft.server.command.CommandManager.literal;

public class DupeMod implements ModInitializer {
	
	public static final String MODNAME = "AutoDuper";
	public static final String MODID = "autoduper";
	public static final String VERSION = "v1";
	
	@Override
	public void onInitialize() {
		
		/**
		 * This would have been nice to have
		 * 
		 * CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
		 *	dispatcher.register(
		 *		literal("autodupe")
		 *		.then(literal("start")
		 *		.executes(command -> {
		 *			AutoDuper.onEnable();
		 *			return 1;
		 *		}))
		 *		.then(literal("stop")
		 *		.executes(command -> {
		 *			AutoDuper.onDisable();
		 *			return 1;
		 *		})));
		 *	});
		 */
	}
}
