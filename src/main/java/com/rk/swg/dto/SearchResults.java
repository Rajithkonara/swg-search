package com.rk.swg.dto;

import com.rk.swg.search.TicketSearch;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchResults {

    private List<com.rk.swg.dto.UserBuilder> userSearchResult;
    private List<com.rk.swg.dto.OrganizationSearchResultBuilder> orgSearchResult;
    private List<TicketSearchResultBuilder> ticketSearchResult;

    public SearchResults(List<com.rk.swg.dto.UserBuilder> userSearchResult,
                         List<com.rk.swg.dto.OrganizationSearchResultBuilder> orgSearchResult,
                         List<TicketSearchResultBuilder> ticketSearchResult) {
        this.userSearchResult = userSearchResult;
        this.orgSearchResult = orgSearchResult;
        this.ticketSearchResult = ticketSearchResult;
    }

    public static class UserBuilder {

        List<com.rk.swg.dto.UserBuilder> userSearchResult;

        public UserBuilder(){}

        public UserBuilder setUserSearchResult(List<com.rk.swg.dto.UserBuilder> userSearchResult) {
            this.userSearchResult = userSearchResult;
            return this;
        }

        public SearchResults build() {
            return new SearchResults(userSearchResult, null, null);
        }
    }


    public static class OrganizationBuilder {

        List<com.rk.swg.dto.OrganizationSearchResultBuilder> orgSearchResult;

        public OrganizationBuilder(){}

        public OrganizationBuilder setOrgSearchResult(List<com.rk.swg.dto.OrganizationSearchResultBuilder> orgSearchResult) {
            this.orgSearchResult = orgSearchResult;
            return this;
        }

        public SearchResults build() {
            return new SearchResults(null, orgSearchResult, null);
        }
    }

    public static class TicketBuilder {

        List<TicketSearchResultBuilder> ticketSearchResult;

        public TicketBuilder() {}

        public TicketBuilder setTicketSearchResult(List<TicketSearchResultBuilder> ticketSearchResult) {
            this.ticketSearchResult = ticketSearchResult;
            return this;
        }

        public SearchResults build() { return new SearchResults(null, null, ticketSearchResult);  }

    }

}