package com.KuudraAutoRq.Listeners;

import com.KuudraAutoRq.KuudraAutoRq;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KuudraListener {

	@SubscribeEvent(receiveCanceled = false)
	public void onClientChatReceived(ClientChatReceivedEvent event) {
		String unformattedText = event.message.getUnformattedText();
		Minecraft minecraft = Minecraft.getMinecraft();
		String playerName = minecraft.thePlayer.getName();

		String cleanedText = unformattedText.replaceAll("§.", "");

		if (cleanedText.matches("^Party >.*dt$")) {
			minecraft.thePlayer.addChatMessage(new ChatComponentText("REGEX OK"));

			if (KuudraAutoRq.getInstance().getSession().isSessionStarted() &&
					KuudraAutoRq.getInstance().getSession().getPartyLeader().equals(playerName)) {


				KuudraAutoRq.getInstance().getSession().setDtTime(7);
				KuudraAutoRq.getInstance().getSession().setSessionTiers(5);
				KuudraAutoRq.getInstance().getSession().stopSession();
				minecraft.thePlayer.addChatMessage(new ChatComponentText("§8[§cautorq§8]§9: stop session"));

				Executors.newScheduledThreadPool(1).schedule(()-> {
					minecraft.thePlayer.sendChatMessage("/pc someone need dt");
				}, 300, TimeUnit.MILLISECONDS);
			}
		}

		String formattedText = event.message.getFormattedText();
		String kuudraDownMessage = "§r§f                               §r§6§lKUUDRA DOWN!§r";
		if (formattedText.equals(kuudraDownMessage) &&
				KuudraAutoRq.getInstance().getSession().isSessionStarted() &&
				KuudraAutoRq.getInstance().getSession().getPartyLeader().equals(playerName)) {

			minecraft.thePlayer.sendChatMessage("/pc auto rq in " + KuudraAutoRq.getInstance().getSession().getDtTime() + " you can type !dt :0");
			Executors.newScheduledThreadPool(1).schedule(this::executeAfterDelay, KuudraAutoRq.getInstance().getSession().getDtTime(), TimeUnit.SECONDS);
		}
	}

	private void executeAfterDelay() {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			if (Minecraft.getMinecraft().thePlayer != null) {
				if (KuudraAutoRq.getInstance().getSession().isSessionStarted() && KuudraAutoRq.getInstance().getSession().getPartyLeader().equals(Minecraft.getMinecraft().thePlayer.getName())) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8[§cautorq§8]§9: auto rs"));
					KuudraAutoRq.getInstance().getSession().joinKuudra();
				}
			}
		});
	}
}
