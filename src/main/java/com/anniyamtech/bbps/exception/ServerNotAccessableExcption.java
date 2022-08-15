package com.anniyamtech.bbps.exception;



/**
 * The Class NotServerAccessExcption.
 */
public class ServerNotAccessableExcption extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5621993860252119284L;

    /** The message. */
    private String message;

    /**
     * Instantiates a new rest exception.
     */
    public ServerNotAccessableExcption() {

    }
   public ServerNotAccessableExcption(String message) {
        super(message);
    }

      public String getMessage() {
        return message;
    }

  
}
