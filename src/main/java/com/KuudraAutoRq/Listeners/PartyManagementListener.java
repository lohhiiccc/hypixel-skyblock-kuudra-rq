package com.KuudraAutoRq.Listeners;

import com.KuudraAutoRq.KuudraAutoRq;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartyManagementListener {

	@SubscribeEvent(receiveCanceled = false)
	public void onClientChatReceived(ClientChatReceivedEvent event) {
		String unformattedText = event.message.getUnformattedText();

		if (unformattedText.equals("You are not currently in a party.") || unformattedText.equals("You left the party.") || unformattedText.equals("The party was disbanded because all invites expired and the party was empty.")) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("debug: no party"));
			KuudraAutoRq.getInstance().getSession().setPartyLeader("no party");
			KuudraAutoRq.getInstance().getSession().stopSession();
		} else {
			Matcher matcherPartyLeader = Pattern.compile("Party Leader: .*?\\s(\\S+)(?:\\s●)?").matcher(unformattedText);
			if (matcherPartyLeader.find()) {
				// Capture le texte pertinent après "Party Leader: "
				String textAfterLeader = unformattedText.substring(matcherPartyLeader.start());
				// Extraire les mots
				String[] words = textAfterLeader.split("\\s+");
				// Le leader est l'avant-dernier mot
				String partyLeader = words[words.length - 2];
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("debug: party leader " + partyLeader));
				KuudraAutoRq.getInstance().getSession().setPartyLeader(partyLeader);
				return; // Arrêter après avoir trouvé le leader
			}

			Matcher matcherPartyTransfer = Pattern.compile("The party was transferred to (\\S+) by (\\S+)").matcher(unformattedText);
			if (matcherPartyTransfer.find()) {
				String newPartyLeader = matcherPartyTransfer.group(1);
				String oldPartyLeader = matcherPartyTransfer.group(2);

				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("debug: party transferred from " + oldPartyLeader + " to " + newPartyLeader));
				KuudraAutoRq.getInstance().getSession().setPartyLeader(newPartyLeader);
				return;
			}

			Matcher matcherKick = Pattern.compile("You have been kicked from the party by (\\S+)").matcher(unformattedText);
			if (matcherKick.find()) {
				String kicker = matcherKick.group(1);
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("debug: kicked from party by " + kicker));
				KuudraAutoRq.getInstance().getSession().setPartyLeader("no party");
				return;
			}

			Matcher matcherJoin = Pattern.compile("You have joined (\\S+)'s party!").matcher(unformattedText);
			if (matcherJoin.find()) {
				String newPartyLeader = matcherJoin.group(1);
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("debug: joined party of " + newPartyLeader));
				KuudraAutoRq.getInstance().getSession().setPartyLeader(newPartyLeader);
			}
		}
	}
}
