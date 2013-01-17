package controllers;

import play.*;
import play.libs.Mail;
import play.mvc.*;

import groovyjarjarantlr.StringUtils;

import java.util.*;

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

	/**
	 * Index.
	 * 
	 * @param question
	 *            the question
	 * @return the string
	 * @throws EmailException
	 */
	public static String index(String q) throws EmailException {
		String answer = "No question to treated";
		if (org.apache.commons.lang.StringUtils.isNotBlank(q)) {
			answer = treatedQuestion(q);

		}
		return answer;
	}

	private static String treatedQuestion(String q) throws EmailException {
		String answer;
		Logger.info("new question: \n" + q + "\n");
		sendMail(q);
		if (EMAIL.equalsIgnoreCase(q)) {
			Logger.info("email question");
			answer = "florian.jose.ferreira@gmail.com";
		} else {
			Logger.info("question not treated");
			answer = "Question not treated";
		}
		return answer;
	}

	private static void sendMail(String q) throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setFrom("florian.jose.ferreira@gmail.com");
		email.addTo("florian.jose.ferreira@gmail.com");
		email.setSubject("New question");
		email.setMsg(q);
		email.getHostName();
		Mail.send(email);
	}
}