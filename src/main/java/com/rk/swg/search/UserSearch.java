package com.rk.swg.search;

import com.rk.swg.dto.*;
import com.rk.swg.util.DataHolder;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class UserSearch implements Search {

    private static final Logger logger = Logger.getLogger(UserSearch.class);

    private TicketSearch ticketSearch;

    public UserSearch() {
        ticketSearch = new TicketSearch();
    }

    @Override
    public SearchResults search(String fieldName, String fieldValue) {

        try {
            //get all the tickets
            List<TicketBuilder> allTickets = ticketSearch.getAllTickets();

            //get all the users related
            List<UserBuilder> allUsers = getUserWithTickets(allTickets);

            List<UserBuilder> searchResult =
                    allUsers.stream().map(p -> Pair.of(p.getUser().get_id(), p))
                            .map(
                                    p -> {
                                        Field field = null;
                                        try {
                                            field = p.getRight().getUser().getClass().getDeclaredField(fieldName);
                                            field.setAccessible(true);
                                        } catch (NoSuchFieldException e) {
                                            logger.error(e);
                                        }
                                        Object object = null;

                                        try {
                                            object = field.get(p.getRight().getUser());
                                        } catch (IllegalAccessException e) {
                                            logger.error(e);
                                        }
                                        return Pair.of(object, p.getRight());
                                    }).filter(
                            left -> Objects.nonNull(left.getLeft())).filter(
                            q -> checkType(fieldValue, q)
                    ).filter(left -> Objects.nonNull(left.getLeft())).map(Pair::getRight)
                            .collect(Collectors.toList());


            return new SearchResults.UserBuilder().setUserSearchResult(searchResult).build();
        } catch (IOException e) {
            logger.error(e);
        }

        return null;
    }

    public boolean checkType(String fieldValue, Pair<Object, UserBuilder> q) {
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

    private List<UserBuilder> getUserWithTickets(List<TicketBuilder> allTickets) throws IOException {

        List<UserBuilder> userResultBuilder = new ArrayList<>();

        DataHolder.getInstance().getUsers().forEach(
                user -> {

                    List<TicketRefBuilder> ticketSubmitters = allTickets.stream()
                            .filter(
                                    ticket -> user.get_id().equals(ticket.getSubmittedByUser())
                            ).map(
                                    ticket -> new TicketRefBuilder.Builder()
                                            .setSubject(ticket.getTicket().getSubject()).build()).
                                    collect(Collectors.toList());

                    List<TicketRefBuilder> ticketAssigners = allTickets.stream()
                            .filter(
                                    ticket -> user.get_id().equals(ticket.getAssignedToUser())
                            ).map(
                                    ticket -> new TicketRefBuilder.Builder()
                                            .setSubject(ticket.getTicket().getSubject()).build()
                            ).collect(Collectors.toList());

                    try {
                        List<String> organizations = DataHolder.getInstance().getOrganizations().stream().filter(
                                organization ->
                                        organization.get_id().equals(user.getOrganization_id())
                        ).map(Organization::getName).collect(Collectors.toList());

                        userResultBuilder.add(
                                new UserBuilder.Builder().setUser(user)
                                        .setOrganizationName(String.valueOf(organizations))
                                        .setTicketsSubmitted(ticketSubmitters)
                                        .setTicketsAssigned(ticketAssigners).build());

                    } catch (IOException e) {
                        logger.error(e);
                    }

                });

        return userResultBuilder;
    }

    /**
     * List of all tickets
     * @return tickets
     */
    List<UserBuilder> getAllUsers() throws IOException {

        return DataHolder.getInstance().getUsers().stream()
                .map(
                        p ->
                                new UserBuilder.Builder().setUser(p).setOrganizationName(p.getOrganization_id())
                                        .build())
                .collect(Collectors.toList());
    }
}
