package com.rk.swg;

import com.rk.swg.dto.SearchResults;
import com.rk.swg.search.SearchFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserSearchTest {

    @Test
    void search_for_users() {

        int choiceNo = 1;
        String fieldValue = "name";
        String searchValue = "Francisca Rasmussen";

        SearchResults searchResults = Mockito.mock(SearchResults.class);

        SearchFactory print = Mockito.mock(SearchFactory.class);

        Mockito.when(print.search(choiceNo, fieldValue, searchValue)).thenReturn(searchResults);

    }

}
