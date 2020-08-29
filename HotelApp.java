package MainUI;
import java.util.InputMismatchException;
import java.util.Scanner;

import hotelsystem.controller.BillPaymentController;
import hotelsystem.controller.CheckInCheckOutController;
import hotelsystem.controller.FoodController;
import hotelsystem.controller.CustomerController;

import hotelsystem.controller.ReservationController;
import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomServiceController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.controller.RoomTypeController;
import hotelsystem.boundary.CheckInBound;
import hotelsystem.boundary.CheckOutBound;
import hotelsystem.boundary.FoodBound;
import hotelsystem.boundary.CustomerBound;
import hotelsystem.boundary.PrintRoomBound;
import hotelsystem.boundary.ReservationBound;
import hotelsystem.boundary.RoomAvailabilityBound;
import hotelsystem.boundary.RoomServiceBound;
import hotelsystem.boundary.RoomBound;


public class HotelApp {
	public static void main(String args[]) {
		
		
		CustomerController.getInst().loadData();
		RoomController.getInst().LoadDB();
		RoomTypeController.getInst().LoadDB();
		RoomStatusController.getInst().LoadDB();
		ReservationController.getInst().loadData();
		CheckInCheckOutController.getInst().loadData();
		RoomServiceController.getInst().loadData();
		FoodController.getInst().LoadDB();
		BillPaymentController.getInst().loadData();
		
		int choice;
		Scanner sc = new Scanner(System.in);
		try {
		do {
			System.out.println("----------------------MAIN SYSTEM MENU-----------------------\n"
							 + " 1. Create/Update Room Details                               \n"
							 + " 2. Create/Update/Cancel/Print Reservation                   \n"
							 + " 3. Create/Update/Search Customers Details                   \n"
							 + " 4. Enter Room Service Orders                                \n"
							 + " 5. Create/Update/Remove Room Service Menu Items             \n"
							 + " 6. Check Room Availability                                  \n"
							 + " 7. Room Check-In                                            \n"
							 + " 8. Room Check-Out & Print Bill Invoice                      \n"
							 + " 9. Print Room Status Statistic report                       \n"
							 + "-------------------------------------------------------------\n");
			
			choice = sc.nextInt();
			sc.nextLine();
				switch(choice) {
					case 1:	RoomBound.getInst().displayOptions();
							break;
					case 2: ReservationBound.getInst().displayOptions();
							break;
					case 3: CustomerBound.getInst().displayOptions();
							break;
					case 4: RoomServiceBound.getInst().displayOptions();
							break;
					case 5: FoodBound.getInst().displayOptions();	
							break;
					case 6: RoomAvailabilityBound.getInst().displayOptions();
							break;
					case 7: CheckInBound.getInst().displayOptions();
							break;
					case 8: CheckOutBound.getInst().displayOptions();
							break;
					case 9: PrintRoomBound.getInst().displayOptions();
							break;
				}
			}while(choice > 0 && choice <= 10);
	    }
		
		catch (InputMismatchException e) {
			sc.close();
        	System.out.println("Invaild Input! Please re-run program.");
        	return;
        }
	
		CustomerController.getInst().storeData();
		RoomController.getInst().SaveDB();
		RoomTypeController.getInst().SaveDB();
		RoomStatusController.getInst().SaveDB();
		ReservationController.getInst().storeData();
		CheckInCheckOutController.getInst().storeData();
		FoodController.getInst().SaveDB();
		RoomServiceController.getInst().storeData();
		BillPaymentController.getInst().storeData();
		sc.close();
	}
}