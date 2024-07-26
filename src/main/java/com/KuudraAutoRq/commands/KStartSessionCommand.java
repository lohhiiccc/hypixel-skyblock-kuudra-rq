package com.KuudraAutoRq.commands;

import com.KuudraAutoRq.KuudraAutoRq;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KStartSessionCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "kStartsession";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/startsession <1/2/3/4/5> <dt time>\ndefault 5, 7";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {

		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§cautorq§8]§9: update party leader"));
		Minecraft.getMinecraft().thePlayer.sendChatMessage("/party list");
		Executors.newScheduledThreadPool(1).schedule(()-> {
			if (args.length == 0 && Minecraft.getMinecraft().thePlayer.getName().equals(KuudraAutoRq.getInstance().getSession().getPartyLeader())) {
				KuudraAutoRq.getInstance().getSession().setDtTime(7);
				KuudraAutoRq.getInstance().getSession().setSessionTiers(5);
				KuudraAutoRq.getInstance().getSession().startSession();
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§cautorq§8]§9: start session t5 dt 7sec"));
			} else if (args.length == 2) {
				if (args[0].equals("1") || args[0].equals("2") || args[0].equals("3") || args[0].equals("4") || args[0].equals("5")) {
					KuudraAutoRq.getInstance().getSession().setSessionTiers(Integer.parseInt(args[0]));
				} else {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§cautorq§8]§9: §9error tiers auto set to 5"));
					KuudraAutoRq.getInstance().getSession().setSessionTiers(5);
				}
				try {
					int number = Integer.parseInt(args[1]);
					if (number >= 5 && number <= 30) {
						KuudraAutoRq.getInstance().getSession().setDtTime(number);
					} else {
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§cautorq§8]§9: §9error dt time set to 7"));
					}
				} catch (NumberFormatException e) {
					KuudraAutoRq.getInstance().getSession().setDtTime(7);
				}
				KuudraAutoRq.getInstance().getSession().startSession();
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§cautorq§8]§9: start session t" + KuudraAutoRq.getInstance().getSession().getSessionTiers() + " dt" + KuudraAutoRq.getInstance().getSession().getDtTime() + "sec"));
			}
		}, 350, TimeUnit.MILLISECONDS);
	}
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }

}
