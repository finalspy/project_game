package controllers;

import play.*;
import play.mvc.*;

import groovyjarjarantlr.StringUtils;

import java.util.*;

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
	 */
	public static String index(String q) {
		Logger.info("new question: \n" + q + "\n");
		if (EMAIL.equalsIgnoreCase(q)) {
			Logger.info("email question");
			return "florian.jose.ferreira@gmail.com";
		}
		Logger.info("question not treated");
		return "Not treated";
	}
}