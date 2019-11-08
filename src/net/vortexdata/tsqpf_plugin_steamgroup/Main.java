package net.vortexdata.tsqpf_plugin_steamgroup;

import net.vortexdata.tsqpf.plugins.TeamspeakPlugin;
import net.vortexdata.tsqpf_plugin_steamgroup.commands.chat.CSteamGroup;
import net.vortexdata.tsqpf_plugin_steamgroup.modules.*;

public class Main extends TeamspeakPlugin {

    private LinkManager linkManager;

    @Override
    public void onEnable() {

        getConfig().setDefault("steamGroupUrl", "https://steamcommunity.com/groups/vortexdatanet");
        getConfig().setDefault("linkGroupId", "1");
        getConfig().setDefault("tempLinkRemoveIntervalInSeconds", "600");
        getConfig().setDefault("messageLinkUrlInvalid", "Sorry, but it looks like the URL you entered is not a valid Steam URL.");
        getConfig().setDefault("messageLinkCreated", "Congratulations! You have now been assigned the Steam verified group.");
        getConfig().setDefault("messageLinkClientNotVerified", "Sorry, but I couldn't verify that this account belongs to you. Please check your pin for any typos and try again. For more information, use the command '!steamgroup guide'.");
        getConfig().setDefault("messageLinkRemoved", "Your account has been successfully unlinked.");
        getConfig().setDefault("messagePinCreated", "Your verification pin has been created. Please paste it in to your Steam profiles summary box and proceed with the verification in the next 10 minutes before the pin gets invalidated. Your pin is: ");
        getConfig().setDefault("messageGuide", "First run '!steamgroup link <STEAM_PROFILE_URL>'. This will generate a PIN which you have to paste in to your Steam profiles info box. After you have done that, run '!steamgroup verify <YOUR_STEAM_URL>'. If you've followed all steps your account should now be linked.");
        getConfig().setDefault("messageSyntax", "!steamgroup <guide | pin | link | unlink>");
        getConfig().setDefault("messageLinkClientAlreadyLinked", "Sorry, but your account seems to be linked already.");
        getConfig().setDefault("messageLinkClientVerificationRunning", "Verifying Steam link... Please wait. This can take a few seconds.");
        getConfig().setDefault("messageLinkClientLinkedNeedsVerification", "Sorry, but your account is already awaiting verification. Please use this pin: ");
        getConfig().setDefault("messageUnknownError", "An unknown error has occurred. Please contact the server administrator and tell them about this issue.");
        getConfig().saveAll();

        try {
            linkManager = new LinkManager(getConfig().readValue("steamGroupUrl"), Integer.parseInt(getConfig().readValue("tempLinkRemoveIntervalInSeconds")));
            registerChatCommand(new CSteamGroup(getAPI(), getConfig(), linkManager, getLogger()), "!steamgroup");
        } catch (NumberFormatException e) {
            getLogger().printError("Failed to parse tempLinkRemoveIntervalInSeconds config value. Please check your config.");
            onDisable();
        }

    }

    @Override
    public void onDisable() {

        linkManager = null;

    }

}
