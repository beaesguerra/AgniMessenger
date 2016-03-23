package test.agni.server.dataguard;

import static org.junit.Assert.*;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import agni.server.dataguard.InfoDataGuard;

public class InfoDataGuardTest {

    private InfoDataGuard infoDataGuard;

    @Before
    public void setup() {
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("mysql -u agni_tester agni_test < AgniTest.sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
        infoDataGuard = new InfoDataGuard("agni_test", "agni_tester", "");
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
