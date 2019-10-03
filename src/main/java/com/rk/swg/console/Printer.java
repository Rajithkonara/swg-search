package com.rk.swg.console;

import com.rk.swg.dto.SearchResults;

public interface Printer {

    /**
     * Prints the formatted result
     * @param results
     */
    void print(SearchResults results);

}
