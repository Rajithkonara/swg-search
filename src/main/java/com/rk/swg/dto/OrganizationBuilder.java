package com.rk.swg.dto;

import lombok.Getter;

@Getter
public class OrganizationBuilder {

    private Organization organization;
    private String name;

    public OrganizationBuilder(Organization organization, String name) {
        this.organization = organization;
        this.name = name;
    }

    public static class Builder {

        private String name;
        private Organization organization;

        public Builder(){}

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setOrganization(Organization organization) {
            this.organization = organization;
            return this;
        }

        public OrganizationBuilder build() {
            return new OrganizationBuilder(organization, name);
        }
    }

}
