package com.rk.swg.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SearchResults {

    private List<com.rk.swg.dto.UserBuilder> userSearchResult;

    public SearchResults(List<com.rk.swg.dto.UserBuilder> userSearchResult) {
        this.userSearchResult = userSearchResult;
    }

    public static class UserBuilder {

        List<com.rk.swg.dto.UserBuilder> userSearchResult;

        public UserBuilder(){}

        public UserBuilder setUserSearchResult(List<com.rk.swg.dto.UserBuilder> userSearchResult) {
            this.userSearchResult = userSearchResult;
            return this;
        }

        public SearchResults build() {
            return new SearchResults(userSearchResult);
        }
    }

}