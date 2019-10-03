package com.rk.swg.console;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rk.swg.dto.SearchResults;
import com.rk.swg.dto.TicketRefBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class OrganizationPrinter implements Printer {

    @Override
    public void print(SearchResults results) {

        if (results.getOrgSearchResult().isEmpty()) {
            System.out.println("No results found ");
        }

        Gson gson = new Gson();
        results.getOrgSearchResult().forEach(
                p -> {
                    String toJson1 = gson.toJson(p.getOrganization());
                    JsonParser parser = new JsonParser();
                    JsonObject jObj = (JsonObject) parser.parse(toJson1);

                    jObj.entrySet().forEach(entry ->
                            System.out.println(String.format("%-20.20s %s", entry.getKey(),
                            entry.getValue().toString())));


                    System.out.print("Ticket Subjects \t ");

                    List<TicketRefBuilder> ticketSubjectList = p.getTickets();

                    if (ticketSubjectList.isEmpty()) {
                        System.out.print("---");
                    }

                    String ticketSubjects = ticketSubjectList.stream().map(TicketRefBuilder::getSubject)
                                            .collect(Collectors.joining(", "));
                    System.out.print(ticketSubjects);

                    List<String> usersList = p.getUsers();

                    System.out.println(" ");
                    System.out.print("User Names \t\t\t ");

                    if (usersList.isEmpty()) {
                        System.out.println("---");
                    }

                   String userNames =  usersList.stream().map(String::toString).
                           collect(Collectors.joining(", "));
                    System.out.println(userNames);

                    System.out.println("\n");
                }
        );
    }
}
