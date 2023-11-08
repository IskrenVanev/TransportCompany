package org.example;

import org.example.configuration.SessionFactoryUtil;

public class Main {
    public static void main(String[] args) {
        SessionFactoryUtil.getSessionFactory().openSession();
        System.out.println("Hello world!");
    }

}