package hotelsystem.boundary;

import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomTypeController;
import hotelsystem.entity.Room;
import hotelsystem.entity.RoomType;

public class PrintRoomBound {
	private static PrintRoomBound inst = null;
	private final Scanner sc;
	private Date today = new Date( );
    private Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));

  
    private PrintRoomBound() { 
        sc = new Scanner(System.in);
    }
    

    public static PrintRoomBound getInst() {
        if (inst == null) {
            inst = new PrintRoomBound();
        }
        return inst;
    }
    
  
    public void displayOptions() {
        int choice;
        try {
	        do {
				System.out.println("--------------------------------------");
	            System.out.println("Print Room Status statistic report by:");
	            System.out.println("  1.Room type occupancy rate");
	            System.out.println("  2.Room status");
				System.out.println("  0.Back to previous level");
				System.out.println("--------------------------------------");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                	    printRoomTypeOccupancyRate();
	                	    break;
	                case 2:
	                    	printRoomStatus();
	                    	break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Invalid Choice");
	                    break;
	            }
	        } while (choice > 0);
        }
        catch (InputMismatchException e) {
        	System.out.println("Invaild Input! Please enter again.");
        }
    } 
    
    
 
    private void printRoomStatus() {
	    System.out.println("Vacant:");
	    ArrayList<Room> vacantroomList = RoomController.getInst().getAllRoom(today,tomorrow);
    	System.out.print("		Rooms: ");
    	int i = 0;
    	for (Room printRList : vacantroomList) {
    		if (i<10) {
    			System.out.print(printRList.getRoomFloorNo() + " ");
    			i++;
    		}
    		else {
    			System.out.println(printRList.getRoomFloorNo() + " ");
    			i=0;
    		}
    	}
	    System.out.println();
	    ArrayList<Room> occupiedroomList = RoomController.getInst().getAllRooms();
	    ArrayList<Room> mroomList = RoomController.getInst().getAllMaintenanceRoom();
	    System.out.println("Occupied:");
	    System.out.print("		Rooms:");
	    	for (Room vRList : vacantroomList) {
	    			occupiedroomList.remove(vRList);
	    	}
	    	for (Room mRList : mroomList) {
    			occupiedroomList.remove(mRList);
	    	}
	    
	    
	    for (Room printRList : occupiedroomList) {
    		if (i<10) {
    			System.out.print(printRList.getRoomFloorNo() + " ");
    			i++;
    		}
    		else {
    			System.out.println();
    			i=0;
    		}
    	}
	    System.out.println();
	    System.out.println("Maintenance:");
	    System.out.print("		Rooms:");
	    for (Room printRList : mroomList) {
    		if (i<10) {
    			System.out.print(printRList.getRoomFloorNo() + " ");
    			i++;
    		}
    		else {
    			System.out.println();
    			i=0;
    		}
    	}

	    System.out.println();
	    
	    vacantroomList.clear();
	    occupiedroomList.clear();
	    mroomList.clear();
	}
	
	private void printRoomTypeOccupancyRate() {
    		    
	    ArrayList<RoomType> roomTList = RoomTypeController.getInst().getAllTypes();
	    ArrayList<Room> roomList = RoomController.getInst().getAllRoom(today,tomorrow);
	    ArrayList<Room> vacantRooms = new ArrayList<>();
	    
	    for (RoomType rtList : roomTList) {
	    	for (Room rList : roomList) {
	    		if (rList.getRoomType() == rtList.getTypeID()) {
	    			vacantRooms.add(rList);
	    		}
		    }
	    	int totalRoomNo = RoomController.getInst().getRoomTypeQty(rtList.getTypeID());
	    	
	    	System.out.println(rtList.getRoomType() +" : Number : "+ vacantRooms.size()  +" out of " + totalRoomNo);
	    	System.out.print("           Rooms: ");
	    	for (Room printRList : vacantRooms) {
	    		System.out.print(printRList.getRoomFloorNo() + " ");
	    	}
	    	System.out.println("");
	    	
	    	vacantRooms.clear();
	    }
    }
}