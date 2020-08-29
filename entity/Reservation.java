package hotelsystem.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Reservation implements Serializable{
	private static int incID = 1;
	private int reservationID;  
	private String reservationCode;  
	private ArrayList<RoomStatus> statusList = new ArrayList<>();
	private int numberOfChildren;  
	private int numberOfAdults;  
	private String status;  
	private Customer customer;
	
	public Reservation(Customer cust, ArrayList<RoomStatus> statusList, 
			int noChild, int noAdult, String status) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
    	LocalDate localDate = LocalDate.now();
    	
		this.reservationID = incID;
		this.reservationCode = "X" + incID + dtf.format(localDate);
		this.customer = cust;
		this.statusList = statusList;
		this.numberOfChildren = noChild;
		this.numberOfAdults = noAdult;
		this.status = status;
		incID++;
	}
	
	public Reservation(Customer cust, int noChild, int noAdult, String status) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
    	LocalDate localDate = LocalDate.now();
    	
		this.reservationID = incID;
		this.reservationCode = "R" + incID + dtf.format(localDate);
		this.customer = cust;
		this.numberOfChildren = noChild;
		this.numberOfAdults = noAdult;
		this.status = status;
		incID++;
	}

	
	
	public int getReservationID() { return this.reservationID; }
	
	public static int getIncID() { return Reservation.incID; }
	
	public static void setIncID(int ID) { Reservation.incID = ID; }
	
	public String getReservationCode() { return reservationCode; }
	
	public void setReservationCode(String reserveCode) { this.reservationCode = reserveCode; }
	
	public Customer getCustomer() { return customer; }
	
	public ArrayList<RoomStatus> getStatusList(){ return statusList; }

	public int getNumberOfChildren() { return numberOfChildren; }

	public void setNumberOfChildren(int numberOfChildren) { this.numberOfChildren = numberOfChildren; }
	
	public String getStatus() { return status; }

	public void setStatus(String status) { this.status = status; }
	
	public int getNumberOfAdults() { return numberOfAdults; }

	public void setNumberOfAdults(int numberOfAdults) { this.numberOfAdults = numberOfAdults; }

	
}