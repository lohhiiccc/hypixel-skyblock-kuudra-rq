package com.KuudraAutoRq.Listeners;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimerHudListener {
	private final Pattern hudListener = Pattern.compile(".*auto rq in (\\d+) you can type !dt :0");
	private Matcher matcher;
	private final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
	private long displayUntil;
	private long timeleft;
	private String line;
	private int width;
	private int height;
	private int txtWidth;

	@SubscribeEvent
	public void onClientChatReceived(ClientChatReceivedEvent event) {
		matcher = hudListener.matcher(event.message.getUnformattedText().replaceAll("§.", ""));
		if (matcher.find()) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("test regex ok"));
			displayUntil = System.currentTimeMillis() + (Long.parseLong(matcher.group(1)) * 1000);
		}
	}

	@SubscribeEvent
	public void renderPlayerInfo(RenderGameOverlayEvent event) {
		if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;

		long currentTime = System.currentTimeMillis();
		if (currentTime < displayUntil) {
			timeleft = displayUntil - currentTime;
			line = String.format("rs in %.2f", timeleft / 1000.0);

			ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
			width = scaledResolution.getScaledWidth();
			height = scaledResolution.getScaledHeight();
			txtWidth = fontRenderer.getStringWidth(line);

			float scaleFactor = 2.0f;

			GL11.glPushMatrix();
			GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);

			int x = (int) ((width / scaleFactor - txtWidth) / 2);
			int y = (int) ((height / scaleFactor) / 2.15);

			fontRenderer.drawString(line, x, y, 0x09c200, true);
			GL11.glPopMatrix();
		}
	}
}