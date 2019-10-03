package com.rk.swg.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UserBuilder {

    private List<UserBuilder> userSearchResult;
    private User user;
    private String organizationName;
    private List<TicketRefBuilder> ticketsAssigned;
    private List<TicketRefBuilder> ticketsSubmitted;


    public UserBuilder(List<UserBuilder> userSearchResult, User user, String organizationName,
                       List<TicketRefBuilder> ticketsAssigned,
                       List<TicketRefBuilder> ticketsSubmitted) {

        this.userSearchResult = userSearchResult;
        this.user = user;
        this.organizationName = organizationName;
        this.ticketsAssigned = ticketsAssigned;
        this.ticketsSubmitted = ticketsSubmitted;
    }

    public static class Builder {

        List<UserBuilder> userSearchResult;
        private User user;
        String organizationName;
        List<TicketRefBuilder> ticketsAssigned;
        List<TicketRefBuilder> ticketsSubmitted;

        public Builder(){}

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        public Builder setTicketsAssigned(List<TicketRefBuilder> ticketsAssigned) {
            this.ticketsAssigned = ticketsAssigned;
            return this;
        }

        public Builder setTicketsSubmitted(List<TicketRefBuilder> ticketsSubmitted) {
            this.ticketsSubmitted = ticketsSubmitted;
            return this;
        }

        public Builder setUserSearchResult(List<UserBuilder> userSearchResult) {
            this.userSearchResult = userSearchResult;
            return this;
        }


        public UserBuilder build() {
            return new UserBuilder(userSearchResult, user, organizationName, ticketsAssigned, ticketsSubmitted);
        }
    }

}
