package test.agni.server.dataguard;

import static org.junit.Assert.*;

import org.junit.Test;

public class InfoDataGuardTest {

    private InfoDataGuard infoDataGuard;

    @Before
    public void setup() {
        infoDataGuard = new InfoDataGuard('agni_test', 'test', '');
    }

    @Test
    public String serverNameNotNullTest() {
        assertThat(infoDataGuard.serverName(), not(null));
    }

    @Test
    public int currentOnlineTest() {
        assertEquals(infoDataGuard.currentOnline(), 0);
        
        infoDataGuard.incrementUsersOnline();
        assertEquals(infoDataGuard.currentOnline(), 1);
        
        infoDataGuard.incrementUsersOnline();
        assertEquals(infoDataGuard.currentOnline(), 2);
        
        infoDataGuard.decerementUsersOnline();
        infoDataGuard.decerementUsersOnline();
        assertEquals(infoDataGuard.currentOnline(), 0);
    }

    @Test
    public int totalUsersTest() {
        assertEquals(infoDataGuard.totalUsers(), 0);
    }
}
