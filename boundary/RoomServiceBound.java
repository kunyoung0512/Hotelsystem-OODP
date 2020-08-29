package hotelsystem.boundary;

import java.util.ArrayList;
import java.util.Scanner;

import hotelsystem.controller.FoodController;
import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomServiceController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.entity.Food;
import hotelsystem.entity.Room;
import hotelsystem.entity.RoomService;
import hotelsystem.entity.RoomStatus;

public class RoomServiceBound {
	private static RoomServiceBound inst = null;
	private Scanner sc;
	
	private RoomServiceBound() {
		sc = new Scanner(System.in);
	}
		
	public static RoomServiceBound getInst() {
		if (inst == null) {
            inst = new RoomServiceBound();
        }
        return inst;
	}
	
	private static void createdFeedback(RoomService rS) {
		System.out.print("RoomService :" + rS.getRoomServiceID() + "/");
		for (Food food : rS.getFoodList()) {
        	System.out.print(food.getfoodName() + " ");
		}
		System.out.print("/" + rS.getDesc() + " has been created.\n");
	}

	public static void updatedFeedback(RoomService rS) {
		System.out.println("RoomService :" + rS.getRoomServiceID() + " " + rS.getRoomStatusID()+ " has been updated.");
		if(rS.getStatus() == true){
			System.out.println("It has been ordered and is on its way");
		}
		else{
			System.out.println("The order has been cancelled");
		}
	}

	public void displayOptions() {
		int choice;
		do {
			System.out.println("--------- ROOM SERVICE MENU ----------");
			System.out.println("1.Create Room Service");
			System.out.println("2.Update Room Service Information");
			System.out.println("0.Back");
			System.out.println("--------------------------------------");
			choice = sc.nextInt();
			switch (choice) {
				case 1:
					createRoomService();
					break;
				case 2:
					updateRoomService();
					break;
				case 0:
					break;
				default:
					System.out.println("I'm sorry, that choice was invalid, please try again.");
					break;
			}
		} while (choice > 0);
	}

	private void createRoomService() {
		sc = new Scanner(System.in);
		double totalPrice = 0;
		Boolean check = true;
		ArrayList<Food> rsList = new ArrayList<>();

		System.out.print("Enter Room No: ");
		String roomNo = sc.next();

		Room rm = RoomController.getInst().getRoom(roomNo);
		if (rm == null) {
			System.out.println("I'm sorry, the room was not found");
			return;
		} else{
			System.out.println("The room's' found!");
		}

		while(check) {
			displayFoodList();
			int foodChoice = sc.nextInt();
			Food f = FoodController.getInst().getFood(foodChoice);
			rsList.add(f);
			System.out.print("Do you wish to add additional food (Y/N) :");
			char confirm = sc.next().charAt(0);
			sc.nextLine();
			if (confirm == 'Y' || confirm == 'y') {
				continue;
			} else {
				check = false;
			}
		};

		System.out.println("Do you wish to add any remarks? (Enter NIL if no)");
		String remarks = sc.nextLine();

		for (Food f : rsList) {
			totalPrice += f.getfoodPrice();
		}
		RoomStatus rS = RoomStatusController.getInst().getStatus(roomNo);
		RoomService roomService = new RoomService(rsList, rS.getRoomBookingsID(), remarks, totalPrice, false);
		RoomServiceController.getInst().addRoomService(roomService);
		createdFeedback(roomService);
	}

	private void updateRoomService() {
		sc = new Scanner(System.in);
		boolean status = false;

		System.out.print("Enter Room No: ");
		String roomNo = sc.next();
		RoomStatus rS = RoomStatusController.getInst().getStatus(roomNo);
		RoomService rService = RoomServiceController.getInst().getRoomService(rS.getRoomBookingsID());
		if (rS != null) {
			System.out.println("Room Found!!");
		}
		System.out.println("------------ ROOM SERVICE DETAILS ------------");
		System.out.println("ROOM NO: " + rS.getRoomFloorNumber());
		System.out.println("FOOD ORDERED: ");
		for (Food food : rService.getFoodList()) {
			System.out.print(food.getfoodName() + " ");
		}
		System.out.println("");
		System.out.println("REMARKS: " + rService.getDesc());
		System.out.println("TOTAL PRICE: " + rService.getTotalPrice());
		System.out.println("STATUS: " + rService.getStatus());
		System.out.println("----------------------------------------------");

		System.out.println("Update Room Service Status");
		System.out.println("1. Not Ordered");
		System.out.println("2. Ordered");
		int choice = sc.nextInt();

		switch (choice) {
			case 1:
				status = false;
				break;
			case 2:
				status = true;
				break;
			case 0:
				break;
		}

		RoomServiceController.getInst().updateRoomService(rS.getRoomBookingsID(), status);
	}

	private void displayFoodList() {
		ArrayList<Food> foodList = FoodController.getInst().getAllFoodList();
		System.out.println("-------------------- FOOD MENU -------------------");
		for (Food f : foodList) {
			System.out.println(f.getfoodID() + "\t" + f.getfoodName() + "\t" + f.getfoodPrice() + "\t"
					+ f.getfoodDescription());
		}
		System.out.println("------------------------------------------------");
		System.out.println("*Please select the food ID you want to add*");
	}
}