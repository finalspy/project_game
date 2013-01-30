package controllers;

import play.*;
import play.libs.Mail;
import play.mvc.*;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import sun.rmi.transport.proxy.HttpReceiveSocket;

import groovyjarjarantlr.StringUtils;

import java.util.*;

import javax.inject.Inject;

import oauth.signpost.http.HttpRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.codehaus.groovy.util.StringUtil;

import models.*;

/**
 * The Class Application.
 */
public class Application extends Controller {

	/** The Constant EMAIL. */
	private static final String EMAIL = "Quelle est ton adresse email";
	private static final String HEUREUX = "Es tu heureux de participer";
	private static final String LIST = "Es tu abonne a la mailing list";
	private static final String MARKDOWN = "Es tu pret a recevoir une enonce au format markdown par http post";
	private static final String PREMIER = "As tu bien recu le premier enonce";
	private static final String SECOND = "As tu bien recu le second enonce";
	private static final String OUI = "Est ce que tu reponds toujours oui";
	private static final String DELOOF = "As tu copie le code de ndeloof";
	private static final String BUGS = "As tu passe une bonne nuit malgre les bugs de l etape precedente";
	private static final String YES_OR_NOT = "(OUI/NON)";

	public static String lastQuestionSend;

	/**
	 * Index.
	 * 
	 * @param q
	 *            the q
	 * @return the string
	 * @throws EmailException
	 *             the email exception
	 */
	public static String index(String q) throws EmailException {
		String answer = "No question to treated";
		if (org.apache.commons.lang.StringUtils.isNotBlank(q)) {
			answer = treatedQuestion(q);

		} else {
			sendMail("Connection whitout question");
		}
		return answer;
	}

	public static void indexPost() throws EmailException {
		Logger.info("Post body " + params.get("body"));
		StringBuilder mailBody = new StringBuilder();
		mailBody.append(params.get("body"));
		sendMail(mailBody.toString());
	}

	/**
	 * Treated question.
	 * 
	 * @param q
	 *            the q
	 * @return the string
	 * @throws EmailException
	 *             the email exception
	 */
	private static String treatedQuestion(String q) throws EmailException {
		String answer;
		Logger.info("new question: \n" + q + "\n");

		sendMail(q);
		if (EMAIL.equalsIgnoreCase(q)) {
			Logger.info("email question");
			answer = "florian.jose.ferreira@gmail.com";
		} else if (HEUREUX.equalsIgnoreCase(q)
			|| LIST.equalsIgnoreCase(q)
			|| MARKDOWN.equalsIgnoreCase(q)
			|| PREMIER.equalsIgnoreCase(q)
			|| SECOND.equalsIgnoreCase(q)) {
			Logger.info("Happy question");
			answer = "OUI";
		} else if (OUI.equalsIgnoreCase(q)
			|| NDELOOF.equalsIgnoreCase(q)) {
			Logger.info("Unhappy question");
			answer = "NON";
		} else if (BUGS.equalsIgnoreCase(q)) {
			Logger.info("Annoying question");
			answer = "QUELS_BUGS";
		} else {
			Logger.info("question not treated");
			answer = "Question not treated";
		}
		return answer;
	}

	/**
	 * Send mail.
	 * 
	 * @param msg
	 *            the msg
	 * @throws EmailException
	 *             the email exception
	 */
	private static void sendMail(final String msg) throws EmailException {

		if (org.apache.commons.lang.StringUtils.isBlank(lastQuestionSend)
				|| !msg.equalsIgnoreCase(msg)) {
			lastQuestionSend = msg;
			SimpleEmail email = new SimpleEmail();
			email.setFrom("florian.jose.ferreira@gmail.com");
			email.addTo("florian.jose.ferreira@gmail.com");
			email.setSubject("New question");
			email.setMsg(msg);
			email.getHostName();
			Mail.send(email);
		}
	}
}
