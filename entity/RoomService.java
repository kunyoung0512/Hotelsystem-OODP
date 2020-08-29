package hotelsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class RoomService implements Serializable{
	private static int incrementID = 1;  
	private int roomServiceID;  
	
	private ArrayList<Food> foodList = new ArrayList<>();
	
	private boolean status;
	private double totalPrice;
	private int roomStatusID;
	private String desc;   
	
	
	public RoomService() {}
	
	public RoomService(ArrayList<Food> foodList, int roomStatusID, String desc, double totalPrice, Boolean status) {
		this.roomServiceID = incrementID;
		this.foodList = foodList;
		this.roomStatusID = roomStatusID;
		this.desc = desc;
		this.totalPrice = totalPrice;
		this.status = status;
		incrementID++;
	}
	
	public static int getincrementID() { return RoomService.incrementID;}
	
	public static void setincrementID(int ID) { RoomService.incrementID = ID; }
	
	public int getRoomStatusID() { return this.roomStatusID; }
	
	public void setRoomStatusID(int roomStatusID) { this.roomStatusID = roomStatusID; }
	
	public int getRoomServiceID() { return roomServiceID; }
	
	public void setRoomServiceID(int roomServiceID) { this.roomServiceID = roomServiceID; }
	
	public ArrayList<Food> getFoodList() { return this.foodList; }
	
	public String getDesc() { return desc; }
	
	public void setDesc(String desc) { this.desc = desc; }
	
	public boolean getStatus() { return status; }
	
	public void setStatus(boolean status) { this.status = status; }
	
	public double getTotalPrice() { return this.totalPrice; }
	
	public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
	
	
}