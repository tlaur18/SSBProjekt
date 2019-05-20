
package ssb.data_layer.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class AdminLoggerManager {
    
    private static final Logger ADMIN_LOGGER = Logger.getLogger(AdminLoggerManager.class.getName());

    
    public static void setupLogger() {
        LogManager.getLogManager().reset();
        ADMIN_LOGGER.setLevel(Level.ALL);
        
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        try {
            FileHandler fh = new FileHandler("AdminLogger " + dateFormat.format(date), true);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            ADMIN_LOGGER.addHandler(fh);
            fh.setFormatter(simpleFormatter);
            fh.setLevel(Level.FINE);
        } catch (java.io.IOException e) {
            // don't stop my program but log out to console.
            ADMIN_LOGGER.log(Level.SEVERE, "File logger not working.", e);
        }
    }
}
