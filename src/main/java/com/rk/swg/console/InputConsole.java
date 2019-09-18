package com.rk.swg.console;

import com.google.gson.Gson;
import com.rk.swg.dto.Organization;
import com.rk.swg.dto.SearchResults;
import com.rk.swg.dto.Ticket;
import com.rk.swg.dto.User;
import com.rk.swg.exception.IllegalInputExeception;
import com.rk.swg.search.SearchFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

public class InputConsole {

    private static final Logger logger = Logger.getLogger(InputConsole.class);

    public static void launchSearchTerminal() throws IllegalInputExeception {

        Scanner scanner = new Scanner(System.in);

        while (true) {
                logger.info("starting the application ..... ");

                System.out.println("Press \n 1) For Users \n 2) For Tickets \n 3) For Organizations \n");

                String choice = scanner.next();
                int choiceNo = Integer.parseInt(choice);

                if (choiceNo < 1 || choiceNo > 3) {
                    throw new IllegalInputExeception();
                }

                System.out.println("Enter the field to search :  ");
                String fieldValue = scanner.next();

                //validation
                boolean isValid = false;

                if (choiceNo == 1) {
                    isValid = validateFields(fieldValue, User.class);
                } else if (choiceNo == 2) {
                    isValid = validateFields(fieldValue, Ticket.class);
                } else if (choiceNo == 3) {
                    isValid = validateFields(fieldValue, Organization.class);
                }

                if (isValid) {
                    System.out.println("Enter Search Value : ");
                    String searchValue = scanner.next();
                    SearchResults print = SearchFactory.getInstance().search(choiceNo, fieldValue, searchValue);
                    Gson gson = new Gson();
                    String toJson = gson.toJson(print.getUserSearchResult());
                    System.out.println(toJson);
                } else {
                    System.out.println("Invalid input");
                    logger.info("Invalid Input");
                }

            System.out.println("Enter N for new search or any other key to exit ");
            boolean continueSearch = scanner.next().equalsIgnoreCase("n");
            if (!continueSearch) break;
        }
    }


    /**
     * Validate the Input fields
     * @param userInput
     * @param c
     * @return boolean
     */
    private static boolean validateFields(String userInput, Class c) {

        Field[] list = c.getDeclaredFields();

        Map<Integer, String> keyValues = new HashMap<>();

        for (int i = 0; i < list.length; i++) {
            keyValues.put(i, list[i].getName());
        }

        return keyValues.containsValue(userInput);
    }

}
