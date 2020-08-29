package hotelsystem.entity;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class RoomStatus implements Serializable{
	private static int max = 1;
	
	private int roomBookingsID; 
	private String roomFloorNumber; 
	private int customerID;  
	private Date dateFrom; 
	private Date dateTo;  
	private String status;
	
	
	public RoomStatus(String roomNo, int CustID2, Date dateTo, Date dateFrom, String status2) {
		super();
		this.roomBookingsID = max;
		this.roomFloorNumber = roomNo;
		this.customerID = CustID2;
		this.status = status2;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		max++; 
	}
	
	
	public RoomStatus(int roomBookingsID,int CustID, String roomNo, Date dateFrom, Date dateTo,
			String status) {
		super();
		this.roomBookingsID = roomBookingsID;
		this.roomFloorNumber = roomNo;
		this.customerID = CustID;
		this.status = status;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}


	public String getRoomFloorNumber() { return roomFloorNumber; }

	public void setRoomFloorNumber(String roomFloorNumber) { this.roomFloorNumber = roomFloorNumber; }
	
	public static int getMax() { return max; }

	public static void setMax(int max) { RoomStatus.max = max; }

	public int getCustomerID() { return customerID; }

	public void setCustomerID(int CustomerID) { this.customerID = CustomerID; }

	public String getStatus() { return status; }

	public void setStatus(String status) { this.status = status; }
	
	public int getRoomBookingsID() { return roomBookingsID; }

	public void setRoomBookingsID(int roomBookingsID) { this.roomBookingsID = roomBookingsID; }

	public Date getDateFrom() { return dateFrom; }

	public void setDateFrom(Date dateFrom) { this.dateFrom = dateFrom; }

	public Date getDateTo() { return dateTo; }

	public void setDateTo(Date dateTo) { this.dateTo = dateTo; }
	
}