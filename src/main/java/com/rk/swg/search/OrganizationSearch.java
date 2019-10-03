package com.rk.swg.search;

import com.rk.swg.dto.*;
import com.rk.swg.util.DataHolder;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrganizationSearch implements Search {

    private static final Logger logger = Logger.getLogger(OrganizationSearch.class);

    private TicketSearch ticketSearch;

    public OrganizationSearch() {
        ticketSearch = new TicketSearch();
    }

    @Override
    public SearchResults search(String fieldName, String fieldValue) {
        try {

            //get all the tickets
            List<TicketBuilder> allTickets = ticketSearch.getAllTickets();

            //get all related tickets and users
            List<OrganizationSearchResultBuilder> getTicketsAndUsers = getTicketsAndUsers(allTickets);

            List<OrganizationSearchResultBuilder> searchResult = getTicketsAndUsers.stream()
                    .map(p -> Pair.of(p.getOrganization().get_id(), p)).map(
                            p -> {
                                Field field = null;
                                try {
                                    field = p.getRight().getOrganization().getClass().getDeclaredField(fieldName);
                                    field.setAccessible(true);
                                } catch (NoSuchFieldException e) {
                                    e.printStackTrace();
                                }
                                Object object = null;
                                try {
                                    object = field.get(p.getRight().getOrganization());
                                } catch (IllegalAccessException e) {
                                    logger.error(e);
                                }
                                return Pair.of(object, p.getRight());
                            })
                            .filter(left -> Objects.nonNull(left.getLeft())).filter(
                                    q -> {
                                        if (q.getLeft() instanceof ArrayList) {
                                            ArrayList<String> left = (ArrayList<String>) q.getLeft();
                                            return left.contains(fieldValue);
                                        } else if (q.getLeft() instanceof Boolean) {
                                            return Boolean.parseBoolean(Boolean.toString((Boolean) q.getLeft()));
                                        } else {
                                            return q.getLeft().equals(fieldValue);
                                        }
                                    }).map(Pair::getRight).collect(Collectors.toList());

            return new SearchResults.OrganizationBuilder().setOrgSearchResult(searchResult).build();
        } catch (IOException e) {
            logger.error(e);
        }


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


    List<OrganizationSearchResultBuilder> getTicketsAndUsers(List<TicketBuilder> tickets) throws IOException {

        List<OrganizationSearchResultBuilder> orgSearchResultList = new ArrayList<>();

        DataHolder.getInstance().getOrganizations().forEach(
                organization -> {

                    List<TicketRefBuilder> attachedTickets =  tickets.stream().filter(
                            ticket ->
                                    organization.get_id().equals(ticket.getOrganizationName()))
                            .map(
                            ticket ->
                                    new TicketRefBuilder.Builder().setSubject(ticket.getTicket().getSubject()).build()).
                            collect(Collectors.toList());

                    try {
                        List<String> users =  DataHolder.getInstance().getUsers().stream().filter(
                                user ->
                                        organization.get_id().equals(user.getOrganization_id())
                        ).map(User::getName).collect(Collectors.toList());

                        orgSearchResultList.add(new OrganizationSearchResultBuilder.Builder().setOraganization(organization).
                                setTickets(attachedTickets).
                                setUsers(users).build());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                });

            return orgSearchResultList;

    }


}
