package com.rk.swg.search;

import com.rk.swg.dto.SearchResults;

public interface Search {

    /**
     * @param fieldName
     * @param fieldValue
     * @return
     */
    SearchResults search(String fieldName, String fieldValue);
}
