package com.KuudraAutoRq.commands;

import com.KuudraAutoRq.KuudraAutoRq;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class KInfoSession extends CommandBase {
	@Override
	public String getCommandName() {
		return "kInfoSession";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/t5";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§cautorq§8]§9: t" + KuudraAutoRq.getInstance().getSession().getSessionTiers() + " dt: " + KuudraAutoRq.getInstance().getSession().getDtTime() + " current party leader: " + KuudraAutoRq.getInstance().getSession().getPartyLeader() + " is: " + KuudraAutoRq.getInstance().getSession().isSessionStarted()));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }

}
