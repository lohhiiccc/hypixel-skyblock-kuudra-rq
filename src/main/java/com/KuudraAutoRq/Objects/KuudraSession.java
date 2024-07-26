package com.KuudraAutoRq.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class KuudraSession {
	private int sessionTiers;
	private int dtTime;
	private String partyLeader;
	private boolean isStarted;
	private String[] kuudraStates = {
			"kuudra_normal",
			"KUUDRA_HOT",
			"KUUDRA_BURNING",
			"KUUDRA_FIERY",
			"KUUDRA_INFERNAL"
	};

	public KuudraSession(int sessionTiers, int dtTime, String partyLeader) {
		if (dtTime < 5) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§4Dt time cannot be less than 5 seconds\nauto set to 5sec"));
			this.sessionTiers = sessionTiers;
			this.dtTime = 5;
		} else if (dtTime > 30) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§4Dt time cannot be above 30 seconds\nauto set to 30sec"));
			this.sessionTiers = sessionTiers;
			this.dtTime = 30;
		} else {
			this.dtTime = dtTime;
		}
		this.sessionTiers = sessionTiers;
		this.partyLeader = partyLeader;
		this.isStarted = false;
	}

	public void startSession() {
		this.isStarted = true;
	}

	public void stopSession() {
		this.isStarted = false;
	}

	public boolean isSessionStarted() {
		return this.isStarted;
	}

	public void setPartyLeader(String partyLeader) {
		this.partyLeader = partyLeader;
	}

	public String getPartyLeader() {
		return partyLeader;
	}

	public void setSessionTiers(int sessionTiers) {
		this.sessionTiers = sessionTiers;
	}

	public void setDtTime(int dtTime) {
		this.dtTime = dtTime;
	}

	public int getDtTime() {
		return dtTime;
	}

	public int getSessionTiers() {
		return sessionTiers;
	}

	public void joinKuudra() {
		Minecraft.getMinecraft().thePlayer.sendChatMessage("/joininstance "+ kuudraStates[this.sessionTiers - 1]);
	}

}