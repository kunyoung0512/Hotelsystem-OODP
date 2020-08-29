package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.RoomService;
import hotelsystem.boundary.RoomServiceBound;


@SuppressWarnings("serial")
public class RoomServiceController implements Serializable{
	
	private static RoomServiceController inst = null;
	
	private final ArrayList<RoomService> roomServiceList;
	
	

	private RoomServiceController() {
		roomServiceList = new ArrayList<>();
    }
	

	public static RoomServiceController getInst() {
        if (inst == null) {
            inst = new RoomServiceController();
        }
        return inst;
    }
	
	
	public void addRoomService(RoomService roomService) {
		roomServiceList.add(roomService);
		storeData();
	}
	

	public RoomService getRoomService(int roomStatusID) {
		for (RoomService rS : roomServiceList) {
            if (rS.getRoomStatusID() ==  roomStatusID)
                return rS;
        }
        return null;
	}
	
	public ArrayList<RoomService> getRSList(int roomStatusID) {
    	ArrayList<RoomService> result = new ArrayList<>();
    	for (RoomService rS : roomServiceList) {
    		if (rS.getRoomStatusID() == roomStatusID) {
    			result.add(rS);
            }
        }
    	return result;
    }
	

	public void updateRoomService(int roomStatusID, boolean status) {
		RoomService rs = getRoomService(roomStatusID);
		rs.setStatus(status);
		RoomServiceBound.updatedFeedback(rs);
		storeData();
	}
	

	
public void loadData () {
        
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/RoomService.ser"));

            int noOfOrdRecords = ois.readInt();
            RoomService.setincrementID(ois.readInt());
            System.out.println("RoomServiceController: " + noOfOrdRecords + " entries loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
            	roomServiceList.add((RoomService) ois.readObject());
                
            }
        } catch (IOException | ClassNotFoundException e1) {
           
            e1.printStackTrace();
        }
    }
	

	public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/RoomService.ser"));
            out.writeInt(roomServiceList.size());
            out.writeInt(RoomService.getincrementID());
            for (RoomService roomService : roomServiceList)
                out.writeObject(roomService);
           
            out.close();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
    
	
    
}