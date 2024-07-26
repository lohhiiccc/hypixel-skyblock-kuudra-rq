package com.KuudraAutoRq.commands;

import com.KuudraAutoRq.KuudraAutoRq;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class KStopSessionCommand extends CommandBase {
		@Override
		public String getCommandName() {
			return "kstopSession";
		}

		@Override
		public String getCommandUsage(ICommandSender sender) {
			return "/t5";
		}

		@Override
		public void processCommand(ICommandSender sender, String[] args) throws CommandException {
			KuudraAutoRq.getInstance().getSession().setDtTime(7);
			KuudraAutoRq.getInstance().getSession().setSessionTiers(5);
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§cautorq§8]§9: stop session"));
		}

		@Override
		public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }


}
