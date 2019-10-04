package com.rk.swg.console;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rk.swg.dto.SearchResults;
import com.rk.swg.dto.UserRefBuilder;

import java.util.List;

public class TicketPrinter implements Printer {

    @Override
    public void print(SearchResults results) {

        if (results.getTicketSearchResult().isEmpty()) {
            System.out.println("No results found ");
        }

        Gson gson = new Gson();

        results.getTicketSearchResult().forEach(
                p -> {
                    String ticketObject = gson.toJson(p.getTicket());
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) parser.parse(ticketObject);

                    jsonObject.entrySet().forEach(entry -> System.out.println(
                                String.format("%-28.28s %s", entry.getKey(),
                                entry.getValue().toString())));

                    System.out.print(String.format("%-29s", "Assignee Name"));

                    List<UserRefBuilder> assigneeNameList = p.getAssigneeNames();
                    List<UserRefBuilder> submitterNameList = p.getSubmitterNames();

                    if (assigneeNameList.isEmpty()) {
                        System.out.print("---");
                    }

                    assigneeNameList.forEach(x -> System.out.print(x.getAssigneeName() + "\n"));

                    System.out.print("Submitter Name \t\t\t\t ");

                    if (submitterNameList.isEmpty()) {
                        System.out.print("---");
                    }

                    submitterNameList.forEach(x -> System.out.print(x.getSubmitterName() + "\n"));

                    System.out.print(String.format("%-29s", "Organization Name "));

                    System.out.print(p.getOrganizationName());

                    System.out.println("\n");

                }
        );

    }
}
