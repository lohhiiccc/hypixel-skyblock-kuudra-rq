package com.KuudraAutoRq.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class t5Command extends CommandBase {

		@Override
		public String getCommandName() {
			return "t5";
		}

		@Override
		public String getCommandUsage(ICommandSender sender) {
			return "/t5";
		}

		@Override
		public void processCommand(ICommandSender sender, String[] args) throws CommandException {
			Minecraft.getMinecraft().thePlayer.sendChatMessage("/joininstance KUUDRA_INFERNAL");
		}

		@Override
		public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }


}
