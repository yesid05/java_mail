package mundo;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Mail {
    
    //ID del destinatario
    private String para = "algo@algo.algo";
    
    //ID del remitente
    private String desde = "algo@gmail.com";
    
    //Suponiendo que está enviando un correo electrónico de localhost
    private String host = "localhost";
    
    //Obtener propiedades del sistema
    private Properties propiedades = System.getProperties();
    
    //Obtener el objeto de sesión predeterminado.
    private Session session = null;
    
    public Mail() {
	//Configurar servidor de correo
	propiedades.put("mail.smtp.auth", "true");
	propiedades.put("mail.smtp.starttls.enable", "true");
	propiedades.put("mail.smtp.host", "smtp.gmail.com");
	propiedades.put("mail.smtp.port", "25");
	
	//Obtener el objeto de sesión predeterminado.
	session = Session.getDefaultInstance(propiedades, new Authenticator() {
	    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		return new javax.mail.PasswordAuthentication(desde, "contraseña del remitente (desde)");
	}
	});
    }
    
    public void enviarCorreo()throws MessagingException{
	// Cree un objeto MimeMessage predeterminado.
	MimeMessage mensaje = new MimeMessage(session);
	
	//Enviar desde: campo de encabezado.
	mensaje.setFrom(new InternetAddress(desde));
	
	//Enviar a: campo de encabezado
	mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
	
	
	//Asunto: campo de encabezado
	mensaje.setSubject("Correo con archivo");
	
	//cuerpo del mensaje
	//mensaje.setText("Mensaje enviado desde java");
	
	//Envía el mensaje HTML real, tan grande como quieras
	//mensaje.setContent("<h1>Hola con html</h1>", "text/html");
	
	//Crearun un objeto de BodyPart
	BodyPart mensajePart = new MimeBodyPart();
	
	//Contenido del mensaje 
	mensajePart.setText("Este es el cuerpo del mensaje");
	
	//Crearun un objeto de Multipart
	Multipart multiPart = new MimeMultipart();
	
	//Parte del mensaje de a enviar
	multiPart.addBodyPart(mensajePart);
	
	//La segunda parte es el archivo adjunto
	mensajePart = new MimeBodyPart();	
	//ruta donde se encuentra el archivo
	URL url = getClass().getResource("/extras/doc/");	
	File archivo = null;
	try {
	    //contruimos el archivo
	    archivo = new File(url.toURI().getPath()+"doc.txt");
	} catch (URISyntaxException e) {
	    //error si no existe el archivo
	    e.printStackTrace();
	    System.err.println(e.getMessage());
	}	
	DataSource src = new FileDataSource(archivo);
	mensajePart.setDataHandler(new DataHandler(src));
	mensajePart.setFileName(archivo.getName());
	multiPart.addBodyPart(mensajePart);
	
	//Enviar las partes de mensaje completas
	mensaje.setContent(multiPart);
	
	//envio de mail
	System.out.println("Enviando mensaje...");
	Transport.send(mensaje);
	System.out.println("Mensaje enviado");
	
    }
    
}
