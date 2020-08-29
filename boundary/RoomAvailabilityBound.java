package hotelsystem.boundary;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import hotelsystem.controller.RoomController;
import hotelsystem.entity.Room;

public class RoomAvailabilityBound {
	private static RoomAvailabilityBound inst = null;
	private Date today = new Date();
    private Date tomorrow = new Date(today.getTime() + 86400000);
    private Scanner sc;
    
    private RoomAvailabilityBound() {
        sc = new Scanner(System.in);
    }
    
    public static RoomAvailabilityBound getInst() {
        if (inst == null) {
            inst = new RoomAvailabilityBound();
        }
        return inst;
    }
    
    public void displayOptions() {
    	ArrayList<Room> matchingRoomList = getAllCurrentVacantRooms();
    	int choice;
    	boolean wifi = false;
    	boolean view = false;
    	boolean noSmoke = false;
    	boolean smoke = false;	
    	boolean bedtype = false;
        do {
        	System.out.println("-------- ROOM AVAILABILITY MENU --------\n"+
        			"Add filter:");
        	choice = sc.nextInt();
        	if(!wifi) {System.out.println("1.WIFI");}
            if(!view) {System.out.println("2.View");}
            if(!smoke) {System.out.println("3.Smoking");}
            if(!noSmoke) {System.out.println("4.No-smoking");}
            if(!bedtype) {System.out.println("5.Bedtype");}
            System.out.println("6.Clear all filters\n"+
            		"0.Back to previous level\n"+
            		"----------------------------------------");
            
            switch (choice) {
            	case 1:
                	    matchingRoomList = WIFIFilter(matchingRoomList);
                	    wifi = true;
                	    printRooms(matchingRoomList);
                		break;
                case 2:
                	    matchingRoomList = viewFilter(matchingRoomList);
                	    view = true;
            	        printRooms(matchingRoomList);   
                    	break;
                case 3:
                  		matchingRoomList = smokingFilter(matchingRoomList);
                  		smoke = true;
            	        printRooms(matchingRoomList);
            	        break;
                case 4: 
                 		matchingRoomList = noSmokingFilter(matchingRoomList);
        	            printRooms(matchingRoomList);
        	            break;
                case 5:
                  		matchingRoomList = bedtypeFilter(matchingRoomList);
                  		noSmoke = true;
        	            printRooms(matchingRoomList);        
        	            break;
                case 6:
	                	wifi = false;
	                	view = false;
	                	smoke = false;
	                	noSmoke = false;
	                	bedtype = false;
                	    matchingRoomList = getAllCurrentVacantRooms();
                	    System.out.println("You filters have been refreshed.");
                	    break;
                case 0:
                    	break;
                default:
                    	System.out.println("Invalid input");
                    	break;
            }
        } while (choice > 0);
    }
    
    private ArrayList<Room> getAllCurrentVacantRooms() {
	    	ArrayList<Room> vacantList = RoomController.getInst().getAllRoom(today,tomorrow);
	    	return vacantList;
    }
    
    private void printRooms(ArrayList<Room> rList) {
    		System.out.println("-------------------- ROOM AVAILABILITY --------------------");
    	    if (rList.isEmpty()) {
	    	    System.out.println("There are no rooms matching your search.\n"+
	    	    		"-----------------------------------------------------------");
    	    }
    	    else {
        	    	System.out.println("Shwoing available rooms matching your search:");
        	     	char floor = rList.get(1).getRoomFloorNo().charAt(1);
        	     	System.out.print("Floor " + floor + ": ");
        	       	for (Room r : rList) {
        	       		if (r.getRoomFloorNo().charAt(1) >floor){
        	       			floor = r.getRoomFloorNo().charAt(1);
        	       			System.out.println();
        	       			System.out.print("Floor " + floor + ": ");
        	       		} 
        	       		System.out.print(r.getRoomFloorNo() + ", ");
        	       		
        	       	}
        	       	System.out.println();
        	       	System.out.println("-----------------------------------------------------------\n");
        	       	System.out.println("Add more filters or clear filters to start a new search.\n");
    	    }
    }
    
    private ArrayList<Room> WIFIFilter(ArrayList<Room> rList) {
      	ArrayList<Room> filterRList = new ArrayList<Room>();
      	for (Room r : rList) {
       		if (r.isWifi()!=false) {
       			filterRList.add(r);
       		}
       	}
    	    return filterRList;
    }
    
    private ArrayList<Room> viewFilter(ArrayList<Room> rList) {
      	ArrayList<Room> filterRList = new ArrayList<Room>();
      	for (Room r : rList) {
       		if (r.isView()!=false) {
       			filterRList.add(r);
       		}
       	}
    	    return filterRList;
    }
    
    private ArrayList<Room> noSmokingFilter(ArrayList<Room> rList) {
      	ArrayList<Room> filterRList = new ArrayList<Room>();
      	for (Room r : rList) {
       		if (r.isSmoking()!=true) {
       			filterRList.add(r);
       		}
       	}
    	    return filterRList;
    }
    
    private ArrayList<Room> smokingFilter(ArrayList<Room> rList) {
      	ArrayList<Room> filterRList = new ArrayList<Room>();
      	for (Room r : rList) {
       		if (r.isSmoking()!=false) {
       			filterRList.add(r);
       		}
       	}
    	    return filterRList;
    }
    
    
    private ArrayList<Room> bedtypeFilter(ArrayList<Room> rList) {
    	   
    	System.out.println("Select your bed type.\n"+
    			"1.Single\n"+
    			"2.Double\n"+
    			"3.Queen\n"+
    			"4.King\n"+
    			"0.Back to previous level");
	
    	String bedtype = null;
      	ArrayList<Room> filterRList = new ArrayList<Room>();
      	int choice = sc.nextInt();
      	
      	switch (choice) {
        case 1:
        	    bedtype = "Single";
        		break;
        case 2:
          	bedtype = "Double";
            	break;
        case 3:
         	bedtype = "Queen";
         	break;
        case 4:
         	bedtype = "Single";
         	break;
        case 0:
            	return rList;
        default:
            	System.out.println("Invalid input! Please insert again.");
            	break;
    }
        
      	for (Room r : rList) {
       		if (r.getBedType().equals(bedtype)) {
       			filterRList.add(r);
       		}
       	}
    	    return filterRList;
    }
}
    