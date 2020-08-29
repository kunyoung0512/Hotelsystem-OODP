package hotelsystem.controller;

import java.io.*;
import java.util.ArrayList;

import hotelsystem.entity.CheckInCheckOut;
import hotelsystem.entity.Customer;
import hotelsystem.entity.RoomStatus;

@SuppressWarnings("serial")
public class CheckInCheckOutController implements Serializable{
	private static CheckInCheckOutController inst = null;
	private final ArrayList<CheckInCheckOut> checkInList = new ArrayList<>();
	
	CheckInCheckOutController() {}
	public static CheckInCheckOutController getInst() {
        if (inst == null) {
            inst = new CheckInCheckOutController();
        }
        return inst;
    }
	

	public void updatedetails(CheckInCheckOut c) {
		checkInList.remove(c);
		checkInList.add(c);
        storeData();
    }
	
 CheckInCheckOut getCustomer(int id) {
        for (CheckInCheckOut c : checkInList) {
            if (c.getID_CICO() == id)
                return c;
        }
        return null;
    }
 
 	public CheckInCheckOut getCustomerChkOut(int id) {
 		for (CheckInCheckOut c2 : checkInList) {
 			if (c2.getCustomer().getID_customer() == id) {
 				if(c2.getStatus().equals("Checked-In")) {
 					return c2; }
 			}	
 		}
 		return null;
 	}
	
	public boolean getCustomerFullChkOut(int ID) {
		boolean check = false;
        for (CheckInCheckOut c2 : checkInList) {
            if (c2.getCustomer().getID_customer() == ID) {
            	ArrayList<RoomStatus> rList = c2.getRm_stat();
            	for (RoomStatus rS : rList) {
            		if(rS.getStatus().equals("Checked-In")) {
            			check = true;
            			break;
            		}
            		else {
            			check = false;
            		}
            	}
            }
        }
        return check;
    }
	
	 public void loadData () {
	        ObjectInputStream objinpstr;
	        try {
	        	objinpstr = new ObjectInputStream(new FileInputStream("DB/CheckInCheckOut.ser"));

	            int no_Records = objinpstr.readInt();
	            Customer.setID_Max(objinpstr.readInt());
	            System.out.println("CheckInController: " + no_Records + " Entries Loaded");
	            for (int i = 0; i < no_Records; i++) {
	                checkInList.add((CheckInCheckOut) objinpstr.readObject());
	            }
	        } catch (IOException | ClassNotFoundException e1) {
	            e1.printStackTrace();
	        }
	    }
	    
	    public void storeData() {
	        try {
	            ObjectOutputStream objoutstr = new ObjectOutputStream(new FileOutputStream("DB/CheckInCheckOut.ser"));
	            objoutstr.writeInt(checkInList.size());
	            objoutstr.writeInt(CheckInCheckOut.getID_max());
	            for (CheckInCheckOut c : checkInList)
	            	objoutstr.writeObject(c);
	            objoutstr.close();
	        } catch (IOException io) {
	            io.printStackTrace();
	        }
	    }
	
    public void deleteCheckIn(CheckInCheckOut c)  {
    	checkInList.remove(c);
        storeData();
    }

    public void insertCheckIn(CheckInCheckOut c) {
    	checkInList.add(c);
        storeData();
    }
    
    

    

}