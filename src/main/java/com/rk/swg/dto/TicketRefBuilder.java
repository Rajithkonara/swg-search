package com.rk.swg.dto;

import lombok.Getter;

@Getter
public class TicketRefBuilder {

    private String subject;

    public TicketRefBuilder(String subject) {
        this.subject = subject;
    }

    public static class Builder {

        private String subject;

        public Builder(){}

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public TicketRefBuilder build() {
            return new TicketRefBuilder(subject);
        }
    }

}
