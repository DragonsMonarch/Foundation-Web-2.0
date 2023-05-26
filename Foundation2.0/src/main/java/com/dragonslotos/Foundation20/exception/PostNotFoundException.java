package com.dragonslotos.Foundation20.exception;

public class PostNotFoundException extends  RuntimeException{
    public PostNotFoundException(String name) {super("Could not find post with theme" + name);}
}
