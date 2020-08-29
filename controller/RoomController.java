package hotelsystem.controller; //

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
	
import hotelsystem.entity.Room;
import hotelsystem.entity.RoomStatus;

public class RoomController extends DatabaseController{
	private static final String DB_PATH = "DB/Room.dat";
	private static RoomController inst = null;
	private final ArrayList<Room> roomList;
	
	private RoomController() {
		roomList = new ArrayList<>();
    }
	
	public static RoomController getInst() {
        if (inst == null) {
            inst = new RoomController();
        }
        return inst;
    }
	
	public int getRoomTypeQty(int roomTypeID) {
		ArrayList<Room> rList = new ArrayList<>();
		for(Room r : roomList) {
			if (roomTypeID == r.getRoomType()) {
			rList.add(r); }
		}
		return rList.size();
	}
	
	public Room getRoom(String roomFloorNo) {
        for (Room room : roomList) {
            if (room.getRoomFloorNo().equals(roomFloorNo))
                return room;
        }
        return null;
    }
	
	public void updateRoom(String roomFloorNo, int Typeid, String bedType, boolean wifi, boolean smoke, boolean view) {
		Room room = getRoom(roomFloorNo);
		room.setBedType(bedType);
		room.setWifi(wifi);
		room.setSmoking(smoke);
		room.setView(view);
		room.setRoomType(Typeid);
		System.out.println("Room :" + room.getRoomFloorNo()  +  " has been updated successfully.");
		SaveDB();
	}
	
	
	public ArrayList<Room> getAllRooms() {
		ArrayList<Room> rList = new ArrayList<>();
		for(Room r : roomList) {
			rList.add(r);
		}
		return rList;
	}
	
	public ArrayList<Room> getAllRoom(Date start, Date end) {
		ArrayList<Room> roomStatusList = new ArrayList<>();
		for(Room room: roomList) {
			String roomFNo = room.getRoomFloorNo();
			ArrayList<RoomStatus> rSList = RoomStatusController.getInst().getRoomStatus(roomFNo);
			if (rSList != null) {
				for (RoomStatus roomStatus : rSList) {
					if ((start.before(roomStatus.getDateFrom()) && end.before(roomStatus.getDateFrom()))) {
						roomStatusList.add(room);
					} else if ((start.after(roomStatus.getDateTo()) && end.after(roomStatus.getDateTo()))) {
						roomStatusList.add(room);
					} else if (roomStatus.getStatus().equals("Cancelled") || roomStatus.getStatus().equals("Expired")
							|| roomStatus.getStatus().equals("Checked-Out")) {
						roomStatusList.add(room);
					} else if (roomStatus.getStatus().equals("Checked-In") || roomStatus.getStatus().equals("Reserved")
							|| roomStatus.getStatus().equals("Under-Maintenance")) {
						int i = 0;
						for (Room rSCheck : roomStatusList) {
							if (rSCheck.getRoomFloorNo().equals(roomStatus.getRoomFloorNumber())) {
								roomStatusList.remove(i);
								break;
							}
							i++;
						}
					} else
						break;

					Object[] st = roomStatusList.toArray();
					for (Object s : st) {
						if (roomStatusList.indexOf(s) != roomStatusList.lastIndexOf(s)) {
							roomStatusList.remove(roomStatusList.lastIndexOf(s));
						}
					}
				}
			} else {
				roomStatusList.add(room);
			}
		}
		Object[] st = roomStatusList.toArray();
		for (Object s : st) {
			if (roomStatusList.indexOf(s) != roomStatusList.lastIndexOf(s)) {
				roomStatusList.remove(roomStatusList.lastIndexOf(s));
			}
		}
		return roomStatusList;
	}

	public ArrayList<Room> getAllMaintenanceRoom() {
		ArrayList<Room> roomStatusList = new ArrayList<>();
		Date current = new Date();
		for (Room room : roomList) {
			String roomFNo = room.getRoomFloorNo();
			ArrayList<RoomStatus> rSList = RoomStatusController.getInst().getRoomStatus(roomFNo);
			if(rSList!=null) {
				for (RoomStatus roomStatus : rSList) {
					if (roomStatus.getStatus().equals("Under-Maintenance")) {
						if(roomStatus.getDateFrom().before(current) && roomStatus.getDateTo().after(current)){
							roomStatusList.add(room);
						
						}
						else if (roomStatus.getDateFrom().equals(current) || roomStatus.getDateTo().equals(current)) {
							roomStatusList.add(room);
						}
					}
				}
			}
		}
		Object[] st = roomStatusList.toArray();
	      for (Object s : st) {
	        if (roomStatusList.indexOf(s) != roomStatusList.lastIndexOf(s)) {
	        	roomStatusList.remove(roomStatusList.lastIndexOf(s));
	         }
	      }
        return roomStatusList;
    }

    public void addRoom(Room room) {
    	roomList.add(room);
    	SaveDB();
    }

	@Override
	public boolean LoadDB() {
		roomList.clear();
        if (checkFileExist(DB_PATH)) {
            try {
                ArrayList<String> stringArray = (ArrayList<String>) read(DB_PATH);

                for (String st : stringArray) {
                    StringTokenizer token = new StringTokenizer(st, SEPARATOR);
                    int id = Integer.parseInt(token.nextToken().trim());                            
                    String roomFloorNo = token.nextToken().trim();
                    int roomTypeID = Integer.parseInt(token.nextToken().trim());
                    String bedType = token.nextToken().trim();
                    boolean wifi = Boolean.parseBoolean(token.nextToken().trim());
                    boolean smoking = Boolean.parseBoolean(token.nextToken().trim());
                    boolean view = Boolean.parseBoolean(token.nextToken().trim());

                    Room room = new Room(id, roomFloorNo, roomTypeID, bedType, wifi, smoking, view);
                    roomList.add(room);
                }

                System.out.printf("RoomController: %,d Entries Loaded.\n", roomList.size());
                return true;

            } catch (IOException | NumberFormatException ex) {
                System.out.println("ERROR. Room database is not loaded.");
                return false;
            }

        } else {
            System.out.println("ERROR. Room database is not loaded.");
            return false;
        }
	}

	@Override
	public void SaveDB() {
		List<String> output = new ArrayList<>();
        StringBuilder st = new StringBuilder();
        if (checkFileExist(DB_PATH)) {
            for (Room room : roomList) {
                st.setLength(0);
                st.append(room.getIDRoom());
                st.append(SEPARATOR);
                st.append(room.getRoomFloorNo());
                st.append(SEPARATOR);
                st.append(room.getRoomType());
                st.append(SEPARATOR);
                st.append(room.getBedType()); 
                st.append(SEPARATOR);
                st.append(room.isWifi());
                st.append(SEPARATOR);
                st.append(room.isSmoking());
                st.append(SEPARATOR);
                st.append(room.isView());
                st.append(SEPARATOR);

                output.add(st.toString());
            }

            try {
                write(DB_PATH, output);
            } catch (Exception ex) {
                System.out.println("Sorry, we were not able to write to the file.");
            }
        } else {
            System.out.println("Sorry, the file was not found.");
        }
	}
}