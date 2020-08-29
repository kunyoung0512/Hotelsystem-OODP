package hotelsystem.boundary;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import hotelsystem.controller.ReservationController;
import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.controller.RoomTypeController;
import hotelsystem.entity.Customer;
import hotelsystem.entity.Reservation;
import hotelsystem.entity.Room;
import hotelsystem.entity.RoomStatus;
import hotelsystem.entity.RoomType;

public class ReservationBound {
	private static ReservationBound inst = null;
	private Scanner sc;

	private ReservationBound() {
		sc = new Scanner(System.in);
	}
	

	public static ReservationBound getInst() {
		if (inst == null) {
            inst = new ReservationBound();
        }
        return inst;
	}
	
	private static void createdFeedback(Reservation r) {
		System.out.print("Reservation :"  + r.getReservationCode()  + " has been CREATED.\n");  
    }

	private static void updatedFeedback(Reservation reservation) {
    	System.out.println("Reservation :" + reservation.getReservationCode() +  " has been UPDATED.");  
    }
	

	public void displayOptions() {
		ReservationController.getInst().checkExpiredRoom();
        int choice;
        do {
        	System.out.println("--------- RESERVATION MENU ---------");
            System.out.println("1. Create Reservation");
            System.out.println("2. Update Reservation Details");
            System.out.println("3. Remove Reservations");
            System.out.println("4. Print Reservation");
            System.out.println("0. Back");
            System.out.println("------------------------------------");  
            System.out.print("Pick a choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1: createReservationDetails();
                		break;
                case 2: retrieveUpdateReservation();
                    	break;
                case 3: removeReservation();
                    	break;
                case 4: printReservationMenu();
                		break;
                case 0: break;
                default:System.out.println("Invalid Choice");
                    	break;
            }
        } while (choice > 0);
    }
	
	private void createReservationDetails() {
        sc = new Scanner(System.in);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");    
           
    	Customer Customer = null; 						
    	ArrayList<RoomStatus> statusList = new ArrayList<RoomStatus>();
    	Boolean addRoomB = null;
        ArrayList<String> roomNoList = new ArrayList<>();
        String roomNo = null;
    	int roomType;
    	String dateFrom;
        String dateTo;
        Date startDate = null;
        Date endDate = null;
    	int noChild;									
    	int noAdult;									
    	String status = "Reserved";		
    	String CustomerName;  
    	String wifi, smoke, view;                           

    	System.out.println("1. Existing Customer\n"                       
    					+  "2. New Customer");
    	System.out.print("Are you a/an ? ");
    	int choiceCustomer = sc.nextInt();
    	switch(choiceCustomer) {
    		case 1:	do {
				        System.out.println("Enter Customer Name: ");
				        CustomerName = sc.next();
				        Customer = CustomerBound.getInst().searchCustomer(CustomerName);   
			        	}
			        while (Customer==null);
    				break;
    		case 2:	Customer = CustomerBound.getInst().createnewCustomer();
    				if(Customer == null) {
    					
    				}
    				break;
    	}
    	System.out.print("Enter the Number of Adults: ");
        noAdult = sc.nextInt();
        System.out.print("Enter the Number of Childrens: ");
        noChild = sc.nextInt();
        System.out.print("Enter the Date From (dd/MM/yyyy): ");
        dateFrom = sc.next();
        System.out.print("Enter the Date To (dd/MM/yyyy): ");
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
        
        do {
            RoomTypeController.getInst().displayAllRoomType();
            System.out.print("Which room type would you want? Please input the corresponding number");
            roomType = sc.nextInt();
            RoomTypeController.getInst().getRoomType(roomType);

			ArrayList<Room> tempRoomList = new ArrayList<>();
			tempRoomList = checkExisting(startDate, endDate, roomType, roomNoList);

			if (tempRoomList != null) {
				System.out.print("Do you wish to filter the list? (Y/N) :");
				char filterChoice = sc.next().charAt(0);
				if (filterChoice == 'Y' || filterChoice == 'y') {
					ArrayList<Room> filterList = filterRoom(tempRoomList, roomNoList, roomType);
					System.out.println("------------------------ ROOMS AVAILABLE ------------------------");
					System.out.println(String.format("%5s %15s %8s %15s %15s", "Room No", "Room Type", "Wifi",
							"Smoking Room", "Window View"));
					if (!filterList.isEmpty()) {
						for (Room room : filterList) {
							if (!room.getRoomFloorNo().equals(roomNo)) {
								RoomType rT = RoomTypeController.getInst().getRoomType(room.getRoomType());
								if (room.isWifi() == true) {
									wifi = "Yes";
								} else {
									wifi = "No";
								}
								if (room.isSmoking() == true) {
									smoke = "Yes";
								} else {
									smoke = "No";
								}
								if (room.isView() == true) {
									view = "Yes";
								} else {
									view = "No";
								}
								System.out.println(String.format("%5s %15s %9s %12s %14s", room.getRoomFloorNo(),
										rT.getRoomType(), wifi, smoke, view));
							}
						}
					} else {
						System.out.println("No Available Rooms for for your chosen dates");
						System.out.println("-----------------------------------------------------------------");
						return;
					}
					System.out.println("-----------------------------------------------------------------");
				} else if (filterChoice == 'N' || filterChoice == 'n') {
				} else {
					System.out.println("Invalid Input!");
				}

				System.out.print("Please Select a Room No(xx-xx): ");
				roomNo = sc.next();
				roomNoList.add(roomNo);

				RoomStatus roomStatus = new RoomStatus(roomNo, Customer.getID_customer(), endDate, startDate, "Reserved");
				statusList.add(roomStatus);

				System.out.print("Do you wish to add additional rooms? (Y/N): ");
				char addRoom = sc.next().charAt(0);
				if (addRoom == 'Y' || addRoom == 'y')
					addRoomB = true;
				else if (addRoom == 'N' || addRoom == 'n')
					addRoomB = false;
			}
		} while (addRoomB);

		if (statusList != null) {
			double total = 0;
			SimpleDateFormat dfS = new SimpleDateFormat("EEE");
			for (RoomStatus rS : statusList) {
				int days = (int) ((rS.getDateTo().getTime() - rS.getDateTo().getTime()) / (1000 * 60 * 60 * 24) - 1);
				Date dateCheck = rS.getDateTo();
				for (int i = 0; i <= days; i++) {
					Room rm = RoomController.getInst().getRoom(rS.getRoomFloorNumber());
					RoomType rT = RoomTypeController.getInst().getRoomType(rm.getRoomType());
					if (dateCheck.equals(rS.getDateTo()) || dateCheck.equals(rS.getDateTo())
							|| (dateCheck.after(rS.getDateTo()) && dateCheck.before(rS.getDateTo()))) {
						String dateCheckS = dfS.format(dateCheck);
						if (dateCheckS.equals("Sat") || dateCheckS.equals("Sun"))
							total += rT.getWeekEndRate();
						else
							total += rT.getWeekDayRate();
					}
					dateCheck = new Date(dateCheck.getTime() + TimeUnit.DAYS.toMillis(1));
				}
			}

			Reservation reservation = new Reservation(Customer, statusList, noChild, noAdult, status);
			printConfirmation(reservation, total);
			ReservationController.getInst().addReservation(reservation);
			for (RoomStatus s : statusList) {
				RoomStatusController.getInst().addRoomStatus(s);
			}
			ReservationBound.createdFeedback(reservation);
		}
	}

	private ArrayList<Room> checkExisting(Date start, Date end, int roomtype, ArrayList<String> roomNo) {
		String wifi, smoke, view;
		ArrayList<Room> checkRoom = new ArrayList<>();
		ArrayList<Room> roomList = RoomController.getInst().getAllRoom(start, end);
		if (!roomList.isEmpty()) {
			for (Room room : roomList) {
				if (!roomNo.contains(room.getRoomFloorNo())) {
					if (room.getRoomType() == roomtype) {
						checkRoom.add(room);
					}
				}
			}
		}
		System.out.println("------------------------ ROOMS AVAILABLE ------------------------");
		System.out.println(
				String.format("%5s %15s %8s %15s %15s", "Room No", "Room Type", "Wifi", "Smoking Room", "Window View"));
		if (!checkRoom.isEmpty()) {
			for (Room room : checkRoom) {
				if (room.getRoomType() == roomtype) {
					RoomType rT = RoomTypeController.getInst().getRoomType(room.getRoomType());
					if (room.isWifi() == true) {
						wifi = "Yes";
					} else {
						wifi = "No";
					}
					if (room.isSmoking() == true) {
						smoke = "Yes";
					} else {
						smoke = "No";
					}
					if (room.isView() == true) {
						view = "Yes";
					} else {
						view = "No";
					}
					System.out.println(String.format("%5s %15s %9s %12s %14s", room.getRoomFloorNo(), rT.getRoomType(),
							wifi, smoke, view));
				}
			}
		} else if (checkRoom.isEmpty()) {
			System.out.println("No Available Rooms for for your chosen dates");
			System.out.println("-----------------------------------------------------------------");
			return null;
		}
		System.out.println("-----------------------------------------------------------------");
		return roomList;
	}

	private void retrieveUpdateReservation() {
		sc = new Scanner(System.in);
		ArrayList<String> roomNoList = new ArrayList<>();
		int adult;
		int child;
		int roomType;
		Date start = null;
		Date end = null;
		String roomNo = null;
		try {
			System.out.print("Please enter the Reservation Code: ");
			String rCode = sc.next();
			Reservation r = ReservationController.getInst().getReservation(rCode);
			System.out.println("-------------- RESERVATION --------------");
			System.out.println("Reservation Code: " + r.getReservationCode());
			System.out.println("Customer Name: " + r.getCustomer().getName());
			System.out.print("Rooms Booked: ");
			for (RoomStatus status : r.getStatusList()) {
				System.out.print("#" + status.getRoomFloorNumber() + " ");
				start = status.getDateTo();
				end = status.getDateTo();
			}
			System.out.println("");
			System.out.println("Date From: " + start);
			System.out.println("Date To: " + end);
			System.out.println("No. of Adults: " + r.getNumberOfAdults());
			System.out.println("No. of Children: " + r.getNumberOfChildren());
			System.out.println("Reservation ln: " + r.getStatus());
			System.out.println("-----------------------------------------");
			System.out.println("------- UPDATE MENU -------");
			System.out.println("1. No. of Adults");
			System.out.println("2. No. of Children");
			System.out.println("3. Add Rooms");
			System.out.println("4. Remove Rooms");
			System.out.println("---------------------------");
			int choice = sc.nextInt();

			switch (choice) {
				case 1:
					System.out.print("Update No. of Adults: ");
					adult = sc.nextInt();
					r.setNumberOfAdults(adult);
					break;
				case 2:
					System.out.print("Update No. of Childrens: ");
					child = sc.nextInt();
					r.setNumberOfChildren(child);
					break;
				case 3:
					RoomTypeController.getInst().displayAllRoomType();
					System.out.print("Which Room Do You Want? ");
					roomType = sc.nextInt();
					ArrayList<Room> tempRoomList = new ArrayList<>();
					tempRoomList = checkExisting(start, end, roomType, roomNoList);
					System.out.print("Do you want to filter? (Y/N) :");
					char filterChoice = sc.next().charAt(0);
					if (filterChoice == 'Y' || filterChoice == 'y') {
						ArrayList<Room> filterList = filterRoom(tempRoomList, roomNoList, roomType);
						System.out.println("---------- ROOMS AVAILABLE ----------");
						if (!filterList.isEmpty()) {
							for (Room room : filterList) {
								if (!room.getRoomFloorNo().equals(roomNo)) {
									RoomType rT = RoomTypeController.getInst().getRoomType(room.getRoomType());
									System.out.println(room.getRoomFloorNo() + " " + rT.getRoomType() + " "
											+ rT.getWeekDayRate() + " " + rT.getWeekEndRate());
								}
							}
						} else {
							System.out.println("No Available Rooms for for your chosen dates");
							System.out.println("-------------------------------------");
							return;
						}
						System.out.println("-------------------------------------");
					} else if (filterChoice == 'N' || filterChoice == 'n') {
					} else {
						System.out.println("Invalid Input!");
					}

					System.out.print("Please Select a Room No(xx-xx): ");
					roomNo = sc.next();
					roomNoList.add(roomNo);

					RoomStatus roomStatus = new RoomStatus(roomNo, r.getCustomer().getID_customer(), end, start, "Reserved");
					RoomStatusController.getInst().addRoomStatus(roomStatus);
					r.getStatusList().add(roomStatus);
					break;
				case 4:
					System.out.print("Enter room to remove:");
					String room = sc.next();
					for (RoomStatus status : r.getStatusList()) {
						if (status.getRoomFloorNumber().equals(room)) {
							RoomStatusController.getInst().updateStatustoCancelled(status);
							r.getStatusList().remove(status);
						}
					}
					break;
				case 0:
					break;
			}

			ReservationController.getInst().updateReservation(r);
			ReservationBound.updatedFeedback(r);
		} catch (NullPointerException e) {
			System.out.println("Invaild Input! Please insert again.");
		}
	}
	private ArrayList<Room> filterRoom(ArrayList<Room> roomList, ArrayList<String> roomNo, int roomtype) {
		ArrayList<Room> filterRoomList = new ArrayList<>();
		Boolean wifiB = null;
		Boolean smokeB = null;
		Boolean viewB = null;
		System.out.print("Wifi (Y/N) :");
		char wifiFilter = sc.next().charAt(0);
		if (wifiFilter == 'Y' || wifiFilter == 'y')
			wifiB = true;
		else if (wifiFilter == 'N' || wifiFilter == 'n')
			wifiB = false;
		System.out.print("Smoking Room (Y/N) :");
		char smokeFilter = sc.next().charAt(0);
		if (smokeFilter == 'Y' || smokeFilter == 'y')
			smokeB = true;
		else if (smokeFilter == 'N' || smokeFilter == 'n')
			smokeB = false;
		System.out.print("Scenery View (Y/N) :");
		char viewFilter = sc.next().charAt(0);
		if (viewFilter == 'Y' || viewFilter == 'y')
			viewB = true;
		else if (viewFilter == 'N' || viewFilter == 'n')
			viewB = false;
		for (Room room : roomList) {
			if (!roomNo.contains(room.getRoomFloorNo()) && room.getRoomType() == roomtype) {
				if (wifiB == room.isWifi() && smokeB == room.isSmoking() && viewB == room.isView())
					filterRoomList.add(room);
			}
		}
		return filterRoomList;
	}

	private void printConfirmation(Reservation r, double total) {
		Date start = null;
		Date end = null;
		System.out.println("--------------- CONFIRMATION ---------------");
		System.out.println("RESERVATION CODE: " + r.getReservationCode());
		System.out.println("Customer: " + r.getCustomer().getName());
		System.out.print("ROOMS RESERVED: ");
		for (RoomStatus status : r.getStatusList()) {
			System.out.print("#" + status.getRoomFloorNumber() + " ");
			start = status.getDateTo();
			end = status.getDateTo();
		}
		System.out.println("");
		System.out.println("DATE FROM: " + start);
		System.out.println("DATE END: " + end);
		System.out.println("NO OF CHILDRENS: " + r.getNumberOfChildren());
		System.out.println("NO OF ADULTS: " + r.getNumberOfAdults());
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("TOTAL CHARGE: $" + df.format(total));
		System.out.println("--------------------------------------------");
	}

	private void removeReservation() {
		sc = new Scanner(System.in);

		System.out.print("Enter Reservation Code to be removed: ");
		String rCode = sc.next();
		Date start = null;
		Date end = null;

		Reservation r = ReservationController.getInst().getReservation(rCode);
		ArrayList<RoomStatus> statusList = new ArrayList<>();
		statusList = r.getStatusList();

		System.out.println("-------------- RESERVATION --------------");
		System.out.println("Reservation Code: " + r.getReservationCode());
		System.out.println("Customer Name: " + r.getCustomer().getName());
		System.out.print("Rooms Booked: ");
		for (RoomStatus status : statusList) {
			System.out.print("#" + status.getRoomFloorNumber() + " ");
			start = status.getDateTo();
			end = status.getDateTo();
		}
		System.out.println("");
		System.out.println("Date From: " + start);
		System.out.println("Date End: " + end);
		System.out.println("No. of Adults: " + r.getNumberOfAdults());
		System.out.println("No. of Children: " + r.getNumberOfChildren());
		System.out.println("Reservation ln: " + r.getStatus());
		System.out.println("-----------------------------------------");
		System.out.println("Do you want to remove " + r.getReservationCode() + " (Y/N)");
		try {
			char confirm = sc.next().charAt(0);
			if (confirm == 'Y' || confirm == 'y') {
				for (RoomStatus rS : statusList) {
					RoomStatusController.getInst().updateStatustoCancelled(rS);
				}
				r.setStatus("Cancelled");
				ReservationController.getInst().updateReservation(r);
				System.out.println("Reservation Removed");
			} else if (confirm == 'N' || confirm == 'n') {
			}
		} catch (InputMismatchException e) {
			System.out.println("ERROR. Invalid input.");
		}
	}

	private void printReservationMenu() {
		sc = new Scanner(System.in);
		System.out.println("-------------------------------"
							+"1. Check Reservation\n"
							+ "2. Print All Reservations\n"
							+ "0. Back" 
							+ "-------------------------------");
		System.out.print("Choose a menu: ");
		int choice = sc.nextInt();

		switch (choice) {
			case 1:
				checkReservation();
				break;
			case 2:
				printAllReservations();
				break;
			case 0:
				break;
			default:
				System.out.println("Invalid Choice");
				break;
		}
	}

	private void checkReservation() {
		sc = new Scanner(System.in);
		Date start = null;
		Date end = null;
		try {
			System.out.print("Enter Reservation Code: ");
			String rCode = sc.next();

			Reservation r = ReservationController.getInst().getReservation(rCode);
			ArrayList<RoomStatus> statusList = new ArrayList<>();
			statusList = r.getStatusList();

			System.out.println("-------------- RESERVATION --------------");
			System.out.println("Reservation Code: " + r.getReservationCode());
			System.out.println("Customer Name: " + r.getCustomer().getName());
			System.out.print("Rooms Booked: ");
			for (RoomStatus status : statusList) {
				System.out.print("#" + status.getRoomFloorNumber() + " ");
				start = status.getDateTo();
				end = status.getDateTo();
			}
			System.out.println("");
			System.out.println("Date From: " + start);
			System.out.println("Date To: " + end);
			System.out.println("No. of Adults: " + r.getNumberOfAdults());
			System.out.println("No. of Children: " + r.getNumberOfChildren());
			System.out.println("Reservation ln: " + r.getStatus());
			System.out.println("-----------------------------------------");
			System.out.println("");
		} catch (NullPointerException e) {
			System.out.println("Invalid Input!");
			System.out.println();
		}
	}

	private void printAllReservations() {
		ArrayList<Reservation> allRList = ReservationController.getInst().getAllReservation();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
		ArrayList<RoomStatus> statusList = null;
		Date start = null;
		Date end = null;

		System.out.println(
				"--------------------------------------- RESERVATION LIST ---------------------------------------");

		if (!allRList.isEmpty()) {
			System.out.println(
					"Printed in the following order (ReservationID/ReservationCode/CustomerName/RoomNo/StartDate/EndDate)");
			for (Reservation r : allRList) {
				r = ReservationController.getInst().getReservation(r.getReservationCode());
				statusList = r.getStatusList();
				System.out.print(String.format("%1s %16s %12s %5s", r.getReservationID(), r.getReservationCode(),
						r.getCustomer().getName(), ""));
				for (RoomStatus rS : statusList) {
					System.out.print("#" + rS.getRoomFloorNumber() + " ");
					start = rS.getDateTo();
					end = rS.getDateTo();
				}
				String stringStart = df.format(start);
				String stringEnd = df.format(end);
				System.out.println(String.format("%15s %12s", stringStart, stringEnd));

			}
		} else {
			System.out.println("No Reservations currently at the moment");
		}
		System.out.println(
				"------------------------------------------------------------------------------------------------");
		try {
			System.out.print("Please enter Reservation ID: ");
			int id = sc.nextInt();
			Reservation view = ReservationController.getInst().getReservationByID(id);
			System.out.println();
			System.out.println("-------------- RESERVATION --------------");
			System.out.println("Reservation Code: " + view.getReservationCode());
			System.out.println("Customer Name: " + view.getCustomer().getName());
			System.out.print("Rooms Booked: ");
			for (RoomStatus status : statusList) {
				System.out.print("#" + status.getRoomFloorNumber() + " ");
				start = status.getDateTo();
				end = status.getDateTo();
			}
			System.out.println("");
			System.out.println("Date From: " + start);
			System.out.println("Date To: " + end);
			System.out.println("No. of Adults: " + view.getNumberOfAdults());
			System.out.println("No. of Children: " + view.getNumberOfChildren());
			System.out.println("Reservation ln: " + view.getStatus());
			System.out.println("-----------------------------------------");
			System.out.println();
		} catch (NullPointerException e) {
			System.out.println("Invalid Input!");
			System.out.println("-----------------------------------------");
			System.out.println();
		}
	}

}