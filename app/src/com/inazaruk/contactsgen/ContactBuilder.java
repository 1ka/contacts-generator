package com.inazaruk.contactsgen;

import java.util.Random;
import java.util.regex.Pattern;

import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;

public class ContactBuilder {

	private final static Random random = new Random();

	private static Pattern WHITESPACE = Pattern.compile("\\W");

	private final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * Get a name that has a randomly generated forename and a random surname
	 * picked from a list
	 * 
	 * @return a name
	 */
	public static ContactField getName() {
		final String name = getRandomNameString(random.nextInt(7)) + " " + ContactData.getSurname();
		return new ContactField(StructuredName.CONTENT_ITEM_TYPE, StructuredName.DISPLAY_NAME, name);
	}

	public static ContactField getEmail(final String name) {
		final String email = clean(name) + "@" + clean(ContactData.getCompany())
				+ ContactData.getEmailSuffix();
		return new ContactField(Email.CONTENT_ITEM_TYPE, Email.DATA, email);
	}

	private static String clean(final String s) {
		return WHITESPACE.matcher(s).replaceAll(".");
	}

	private static String getRandomNameString(final int length) {
		final int len = length < 1 ? 1 : length;
		final StringBuilder s = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			s.append(alphabet.charAt(random.nextInt(alphabet.length())));
		}
		return s.toString();
	}

}
