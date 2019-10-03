package com.rk.swg.console;

import com.rk.swg.dto.SearchResults;

/**
 * Return the relevant Printer method
 */
public class DisplayFactory {

    private DisplayFactory(){}

    private static DisplayFactory instance;

    public static synchronized DisplayFactory getInstance() {
        if (instance == null) {
            instance = new DisplayFactory();
        }
        return instance;
    }

    public void display(int choice, SearchResults results) {

        switch (choice) {
            case 1:
                new UserPrinter().print(results);
                break;
            case 2:
                new TicketPrinter().print(results);
                break;
            case 3:
                new OrganizationPrinter().print(results);
                break;
            default:
                throw new IllegalArgumentException("Not a valid criteria");
        }

    }

}
