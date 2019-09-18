package com.rk.swg.search;

import com.rk.swg.dto.SearchResults;
import com.rk.swg.dto.TicketBuilder;
import com.rk.swg.util.DataHolder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TicketSearch implements Search {

    private static final Logger logger = Logger.getLogger(TicketSearch.class);

    @Override
    public SearchResults search(String fieldName, String fieldValue) {
        logger.info("Service not yet Implemented ");
        return null;
    }

    /**
     * List of all tickets
     * @return tickets
     */
    List<TicketBuilder> getAllTickets() throws IOException {

        return DataHolder.getInstance().getTickets().stream()
                .map(
                     p ->
                         new TicketBuilder.Builder().setTicket(p).setAssignedToUser(p.getAssignee_id())
                             .setSubmittedByUser(p.getSubmitter_id())
                             .setOrganizationName(p.getOrganization_id())
                             .build())
                .collect(Collectors.toList());
    }
}
