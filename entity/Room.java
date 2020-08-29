package hotelsystem.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Room implements Serializable{
	private static int max = 1;
	private int ID_room;
	private String room_floor_no;
	private int roomTypeID;
	private String bed_type; 
	private boolean wifi;
	private boolean smoking;
	private boolean view;
	
	public Room(String roomFloorNo, int roomTypeID, String bedType, boolean wifi, boolean smoking, boolean view) {
		this.ID_room = max;
		this.room_floor_no = roomFloorNo;
		this.roomTypeID = roomTypeID;
		this.bed_type = bedType;
		this.wifi = wifi;
		this.smoking = smoking;
		this.view = view;
		max++;
	}
	
	public Room(int IDroom, String roomFloorNo, int roomTypeID, String bedType, boolean wifi, boolean smoking, boolean view) {
		this.ID_room = max;
		this.room_floor_no = roomFloorNo;
		this.roomTypeID = roomTypeID;
		this.bed_type = bedType;
		this.wifi = wifi;
		this.smoking = smoking;
		this.view = view;
		max++;
	}
	
	public int getIDRoom() { return this.ID_room; }
	
	public static int getmax() { return Room.max; }
	
	public static void setmax(int ID) { Room.max = ID; }

	public String getRoomFloorNo() { return room_floor_no; }
	
	public void setRoomFloorNo(String roomFloorNo) { this.room_floor_no = roomFloorNo; }

	public int getRoomType() { return roomTypeID; }

	public void setRoomType(int roomTypeID) { this.roomTypeID = roomTypeID; }
	
	public String getBedType() { return bed_type; }
	
	public void setBedType(String bedType) { this.bed_type = bedType; }

	public boolean isWifi() { return wifi; }

	public void setWifi(boolean wifi) { this.wifi = wifi; }

	public boolean isSmoking() { return smoking; }

	public void setSmoking(boolean smoking) { this.smoking = smoking; }

	public boolean isView() { return view; }

	public void setView(boolean view) { this.view = view;}

}