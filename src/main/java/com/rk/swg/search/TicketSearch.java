package com.rk.swg.search;

import com.rk.swg.dto.*;
import com.rk.swg.util.DataHolder;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TicketSearch implements Search {

    private static final Logger logger = Logger.getLogger(TicketSearch.class);

    @Override
    public SearchResults search(String fieldName, String fieldValue) {

        try {

            UserSearch userSearch = new UserSearch();

            List<UserBuilder> getAllUsers = userSearch.getAllUsers();

            List<TicketSearchResultBuilder> getUsersAndOrg = getUsersAndOrganization(getAllUsers);

            List<TicketSearchResultBuilder> searchResults = getUsersAndOrg.stream().
                    map(p -> Pair.of(p.getTicket().get_id(), p)).map(
                            p -> {
                                Field field = null;
                                try {
                                    field = p.getRight().getTicket().getClass().getDeclaredField(fieldName);
                                    field.setAccessible(true);
                                } catch (NoSuchFieldException e) {
                                    logger.error(e);
                                }
                                Object object = null;
                                try {
                                    object = field.get(p.getRight().getTicket());
                                } catch (IllegalAccessException e) {
                                    logger.error(e);
                                }
                                return Pair.of(object, p.getRight());
                            }).filter(left -> Objects.nonNull(left.getLeft())).filter(
                        q -> checkType(fieldValue, q))
                        .map(Pair::getRight).collect(Collectors.toList());

                return new SearchResults.TicketBuilder().setTicketSearchResult(searchResults).build();

        } catch (IOException e) {
            logger.error(e);
        }

        return null;
    }

    public boolean checkType(String fieldValue, Pair<Object, TicketSearchResultBuilder> q) {
        if (q.getLeft().getClass().isArray()) {
            String[] left = (String[]) q.getLeft();
            List<String> list = Arrays.asList(left);
            return list.contains(fieldValue);
        } else if (q.getLeft() instanceof Boolean) {
            return Boolean.parseBoolean(Boolean.toString((Boolean) q.getLeft()));
        } else {
            return q.getLeft().equals(fieldValue);
        }
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


    List<TicketSearchResultBuilder> getUsersAndOrganization(List<UserBuilder> users) throws IOException {

        List<TicketSearchResultBuilder> ticketSearchResult = new ArrayList<>();


        DataHolder.getInstance().getTickets().forEach(

                ticket -> {

                    List<UserRefBuilder> assigneddUsers = users.stream().filter(
                            user ->
                                    user.getUser().get_id().equals(ticket.getAssignee_id())).
                            map(
                              user -> new UserRefBuilder.Builder().setAssigneeName(user.getUser().getName()).
                                      build()).collect(Collectors.toList());

                    List<UserRefBuilder> submitterUsers = users.stream().filter(
                            user ->
                                    ticket.getSubmitter_id().equals(user.getUser().get_id())).
                            map(
                                    user -> new UserRefBuilder.Builder().setSubmitterName(user.getUser().getName()).
                                            build()).collect(Collectors.toList());

                    try {
                        List<String> organizations = DataHolder.getInstance().getOrganizations().stream().filter(

                                organization ->
                                        organization.get_id().equals(ticket.getOrganization_id())
                        ).map(Organization::getName).collect(Collectors.toList());

                        ticketSearchResult.add(new TicketSearchResultBuilder.Builder().setTicket(ticket)
                                .setAssigneeName(assigneddUsers).setSubmitterName(submitterUsers).
                                        setOrganizationName(String.valueOf(organizations)).build()

                        );

                    } catch (IOException e) {
                        logger.error(e);
                    }
                });

            return ticketSearchResult;
    }

}
