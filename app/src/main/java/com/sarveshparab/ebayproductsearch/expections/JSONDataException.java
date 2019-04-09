package com.sarveshparab.ebayproductsearch.expections;

public class JSONDataException extends RuntimeException {

    public JSONDataException(String message) {
        super(message);
    }

    public JSONDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
