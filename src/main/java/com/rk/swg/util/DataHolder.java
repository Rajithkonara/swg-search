package com.rk.swg.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.rk.swg.dto.Organization;
import com.rk.swg.dto.Ticket;
import com.rk.swg.dto.User;
import lombok.Getter;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Getter
public class DataHolder {

    private static final Logger logger = Logger.getLogger(DataHolder.class);

    private static final String USERSJSON = "users.json";
    private static final String TICKETSJSON = "tickets.json";
    private static final String ORGJSON = "organizations.json";
    private static final String FILENOTFOUND = "File Not Found";

    private List<User> users;
    private List<Ticket> tickets;
    private List<Organization> organizations;

    private DataHolder() throws IOException {
        users = readUsers();
        tickets = readTickets();
        organizations = readOrganizations();
    }

    private static DataHolder instance;

    public static synchronized DataHolder getInstance() throws IOException {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    /**
     * Read the users.json
     *
     * @return List of users
     */
    private static List<User> readUsers() throws IOException {

        ClassLoader classLoader = DataHolder.class.getClassLoader();

        InputStream path = classLoader.getResourceAsStream(USERSJSON);

        if (path != null) {
            JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(path)));
            User[] data = new Gson().fromJson(reader, User[].class);
            reader.close();
            return Arrays.asList(data);
        } else {
            try {
                throw new FileNotFoundException(FILENOTFOUND);
            } catch (FileNotFoundException e) {
                    logger.error(e);
            }
        }

        return Arrays.asList();
    }

    /**
     * Read the tickets.json
     *
     * @return List of tickets
     */
    private static List<Ticket> readTickets() throws IOException {

        ClassLoader classLoader = DataHolder.class.getClassLoader();

        InputStream path = classLoader.getResourceAsStream(TICKETSJSON);

        if (path != null) {

            JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(path)));

            Ticket[] data = new Gson().fromJson(reader, Ticket[].class);
            reader.close();
            return Arrays.asList(data);
        } else {
            try {
                throw new FileNotFoundException(FILENOTFOUND);
            } catch (FileNotFoundException e) {
                logger.error(e);
            }
        }
        return Arrays.asList();
    }

    /**
     * Read the organization.json
     *
     * @return List of organizations
     */
    private static List<Organization> readOrganizations() throws IOException {

        ClassLoader classLoader = DataHolder.class.getClassLoader();

        InputStream path = classLoader.getResourceAsStream(ORGJSON);

        if (path != null) {

            JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(path)));

            Organization[] data = new Gson().fromJson(reader, Organization[].class);
            reader.close();
            return Arrays.asList(data);
        } else {
            try {
                throw new FileNotFoundException(FILENOTFOUND);
            } catch (FileNotFoundException e) {
                logger.error(e);
            }
        }
        return Arrays.asList();
    }

}
