package hotelsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class BillPayment implements Serializable{
	private static int maxID = 1;
	private int billPaymentID;  
	private CheckInCheckOut checkInCheckOut;  
	private ArrayList<RoomService> statusList = new ArrayList<>();
	private double roomTotalPrice;  
	private double roomServiceTotalPrice; 
	private double totalPrice;
	private double discount; 
	private double tax; 
	private double finalTotalPrice; 
	private String paymentMode; 
	private Card card;
	private String paymentStatus;
	

	public BillPayment(CheckInCheckOut checkInCheckOut, ArrayList<RoomService> statusList, double roomTotalPrice,
			double roomServiceTotalPrice, double totalPrice, double discount, double tax, double finalTotalPrice,
			String paymentMode, Card card, String paymentStatus) {
		super();
		this.billPaymentID = maxID;
		this.checkInCheckOut = checkInCheckOut;
		this.statusList = statusList;
		this.roomTotalPrice = roomTotalPrice;
		this.roomServiceTotalPrice = roomServiceTotalPrice;
		this.totalPrice = totalPrice;
		this.discount = discount;
		this.tax =tax;
		this.finalTotalPrice = finalTotalPrice;
		this.paymentMode = paymentMode;
		this.card = card;
		this.paymentStatus = paymentStatus;
		maxID++;
	}
	
	public BillPayment(int billPaymentID, CheckInCheckOut checkInCheckOut, ArrayList<RoomService> statusList, double roomTotalPrice,
			double roomServiceTotalPrice, double totalPrice, double discount, double tax, double finalTotalPrice,
			String paymentMode, Card card, String paymentStatus) {
		super();
		this.billPaymentID = billPaymentID;
		this.checkInCheckOut = checkInCheckOut;
		this.statusList = statusList;
		this.roomTotalPrice = roomTotalPrice;
		this.roomServiceTotalPrice = roomServiceTotalPrice;
		this.totalPrice = totalPrice;
		this.discount = discount;
		this.tax =tax;
		this.finalTotalPrice = finalTotalPrice;
		this.paymentMode = paymentMode;
		this.card = card;
		this.paymentStatus = paymentStatus;
	}
	
	

	

	public ArrayList<RoomService> getStatusList() { 
		return statusList; 
	}
	
	public static int getMaxID() { 
		return maxID; 
	}

	public static void setMaxID(int maxID) { 
		BillPayment.maxID = maxID; 
	}

	public int getbillPaymentID() { 
		return billPaymentID; 
	}

	public void setbillPaymentID(int billPaymentID) { 
		this.billPaymentID = billPaymentID; 
	}

	public CheckInCheckOut getcheckInCheckOut() { 
		return checkInCheckOut; 
	}

	public void setcheckInCheckOut(CheckInCheckOut checkInCheckOut) { 
		this.checkInCheckOut = checkInCheckOut; 
	}

	public void setStatusList(ArrayList<RoomService> statusList) { 
		this.statusList = statusList; 
	}

	public double getdiscount() { 
		return discount; 
	}

	public void setdiscount(double discount) { 
		this.discount = discount; 
	}
	
	public double getroomTotalPrice() { 
		return roomTotalPrice; 
	}

	public void setroomTotalPrice(double roomTotalPrice) { 
		this.roomTotalPrice = roomTotalPrice; 
	}

	public double getRoomServiceTotalPrice() { 
		return roomServiceTotalPrice; 
	}

	public void setRoomServiceTotalPrice(double roomServiceTotalPrice) { 
		this.roomServiceTotalPrice = roomServiceTotalPrice; 
	}

	public double getTotalPrice() { 
		return totalPrice; 
	}
	
	public void setTotalPrice(double totalPrice) { 
		this.totalPrice = totalPrice; 
	}

	public String getPaymentMode() { 
		return paymentMode; 
	}

	public void setPaymentMode(String paymentMode) { 
		this.paymentMode = paymentMode; 
	}

	public double getTaxAmt() { 
		return tax; 
	}

	public void setTaxAmt(double tax) { 
		this.tax =tax; 
	}

	public double getfinalTotalPrice() { 
		return finalTotalPrice; 
	}

	public void setfinalTotalPrice(double finalTotalPrice) { 
		this.finalTotalPrice = finalTotalPrice; 
	}
	
	public Card getCard() { 
		return card; 
	}

	public void setCard(Card card) { 
		this.card = card; 
	}

	public String getPaymentStatus() { 
		return paymentStatus; 
	}

	public void setPaymentStatus(String paymentStatus) { 
		this.paymentStatus = paymentStatus; 
	}
	
}