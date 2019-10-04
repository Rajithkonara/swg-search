package com.rk.swg.search;

import com.rk.swg.dto.SearchResults;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizationSearchTest {

    @Test
    void return_ticket_test() {
        SearchResults searchResults = SearchFactory.getInstance().search(3, "_id", "101");
        assertNotNull(searchResults);
        assertEquals("101", searchResults.getOrgSearchResult().get(0).getOrganization().get_id());
    }

    @Test
    void return_ticket_subjects_test() {
        SearchResults searchResults = SearchFactory.getInstance().search(3, "_id", "101");
        assertNotNull(searchResults);
        assertTrue(searchResults.getOrgSearchResult().get(0).getTickets().get(0).getSubject().
                contains("A Drama in Portugal"));
    }

    @Test
    void return_user_name_test() {
        SearchResults searchResults = SearchFactory.getInstance().search(3, "_id", "101");
        assertNotNull(searchResults);
        assertTrue(searchResults.getOrgSearchResult().get(0).getUsers().get(0).contains("Loraine Pittman"));
    }

}
