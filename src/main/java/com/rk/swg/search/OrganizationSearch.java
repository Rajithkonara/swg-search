package com.rk.swg.search;

import com.rk.swg.dto.OrganizationBuilder;
import com.rk.swg.dto.SearchResults;
import com.rk.swg.util.DataHolder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizationSearch implements Search {

    private static final Logger logger = Logger.getLogger(OrganizationSearch.class);

    @Override
    public SearchResults search(String fieldName, String fieldValue) {
        logger.info("Service not yet Implemented ");
        return null;
    }

    List<OrganizationBuilder> getAllOrganizations() throws IOException {

        return DataHolder.getInstance().getOrganizations().stream()
                .map(
                        p ->
                                new OrganizationBuilder.Builder().setOrganization(p)
                                        .setName(p.getName())
                                        .build())
                .collect(Collectors.toList());
    }

}
