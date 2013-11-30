package com.inazaruk.contactsgen;

import java.util.Random;
import java.util.regex.Pattern;

import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;

public class ContactBuilder {

	private static final int minPhone = 100000000;
	private static final int rangePhone = 899999999;

	private final static Random r = new Random();

	private static Pattern WHITESPACE = Pattern.compile("\\W");

	private final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * Get a name that has a randomly generated forename and a surname picked
	 * randomly from a list
	 * 
	 * @return a name field
	 */
	public static ContactField getName() {
		final String name = getRandomNameString(r.nextInt(7)) + " " + ContactData.getSurname();
		return new ContactField(StructuredName.CONTENT_ITEM_TYPE, StructuredName.DISPLAY_NAME, name);
	}

	/**
	 * Get a nickname picked randomly from a list
	 * 
	 * @return a nickname field
	 */
	public static ContactField getNickName() {
		return new ContactField(Nickname.CONTENT_ITEM_TYPE, Nickname.NAME,
				ContactData.getForename());
	}

	/**
	 * Get an address that has random street number, street name, street type ,
	 * town and country
	 * 
	 * @return the formatted address field
	 */
	public static ContactField getAddress() {
		final StringBuilder a = new StringBuilder();
		a.append(r.nextInt(999) + 1);
		a.append(" ");
		a.append(ContactData.getSurname());
		a.append(ContactData.getStreetType());
		a.append("\n");
		a.append(ContactData.getTown());
		a.append("\n");
		a.append(ContactData.getCountry());

		return new ContactField(StructuredPostal.CONTENT_ITEM_TYPE,
				StructuredPostal.FORMATTED_ADDRESS, a.toString(), StructuredPostal.TYPE,
				ContactData.getAddressType());
	}

	/**
	 * Get an organisation picked randomly from a list.
	 * 
	 * @return the organisation field
	 */
	public static ContactField getOrganisation() {
		return new ContactField(Organization.CONTENT_ITEM_TYPE, Organization.COMPANY,
				ContactData.getCompany());
	}

	/**
	 * Generate a random email using the supplied name and a company name picked
	 * from a list. The name is changed to be a valid email format.
	 * 
	 * @param name
	 *            the name of the email recipient
	 * @return the full email address field
	 */
	public static ContactField getEmail(final String name) {
		final String email = clean(name).toLowerCase() + "@"
				+ clean(ContactData.getCompany()).toLowerCase() + ContactData.getEmailSuffix();
		return new ContactField(Email.CONTENT_ITEM_TYPE, Email.DATA, email, Email.TYPE,
				ContactData.getEmailType());
	}

	/**
	 * Generate a random phone number with a random phone type
	 * 
	 * @return a phone field
	 */
	public static ContactField getPhone() {
		return new ContactField(Phone.CONTENT_ITEM_TYPE, Phone.DATA, ""
				+ (minPhone + r.nextInt(rangePhone)), Phone.TYPE, ContactData.getPhoneType());
	}

	/**
	 * Generate a random website url with a random type.
	 * 
	 * @return a website field
	 */
	public static ContactField getWebsite() {
		final StringBuilder a = new StringBuilder();
		a.append("www.");
		a.append(clean(ContactData.getCompany()).toLowerCase());
		a.append(ContactData.getEmailSuffix());

		return new ContactField(Website.CONTENT_ITEM_TYPE, Website.URL, a.toString(), Website.TYPE,
				ContactData.getWebsiteType());
	}

	private static String clean(final String s) {
		return WHITESPACE.matcher(s).replaceAll(".");
	}

	private static String getRandomNameString(final int length) {
		final int len = length < 2 ? 2 : length;
		final StringBuilder s = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			s.append(alphabet.charAt(r.nextInt(alphabet.length())));
		}
		return s.charAt(0) + s.substring(1).toLowerCase();
	}

}
