package com.rk.swg.dto;

import lombok.Getter;

@Getter
public class UserRefBuilder {

    private String assigneeName;
    private String submitterName;

    public UserRefBuilder(String assigneeName, String submitterName) {
        this.assigneeName = assigneeName;
        this.submitterName = submitterName;
    }

    public static class Builder {
        private String assigneeName;
        private String submitterName;

        public Builder(){}

        public Builder setAssigneeName(String assigneeName) {
            this.assigneeName = assigneeName;
            return this;
        }

        public Builder setSubmitterName(String submitterName) {
            this.submitterName = submitterName;
            return this;
        }


        public UserRefBuilder build() {
            return new UserRefBuilder(assigneeName, submitterName);
        }
    }

}
