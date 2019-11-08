package net.vortexdata.tsqpf_plugin_steamgroup.commands.chat;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import net.vortexdata.tsqpf.listeners.ChatCommandInterface;
import net.vortexdata.tsqpf.plugins.*;
import net.vortexdata.tsqpf_plugin_steamgroup.modules.*;
import net.vortexdata.tsqpf_plugin_steamgroup.utils.*;
import sun.net.util.*;

import java.net.*;

public class CSteamGroup implements ChatCommandInterface {

    private TS3Api api;
    private PluginConfig config;
    private LinkManager linkManager;
    private PinGenerator pinGenerator;
    private UrlValidator urlValidator;
    private PluginLogger logger;

    public CSteamGroup(TS3Api api, PluginConfig config, LinkManager linkManager, PluginLogger logger) {
        this.api = api;
        this.config = config;
        this.linkManager = linkManager;
        this.pinGenerator = new PinGenerator();
        this.urlValidator = new UrlValidator();
        this.logger = logger;
    }

    @Override
    public void gotCalled(TextMessageEvent textMessageEvent) {

        int invokerId = textMessageEvent.getInvokerId();
        String[] command = textMessageEvent.getMessage().split(" ");

        if (command.length > 1) {

            if (command[1].equalsIgnoreCase("GUIDE")) {
                api.sendPrivateMessage(invokerId, config.readValue("messageGuide"));
            } else if (command[1].equalsIgnoreCase("LINK")) {

                if (command.length >= 3) {

                    // Remove teamspeak URL tags
                    command[2] = command[2].replace("[URL]", "");
                    command[2] = command[2].replace("[/URL]", "");

                    // Check if URL is valid
                    if (!urlValidator.validateProfileUrl(command[2])) {
                        api.sendPrivateMessage(invokerId, config.readValue("messageLinkUrlInvalid"));

                        return;
                    }

                    String pin = pinGenerator.nextPin();
                    linkManager.storeLink(command[2], pin);
                    api.sendPrivateMessage(invokerId, config.readValue("messagePinCreated") + pin);


                } else {
                    api.sendPrivateMessage(invokerId, config.readValue("messageSyntax"));
                }

            } else if (command[1].equalsIgnoreCase("VERIFY")) {

                if (command.length >= 3) {

                    // Remove teamspeak URL tags
                    command[2] = command[2].replace("[URL]", "");
                    command[2] = command[2].replace("[/URL]", "");

                    textMessageEvent.getInvokerUniqueId();

                    new Thread(new LinkCreationCheck(command, api, textMessageEvent.getInvokerUniqueId(), invokerId, config, linkManager, logger)).start();

                } else {
                    api.sendPrivateMessage(invokerId, config.readValue("messageSyntax"));
                }

            } else if (command[1].equalsIgnoreCase("UNLINK")) {

            } else {
                api.sendPrivateMessage(invokerId, config.readValue("messageSyntax"));
            }

        } else {
            api.sendPrivateMessage(invokerId, config.readValue("messageSyntax"));
        }


    }

}
