package test.net.vortexdata.tsqpf_plugin_steamgroup;

import net.vortexdata.tsqpf_plugin_steamgroup.utils.*;
import org.junit.*;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class UrlValidaterTest {

    UrlValidator urlValidator = new UrlValidator();

    @Test
    public void testUrlValidation0() {
        assertEquals(true, urlValidator.validateProfileUrl("https://steamcommunity.com/id/taxset/"));
    }

    @Test
    public void testUrlValidation1() {
        assertEquals(false, urlValidator.validateProfileUrl("https://steamcommunity.a/id/taxset/"));
    }

    @Test
    public void testUrlValidation2() {
        assertEquals(false, urlValidator.validateProfileUrl("https://stecom/id/set/"));
    }

    @Test
    public void testUrlValidation3() {
        assertEquals(false, urlValidator.validateProfileUrl("htts://steamcommunity.a/id/taxset/"));
    }


}