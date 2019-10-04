package com.rk.swg.search;

import com.rk.swg.dto.SearchResults;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserSearchTest {

  @Test
  void return_user() {

      SearchResults searchResults = SearchFactory.getInstance().search(1, "_id", "71");
      assertNotNull(searchResults);
      assertEquals("Prince Hinton", searchResults.getUserSearchResult().get(0).getUser().getName());

  }

  @Test
  void return_org_name() {

      SearchResults searchResults = SearchFactory.getInstance().search(1, "_id", "71");
      assertNotNull(searchResults);
      assertTrue(searchResults.getUserSearchResult().get(0).getOrganizationName().
              contains("Hotcâkes"));

  }

  @Test
  void return_assigned_tickets() {

      SearchResults searchResults = SearchFactory.getInstance().search(1, "_id", "71");
      assertNotNull(searchResults);
      assertTrue(searchResults.getUserSearchResult().get(0).getTicketsAssigned().get(0).getSubject().
              contains("A Catastrophe in Sierra Leone"));
  }

  @Test
  void return_submitted_tickets() {
      SearchResults searchResults = SearchFactory.getInstance().search(1, "_id", "71");
      assertNotNull(searchResults);
      assertTrue(searchResults.getUserSearchResult().get(0).getTicketsSubmitted().
              get(0).getSubject().contains("A Catastrophe in Micronesia"));
  }

}
