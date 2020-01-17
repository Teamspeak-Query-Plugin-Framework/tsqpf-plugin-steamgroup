package net.vortexdata.tsqpf_plugin_steamgroup.modules;

import com.github.theholywaffle.teamspeak3.*;
import net.vortexdata.tsqpf.plugins.*;
import net.vortexdata.tsqpf_plugin_steamgroup.exceptions.*;
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

        // Send message to client
        api.sendPrivateMessage(invokerId, config.readValue("messageLinkClientVerificationRunning"));

        try {
            linkManager.getPin(command[2]);
        } catch (TempLinkNotFoundException e) {
            api.sendPrivateMessage(invokerId, config.readValue("messageLinkMissing"));
            return;
        }

        // Check if URL is valid
        if (!urlValidator.validateProfileUrl(command[2])) {
            api.sendPrivateMessage(invokerId, config.readValue("messageLinkUrlInvalid"));
            return;
        }

        if (linkManager.verifyLink(command[2])) {
            try {
                api.addClientToServerGroup(Integer.parseInt(config.readValue("linkGroupId")), api.getClientByUId(invokerUid).getDatabaseId());
                api.sendPrivateMessage(invokerId, config.readValue("messageLinkCreated"));
                linkManager.removeLink(command[2]);
                logger.printDebug("Verification for user " + api.getClientByUId(invokerUid).getLoginName() + " completed successfully.");
            } catch (Exception e) {
                api.sendPrivateMessage(invokerId, config.readValue("messageUnknownError"));
                logger.printWarn("Failed to assign Steam server group, dumping error details: " + e.getMessage());
            }
        } else {
            api.sendPrivateMessage(invokerId, config.readValue("messageLinkClientNotVerified"));
        }
    }

}
