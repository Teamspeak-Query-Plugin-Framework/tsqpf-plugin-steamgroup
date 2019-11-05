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
        getConfig().setDefault("messageLinkUrlInvalid", "Sorry, but it looks like the URL you entered is not a valid url that belongs to Steam.");
        getConfig().setDefault("messageLinkCreated", "Congratulations! You have now been assigned the steam verified group.");
        getConfig().setDefault("messageLinkClientNotFound", "Sorry, but I couldn't find this username.");
        getConfig().setDefault("messageLinkClientNotVerified", "Sorry, but I couldn't verify that this account belongs to you.");
        getConfig().setDefault("messageLinkRemoved", "Your account has been successfully unlinked.");
        getConfig().setDefault("messagePinCreated", "Your verification pin has been created. Please paste it in to your steam profiles info box. Your pin is: ");
        getConfig().setDefault("messageGuide", "First run '!steamgroup link <STEAM_PROFILE_URL>'. This will generate a PIN which you have to paste in to your Steam profiles info box. After you have done that, run '!steamgroup verify <YOUR_STEAM_URL>'. If you've followed all steps your account should now be linked.");
        getConfig().setDefault("messageSyntax", "!steamgroup <guide | pin | link | unlink>");
        getConfig().saveAll();

        registerChatCommand(new CSteamGroup(getAPI(), getConfig(), linkManager), "!steamgroup");

        linkManager = new LinkManager(getConfig().readValue("steamGroupUrl"));

    }

    @Override
    public void onDisable() {



    }

}
