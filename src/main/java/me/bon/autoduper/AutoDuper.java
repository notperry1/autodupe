package me.bon.autoduper;

import java.util.Comparator;

import org.spongepowered.asm.mixin.MixinEnvironment.Side;

import me.bon.autoduper.util.MessageUtil;
import me.bon.autoduper.util.Wrapper;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import static me.bon.autoduper.util.MessageUtil.sendMessage;;

public class AutoDuper implements Wrapper {
	
	public static boolean DUPING = false;
	
	private static ChestBlockEntity targetChest;

	//Stage helps break it up a lil
	private static int stage, restticks, book;

	
	public static void onEnable() {
		sendMessage(Formatting.GREEN + "ENABLED");
		
		stage = -1;
		restticks = 0;
		
		//Find chest
		targetChest = (ChestBlockEntity) mc.world.blockEntities.stream()
		.filter(b -> b instanceof ChestBlockEntity)
		.filter(b -> mc.player.squaredDistanceTo(b.getPos().getX(), b.getPos().getY(), b.getPos().getZ()) <= (6 * 6))
		.sorted(Comparator.comparing(b -> mc.player.squaredDistanceTo(b.getPos().getX(), b.getPos().getY(), b.getPos().getZ())))
		.findFirst()
		.orElse(null);
		
		//Find book
		book = -1;
		for(int i = 0; i < 9; i++) {
			Item slot = mc.player.inventory.getStack(i).getItem();
			if(slot == Items.WRITABLE_BOOK) {
				book = i;
			} else {
				book = -1;
			}
		}
		
		if(targetChest == null) {
			sendMessage("No chests in range!");
			onDisable();
		} else if(book == -1) {
			sendMessage("Place a book and quill in your last hotbar slot!");
			onDisable();
		} else {
			DUPING = true;
			stage = 0;
		}
	}
	
	public static void onUpdate() {
		if(stage == 0) {
			restticks++;
			if(mc.currentScreen == null && restticks == 2) {
				mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(new Vec3d(0, 0, 0), Direction.DOWN, targetChest.getPos(), false)));
				restticks++;
			}
			
			if(mc.currentScreen != null) {
				if(restticks == 8) {
					for(int i = 0; i < mc.player.currentScreenHandler.slots.size() - 36; i++) {
						mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 1, SlotActionType.THROW, mc.player);
					}
				}
				if(restticks == 16) {
					for(int i = mc.player.currentScreenHandler.slots.size() - 45; i < mc.player.currentScreenHandler.slots.size() - 1; i++) {
						mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
					}
				}
				
				if(restticks == 24) {
					mc.player.closeScreen();
					mc.player.closeHandledScreen();
				}
			}
				
			if(restticks == 32) {
				mc.player.inventory.selectedSlot = book;
			}
				
			if(restticks == 40) {
				mc.player.sendChatMessage(".d");
			}
				
			if(restticks > 55) {
					stage = 1;
			}
		}
		
		if(stage == 1) {
			if(!(mc.player.inventory.getMainHandStack().getItem() == Items.WRITABLE_BOOK)) return;
			restticks++;
			if(restticks > 35) {
				stage = 0;
				restticks = 0;
			}
		}
	}
	
	public static void onDisable() {
		sendMessage(Formatting.RED + "DISABLED");
		DUPING = false;
		targetChest = null;
		stage = -1;
		book = -1;
		restticks = 0;
	}

}
