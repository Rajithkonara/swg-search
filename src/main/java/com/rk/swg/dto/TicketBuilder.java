package com.rk.swg.dto;

import lombok.Getter;

@Getter
public class TicketBuilder {

    private Ticket ticket;
    private String submittedByUser;
    private String organizationName;
    private String assignedToUser;

    public TicketBuilder(Ticket ticket, String submittedByUser, String organizationName, String assignedToUser) {
        this.ticket = ticket;
        this.submittedByUser = submittedByUser;
        this.organizationName = organizationName;
        this.assignedToUser = assignedToUser;
    }

    public static class Builder {

        private Ticket ticket;
        private String submittedByUser;
        private String organizationName;
        private String assignedToUser;

        public Builder(){}

        public Builder setTicket(Ticket ticket) {
            this.ticket = ticket;
            return this;
        }

        public Builder setSubmittedByUser(String submittedByUser) {
            this.submittedByUser = submittedByUser;
            return this;
        }

        public Builder setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        public Builder setAssignedToUser(String assignedToUser) {
            this.assignedToUser = assignedToUser;
            return this;
        }

        public TicketBuilder build() {
            return new TicketBuilder(ticket, submittedByUser, organizationName ,assignedToUser);
        }
    }
}
