package com.rk.swg.console;

import com.google.gson.*;
import com.rk.swg.dto.SearchResults;
import com.rk.swg.dto.TicketRefBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class UserPrinter implements Printer {

    @Override
    public void print(SearchResults results) {

        if (results.getUserSearchResult().isEmpty()) {
            System.out.println("No results found ");
        }

        Gson gson = new Gson();

        results.getUserSearchResult().forEach(
                p -> {

                    String userObject = gson.toJson(p.getUser());
                    JsonParser parser = new JsonParser();
                    JsonObject jObj = (JsonObject) parser.parse(userObject);

                    jObj.entrySet().forEach(entry ->
                            System.out.println(String.format("%-28.28s %s", entry.getKey(),
                                    entry.getValue().toString())));

                    System.out.println("organization_name \t\t\t " + p.getOrganizationName());

                    System.out.print("Assigned Tickets \t\t\t ");

                    List<TicketRefBuilder> assignedTicketList = p.getTicketsAssigned();
                    List<TicketRefBuilder> submittedTicketList = p.getTicketsSubmitted();

                    if (assignedTicketList.isEmpty()) {
                        System.out.print("---");
                    }

                    String assignedTickets =
                            assignedTicketList.stream().map(TicketRefBuilder::getSubject).
                                    collect(Collectors.joining(", "));
                    System.out.print(assignedTickets);

                    System.out.println(" ");
                    System.out.print("Submitted Tickets \t\t\t ");

                    if (submittedTicketList.isEmpty()) {
                        System.out.print("---");
                    }

                    String submittedTickets = submittedTicketList.stream().map(TicketRefBuilder::getSubject)
                            .collect(Collectors.joining(", "));
                    System.out.print(submittedTickets);

                    System.out.println("\n \n");
                }
        );
    }

}
