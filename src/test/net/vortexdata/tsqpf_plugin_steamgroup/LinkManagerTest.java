package test.net.vortexdata.tsqpf_plugin_steamgroup;

import net.vortexdata.tsqpf_plugin_steamgroup.*;
import net.vortexdata.tsqpf_plugin_steamgroup.exceptions.*;
import org.junit.*;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class LinkManagerTest {

    LinkManager linkManager = new LinkManager("https://steamcommunity.com/groups/vortexdatanet", 10000);

    @Test
    public void testStoreLink() {
        assertEquals(true, linkManager.storeLink("https://steamcommunity.com/id/taxset/", 12345));
    }

    @Test
    public void testGetPin() {
        try {
            assertEquals(12345, linkManager.getPin("https://steamcommunity.com/id/taxset/"));
        } catch (TempLinkNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVerifyLink() {
        boolean isVerified = linkManager.verifyLink("https://steamcommunity.com/id/taxset/");
        assertEquals(true, isVerified);
    }

    @Test
    public void testTempLinkRemover() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            //
        }
    }

}