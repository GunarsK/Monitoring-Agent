/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade;

import static jade.DBOperations.dateFormat;
import java.net.UnknownHostException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;


/**
 *
 * @author Gunars
 */

//klase, kas atbild par e-pasta nosūtīšanu
public class SendEmail {
    //metode send nosūta e-pastu uz norādīto adresi ar norādīto saturu.
    //receiver - saņēmēja e-pasta adrese
    public static void send(String receiver) throws UnknownHostException
   {    
      final String username = "yourmonitoringagent@gmail.com";
        //iegūst vakardienas datumu
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String date = dateFormat.format(cal.getTime());
      
		final String password = "JadeAgent";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
                
                //vēstules satura definēšana
		try {
                        
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(receiver));
			message.setSubject("Monitoring on date: " +date);
                        
                        String user = System.getProperty("user.name");
                        
                        java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
                        String PcName = localMachine.getHostName();
                        
                        
                        String text = "Comupter:"+PcName+" Username:"+user+ " usage on date "+date+" \n";
                        List<DBOperations.typeList> typeList = DBOperations.getTypeList();
                            for (DBOperations.typeList type : typeList) {
                            text += type.typeName + ": " + DBOperations.getTimeFormat(type.typeTimeCount_day) + "\n";
                            }
                        
			message.setText(text);
                        //vēstules nosūtīšana
			Transport.send(message);
  
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null, "Couldn't send email. \nWrong username/password", "Alert", JOptionPane.ERROR_MESSAGE); 
		}
	}
}
