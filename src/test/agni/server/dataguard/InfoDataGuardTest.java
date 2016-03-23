package test.agni.server.dataguard;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import agni.server.dataguard.InfoDataGuard;

public class InfoDataGuardTest {

    private InfoDataGuard infoDataGuard;

    @Before
    public void setup() {
        infoDataGuard = new InfoDataGuard("agni_test", "test", "");
    }

    @Test
    public void serverNameNotNullTest() {
        assertThat(infoDataGuard.serverName(), not(null));
    }

    @Test
    public void currentOnlineTest() {
        assertEquals(infoDataGuard.currentOnline(), 0);
        
        infoDataGuard.incrementUsersOnline();
        assertEquals(infoDataGuard.currentOnline(), 1);
        
        infoDataGuard.incrementUsersOnline();
        assertEquals(infoDataGuard.currentOnline(), 2);
        
        infoDataGuard.decrementUsersOnline();
        infoDataGuard.decrementUsersOnline();
        assertEquals(infoDataGuard.currentOnline(), 0);
    }

    @Test
    public void totalUsersTest() {
        assertEquals(infoDataGuard.totalUsers(), 0);
    }
}
