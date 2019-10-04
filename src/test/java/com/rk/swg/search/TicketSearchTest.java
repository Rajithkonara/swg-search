package com.rk.swg.search;

import com.rk.swg.dto.SearchResults;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketSearchTest {

    @Test
    void return_ticket_test() {
        SearchResults searchResults = SearchFactory.getInstance().search(2,
                "_id", "436bf9b0-1147-4c0a-8439-6f79833bff5b");
        assertNotNull(searchResults);
        assertTrue(searchResults.getTicketSearchResult().get(0).getTicket().get_id().
                contains("436bf9b0-1147-4c0a-8439-6f79833bff5b"));
    }

    @Test
    void return_assignee_name_test() {
        SearchResults searchResults = SearchFactory.getInstance().search(2,
                "_id", "436bf9b0-1147-4c0a-8439-6f79833bff5b");
        assertNotNull(searchResults);
        assertEquals("Harris Côpeland",
                searchResults.getTicketSearchResult().get(0).getAssigneeNames().get(0).getAssigneeName());

    }

    @Test
    void return_submitter_name_test() {
        SearchResults searchResults = SearchFactory.getInstance().search(2,
                "_id", "436bf9b0-1147-4c0a-8439-6f79833bff5b");
        assertNotNull(searchResults);
        assertEquals("Elma Castro",
                searchResults.getTicketSearchResult().get(0).getSubmitterNames().get(0).getSubmitterName());
    }

    @Test
    void return_organization_name_test() {
        SearchResults searchResults = SearchFactory.getInstance().search(2,
                "_id", "436bf9b0-1147-4c0a-8439-6f79833bff5b");
        assertNotNull(searchResults);
        assertEquals("[Zentry]",
                searchResults.getTicketSearchResult().get(0).getOrganizationName());
    }

}
