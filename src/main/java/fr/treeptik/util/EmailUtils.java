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

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.AlerteEmise;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.NiveauAlerte;
import fr.treeptik.model.TendanceAlerte;

@Component
public class EmailUtils {

	private Logger logger = Logger.getLogger(EmailUtils.class);

	@Inject
	private Environment env;

	private MimeMessage initEmail() throws AddressException, MessagingException {

		final String apiKey = env.getProperty("mail.apiKey");
		final String secretKey = env.getProperty("mail.secretKey");
		String emailFrom = env.getProperty("mail.emailFrom");
		String mailSmtpHost = env.getProperty("mail.smtpHost");
		String socketFactoryPort = env.getProperty("mail.socketFactoryPort");
		String smtpPort = env.getProperty("mail.smtpPort");

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
		message.setFrom(new InternetAddress(emailFrom));

		return message;
	}

	/**
	 * 
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendAcquittementEmail(AlerteEmise alerteEmise,
			String destinataireEmails) throws ServiceException,
			AddressException, MessagingException {
		logger.info("--sendAcquittementEmail EmailUtils--");

		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"E dd-MM-y 'à' HH:mm", new Locale("fr"));
		String date = dateFormatter.format(alerteEmise.getDate());
		String midEnregistreur = alerteEmise.getEnregistreur().getMid();
		String alerteSubject = "[Solices] - Une "
				+ alerteEmise.getNiveauAlerte().getDescription()
				+ " a été émise par ";

		MimeMessage message = this.initEmail();

		String linkPrefix = env.getProperty("mail.acquittement.url.prefix");

		String body = "<p><div>Bonjour,</div></p>"
				+ "<p>Le capteur n° "
				+ midEnregistreur
				+ " vient de détecter une valeur de "
				+ +alerteEmise.getMesureLevantAlerte().getValeur()
				+ " considérée comme dépassant le seuil "
				+ this.niveauAlerteToString(alerteEmise)
				+ " "
				+ this.tendanceAlerteToString(alerteEmise)
				+ ", le "
				+ date
				+ ".</p>"
				+ "<p>Pour acquitter cette "
				+ alerteEmise.getNiveauAlerte().getDescription()
				+ " et accéder de détails, veuillez cliquer sur le lien ci-dessous :</p>"
				+ "<p><a href=" + linkPrefix + "/" + alerteEmise.getId()
				+ " >acquittement/détails</a><p>"
				+ "<p> </div><div>Cordialement</div>"
				+ "<div>L'équipe solices</div></p>";

		message.setRecipients(Message.RecipientType.BCC,
				InternetAddress.parse(destinataireEmails));

		message.setSubject(alerteSubject + midEnregistreur);
		message.setContent(body, "text/html; charset=utf-8");

		Transport.send(message);

	}

	public void sendFinAlerteEmail(AlerteEmise alerteEmise, Mesure mesure,
			String destinataireEmails) throws ServiceException,
			AddressException, MessagingException {

		logger.info("--sendFinAlerteEmail EmailUtils--");

		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"E dd-MM-y 'à' HH:mm", new Locale("fr"));
		String date = dateFormatter.format(mesure.getDate());
		String midEnregistreur = alerteEmise.getEnregistreur().getMid();
		String alerteSubject = "[Solices] - Une FIN "
				+ this.niveauAlerteToString(alerteEmise) + " a été émise par ";

		MimeMessage message = this.initEmail();

		String body = "<p><div>Bonjour,</div></p>" + "<p>Le capteur n° "
				+ midEnregistreur
				+ " a constaté une absence de dépassement de seuil "
				+ this.niveauAlerteToString(alerteEmise)
				+ " durant 3 heures sur la valeur de " + mesure.getValeur()
				+ ", le " + date + ".</p>"
				+ "<p> </div><div>Cordialement</div>"
				+ "<div>L'équipe solices</div></p>";

		message.setRecipients(Message.RecipientType.BCC,
				InternetAddress.parse(destinataireEmails));

		message.setSubject(alerteSubject + midEnregistreur);
		message.setContent(body, "text/html; charset=utf-8");

		Transport.send(message);

	}

	private String niveauAlerteToString(AlerteEmise alerteEmise) {

		String niveauAlerte = null;

		if (alerteEmise.getNiveauAlerte().equals(NiveauAlerte.ALERTE)) {
			niveauAlerte = "d'alerte";
		}
		if (alerteEmise.getNiveauAlerte().equals(NiveauAlerte.PREALERTE)) {
			niveauAlerte = "de pré-alerte";
		}

		return niveauAlerte;

	}

	private String tendanceAlerteToString(AlerteEmise alerteEmise) {

		String tendanceAlerte = null;
		if (alerteEmise.getTendance().equals(TendanceAlerte.INFERIEUR)) {
			tendanceAlerte = "bas";
		}
		if (alerteEmise.getTendance().equals(TendanceAlerte.SUPERIEUR)) {
			tendanceAlerte = "haut";
		}

		return tendanceAlerte;

	}

	public void sendAlerteAcquittementTimeout(AlerteEmise alerteEmise,
			String destinataireEmails) throws AddressException, MessagingException {
		logger.info("--sendAlerteAcquittementTimeout EmailUtils--");

		String linkPrefix = env.getProperty("mail.acquittement.url.prefix");

		String midEnregistreur = alerteEmise.getEnregistreur().getMid();
		String alerteSubject = "[Solices] - Une "
				+ alerteEmise.getNiveauAlerte().getDescription() + " émise par le capteur n° " + midEnregistreur + " n'a pas encore été acquittée" ;

		MimeMessage message = this.initEmail();

		String body = "<p><div>Bonjour,</div></p>"
				+ "<p>L'acquittement de l'alerte de niveau "
				+ alerteEmise.getNiveauAlerte().getDescription()
				+ " émise par le capteur n° "
				+ midEnregistreur
				+ " n'a toujours pas été effectué "
				+ "<p> Une prise de contact avec le client est peut être nécessaire ou bien l'acquittement peut être effectué pour lui</p>"
				+ "<p>Pour acquitter cette "
				+ alerteEmise.getNiveauAlerte().getDescription()
				+ " et accéder de détails, veuillez cliquer sur le lien ci-dessous :</p>"
				+ "<p><a href=" + linkPrefix + "/" + alerteEmise.getId()
				+ " >acquittement/détails</a><p>" + ".</p>"
				+ "<p> </div><div>Cordialement</div>"
				+ "<div>L'équipe solices</div></p>";

		message.setRecipients(Message.RecipientType.BCC,
				InternetAddress.parse(destinataireEmails));

		message.setSubject(alerteSubject);
		message.setContent(body, "text/html; charset=utf-8");

		Transport.send(message);

	}
}
