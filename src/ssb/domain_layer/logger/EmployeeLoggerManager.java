
package ssb.domain_layer.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class EmployeeLoggerManager {

    private static final Logger EMPLOYEE_LOGGER = Logger.getLogger(EmployeeLoggerManager.class.getName());

    
    public static void setupLogger() {
        LogManager.getLogManager().reset();
        EMPLOYEE_LOGGER.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        EMPLOYEE_LOGGER.addHandler(ch);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        try {
            FileHandler fh = new FileHandler("EmployeeLogger " + dateFormat.format(date), true);
            fh.setLevel(Level.FINE);
            EMPLOYEE_LOGGER.addHandler(fh);
        } catch (java.io.IOException e) {
            // don't stop my program but log out to console.
            EMPLOYEE_LOGGER.log(Level.SEVERE, "File logger not working.", e);
        }
    }
}
