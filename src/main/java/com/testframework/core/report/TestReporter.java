package com.testframework.core.report;
import com.testframework.core.utils.MessageColour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TestReporter {
    public static final Logger logger = LogManager.getLogger(TestReporter.class);
    private static final String DEBUG_STATUS = "debug";
    private static final String ERROR_STATUS = "error";
    private static final String INFO_STATUS = "info";

    /**
     * Report debug step
     *
     * @param debugStepMessage debug message
     * @param parameters       message constructing parameters
     * @see String#format
     */
    public static void reportDebugStep(String debugStepMessage, Object... parameters) {
        checkLoggerStatus(DEBUG_STATUS, String.format(debugStepMessage, getParametersByNewObject(parameters)));
    }

    /**
     * Report error step
     *
     * @param errorStepMessage error message
     * @param parameters       message constructing parameters
     * @see String#format
     */
    public static void reportErrorStep(String errorStepMessage, Object... parameters) {
        checkLoggerStatus(ERROR_STATUS, String.format(errorStepMessage, getParametersByNewObject(parameters)));
    }

    /**
     * com.demo.testframework.core.driver.report info step
     *
     * @param infoStepMessage error message
     * @param parameters      message constructing parameters
     * @see String#format
     */
    public static void reportInfoStep(String infoStepMessage, Object... parameters) {
        checkLoggerStatus(INFO_STATUS, String.format(infoStepMessage, getParametersByNewObject(parameters)));
    }


    private static void checkLoggerStatus(String loggerInfo, String message) {
        if (loggerInfo.equals("info")) {
            logger.info(message);
        }
        if (loggerInfo.equals("debug")) {
            logger.debug(message);
        }
        if (loggerInfo.equals("error")) {
            logger.error(message);
        }
    }

    /**
     * Get parameter for console with color by creation new object
     *
     * @param parameters logger
     * @return new instance
     */
    public static Object[] getParametersByNewObject(Object[] parameters) {
        Object[] newObject = new Object[parameters.length];
        String temp;
        for (int i = 0; i < parameters.length; i++) {
            temp = MessageColour.ANSI_YELLOW + parameters[i] + MessageColour.ANSI_CYAN;
            newObject[i] = temp;
        }
        return newObject;
    }
}
