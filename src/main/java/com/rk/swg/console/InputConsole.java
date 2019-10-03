package com.rk.swg.console;

import com.rk.swg.dto.*;
import com.rk.swg.exception.IllegalInputException;
import com.rk.swg.search.SearchFactory;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

public class InputConsole {

    private static final Logger logger = Logger.getLogger(InputConsole.class);

    public static void launchSearchTerminal() throws IllegalInputException {

        Scanner scanner = new Scanner(System.in);

        while (true) {
                logger.info("starting the application ..... ");

                System.out.println("Press \n 1) For Users \n 2) For Tickets \n 3) For Organizations \n");

                String choice = scanner.nextLine();

                boolean isNumber = NumberUtils.isNumber(choice);

                int choiceNo = 0;

                if (isNumber) {
                   choiceNo  = Integer.parseInt(choice);
                }

                if (choiceNo < 1 || choiceNo > 3) {
                    throw new IllegalInputException();
                }

                System.out.println("Enter the field to search :  ");
                String fieldValue = scanner.nextLine();

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
                    String searchValue = scanner.nextLine();

                    SearchResults print = SearchFactory.getInstance().search(choiceNo, fieldValue, searchValue);

                    DisplayFactory.getInstance().display(choiceNo, print);

                } else {
                    System.out.println("Invalid input Field provided ");
                    logger.error("Invalid input Field provided");
                }

            System.out.println("Enter N for new search or any other key to exit ");
            boolean continueSearch = scanner.nextLine().equalsIgnoreCase("n");
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
