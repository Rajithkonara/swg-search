package com.rk.swg.search;

import com.rk.swg.dto.SearchResults;

public class SearchFactory {

    private SearchFactory() {}

    private static SearchFactory instance;

    public static synchronized SearchFactory getInstance() {
        if (instance == null) {
            instance = new SearchFactory();
        }
        return instance;
    }

    public SearchResults search(int choice, String field, String value) {
        switch (choice) {
            case 1:
                return new UserSearch().search(field, value);
            case 2:
                return new TicketSearch().search(field, value);
            case 3:
                return new OrganizationSearch().search(field, value);
            default:
                throw new IllegalArgumentException("Not a valid criteria");
        }
    }
}


