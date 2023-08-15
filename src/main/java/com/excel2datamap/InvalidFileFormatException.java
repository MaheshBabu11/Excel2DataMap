package com.excel2datamap;

/**
 * This is a custom exception class
 */
public class InvalidFileFormatException extends Exception{

    /**
     * Throws this custom exception when the file format is not supported.
     */
    public InvalidFileFormatException() {
        super("File format not supported!");
    }
}
