package com.inazaruk.contactsgen;

import java.util.Random;

import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;

public class ContactData {

	private final static Random random = new Random();

	private static final String[] FORENAMES = new String[] { "Rozanne", "Jettie", "Hyman",
			"Jolynn", "Tasha", "Deidra", "Evelyne", "Erlinda", "Bertram", "Lorette", "Wendi",
			"Tynisha", "Sherly", "Lashawna", "Harriet", "Tessie", "Janis", "Alvaro", "Reena",
			"Providencia", "Chase", "Buddy", "Diedre", "Doria", "Alysha", "Tami", "Patsy", "Louie",
			"Merlene", "Katherine", "Trudy", "Sharonda", "Vasiliki", "Pandora", "Calandra",
			"Monica", "Alisa", "Dione", "Genevie", "Therese", "Alessandra", "Charlene", "Kayla",
			"Daren", "Kendra", "Sandy", "Devora", "Leora", "Rosella", "Tennie", "Cherise",
			"Violette", "Tim", "Tinisha", "Luci", "Margarett", "Carmine", "Agnes", "Shellie",
			"Abraham", "Modesto", "Omega", "Deandra", "Latasha", "Stephen", "Orpha", "Robyn",
			"Leeanne", "Michaele", "Tracy", "Soon", "Janetta", "Vernia", "Kami", "Joaquin",
			"Ursula", "Alberto", "Bettyann", "Beau", "Randall", "Vina", "Monroe", "Jeniffer",
			"Sara", "Jaleesa", "Emelina", "Cherri", "Harland", "Naoma", "Louis", "Simone",
			"Victorina", "Collen", "Clemmie", "Adelia", "Valerie", "Nanette", "Alida", "Elba",
			"Zenobia", "Karl" };

	private static final String[] SURNAMES = new String[] { "Lipkind", "Maharay", "Boos",
			"Challis", "Welby", "Radde", "Garboczi", "Mcgirr", "Cantwell", "Palke", "Holton",
			"Sloane", "Prodromou", "Blotner", "Tobias", "Orfanos", "Hanning", "Penrose",
			"Palleschi", "Cote", "Austen", "Wathelet", "Plant", "Wilkening", "Bonner", "Eldred",
			"Berkman", "Hotopp", "Fuentes-ortega", "Draper", "Marobella", "Donvan", "Finkelstein",
			"Ehrendorfer", "Sommers", "Ginsberg", "Garvin", "Routh", "Kalata", "Klint", "Gilborn",
			"Wilcox", "Kurihara", "Ophir", "Simon", "Stirling", "Tachikawa", "Paul", "Pope",
			"Mead", "Mcvay", "Usseglio", "Sams", "Nixon", "O'neill", "Duffy", "Thrainsson",
			"Delacey", "Steinitz", "Morgan", "Fryar", "Liakos", "Pitfield", "Bunting", "Etheridge",
			"Richer", "Trifiro", "Lawless", "Papandreou", "Hazzard", "Holwell", "Hanify",
			"Ivanoff", "Linsley", "Abernathy", "Vigier", "Servi", "Dzur", "Quiroga", "Barnaby",
			"Bethe", "Marzke", "Merrithew" };

	private static final String[] PLACES = new String[] { "Freymont", "Valmead", "Orlea",
			"Bridgeacre", "Ironmoor", "Orbeach", "Bayhedge", "Mallowshade", "Waterbank",
			"Falldeer", "Wellglass", "Waypond", "Westcastle", "Aldbush", "Fallness", "Highbush",
			"Merrowport", "Esterhaven", "Dorport", "Wyvernview", "Eastborough", "Woodviolet",
			"Deepapple", "Beachgate", "Foxpond", "Northden", "Bluehedge", "Bywall", "Watermont",
			"Lightdeer", "Woodmage", "Greengriffin", "Lightdell", "Lorlyn", "Aldholt",
			"Merrifield", "Wellwald", "Merrivale", "Icecrest", "Morfield", "Eriacre", "Elfden",
			"Silvergrass", "Westermoor", "Faymage", "Aellake", "Snowden", "Summercliff", "Iceloch",
			"Mallowshadow", "Deepbridge", "Faywheat", "Deepoak", "Valgate", "Spellacre", "Erikeep",
			"Ironedge", "Witchcastle", "Havenden", "Merrowcliff", "Summerwind", "Cormount",
			"Sagebarrow", "Eastness", "Foxwall", "Woodmoor", "Wolfholt", "Coastpond", "Northhedge",
			"Whiteston", "Lightmont", "Mallowpine", "Summergate", "Elfdale", "Havendell",
			"Griffinmeadow", "Marshvale", "Northfair", "Merridell", "Norland", "Mallowmarsh",
			"Millcastle", "Deepdell", "Griffinfort", "Millford", "Westercoast", "Waterwick",
			"Westerkeep", "Deeredge", "Edgemead", "Mallowdragon", "Aldlyn", "Bayston", "Belbourne",
			"Goldmist", "Woodfield", "Oaktown", "Crystallake", "Vertspell", "Loracre", "Newfield",
			"Ashland", "Pondcastle", "Witchedge", "Shadowcourt", "Icebush", "Redflower",
			"Millcoast", "Glassdragon", "Northborough", "Snowmill", "Wilderock", "Esterness",
			"Greyby", "Dorville", "Fayshore", "Brightbridge", "Faydragon", "Springdale",
			"Crystaldeer", "Elfmill", "Stonebridge", "Snowelf", "Clearborough", "Oldpond",
			"Brookfield" };

	private static final String[] STREETTYPE = new String[] { " Road", " Close", " Street",
			" Avenue", " Copse", " Alley", " Boulevard", " Estate" };

	private static final String[] EMAIL_SUFFIX = new String[] { ".com", ".co.uk", ".co.fr", ".biz",
			".org", ".net", ".info" };

	private static final String[] COMPANIES = new String[] { "Google", "SAS", "CHG Healthcare",
			"Boston Consulting", "Wegmans", "NetApp", "Hilcorp Energy", "Edward Jones",
			"Ultimate Software", "Camden Property Trust", "Qualcomm", "DreamWorks",
			"Quicken Loans", "Robert W Baird", "DPR Construction", "Container Store",
			"Recreational Equipment", "Burns and McDonnell", "Salesforce", "Millennium",
			"Gore and Associates", "Intuit", "Alston Bird", "World Wide Technology",
			"Plante Moran", "Chesapeake Energy", "Devon Energy", "Kimpton Hotels and Restaurants",
			"Southern Ohio Medical Center", "Mercedes-Benz", "Zappos", "JM Family Enterprises",
			"Perkins Coie", "Rackspace Hosting", "National Instruments", "Genentech",
			"Nugget Market", "NuStar Energy", "Meridian Health", "USAA", "Mayo Clinic", "Cisco",
			"Apple", "Scripps Health", "FactSet Research Systems", "Baker Donelson",
			"Childrens Healthcare of Atlanta", "Deloitte", "Novo Nordisk", "Atlantic Health",
			"Mens Wearhouse", "American Express", "Scottrade", "Autodesk", "Umpqua Bank",
			"Navy Federal", "Ernst Young", "Everett Clinic", "Allianz", "Teach America", "Stryker",
			"Arnold Porter", "Hitachi Data Systems", "Marriott International",
			"Darden Restaurants", "QuikTrip", "Methodist Hospital System", "Intel Corporation",
			"OhioHealth", "Automotive Resources International", "Whole Foods Market", "EOG",
			"PCL Construction", "CarMax", "Microsoft", "Publix Super Markets", "TEKsystems",
			"Rothstein Kass", "PricewaterhouseCoopers", "Bingham McCutchen", "Four Seasons Hotels",
			"Aflac", "Balfour Beatty", "TDIndustries", "Capital One", "Nordstrom",
			"Roche Diagnostics", "Bright Horizons", "Accenture", "Hasbro", "Goldman Sachs",
			"Starbucks", "Mars", "Mattel", "Aéropostale", "FedEx Corporation", "Grainger",
			"SoftWyer" };

	private static String[] COUNTRIES = new String[] { "Afghanistan", "Albania", "Algeria",
			"Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba",
			"Australia", "Austria", "Azerbaijan", "Bahamas, The", "Bahrain", "Bangladesh",
			"Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia",
			"Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso",
			"Burma", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde",
			"Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros",
			"Congo, Democratic Republic of the", "Congo, Republic of the", "Costa Rica",
			"Cote d'Ivoire", "Croatia", "Cuba", "Curacao", "Cyprus", "Czech Republic", "Denmark",
			"Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt",
			"El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji",
			"Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece",
			"Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Holy See",
			"Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq",
			"Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya",
			"Kiribati", "Korea, North", "Korea, South", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos",
			"Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania",
			"Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives",
			"Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia",
			"Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Namibia",
			"Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Zealand", "Nicaragua",
			"Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau",
			"Palestinian Territories", "Panama", "Papua New Guinea", "Paraguay", "Peru",
			"Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda",
			"Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa",
			"San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia",
			"Seychelles", "Sierra Leone", "Singapore", "Sint Maarten", "Slovakia", "Slovenia",
			"Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain",
			"Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria",
			"Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga",
			"Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda",
			"Ukraine", "United Arab Emirates", "United Kingdom", "Uruguay", "Uzbekistan",
			"Vanuatu", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe" };

	private static int[] TYPE_EMAIL = new int[] { Email.TYPE_HOME, Email.TYPE_MOBILE,
			Email.TYPE_WORK, Email.TYPE_OTHER };

	private static int[] TYPE_WEBSITE = new int[] { Website.TYPE_BLOG, Website.TYPE_FTP,
			Website.TYPE_HOME, Website.TYPE_HOMEPAGE, Website.TYPE_OTHER, Website.TYPE_PROFILE,
			Website.TYPE_WORK };

	private static int[] TYPE_PHONE = new int[] { Phone.TYPE_ASSISTANT, Phone.TYPE_CALLBACK,
			Phone.TYPE_CAR, Phone.TYPE_COMPANY_MAIN, Phone.TYPE_FAX_HOME, Phone.TYPE_FAX_WORK,
			Phone.TYPE_HOME, Phone.TYPE_ISDN, Phone.TYPE_MAIN, Phone.TYPE_MMS, Phone.TYPE_MOBILE,
			Phone.TYPE_OTHER, Phone.TYPE_OTHER_FAX, Phone.TYPE_PAGER, Phone.TYPE_RADIO,
			Phone.TYPE_TELEX, Phone.TYPE_TTY_TDD, Phone.TYPE_WORK, Phone.TYPE_WORK_MOBILE,
			Phone.TYPE_WORK_PAGER };

	private static int[] TYPE_ADDRESS = new int[] { StructuredPostal.TYPE_HOME,
			StructuredPostal.TYPE_WORK, StructuredPostal.TYPE_OTHER };

	private static String getRandom(final String[] data) {
		return data[random.nextInt(data.length)];
	}

	private static int getRandom(final int[] data) {
		return data[random.nextInt(data.length)];
	}

	public static String getSurname() {
		return getRandom(SURNAMES);
	}

	public static String getEmailSuffix() {
		return getRandom(EMAIL_SUFFIX);
	}

	public static String getCompany() {
		return getRandom(COMPANIES);
	}

	public static String getForename() {
		return getRandom(FORENAMES);
	}

	public static String getStreetType() {
		return getRandom(STREETTYPE);
	}

	public static String getTown() {
		return getRandom(PLACES);
	}

	public static String getCountry() {
		return getRandom(COUNTRIES);
	}

	public static int getPhoneType() {
		return getRandom(TYPE_PHONE);
	}

	public static int getEmailType() {
		return getRandom(TYPE_EMAIL);
	}

	public static int getAddressType() {
		return getRandom(TYPE_ADDRESS);
	}

	public static int getWebsiteType() {
		return getRandom(TYPE_WEBSITE);
	}

}
