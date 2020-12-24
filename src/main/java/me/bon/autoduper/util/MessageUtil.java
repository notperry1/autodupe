package me.bon.autoduper.util;

import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class MessageUtil implements Wrapper {
	
	public static void sendMessage(String message) {
		mc.inGameHud.getChatHud().addMessage(new LiteralText(Formatting.BLUE + "[AutoDuper] " + Formatting.GRAY + message));
	}

}
