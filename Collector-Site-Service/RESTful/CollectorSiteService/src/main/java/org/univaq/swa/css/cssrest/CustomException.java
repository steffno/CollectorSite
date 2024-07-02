package org.univaq.swa.css.cssrest;

/**
 *
 * @author MastroGibbs, Kama, Caprielos
 */
public class CustomException extends Exception {

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

}