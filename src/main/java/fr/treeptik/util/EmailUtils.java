package fr.treeptik.util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import fr.treeptik.model.AlerteEmise;
import fr.treeptik.model.NiveauAlerte;
import fr.treeptik.model.TendanceAlerte;

@Component
public class EmailUtils {

	private Logger logger = Logger.getLogger(EmailUtils.class);

	@Inject
	private Environment env;
	static String ALERTEMESSAGE = "[Solices] - Une alerte a été émise par ";

	/**
	 * 
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendAcquittementEmail(AlerteEmise alerteEmise, String emailDestinataire)
			throws AddressException, MessagingException {
		logger.info("--sendAcquittementEmail EmailUtils--");

		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"E dd-MM-y 'à' HH:mm", new Locale("fr"));
		String date = dateFormatter.format(alerteEmise.getDate());
		String midEnregistreur = alerteEmise.getEnregistreur().getMid();
		String niveauAlerte = null;
		String seuil = null;

		if (alerteEmise.getNiveauAlerte().equals(NiveauAlerte.ALERTE)) {
			niveauAlerte = "d'alerte";
		}
		if (alerteEmise.getNiveauAlerte().equals(NiveauAlerte.PREALERTE)) {
			niveauAlerte = "de pré-alerte";
		}

		if (alerteEmise.getTendance().equals(TendanceAlerte.INFERIEUR)) {
			seuil = "bas";
		}
		if (alerteEmise.getTendance().equals(TendanceAlerte.SUPERIEUR)) {
			seuil = "haut";
		}

		final String apiKey = env.getProperty("mail.apiKey");
		final String secretKey = env.getProperty("mail.secretKey");
		String emailFrom = env.getProperty("mail.emailFrom");
		String mailSmtpHost = env.getProperty("mail.smtpHost");
		String socketFactoryPort = env.getProperty("mail.socketFactoryPort");
		String smtpPort = env.getProperty("mail.smtpPort");

		String linkPrefix = env.getProperty("mail.acquittement.url.prefix");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", mailSmtpHost);
		props.put("mail.smtp.socketFactory.port", socketFactoryPort);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", smtpPort);

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(apiKey, secretKey);
					}
				});

		MimeMessage message = new MimeMessage(session);

		String body = "<p><div>Bonjour,</div></p>"
				+ "<p>Le capteur n° "
				+ midEnregistreur
				+ " vient de détecter une valeur de "
				+ +alerteEmise.getMesureLevantAlerte().getValeur()
				+ " considérée comme dépassant le seuil "
				+ niveauAlerte
				+ " "
				+ seuil
				+ ", le "
				+ date
				+ ".</p>"
				+ "<p>Pour acquitter cette "
				+ alerteEmise.getNiveauAlerte().getDescription()
				+ " et avoir plus de détails, veuillez suivre le lien ci-dessous :</p>"
				+ "<p><a href=" + linkPrefix + "/" + alerteEmise.getId()
				+ " >acquittement/détails</a><p>"
				+ "<p> </div><div>Cordialement</div>"
				+ "<div>L'équipe solices</div></p>";

		message.setFrom(new InternetAddress(emailFrom));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailDestinataire));
		message.setSubject(ALERTEMESSAGE + midEnregistreur);
		message.setContent(body, "text/html");

		Transport.send(message);

	}
}
