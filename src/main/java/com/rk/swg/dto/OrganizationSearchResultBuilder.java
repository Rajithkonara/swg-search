package com.rk.swg.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrganizationSearchResultBuilder {

    private Organization organization;
    private List<String> users;
    private List<TicketRefBuilder> tickets;

    public OrganizationSearchResultBuilder(Organization organization, List<String> users,
                                           List<TicketRefBuilder> tickets) {
        this.organization = organization;
        this.users = users;
        this.tickets = tickets;
    }

    public static class Builder {

        private Organization organization;
        private List<String> users;
        private List<TicketRefBuilder> tickets;

        public Builder() {}

        public Builder setOrganization(Organization oraganization) {
            this.organization = oraganization;
            return this;
        }

        public Builder setUsers(List<String> users) {
            this.users = users;
            return this;
        }

        public Builder setTickets(List<TicketRefBuilder> tickets){
            this.tickets = tickets;
            return this;
        }

        public OrganizationSearchResultBuilder build() {
            return new OrganizationSearchResultBuilder(organization, users, tickets);
        }

    }



}
