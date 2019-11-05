package net.vortexdata.tsqpf_plugin_steamgroup.utils;

import java.net.*;

public class UrlValidator {

    public boolean validateProfileUrl(String url) {
        try {
            URL cUrl = new URL(url);

            if(!url.split(":")[0].equalsIgnoreCase("HTTPS"))
                return false;

            if(!url.split("\\.")[0].equalsIgnoreCase("https://steamcommunity"))
                return false;

            if(!url.split("\\.")[1].split("/")[0].equalsIgnoreCase("COM"))
                return false;

        } catch (MalformedURLException e) {
            return false;
        }

        return true;
    }

}
