package hotelsystem.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Customer implements Serializable{
	private static int max = 1;
	private int ID_customer;
	private String nric;
	private String name;
	private String address;
	private long contact;
	private char gender;
	private String country;
	private String nationality;
	private Card card_Info;
	
	public Customer(String nric, String name, String address, long contact, String country2, char gender2, String nationality2, Card newcc)
	{
		this.ID_customer = max;
		this.nric = nric;
		this.address = address;
		this.card_Info = newcc;
		this.contact = contact;
		this.country = country2;
		this.nationality = nationality2;
		this.name = name;
		this.gender = gender2;
		max++;
	}

	public static int getID_Max() {
		return max;
	}

	public static void setID_Max(int ID) {
		max = ID;
	}

	public int getID_customer() {
		return ID_customer;
	}

	public void setID_customer(int g) {
		this.ID_customer = g;
	}

	public String getNric() {
		return nric;
	}

	public void setNric(String nric) {
		this.nric = nric;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long c) {
		this.contact = c;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char g) {
		this.gender = g;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String n) {
		this.nationality = n;
	}

	public Card getCard_Info() {
		return card_Info;
	}
	public void setCard_Info(Card c) {
		this.card_Info = c;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String Country) {
		this.country = Country;
	}

	public int getGuest_ID() {
		return 0;
	}
}



