package hotelsystem.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import hotelsystem.entity.RoomStatus;

public class RoomStatusController extends DatabaseController{
	private static final String DB_PATH = "DB/RoomStatus.dat";
	private static RoomStatusController inst = null;
	private final ArrayList<RoomStatus> roomStatusList;
	
	private RoomStatusController() {
		roomStatusList = new ArrayList<>();
    }
	
	public static RoomStatusController getInst() {
        if (inst == null) {
            inst = new RoomStatusController();
        }
        return inst;
    }
	
	
	public void updateStatustoCheckedIn(RoomStatus roomStatus) {
		for (RoomStatus status : roomStatusList) {
            if (status.getRoomBookingsID() == roomStatus.getRoomBookingsID()) {
            	status.setStatus("Checked-In");
            } 
        }
        SaveDB(); 
    }
	
	public void updateStatustoCheckedOut(RoomStatus roomStatus) {
		for (RoomStatus status : roomStatusList) {
            if (status.getRoomBookingsID() == roomStatus.getRoomBookingsID()) {
            	status.setStatus("Checked-Out");
            }
        }
        SaveDB();
    }
	
	public void updateStatustoExpired(RoomStatus roomStatus) {
		for (RoomStatus status : roomStatusList) {
            if (status.getRoomBookingsID() == roomStatus.getRoomBookingsID()) {
            	status.setStatus("Expired");
            }
        }
        SaveDB();
	}
	
	public void updateStatustoCancelled(RoomStatus roomStatus) {
		for (RoomStatus status : roomStatusList) {
            if (status.getRoomBookingsID() == roomStatus.getRoomBookingsID()) {
            	status.setStatus("Cancelled");
            }
        }
        SaveDB();
	}
	
	
	public Boolean checkRoomStatus(RoomStatus rs) {
		Boolean check = null;
		for (RoomStatus roomStatus : roomStatusList) {
			if(roomStatus.getDateFrom().equals(rs.getDateFrom()) && roomStatus.getDateTo().equals(rs.getDateTo())) {
				if (roomStatus.getStatus().equals("Under Maintenance") || roomStatus.getStatus().equals("Reserved") || roomStatus.getStatus().equals("Checked-In")) 
					return check = false;
	            else
	            	return check = true;
			}
        }
		return check;
	}
	
	public RoomStatus getStatus(String roomFloorNo) {
		Date current = new Date();
        for (RoomStatus roomStatus : roomStatusList) {
            if (roomStatus.getRoomFloorNumber().equals(roomFloorNo)&& roomStatus.getStatus().equals("Checked-In") && current.before(roomStatus.getDateTo())) 
            	return roomStatus;
        }
        return null;
    }
	
	public void addRoomStatus(RoomStatus roomStatus) {
		roomStatusList.add(roomStatus);
    	SaveDB();
    }
	
	public ArrayList<RoomStatus> getRoomStatus(String roomFloorNo) {
		ArrayList<RoomStatus> result = new ArrayList<>();
        for (RoomStatus roomStatus : roomStatusList) {
            if (roomStatus.getRoomFloorNumber().equals(roomFloorNo)) 
            	result.add(roomStatus);
        }
        if(!result.isEmpty())
        	return result;
        else 
        	return null;
        
    }
	
	public ArrayList<RoomStatus> getAllReserveRoom(Date start, Date end){
		ArrayList<RoomStatus> reserveList = new ArrayList<>();
		for (RoomStatus roomS : roomStatusList) {
				if(start.equals(roomS.getDateFrom()) || end.equals(roomS.getDateTo())) {
					reserveList.add(roomS);
				}
		}
        return reserveList;
	}
	

	@Override
	public boolean LoadDB() {
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yy");
		roomStatusList.clear();
        if (!checkFileExist(DB_PATH)) {
            System.out.println("I'm sorry but the RoomStatus database does not exist.");
            return false;

        } else {
            try {
                ArrayList<String> stringArray = (ArrayList<String>) read(DB_PATH);

                for (String st : stringArray) {
                    StringTokenizer token = new StringTokenizer(st, SEPARATOR);
                    @SuppressWarnings("unused")
					int id = Integer.parseInt(token.nextToken().trim()); 
                    String ID_room = token.nextToken().trim();
                    int guestID = Integer.parseInt(token.nextToken().trim());
                    String status = token.nextToken().trim();
                    Date dateFrom = null;
                    Date dateTo = null;
					try {
						dateFrom = df.parse(token.nextToken().trim());
						dateTo = df.parse(token.nextToken().trim());
					} catch (ParseException e) {
						e.printStackTrace();
					}

                    RoomStatus roomStatus = new RoomStatus(ID_room,guestID,dateTo,dateFrom,status);
                    roomStatusList.add(roomStatus);
                }

                System.out.printf("RoomStatusController: %,d Entries Loaded.\n", roomStatusList.size());
                return true;

            } catch (IOException | NumberFormatException ex) {
                System.out.println("I'm sorry but the RoomStatus database was not loaded.");
                return false;
            }
        }
	}

	@Override
	public void SaveDB() { 
		List<String> output = new ArrayList<>();
        StringBuilder st = new StringBuilder();
        if (checkFileExist(DB_PATH)) {
            for (RoomStatus roomStatus : roomStatusList) {
                st.setLength(0);
                st.append(roomStatus.getRoomBookingsID()); 
                st.append(SEPARATOR);
                st.append(roomStatus.getRoomFloorNumber());
                st.append(SEPARATOR);
                st.append(roomStatus.getCustomerID());
                st.append(SEPARATOR);
                st.append(roomStatus.getStatus());
                st.append(SEPARATOR);
                st.append(roomStatus.getDateFrom());
                st.append(SEPARATOR);
                st.append(roomStatus.getDateTo());
                st.append(SEPARATOR);

                output.add(st.toString());
            }

            try {
                write(DB_PATH, output);

            } catch (Exception ex) {
                System.out.println("Sorry, could not save to database");
            }
        } else {
            System.out.println("Sorry, file not found.");
        }
	}
}