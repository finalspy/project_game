package controllers;

import play.*;
import play.libs.Mail;
import play.mvc.*;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import sun.rmi.transport.proxy.HttpReceiveSocket;

import org.apache.commons.lang.StringUtils;

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
		String answer = "No question to treat";
		if (StringUtils.isNotBlank(q)) {
			answer = treatedQuestion(q);

		} else {
			sendMail("Connection without question");
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
		} else if (StringUtils.containsIgnoreCase(q,YES_OR_NOT)) {
			// attention a la question : "est ce que tu reponds toujours OUI ? (OUI|NON)
			// et quid s'il y a une question : "veux tu perdre ou me donner 100€ ? (OUI|NON)
			Logger.info("Happy question");
			answer = "OUI";
		} else {
			Logger.info("Question not treated");
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

		if (StringUtils.isBlank(lastQuestionSend)
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
