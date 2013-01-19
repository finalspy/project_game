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
	private static final String HAPPY = "Es tu heureux de participer(OUI/NON)";
	private static final String MAILING_LIST = "Es tu abonne a la mailing list(OUI/NON)";
	private static final String READY = "Es tu pret a recevoir une enonce au format markdown par http post(OUI/NON)";

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
		Logger.info("Yoooooooooooooo: " + Http.Request.current().toString());
		if (org.apache.commons.lang.StringUtils.isNotBlank(q)) {
			answer = treatedQuestion(q);

		} else {
			sendMail("Connection whitout question");
		}
		return answer;
	}

	public static void indexPost() throws EmailException {
		StringBuilder mailBody = new StringBuilder();
		mailBody.append(" To string : " + Http.Request.current().toString()
				+ "\n");
		Logger.info("To string request" + Http.Request.current().toString());
		Logger.info("\n " + Http.Request.current().args);
		mailBody.append(" Args " + Http.Request.current().args.keySet() + " \n");
		mailBody.append(" Body " + Http.Request.current().body + " \n\n");
		mailBody.append("String builder: "
				+ ToStringBuilder.reflectionToString(Http.Request.current()));
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
		} else if (HAPPY.equalsIgnoreCase(q)
				|| MAILING_LIST.equalsIgnoreCase(q)
				|| READY.equalsIgnoreCase(q)) {
			Logger.info("Happy question");
			answer = "OUI";
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
	private static void sendMail(String msg) throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setFrom("florian.jose.ferreira@gmail.com");
		email.addTo("florian.jose.ferreira@gmail.com");
		email.setSubject("New question");
		email.setMsg(msg);
		email.getHostName();
		Mail.send(email);
	}
}