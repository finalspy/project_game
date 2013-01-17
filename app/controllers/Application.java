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
	 * @param question the question
	 * @return the string
	 */
	public static String index(String q) {
		if (EMAIL.equalsIgnoreCase(q)) {
			return "florian.jose.ferreira@gmail.com";
		}
		return "Not treated";
	}
}