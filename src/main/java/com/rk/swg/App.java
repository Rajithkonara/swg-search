package com.rk.swg;

import com.rk.swg.console.InputConsole;
import com.rk.swg.exception.IllegalInputExeception;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {

        ClassLoader classLoader = App.class.getClassLoader();
        PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
        BasicConfigurator.configure();

        try {
            InputConsole.launchSearchTerminal();
        } catch (IllegalInputExeception illegalInputExeception) {
            logger.error(illegalInputExeception);
        }

    }
}
