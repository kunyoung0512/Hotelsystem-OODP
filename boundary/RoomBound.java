package hotelsystem.boundary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.controller.RoomTypeController;
import hotelsystem.entity.Room;
import hotelsystem.entity.RoomStatus;
import hotelsystem.entity.RoomType;


public class RoomBound {
	private static RoomBound inst = null;
    private Scanner sc;

    private RoomBound() {
        sc = new Scanner(System.in);
    }
    
    public static RoomBound getInst() {
        if (inst == null) {   
            inst = new RoomBound();
        }
        return inst; 
    }   
    
    public void displayOptions() {
        int choice;
        do {
        	System.out.println("----------- ROOM MENU -----------");
			System.out.println("1.Create new room");
			System.out.println("2.Create new room type");
			System.out.println("3.Update details of a room");
			System.out.println("4.Update room type details");
            System.out.println("0.Back");
            System.out.println("---------------------------------");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    	createRoomDetails();
                		break;
                case 2:
						createRoomType();
						break;
				case 3:
						updateRoomDetails();
						break;
				case 4:
						updateRoomTypeDetails();
						break;
                case 0:
                    	break;
                default:
                    	System.out.println("Invalid input");
                    	break;
            }
        } while (choice > 0);
    }
    
    private void createRoomDetails() {
        sc = new Scanner(System.in);
        String roomFloorNo;
        int typeID;
        String bedType;
        boolean wifi;
        String wifiS;
        boolean smoking;
        String smokeS;
        boolean view;
		String viewS;
        
        System.out.print("Enter Room Floor(xx-xx): ");
        roomFloorNo = sc.next();
        
		RoomTypeController.getInst().displayAllRoomType();

        System.out.print("Enter relevant room type ID: ");
        typeID = sc.nextInt();
        
        System.out.print("Bed Type: ");
        bedType = sc.next();
        
        System.out.print("Wifi (Y/N): ");
        wifiS = sc.next();
        if(wifiS.equals("Y") || wifiS.equals("y"))
        	wifi = true;
        else
        	wifi = false;

        System.out.print("Smoking Room (Y/N): ");
        smokeS = sc.next();
        if(smokeS.equals("Y") || smokeS.equals("y"))
        	smoking = true;
        else
        	smoking = false;

        System.out.print("View (Y/N): ");
        viewS = sc.next();
        if(viewS.equals("Y") || viewS.equals("y"))
        	view = true;
        else
        	view = false;

		Room room = new Room(roomFloorNo, typeID, bedType, wifi, smoking, view);
        System.out.println("Room :" + room.getRoomFloorNo() +  " has been CREATED.");
		RoomController.getInst().addRoom(room);
    }
	
	private void createRoomType() {
		String roomType;
		double weekDyrt;
		double weekEdrt;
		int typeID;

		System.out.print("Enter new room type ID: ");
		typeID = sc.nextInt();
		
		System.out.print("Enter room type: ");
		roomType = sc.next();

		System.out.print("Weekday rate?: ");
		weekDyrt = sc.nextDouble();

		System.out.print("Weekend rate?: ");
		weekEdrt = sc.nextDouble();

		RoomType rT2 = new RoomType(typeID, roomType,weekDyrt,weekEdrt);
		RoomTypeController.getInst().addRoomType(rT2);
	}

	private void updateRoomDetails() {
    	sc = new Scanner(System.in);
    	String roomFloorNo;
    	
    	System.out.print("Enter Room No:");
    	roomFloorNo = sc.next();
    	
		Room room = RoomController.getInst().getRoom(roomFloorNo);
    	
    	if(room != null) {
    		ArrayList<RoomStatus> roomStatusList = new ArrayList<>();
    		String bedType = room.getBedType();
    		boolean wifi = room.isWifi();
        	boolean smoke = room.isSmoking();
			boolean view = room.isView();
			int typeID2 = 0;
			

    		System.out.println("Room " + room.getRoomFloorNo() + " found! Which detail do you want to update?");
    		System.out.println("1. Bed Type\n"
    						+  "2. Wifi\n"
    						+  "3. Smoking Room\n"
    						+  "4. View\n"
							+  "5. Room Status\n"
							+  "6. Room Type");
    		int choice = sc.nextInt();
    		switch(choice) {
    			case 1: System.out.println("Please Choose Bed Type.\n"
    									+  "1. Single\n"
    									+  "2. Double\n"
    									+  "3. Queen\n"
    									+  "4. King\n"
    									+  "0. Back");
    					int bedTypeId = sc.nextInt();
    					switch(bedTypeId) {
    						case 1: bedType = "Single";
    								break;
    						case 2: bedType = "Double";
    								break;
    						case 3: bedType = "Queen";
    								break;
    						case 4: bedType = "King";
    								break;
    						case 0:	break;
    					}
    					break;
    					
    			case 2:	System.out.print("Wifi (Y/N): ");
    					char wifiS = sc.next().charAt(0);
    					if(wifiS=='Y' || wifiS =='y')
    						wifi = true;
    					else
    						wifi = false;
    					break;
    			
    			case 3:	System.out.print("Smoking Room (Y/N): ");
    					String smokeS = sc.next();
						if(smokeS.equals("Y") || smokeS.equals("y"))
							smoke = true;
						else
							smoke = false;
    					break;
    					
    			case 4:	System.out.print("View (Y/N): ");
						String viewS = sc.next();
						if(viewS.equals("Y") || viewS.equals("y")) 
							view = true;
						else 
							view = false;
    					break;
    					
    			case 5:	roomStatusList = RoomStatusController.getInst().getRoomStatus(roomFloorNo);
			        	if(roomStatusList != null) {
			        		System.out.println("Room Status ID	Status	Start Date	End Date");
			        		for(RoomStatus roomStatus : roomStatusList){
			        			System.out.println(roomStatus.getRoomBookingsID() +"		"+ roomStatus.getStatus() +"		"+ roomStatus.getDateFrom() +"		"+ roomStatus.getDateTo());
			        		}
			        	}
			        	else {
			        		System.out.println("Room is Vacant");
			        	}
						System.out.println("Select.\n"
						+  "1. Add New Status\n"
						+  "0. Back");
						SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
						int status2Id = sc.nextInt();
						String dateFrom;
				        String dateTo;
				        Date startDate = null;
				        Date endDate = null;
						switch(status2Id) {
							case 1: String statusName = null;
									System.out.println("Please Choose Status.\n"
												+  "1. Under-Maintenance\n"
												+  "0. Back");
							        int status1 = sc.nextInt();
							        switch(status1) {
								        case 1: statusName = "Under-Maintenance";
										        System.out.print("Enter Date From (Format - dd/MM/yy): ");
										        dateFrom = sc.next();
										        System.out.print("Enter Date To (Format - dd/MM/yy): ");
										        dateTo = sc.next();
										        try {
										            startDate = df.parse(dateFrom + " 12:00");
										        } catch (ParseException e) {
										            e.printStackTrace();
										        }
										        try {
										            endDate = df.parse(dateTo + " 12:00");
										        } catch (ParseException e) {
										            e.printStackTrace();
										        }
										        RoomStatus roomS = new RoomStatus(roomFloorNo,0,endDate,startDate,statusName);
										        RoomStatusController.getInst().addRoomStatus(roomS);
								        case 0:	break;
							        }
							case 0: break;
						} break;
							
				case 6: RoomTypeController.getInst().displayAllRoomType();
						System.out.print("Choose new room type ID: ");
						typeID2 = sc.nextInt();
						break;
				
    		}
			RoomController.getInst().updateRoom(room.getRoomFloorNo(), typeID2, bedType, wifi, smoke, view);
    	}else {
    		System.out.println("Room does not exist. Please enter an available room");
    	}
	}
	
	public void updateRoomTypeDetails() {
		Double wkDayRate;
		Double wkEndRate;
		int t;
		Boolean chk = false;
		RoomType rT = null;
		do{
			RoomTypeController.getInst().displayAllRoomType();

			System.out.print("Enter room type ID you wish to edit: ");
		
			t = sc.nextInt();
		
			rT = RoomTypeController.getInst().getRoomType(t);
			if(rT == null)
			{
				System.out.print("Sorry that room type does not exist, please choose again!");
			}
			else {
				chk = true;
			}

		}while(!chk);
		System.out.println("Please enter Weekday Price: ");
		wkDayRate = sc.nextDouble();

		System.out.println("Please enter Weekend Price: ");
		wkEndRate = sc.nextDouble();

		RoomTypeController.getInst().updateRoomType(rT.getTypeID(),rT.getRoomType(), wkDayRate, wkEndRate);
		System.out.println("New room pricing updated for type: " + rT.getRoomType() + "!"); 		
		
	}
}