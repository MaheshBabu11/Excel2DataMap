package com.excel2datamap;

/**
 * This is a custom exception class
 */
public class InvalidFileFormatException extends Exception{

    public InvalidFileFormatException() {
        super("File format not supported!");
    }
}
