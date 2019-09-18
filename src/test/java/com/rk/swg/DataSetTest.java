package com.rk.swg;

import com.rk.swg.dto.Organization;
import com.rk.swg.dto.Ticket;
import com.rk.swg.dto.User;
import com.rk.swg.util.DataHolder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DataSetTest {

    @Test
    void getUsersCountTest() throws IOException {

        List<User> users = DataHolder.getInstance().getUsers();
        assertNotNull(users);
        assertEquals(75, users.size());
    }

    @Test
    void getTicketsCountTest() throws IOException {
        List<Ticket> tickets = DataHolder.getInstance().getTickets();
        assertNotNull(tickets);
        assertEquals(200, tickets.size());
    }

    @Test
    void getOrganizationCountTest() throws IOException {
        List<Organization> organizations = DataHolder.getInstance().getOrganizations();
        assertNotNull(organizations);
        assertEquals(25, organizations.size());

    }
}
