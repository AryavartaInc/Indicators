package com.fin.technical;

/*
 import java.util.Scanner;
 import org.scribe.builder.*;
 import org.scribe.builder.api.*;
 import org.scribe.model.*;
 import org.scribe.oauth.*;
 import javax.mail.internet.*;
 import com.sun.mail.imap.IMAPSSLStore;
 import com.sun.mail.smtp.SMTPTransport;
 */
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.*;
import javax.activation.*;

import org.springframework.context.ApplicationContext;

import com.sun.mail.smtp.SMTPTransport;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */
//cacerts from 
// https://java.net/projects/javamail/pages/Home#Samples

// https://www.geotrust.com/resources/root-certificates/index.html

// Web Services Directory
// http://www.programmableweb.com/apis/directory/1?apicat=Email
// http://www.javatpoint.com/spring-java-mail-tutorial

// http://www.mkyong.com/java/javamail-api-sending-strEmailAddress-via-gmail-smtp-example/

// http://code.google.com/p/google-mail-xoauth-tools/wiki/JavaSampleCode
// You'll need the following packages installed on your system to run the sample code:
// JDK version 1.5 or higher
// The JavaMail API. This is available alone or as part of Java EE.
// Apache Ant
// An OAuth Token and Token Secret for a Google Mail account. You can use xoauth.py to generate these for your own account.
// http://www.oracle.com/technetwork/java/javamail/index-138643.html

/* 

 oauth_token_secret: I38aKmrwfWOGx_BI96IMVLqM
 oauth_token: 4/vhQE8pTuCtCF1icLEGakYigYYKhh

 oauth_callback_confirmed: true
 To authorize token, visit this url and follow the directions to generate a verification code:
 https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=4%2FvhQE8pTuCtCF1icLEGakYigYYKhh

 Enter verification code: adr3-WV3z2dVwKcdenbfDcrQ

 oauth_token: 1/eXe53o2AVdveR1MM3YL-vpPb-lniV4kZsGB3b4T4NLo
 oauth_token_secret: qC1C5aWBUwriJ5yy2YNQgUs3 

 signature base string:
 GET&https%3A%2F%2Fmail.google.com%2Fmail%2Fb%2Fthe.greatest.magadh%40gmail.com%2Fimap%2F&oauth_consumer_key%3Danonymous%26oauth_nonce%3D10328317596706920819%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1323842061%26oauth_token%3D1%252FeXe53o2AVdveR1MM3YL-vpPb-lniV4kZsGB3b4T4NLo%26oauth_version%3D1.0

 xoauth string (before base64-encoding):

 GET https://mail.google.com/mail/b/the.greatest.magadh@gmail.com/imap/ oauth_consumer_key="anonymous",oauth_nonce="10328317596706920819",oauth_signature="LcHVExIipTxsNxHo4xttWUJm7bU%3D",oauth_signature_method="HMAC-SHA1",oauth_timestamp="1323842061",oauth_token="1%2FeXe53o2AVdveR1MM3YL-vpPb-lniV4kZsGB3b4T4NLo",oauth_version="1.0"

 XOAUTH string (base64-encoded): R0VUIGh0dHBzOi8vbWFpbC5nb29nbGUuY29tL21haWwvYi90aGUuZ3JlYXRlc3QubWFnYWRoQGdtYWlsLmNvbS9pbWFwLyBvYXV0aF9jb25zdW1lcl9rZXk9ImFub255bW91cyIsb2F1dGhfbm9uY2U9IjEwMzI4MzE3NTk2NzA2OTIwODE5IixvYXV0aF9zaWduYXR1cmU9IkxjSFZFeElpcFR4c054SG80eHR0V1VKbTdiVSUzRCIsb2F1dGhfc2lnbmF0dXJlX21ldGhvZD0iSE1BQy1TSEExIixvYXV0aF90aW1lc3RhbXA9IjEzMjM4NDIwNjEiLG9hdXRoX3Rva2VuPSIxJTJGZVhlNTNvMkFWZHZlUjFNTTNZTC12cFBiLWxuaVY0a1pzR0IzYjRUNE5MbyIsb2F1dGhfdmVyc2lvbj0iMS4wIg==

 D:\apache-ant-1.8.2\bin>python D:\MANOJTIWARI\workspace\EMACalculator\xoauth-java-sample-20100617\xoauth.py --test_imap_authentication --user=the.greatest.magadh@gmail.com --oauth_token=1/eXe53o2AVdveR1MM3YL-vpPb-lniV4kZsGB3b4T4NLo --oauth_token_secret=qC1C5aWBUwriJ5yy2YNQgUs3
 */

public class TransportReport {
    
	private static String m_strSMTPHostName = "smtp.gmail.com";
    private static int m_nSMTPHostPort = 465;
    private static String m_strFrom = "";
    private static String m_strSenderPasscode = "";
    private Properties m_objSMTPProperties = null;
    private ApplicationContext m_objSpringAppCtx = null;
    private Util m_objUtil = null;
    ReportWriter m_objReportWriter = null;
    // Information I provided

    // Project Name: EMA Calculator
    // Description: An application intended for Capital Market users. It
    // provides Technical Indicator and overlays reports for a defined number of
    // Stocks traded in BSE and NSE of India.

    // Your API Key has been approved!
    // This API key allows you to write an application that interacts with
    // OAuth-enabled Yahoo! APIs.
    // Authentication Information: OAuth

    // http://www.programmableweb.com/api/yahoo-mail
    // Client Install Required: No
    // Service Endpoint: http://mail.yahooapis.com/ws/mail/v1.1/
    // WSDL: http://mail.yahooapis.com/ws/mail/v1.1/wsdl

    // http://developer.yahoo.com/mail/docs/user_guide/ConstructTheSOAPClient.html
    // http://developer.yahoo.com/mail/docs/user_guide/Methods.html
    // http://developer.yahoo.com/mail/docs/user_guide/UploadAttachment.html
    // http://www.javarants.com/2007/08/31/using-the-yahoo-mail-soap-api-1-1-from-java-s-jax-ws-2-1/
    // http://code.google.com/apis/gdata/docs/auth/oauth.html
    // http://code.google.com/p/gdata-java-client/objFileDataSource/browse/trunk/java/sample/oauth/TwoLeggedOAuthExample.java
    // http://code.google.com/p/gdata-java-client/downloads/list
    // private static final String PROTECTED_RESOURCE_URL =
    // "http://mail.yahooapis.com/ws/mail/v1.1/jsonrpc";
    // http://developer.yahoo.com/oauth/guide/oauth-auth-flow.html
    // https://developer.apps.yahoo.com/projects/JdX4fk6q
    // http://developer.yahoo.com/mail/docs/user_guide/DetermineTheUsersAccountCapabilities.html
    // Application ID: JdX4fk6q
    // Consumer Key:
    // dj0yJmk9TWlrRHdtZXQzc1pKJmQ9WVdrOVNtUllOR1pyTm5FbWNHbzlPREEzT0Rrek5EWXkmcz1jb25zdW1lcnNlY3JldCZ4PTI4
    // and
    // Consumer Key:
    // dj0yJmk9TDIyYmROQnNGMFh4JmQ9WVdrOVNtUllOR1pyTm5FbWNHbzlPREEzT0Rrek5EWXkmcz1jb25zdW1lcnNlY3JldCZ4PTcz
    // Consumer Key:
    // dj0yJmk9UUQyZXRMY3R3YlBIJmQ9WVdrOVNtUllOR1pyTm5FbWNHbzlPREEzT0Rrek5EWXkmcz1jb25zdW1lcnNlY3JldCZ4PTlj
    // Consumer Secret: db3ca685e1ef04cae2ac854ff03a65f40ea8f0de
    // and
    // Consumer Secret: 637aa888c648ce7136421e0ef4b036acaf2acee8
    // Consumer Secret: 4ce0b1a0c1bc012a519ece39aa4916622fb3bbb5
    public TransportReport() {
        // Get system properties
    	if(m_objSMTPProperties == null) {
    		m_objSMTPProperties = System.getProperties();
    	}
    	if(m_objUtil == null) {
    		m_objUtil = Util.getInstance();
    	}
    	if(m_objSpringAppCtx == null) {
    		m_objSpringAppCtx = m_objUtil.getSpringFXApplicationContext();
    	}
    	if(m_objReportWriter == null) {
    		m_objReportWriter = (ReportWriter)m_objSpringAppCtx.getBean("reportWriter");
    	}
    	
    }
    
    public TransportReport(Properties objSMTPProperties, Util objUtil, 
    		ApplicationContext objSpringAppCtx, ReportWriter objReportWriter) {
        // Get system properties
        m_objSMTPProperties = objSMTPProperties;
        m_objUtil = objUtil;
        m_objSpringAppCtx = objSpringAppCtx;
        m_objReportWriter = objReportWriter;        
    }
    
    public void OAuthAuthenticate() {

        /*
         * USER_EMAIL_OAUTH_TOKEN=1/eXe53o2AVdveR1MM3YL-vpPb-lniV4kZsGB3b4T4NLo
         *
         * USER_EMAIL_OAUTH_TOKEN_SECRET=qC1C5aWBUwriJ5yy2YNQgUs3
         */
        try {
            /*
             * Util m_objUtil = Util.getInstance(); String strEmailAddress =
             * m_objUtil.getUserEmailAddress(); String objOAuthToken =
             * m_objUtil.getUserEmailOAuthToken(); String objOAuthTokenSecret =
             * m_objUtil.getUserEmailOAuthTokenSecret();
             */

            /*
             * IMAPSSLStore objIMAPSSLStore = XOAuthGoogleMail.connectToImap(
             * "imap.googlemail.com", 993, strEmailAddress, objOAuthToken,
             * objOAuthTokenSecret, XOAuthGoogleMail.getAnonymousConsumer(),
             * true);
             * System.out.println("Successfully authenticated to IMAP.\n");
             *
             * SMTPTransport objSMTPTransport = XOAuthGoogleMail.connectToSmtp(
             * "smtp.googlemail.com", 587, strEmailAddress, objOAuthToken,
             * objOAuthTokenSecret, XOAuthGoogleMail.getAnonymousConsumer(),
             * true);
             */

        } catch (Exception objExcep) {
            objExcep.printStackTrace(System.out);
        }

    }

    // http://www.journaldev.com/2532/javamail-example-send-mail-in-java-smtp
    public void sendMail() throws Exception {
        String strCRLF = System.getProperty("line.separator");
        // https://www.javainterviewpoint.com/spring-propertyplaceholderconfigurer-example/	
        String strTemp = m_objUtil.getSMTPHostName();
        if (strTemp != null && !strTemp.isEmpty()) {
        	m_strSMTPHostName = strTemp;
        }
        m_nSMTPHostPort = m_objUtil.getSMTPHostPort();
        
        strTemp = m_objUtil.getSMTPSenderEmailAddress();
        if (strTemp != null && !strTemp.isEmpty()) {
        	m_strFrom = strTemp;
        }
        strTemp = m_objUtil.getSMTPSenderEmailPasscode();
        if (strTemp != null && !strTemp.isEmpty()) {
        	m_strSenderPasscode = strTemp;
        }
        String strSubject = m_objUtil.getEmailSubject();
        if (strSubject != null && !strSubject.isEmpty()) {
            strSubject = String.format(strSubject,
                    m_objUtil.getDataSourceSettings());
        } else {
            strSubject = String.format(Constants.STR_DEFAULT_EMAIL_SUBJECT,
                    m_objUtil.getDataSourceSettings());
        }
        // Setup mail server
        //Security.addProvider(new com.provider.JSSEProvider());
        m_objSMTPProperties.setProperty("mail.transport.protocol", "smtp");
        m_objSMTPProperties.put("mail.smtp.host", m_strSMTPHostName);        
        m_objSMTPProperties.put("mail.smtp.port", String.valueOf(m_nSMTPHostPort));
        m_objSMTPProperties.put("mail.smtp.auth", "true");    
        // m_objSMTPProperties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS    
        // m_objSMTPProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");           
        // m_objSMTPProperties.put("mail.smtp.socketFactory.fallback", "false");   
        // m_objSMTPProperties.setProperty("mail.smtp.quitwait", "false"); 
        //create Authenticator object to pass in Session.getInstance argument
        /*
        final String strFromAddr = m_strFrom;
        final String strPassword = m_strSenderPasscode;
  		Authenticator objAuthenticator = new Authenticator() {
  			//override the getPasswordAuthentication method
  			protected PasswordAuthentication getPasswordAuthentication() {
  				return new PasswordAuthentication(strFromAddr, strPassword);
  			}
  		};
        // Get objSession
        Session objSession = Session.getDefaultInstance(m_objSMTPProperties, objAuthenticator);
        */
        Session objSession = Session.getDefaultInstance(m_objSMTPProperties);
        objSession.setDebug(m_objUtil.getDebugSMTPSession());
        SMTPTransport objTransport = (SMTPTransport)objSession.getTransport("smtp");
        objTransport.connect(m_strSMTPHostName, m_nSMTPHostPort, m_strFrom, m_strSenderPasscode);
        // Define message
        MimeMessage message = new MimeMessage(objSession);
        message.addHeader("Content-type", "text/HTML; charset=UTF-8");
        message.addHeader("format", "flowed");
        message.addHeader("Content-Transfer-Encoding", "8bit");
        message.setFrom(new InternetAddress(m_strFrom));
        message.setReplyTo(InternetAddress.parse(m_strFrom, false));
        String[] strRecipients = m_objUtil.getRecipientEmailAddress();
        if (strRecipients != null && strRecipients.length > 1) {
            for (int nIdx = 0; nIdx < strRecipients.length; nIdx++) {
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(strRecipients[nIdx]));
            }
        } else {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
            		m_strFrom));
        }
        message.setSubject(strSubject, "UTF-8");
        // create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        // fill message
        StringBuffer strBufMessageBody = new StringBuffer(String.format(
                m_objUtil.getEmailBody(), strCRLF, strCRLF, strCRLF, strCRLF,
                strCRLF, strCRLF, strCRLF, strCRLF, strCRLF, strCRLF, strCRLF,
                strCRLF));
        
        DataSource objDataSource = (DataSource)((Util.getInstance()).getSpringFXApplicationContext()).getBean("dataSource");
        String strIndiaVIX = objDataSource.archiveIndiaVIX();
        if (strIndiaVIX != null) {
            strBufMessageBody.append(strCRLF).append(strIndiaVIX);
        }
        messageBodyPart.setText(strBufMessageBody.toString());
        Multipart objMultipart = new MimeMultipart();
        objMultipart.addBodyPart(messageBodyPart);
        
        // Part two is attachment
        // Attach EMA Cross-over report        
        /*
        if (m_objUtil.getGenMasterEMAReportValue() == 1) {
            try {
                String strFileAttachment = m_objReportWriter.getReportFileNameForEMACrossover();
                Path file = Paths.get(strFileAttachment.toString());
                if (Files.exists(file, LinkOption.NOFOLLOW_LINKS))
                {
                	messageBodyPart = new MimeBodyPart();
	                FileDataSource objFileDataSource = new FileDataSource(
	                        strFileAttachment);
	                messageBodyPart.setDataHandler(new DataHandler(objFileDataSource));
	                messageBodyPart.setFileName(objFileDataSource.getName());
	                objMultipart.addBodyPart(messageBodyPart);
                }
            } catch(Exception excep) {
                excep.printStackTrace(System.out);
            }
        }
        if (m_objUtil.getGenBollReportValue() == 1) {
            // Attach Bollinger Band report
            try {
                String strFileAttachment = m_objReportWriter
                        .getReportFileNameForBollingerBand();
            	Path file = Paths.get(strFileAttachment.toString());
                if (Files.exists(file, LinkOption.NOFOLLOW_LINKS))
                {
	                messageBodyPart = new MimeBodyPart();
	                FileDataSource objFileDataSource = new FileDataSource(
	                        strFileAttachment);
	                messageBodyPart.setDataHandler(new DataHandler(objFileDataSource));
	                messageBodyPart.setFileName(objFileDataSource.getName());
	                objMultipart.addBodyPart(messageBodyPart);
                }
            } catch(Exception excep) {
                excep.printStackTrace(System.out);
            }
        }

        if (m_objUtil.getGenPivotPointReportValue() == 1) {
            // Attach Pivot point report
            try {
                String strFileAttachment = m_objReportWriter.getReportFileNameForPivot(false);
            	Path file = Paths.get(strFileAttachment.toString());
                if (Files.exists(file, LinkOption.NOFOLLOW_LINKS))
                {
                	messageBodyPart = new MimeBodyPart();
	                FileDataSource objFileDataSource = new FileDataSource(
	                        strFileAttachment);
	                messageBodyPart.setDataHandler(new DataHandler(objFileDataSource));
	                messageBodyPart.setFileName(objFileDataSource.getName());
	                objMultipart.addBodyPart(messageBodyPart);

	                // Attach Fibonacci Pivot point report
	                messageBodyPart = new MimeBodyPart();
	                strFileAttachment = m_objReportWriter.getReportFileNameForPivot(true);
	                objFileDataSource = new FileDataSource(strFileAttachment);
	                messageBodyPart.setDataHandler(new DataHandler(objFileDataSource));
	                messageBodyPart.setFileName(objFileDataSource.getName());
	                objMultipart.addBodyPart(messageBodyPart);
                }
            } catch(Exception excep) {
                excep.printStackTrace(System.out);
            }
        }
        if (m_objUtil.getGenRSIReportValue() == 1) {
            // Attach RSI Over-bought and over-sold report
            try {
                String strFileAttachment = m_objReportWriter.getReportFileNameForRSIObOs();
            	Path file = Paths.get(strFileAttachment.toString());
                if (Files.exists(file, LinkOption.NOFOLLOW_LINKS))
                {
	                messageBodyPart = new MimeBodyPart();
	                FileDataSource objFileDataSource = new FileDataSource(
	                        strFileAttachment);
	                messageBodyPart.setDataHandler(new DataHandler(objFileDataSource));
	                messageBodyPart.setFileName(objFileDataSource.getName());
	                objMultipart.addBodyPart(messageBodyPart);
                }
            } catch(Exception excep) {
                excep.printStackTrace(System.out);
            }
        }
        if (m_objUtil.getGenADXReportValue() == 1) {
            // Attach RSI Over-bought and over-sold report
            try {
                String strFileAttachment = m_objReportWriter.getReportFileNameForADX();
            	Path file = Paths.get(strFileAttachment.toString());
                if (Files.exists(file, LinkOption.NOFOLLOW_LINKS))
                {
	                messageBodyPart = new MimeBodyPart();
	                FileDataSource objFileDataSource = new FileDataSource(
	                        strFileAttachment);
	                messageBodyPart.setDataHandler(new DataHandler(objFileDataSource));
	                messageBodyPart.setFileName(objFileDataSource.getName());
	                objMultipart.addBodyPart(messageBodyPart);
                }
            } catch(Exception excep) {
                excep.printStackTrace(System.out);
            }
        }
        */
        // Put parts in message
        message.setContent(objMultipart);
        message.setSentDate(new Date());

        // Send the message
        // objTransport.send( message );
        // objTransport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        // objTransport.close();
        Transport.send(message);
    }
    
    public Properties getProperties() {
    	return m_objSMTPProperties;
    }
    
    public void setSMTPProperties(Properties objSMTPProperties) {
    	m_objSMTPProperties = objSMTPProperties;
    }
}
