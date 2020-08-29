package hotelsystem.boundary;

import java.util.ArrayList;
import java.util.Scanner;

import hotelsystem.controller.CheckInCheckOutController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.entity.CheckInCheckOut;
import hotelsystem.entity.Customer;
import hotelsystem.entity.RoomStatus;


public class CheckOutBound {  
	private static CheckOutBound inst = null;  
    private Scanner sc;

    private CheckOutBound() {
        sc = new Scanner(System.in);
    }
    
    public static CheckOutBound getInst() {                   
        if (inst == null) { 
            inst = new CheckOutBound();
        }
        return inst;
    }
	
	public void displayOptions() {
        int choice;
        Customer g = null;
        String gName;
        sc = new Scanner(System.in);
        while (g==null){
	        System.out.println("Enter the relevant Customer's name: ");
	        gName = sc.nextLine();
	        g = CustomerBound.getInst().searchCustomer(gName);  
        }
        
        
        CheckInCheckOut c = CheckInCheckOutController.getInst().getCustomerChkOut(g.getID_customer());
        if (c==null) {
        	System.out.println("I'm sorry but that Customer is not in our database.");
        }
        else {
        	System.out.println(c.getRm_stat().size() + " Rooms Found");
        	System.out.println("Room Booking ID	    Room Number	Status		Checked-In Date		Check Out Date");
        	ArrayList<RoomStatus> rSlist = c.getRm_stat();
        	for (RoomStatus r : rSlist) {
        		System.out.println(r.getRoomBookingsID() +"		  "+ r.getRoomFloorNumber() +"      "+ r.getStatus() +"	 "+ r.getDateFrom() +" "+ r.getDateTo());
             }
        	
        	do {
			System.out.println("---------------------------------------------");
        	System.out.println("Select an option: ");
        	System.out.println("1. Check out selected rooms");
			System.out.println("2. Check out all rooms and print bill invoice");
			System.out.println("---------------------------------------------");
        	choice = sc.nextInt();
            sc.nextLine();
	            switch (choice) {
	                case 1:
	                	checkOutSomeRooms(c);  
	                	return;
	                case 2:
	                    checkOutAll(c);
	                    return;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Invalid choice, please try again");
	                    break;
	            }
        	} while (choice > 0);
        }
    }
	

	private void checkOutAll(CheckInCheckOut c) {
		ArrayList<RoomStatus> updatedList = new ArrayList<RoomStatus>();
		
		for (RoomStatus roomStatus : c.getRm_stat()) {
			if(!roomStatus.getStatus().equals("Checked-In")) {
        		updatedList.add(roomStatus);
        	}
			else {
				RoomStatus rS = new RoomStatus(roomStatus.getRoomBookingsID(), roomStatus.getCustomerID(),roomStatus.getRoomFloorNumber() , roomStatus.getDateFrom(), roomStatus.getDateTo(), "Checked-Out");
				RoomStatusController.getInst().updateStatustoCheckedOut(rS);
				updatedList.add(rS);
			}
        }
		c.setRm_stat(updatedList);
		c.setStatus("Checked-Out");
		CheckInCheckOutController.getInst().updatedetails(c);
		System.out.println("All rooms have been checked-out. Thank you for your patience. Printing invoice");
		BillPaymentBound.getInst().generateBill(c);
	}
	
	private void checkOutSomeRooms(CheckInCheckOut c) {
		ArrayList<RoomStatus> updatedList = new ArrayList<RoomStatus>();
		System.out.println("Please select a room number(xx-xx): ");
        String roomNo = sc.next();
        
        for (RoomStatus roomStatus : c.getRm_stat()) {
        	if(!roomStatus.getRoomFloorNumber().equals(roomNo)) {
        		updatedList.add(roomStatus);
        	}
        	else{
        		RoomStatus rS = new RoomStatus(roomStatus.getRoomBookingsID(), roomStatus.getCustomerID(),roomStatus.getRoomFloorNumber(), roomStatus.getDateFrom(), roomStatus.getDateTo(), "Checked-Out");
            	RoomStatusController.getInst().updateStatustoCheckedOut(rS);
            	updatedList.add(rS);
        	} 
        }
        c.setRm_stat(updatedList);
		System.out.println("Room Number: " + roomNo + " has been checked-out successfully.");
		boolean check = CheckInCheckOutController.getInst().getCustomerFullChkOut(c.getCustomer().getID_customer());
		if(!check)  {
			c.setStatus("Checked-Out");
			CheckInCheckOutController.getInst().updatedetails(c);
			System.out.println("All rooms have been checked-out. Thank you for your patience. Printing invoice");
			BillPaymentBound.getInst().generateBill(c);
		}
		
		else{
			CheckInCheckOutController.getInst().updatedetails(c);
	}
	}
}