/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssb.domain_layer.logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author morte
 */
public class LoggerManager {

    private static final Logger LOGGER = Logger.getLogger(LoggerManager.class.getName());

    
    public static void setupLogger() {
        LogManager.getLogManager().reset();
        LOGGER.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        LOGGER.addHandler(ch);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        try {
            FileHandler fh = new FileHandler(dateFormat.format(date), true);
            fh.setLevel(Level.FINE);
            LOGGER.addHandler(fh);
        } catch (java.io.IOException e) {
            // don't stop my program but log out to console.
            LOGGER.log(Level.SEVERE, "File logger not working.", e);
        }
    }
}
