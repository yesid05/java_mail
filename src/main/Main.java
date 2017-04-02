package main;

import javax.mail.MessagingException;
import mundo.Mail;

public class Main {

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	Mail mail = new Mail();
	try {
	    mail.enviarCorreo();
	} catch (MessagingException e) {
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	}
    }

}
