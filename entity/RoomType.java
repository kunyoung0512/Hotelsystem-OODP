package hotelsystem.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RoomType implements Serializable{
	
	
	private int typeID;  
	private String roomType;  
	private double weekDayRate;  
	private double weekEndRate;  
	
	
	
	public RoomType() {}
	
	public RoomType(int typeID, String roomType, double weekDayRate, double weekEndRate) {
		this.typeID = typeID;
		this.roomType = roomType;
		this.weekDayRate = weekDayRate;
		this.weekEndRate = weekEndRate;
	}
	
	
	
	public double getWeekEndRate() { return weekEndRate; }
	
	public void setWeekEndRate(double weekEndRate) { this.weekEndRate = weekEndRate; }

	
	public double getWeekDayRate() { return weekDayRate; }
	
	public void setWeekDayRate(double weekDayRate) { this.weekDayRate = weekDayRate; }
	
	public String getRoomType() { return roomType; }
	
	public void setRoomType(String roomType) { this.roomType = roomType; }
	
	public int getTypeID() { return this.typeID; }
	
}