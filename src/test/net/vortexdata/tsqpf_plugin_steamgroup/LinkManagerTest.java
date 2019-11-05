package test.net.vortexdata.tsqpf_plugin_steamgroup;

import net.vortexdata.tsqpf_plugin_steamgroup.exceptions.*;
import net.vortexdata.tsqpf_plugin_steamgroup.modules.*;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LinkManagerTest {

    LinkManager linkManager = new LinkManager("https://steamcommunity.com/groups/vortexdatanet", 10000);

    @Test
    public void testStoreLink() {
        assertEquals(true, linkManager.storeLink("https://steamcommunity.com/id/taxset/", "23141243ds"));
    }

    @Test
    public void testGetPin() {
        try {
            assertEquals(12345, linkManager.getPin("https://steamcommunity.com/id/taxset/"));
        } catch (TempLinkNotFoundException e) {
            fail();
        }
    }

    @Test
    public void testVerifyLink() {
        boolean isVerified = linkManager.verifyLink("https://steamcommunity.com/id/taxset/");
        assertEquals(true, isVerified);
    }



}