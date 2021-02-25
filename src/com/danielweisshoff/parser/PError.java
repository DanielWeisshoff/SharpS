package com.danielweisshoff.parser;

import com.danielweisshoff.logger.Logger;

/**
 * Logs the error message and exits the program
 */
public class PError {

    public PError(String errorMessage) {
        System.out.println(errorMessage);
        Logger.log(errorMessage);
        System.exit(1);
    }
}
