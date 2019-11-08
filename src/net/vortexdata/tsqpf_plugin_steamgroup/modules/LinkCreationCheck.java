package net.vortexdata.tsqpf_plugin_steamgroup.modules;

import com.github.theholywaffle.teamspeak3.*;
import net.vortexdata.tsqpf.plugins.*;
import net.vortexdata.tsqpf_plugin_steamgroup.utils.*;

public class LinkCreationCheck implements Runnable {

    private String[] command;
    private UrlValidator urlValidator;
    private TS3Api api;
    private String invokerUid;
    private int invokerId;
    private PluginConfig config;
    private LinkManager linkManager;
    private PluginLogger logger;

    public LinkCreationCheck(String[] command, TS3Api api, String invokerUID, int invokerId, PluginConfig config, LinkManager linkManager, PluginLogger logger) {
        this.command = command;
        this.urlValidator = new UrlValidator();
        this.api = api;
        this.invokerUid = invokerUID;
        this.config = config;
        this.linkManager = linkManager;
        this.logger = logger;
        this.invokerId = invokerId;
    }

    @Override
    public void run() {

        // TODO: Add a task run confirmation message

        // Check if URL is valid
        if (!urlValidator.validateProfileUrl(command[2])) {
            api.sendPrivateMessage(invokerId, config.readValue("messageLinkUrlInvalid"));
            return;
        }

        if (linkManager.verifyLink(command[2])) {
            try {
                api.addClientToServerGroup(Integer.parseInt(config.readValue("linkGroupId")), invokerId);
                api.sendPrivateMessage(invokerId, config.readValue("messageLinkCreated"));
                linkManager.
            } catch (Exception e) {
                api.sendPrivateMessage(invokerId, config.readValue("messageUnknownError"));
                logger.printWarn("Link server group was not found. Please check your config.");
            }
        } else {
            api.sendPrivateMessage(invokerId, config.readValue("messageLinkClientNotVerified"));
        }
    }

}
