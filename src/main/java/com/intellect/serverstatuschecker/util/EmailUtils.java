package com.intellect.serverstatuschecker.util;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.intellect.serverstatuschecker.domain.ServerMonitorDetails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendEmail(String[] to, String subject, String body) throws MessagingException {
		
		try {
	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(body, true);
	        javaMailSender.send(mimeMessage);
	        return true;
	    } catch (MessagingException e) {
	        e.printStackTrace(); 
	    }
		return false;
	}
	private static void sendInBlueInitialization(String bodyemails, Environment env) {
		String url = env.getProperty("user.credentialurl");
		String apikey = env.getProperty("user.credentialApi");
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");

		RequestBody body = RequestBody.create(mediaType, bodyemails);
		Request request = new Request.Builder().url(url).header("api-key", apikey).post(body).build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println(response.body().string());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendServerStatusListMail(List<ServerMonitorDetails>serverMonitorDetailsList,List<String> ccMails, String fromEmail,
			List<String> toMails, Environment env) {
			String bodyemail = null;
			bodyemail = "\"htmlContent\":\"<font face='Verdana'>" + env.getProperty("server.dear") + " "+ env.getProperty("server.team");
//					+ env.getProperty("fhr.team")// user.getFirstName()
////					+ user.getLastName() 
//					+ ", " + "<br><br>" + env.getProperty("fhr.fhrfvrStatus") + "<br><br>" + env.getProperty("user.thanks")
//					+ "<br>" + env.getProperty("user.team") + "<br><br>"
//					+ "</font>";
		
		StringBuilder body = new StringBuilder();
		body.append("<h3>Below servers are down, please look at once.</h3>");
		body.append("<br>");
		body.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse;'>");
		body.append("<thead>");
		body.append("<tr>");
		body.append("<th> host Name</th>");
		body.append("<th>Service Name</th>");
		body.append("<th>Server Ip Address</th>");
		body.append("<th>Server Port</th>");
		body.append("<th>Server Status</th>");
		body.append("</tr>");
		body.append("</thead>");
		body.append("<tbody>");
		for (ServerMonitorDetails server : serverMonitorDetailsList) {
			body.append("<tr>");
			body.append("<td>").append(server.getHostName()).append("</td>");
			body.append("<td>").append(server.getServiceName()).append("</td>");
			body.append("<td>").append(server.getServerIpAddress()).append("</td>");
			body.append("<td>").append(server.getServerPort()).append("</td>");
			body.append("<td>").append(server.getServerStatusName()).append("</td>");
			body.append("</tr>");
		}
		body.append("</tbody>");
		body.append("</table>");
		body.append("<br>");
		body.append("<p><h3>Thanks & Regards,</h3><br>Your Automated Monitoring System</p>");
		
//			for sending mails to users

			String bodyemails = "{\"tags\":[\"important\"]," + "\"sender\":{\"email\":\"" + fromEmail + "\",\"name\":\""
					+ fromEmail + "\"}," + "\"replyTo\":{\"email\":\"" + fromEmail + "\"}," + "\"subject\":\""
					+ env.getProperty("fhr.fhrfvrStatus.subject") + "\"," + "\"to\":[";
			if (toMails != null && !toMails.isEmpty()) {
				int i = 0;
				String mails = null;
				for (String email : toMails) {
					if (i == 0) {
						mails = "{\"email\":\"" + email + "\",\"name\":\"" + email + "\"}";
						i++;
					} else {
						mails = mails + "," + "{\"email\":\"" + email + "\",\"name\":\"" + email + "\"}";
						i++;
					}

				}
				bodyemails = bodyemails + mails;

			}
			
			bodyemails = bodyemails + "]," + "\"cc\":[";
			
			if (ccMails != null && !ccMails.isEmpty()) {
				int i = 0;
				String mails = null;
				for (String email : ccMails) {
					if (i == 0) {
						mails = "{\"email\":\"" + email + "\",\"name\":\"" + email + "\"}";
						i++;
					} else {
						mails = mails + "," + "{\"email\":\"" + email + "\",\"name\":\"" + email + "\"}";
						i++;
					}

				}
				bodyemails = bodyemails + mails;
			}
			bodyemails = bodyemails + "],";

//			if (contentAttachment != null) {
//				bodyemails = bodyemails + "\"attachment\":[" + contentAttachment + "],";
//			}
			//bodyemails = bodyemails + "" + bodyemail + "\"}";
			
			bodyemails = bodyemails + "" + bodyemail + ""+body+ "\"}";

			sendInBlueInitialization(bodyemails, env);

	}
}