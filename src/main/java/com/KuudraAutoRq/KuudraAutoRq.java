package com.KuudraAutoRq;

import com.KuudraAutoRq.Listeners.KuudraListener;
import com.KuudraAutoRq.Listeners.PartyManagementListener;
import com.KuudraAutoRq.Listeners.TimerHudListener;
import com.KuudraAutoRq.Objects.KuudraSession;
import com.KuudraAutoRq.commands.KInfoSession;
import com.KuudraAutoRq.commands.KStartSessionCommand;
import com.KuudraAutoRq.commands.KStopSessionCommand;
import com.KuudraAutoRq.commands.t5Command;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "kuudraautorq", useMetadata=true)
public class KuudraAutoRq {
    private static KuudraAutoRq instance;
    private KuudraSession session;

    public KuudraAutoRq() {
        instance = this;
        session = new KuudraSession(5, 7, "");
    }

    public static KuudraAutoRq getInstance() {
        return instance;
    }

    public void newSession(int sessionTiers, int dtTime, String partyLeader) {
        session = new KuudraSession(sessionTiers, dtTime, partyLeader);
    }

    public KuudraSession getSession() {
        return session;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        registerCommand();
        registerEvent();
    }

    private void registerEvent() {
        MinecraftForge.EVENT_BUS.register(new PartyManagementListener());
        MinecraftForge.EVENT_BUS.register(new KuudraListener());
        MinecraftForge.EVENT_BUS.register(new TimerHudListener());
    }

    private void registerCommand() {
        ClientCommandHandler.instance.registerCommand(new t5Command());
        ClientCommandHandler.instance.registerCommand(new KStartSessionCommand());
        ClientCommandHandler.instance.registerCommand(new KStopSessionCommand());
        ClientCommandHandler.instance.registerCommand(new KInfoSession());
    }
}

///joininstance KUUDRA_INFERNAL