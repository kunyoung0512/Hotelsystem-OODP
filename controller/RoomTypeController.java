package hotelsystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import hotelsystem.entity.RoomType;

public class RoomTypeController extends DatabaseController{
	private static final String DB_PATH = "DB/RoomType.dat";
	private static RoomTypeController inst = null;
	private final ArrayList<RoomType> roomTypeList;
	
	private RoomTypeController() { 
		roomTypeList = new ArrayList<>();
    }
	
	public static RoomTypeController getInst() {
        if (inst == null) {
            inst = new RoomTypeController();
        }
        return inst;
    }
	
	public ArrayList<RoomType> getAllTypes() {
		ArrayList<RoomType> rTList = new ArrayList<>();
		for(RoomType rT : roomTypeList) {
			rTList.add(rT);
		}
		return rTList;
	}

	public RoomType getRoomType(int roomTypeID) {
		for (RoomType rT : roomTypeList) {
            if (rT.getTypeID()== roomTypeID)
                return rT;
        }
        return null;
	}
	
	public int getAllRoomType() {
		System.out.println("------------------------ Room Types ------------------------");
		System.out.println(String.format("%5s %15s %8s %18s", "RoomTypeID", "RoomType    ", "WeekdayPrice($)", "WeekendPrice($)"));
        for (RoomType rT : roomTypeList) {
             System.out.println(String.format("%5s %18s %10s %20s",rT.getTypeID(),rT.getRoomType(),rT.getWeekDayRate(),rT.getWeekEndRate()));
        }
        System.out.println("-----------------------------------------------------------------");
        return roomTypeList.size();
    }
	
	public void displayAllRoomType() {
		System.out.println("----------------------------- Room Types ------------------------");
		System.out.println(String.format("%5s %15s %8s %18s", "RoomTypeID", "RoomType    ", "WeekdayPrice($)", "WeekendPrice($)"));
        for (RoomType rT : roomTypeList) {
             System.out.println(String.format("%5s %18s %10s %20s",rT.getTypeID(),rT.getRoomType(),rT.getWeekDayRate(),rT.getWeekEndRate()));
        }
        System.out.println("-----------------------------------------------------------------");
    }
	
	@Override
	public void SaveDB() {
		List<String> output = new ArrayList<>();
        StringBuilder st = new StringBuilder();
        if (checkFileExist(DB_PATH)) {
            for (RoomType rT : roomTypeList) {
                st.setLength(0);
                st.append(rT.getTypeID());
                st.append(SEPARATOR);
                st.append(rT.getRoomType());
                st.append(SEPARATOR);
                st.append(rT.getWeekDayRate()); 	
                st.append(SEPARATOR);
                st.append(rT.getWeekEndRate()); 		
                st.append(SEPARATOR);

                output.add(st.toString());
            }

            try {
                write(DB_PATH, output);

            } catch (Exception ex) {
                System.out.println("ERROR. Write Error.");
            }
        } else {
            System.out.println("ERROR. File not found.");
        }
    }
    
    public void addRoomType(RoomType rT) {
        roomTypeList.add(rT);
        SaveDB();
    }

    public void updateRoomType (int typeID, String roomType, double weekdayRate, double weekendRate) {
        RoomType rt = getRoomType(typeID);
        rt.setWeekDayRate(weekdayRate);
        rt.setWeekEndRate(weekendRate);
        rt.setRoomType(roomType);
        System.out.println("The roomtype of id "+ rt.getTypeID()+" has been updated successfully");
        SaveDB();
    }
	
	
	@Override
	public boolean LoadDB() {
		roomTypeList.clear();
        if (checkFileExist(DB_PATH)) {
            try {
                ArrayList<String> stringArray = (ArrayList<String>) read(DB_PATH);

                for (String st : stringArray) {
                    StringTokenizer token = new StringTokenizer(st, SEPARATOR);
                    int id = Integer.parseInt(token.nextToken().trim());                  
                    String roomType = token.nextToken().trim();
                    double weekdayRate = Double.parseDouble(token.nextToken().trim());
                    double weekendRate = Double.parseDouble(token.nextToken().trim());

                    RoomType rT = new RoomType(id, roomType, weekdayRate, weekendRate);
                    roomTypeList.add(rT);
                }

                System.out.printf("RoomTypeController: %,d Entries Loaded.\n", roomTypeList.size());
                return true;

            } catch (IOException | NumberFormatException e) {
                System.out.println("Sorry, the RoomType Database has not been loaded.");
                return false;
            }

        } else {
            System.out.println("ERROR. RoomType Database has not been loaded.");
            return false;
        }
	}

	
}