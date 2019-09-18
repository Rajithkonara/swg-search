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

    private OrganizationSearch organizationSearch;

    public UserSearch() {
        ticketSearch = new TicketSearch();
        organizationSearch = new OrganizationSearch();
    }

    @Override
    public SearchResults search(String fieldName, String fieldValue) {
        try {
            //get all the tickets
            List<TicketBuilder> allTickets = ticketSearch.getAllTickets();

            //get all organizations
            List<OrganizationBuilder> allOrganizations = organizationSearch.getAllOrganizations();

            //get all the users related
            List<UserBuilder> allUsers = getUserWithTickets(allTickets, allOrganizations);

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
                            q -> {
                                if (q.getLeft() instanceof ArrayList) {
                                    ArrayList<String> left = (ArrayList<String>) q.getLeft();
                                    return left.contains(fieldValue);
                                } else if (q.getLeft() instanceof Boolean) {
                                    return Boolean.parseBoolean(Boolean.toString((Boolean) q.getLeft()));
                                } else {
                                    return q.getLeft().equals(fieldValue);
                                }
                            }
                    ).filter(left -> Objects.nonNull(left.getLeft())).map(q -> q.getRight())
                            .collect(Collectors.toList());


            return new SearchResults.UserBuilder().setUserSearchResult(searchResult).build();
        } catch (IOException e) {
            logger.error(e);
        }

        return null;
    }

    private List<UserBuilder> getUserWithTickets(List<TicketBuilder> allTickets,
                                                 List<OrganizationBuilder> allorg) throws IOException {

        List<UserBuilder> userResultBuilder = new ArrayList<>();

        DataHolder.getInstance().getUsers().forEach(
                user -> {

                    List<TicketRefBuilder> ticketSubmitters = allTickets.stream()
                            .filter(
                                    ticket -> user.get_id().equals(ticket.getSubmittedByUser())
                            ).map(
                                    ticket -> new TicketRefBuilder.Builder()
                                            .setSubject(ticket.getTicket().getSubject()).build()).collect(Collectors.toList());

                    List<TicketRefBuilder> ticketAssigners = allTickets.stream()
                            .filter(
                                    ticket -> user.get_id().equals(ticket.getAssignedToUser())
                            ).map(
                                    ticket -> new TicketRefBuilder.Builder()
                                            .setSubject(ticket.getTicket().getSubject()).build()
                            ).collect(Collectors.toList());

                    userResultBuilder.add(
                            new UserBuilder.Builder().setUser(user)
                                    .setOrganizationName(null)
                                    .setTicketsSubmitted(ticketSubmitters)
                                    .setTicketsAssigned(ticketAssigners).build());

                });

        return userResultBuilder;
    }

}
