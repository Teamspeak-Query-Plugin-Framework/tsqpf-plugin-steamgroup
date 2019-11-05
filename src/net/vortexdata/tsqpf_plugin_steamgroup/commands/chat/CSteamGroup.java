package net.vortexdata.tsqpf_plugin_steamgroup.commands.chat;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import net.vortexdata.tsqpf.listeners.ChatCommandInterface;
import net.vortexdata.tsqpf.plugins.PluginConfig;

public class CSteamGroup implements ChatCommandInterface {

    private TS3Api api;
    private PluginConfig config;

    public CSteamGroup(TS3Api api, PluginConfig config) {
        this.api = api;
        this.config = config;
    }

    @Override
    public void gotCalled(TextMessageEvent textMessageEvent) {

        int invokerId = textMessageEvent.getInvokerId();
        String[] command = textMessageEvent.getMessage().split(" ");

        if (command.length > 1) {

            if (command[1].equalsIgnoreCase("GUIDE")) {
                api.sendPrivateMessage(invokerId, config.readValue("messageGuide"));
            } else if (command[1].equalsIgnoreCase("LINK")) {

            } else if (command[1].equalsIgnoreCase("VERIFY")) {

            } else if (command[1].equalsIgnoreCase("UNLINK")) {

            }

        } else {
            api.sendPrivateMessage(invokerId, config.readValue("messageSyntax"));
        }


    }

}
