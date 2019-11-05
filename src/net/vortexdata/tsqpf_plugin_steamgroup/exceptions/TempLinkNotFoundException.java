package net.vortexdata.tsqpf_plugin_steamgroup.exceptions;

public class TempLinkNotFoundException extends Exception {

    public TempLinkNotFoundException() {
        super("There is not temp link associated with the requested URL.");
    }
}
