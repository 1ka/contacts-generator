package com.inazaruk.contactsgen;

public class ContactField {

	private final String contentItemType;

	private String metaType;
	private int type;

	private final String metaValue;
	private final String value;

	public ContactField(final String aContentItemType, final String aMetaValue, final String aValue) {
		contentItemType = aContentItemType;
		metaValue = aMetaValue;
		value = aValue;
	}

	public ContactField(final String aContentItemType, final String aMetaValue,
			final String aValue, final String aMetaType, final int aType) {
		this(aContentItemType, aMetaValue, aValue);
		metaType = aMetaType;
		type = aType;
	}

	public String getContentItemType() {
		return contentItemType;
	}

	public int getFieldType() {
		return type;
	}

	public String getFieldValue() {
		return value;
	}

	public boolean hasFieldType() {
		return metaType != null;
	}

	public String getMetaType() {
		return metaType;
	}

	public String getMetaValue() {
		return metaValue;
	}
}
