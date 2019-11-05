package net.vortexdata.tsqpf_plugin_steamgroup.commands.chat;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import net.vortexdata.tsqpf.listeners.ChatCommandInterface;
import net.vortexdata.tsqpf.plugins.PluginConfig;
import net.vortexdata.tsqpf_plugin_steamgroup.modules.*;
import net.vortexdata.tsqpf_plugin_steamgroup.utils.*;

public class CSteamGroup implements ChatCommandInterface {

    private TS3Api api;
    private PluginConfig config;
    private LinkManager linkManager;
    private PinGenerator pinGenerator;

    public CSteamGroup(TS3Api api, PluginConfig config, LinkManager linkManager) {
        this.api = api;
        this.config = config;
        this.linkManager = linkManager;
        this.pinGenerator = new PinGenerator();
    }

    @Override
    public void gotCalled(TextMessageEvent textMessageEvent) {

        int invokerId = textMessageEvent.getInvokerId();
        String[] command = textMessageEvent.getMessage().split(" ");

        if (command.length > 1) {

            if (command[1].equalsIgnoreCase("GUIDE")) {
                api.sendPrivateMessage(invokerId, config.readValue("messageGuide"));
            } else if (command[1].equalsIgnoreCase("LINK")) {

                // Check if URL is valid
                String pin = pinGenerator.nextPin();
                linkManager.storeLink(command[2], pin);

            } else if (command[1].equalsIgnoreCase("VERIFY")) {

            } else if (command[1].equalsIgnoreCase("UNLINK")) {

            }

        } else {
            api.sendPrivateMessage(invokerId, config.readValue("messageSyntax"));
        }


    }

}
