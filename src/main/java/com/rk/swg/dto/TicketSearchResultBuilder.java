package com.rk.swg.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TicketSearchResultBuilder {

    private Ticket ticket;
    private List<UserRefBuilder> assigneeNames;
    private List<UserRefBuilder> submitterNames;
    private String organizationName;

    public TicketSearchResultBuilder(Ticket ticket, List<UserRefBuilder> assigneeNames,
                                     List<UserRefBuilder> submitterNames, String organizationName) {

        this.ticket = ticket;
        this.assigneeNames = assigneeNames;
        this.submitterNames = submitterNames;
        this.organizationName = organizationName;
    }

    public static class Builder {

        private Ticket ticket;
        private List<UserRefBuilder> assigneeNames;
        private List<UserRefBuilder> submitterNames;
        private String organizationName;

        public Builder() {}

        public Builder setTicket(Ticket ticket) {
            this.ticket = ticket;
            return this;
        }

        public Builder setAssigneeName(List<UserRefBuilder> assigneeNames) {
            this.assigneeNames = assigneeNames;
            return this;
        }

        public Builder setSubmitterName(List<UserRefBuilder> submitterNames) {
            this.submitterNames = submitterNames;
            return this;
        }

        public Builder setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        public TicketSearchResultBuilder build() {
            return new TicketSearchResultBuilder(ticket, assigneeNames, submitterNames, organizationName);
        }

    }


}
