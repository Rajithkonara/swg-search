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

public class OrganizationSearch implements Search {

    private static final Logger logger = Logger.getLogger(OrganizationSearch.class);

    private TicketSearch ticketSearch;

    public OrganizationSearch(TicketSearch ticketSearch) {
        this.ticketSearch = ticketSearch;
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
                                    logger.error(e);
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
                            q -> checkType(fieldValue, q)).
                            map(Pair::getRight).collect(Collectors.toList());

            return new SearchResults.OrganizationBuilder().setOrgSearchResult(searchResult).build();
        } catch (IOException e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Check the value type
     * @param fieldValue
     * @param q
     * @return
     */
    public boolean checkType(String fieldValue, Pair<Object, OrganizationSearchResultBuilder> q) {
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
     * get all relevant tickets and users
     * @param tickets
     * @return
     * @throws IOException
     */
    private List<OrganizationSearchResultBuilder> getTicketsAndUsers(List<TicketBuilder> tickets) throws IOException {

        List<OrganizationSearchResultBuilder> orgSearchResultList = new ArrayList<>();

        DataHolder.getInstance().getOrganizations().forEach(
                organization -> {

                    List<TicketRefBuilder> attachedTickets = tickets.stream().filter(
                            ticket ->
                                    organization.get_id().equals(ticket.getOrganizationName()))
                            .map(
                                    ticket ->
                                            new TicketRefBuilder.Builder().setSubject(ticket.getTicket().getSubject()).build()).
                                    collect(Collectors.toList());

                    try {
                        List<String> users = DataHolder.getInstance().getUsers().stream().filter(
                                user ->
                                        organization.get_id().equals(user.getOrganization_id())
                        ).map(User::getName).collect(Collectors.toList());

                        orgSearchResultList.add(new OrganizationSearchResultBuilder.Builder().setOrganization(organization).
                                setTickets(attachedTickets).
                                setUsers(users).build());
                    } catch (IOException e) {
                        logger.error(e);
                    }

                });

        return orgSearchResultList;
    }
}
