package org.sinhasoft.basicspringboot.service;

import org.springframework.stereotype.Service;

@Service
public class MailService {

    public void sendEMail(String name, String surname) {
        System.out.println(name + " " + surname + " hoşgeldiniz...");
    }

}
