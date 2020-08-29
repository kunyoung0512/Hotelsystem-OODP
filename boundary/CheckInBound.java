package hotelsystem.boundary;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import hotelsystem.controller.CheckInCheckOutController;
import hotelsystem.controller.ReservationController;
import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.controller.RoomTypeController;
import hotelsystem.entity.CheckInCheckOut;
import hotelsystem.entity.Customer;
import hotelsystem.entity.Reservation;
import hotelsystem.entity.Room;
import hotelsystem.entity.RoomStatus;
import hotelsystem.entity.RoomType;


public class CheckInBound {
	private static CheckInBound inst = null;
    private Scanner sc;

    private CheckInBound() {
        sc = new Scanner(System.in);
    }
    
    public static CheckInBound getInst() {
        if (inst == null) {
            inst = new CheckInBound();
        }
        return inst;
    }


    public void displayOptions() {
        int choice;
        try {
	        do {
	            System.out.println("---------- CHECK-IN MENU -----------");
	            System.out.println("1. Walk-in");
	            System.out.println("2. Reservation");
	            System.out.println("0. Go Back");
	            System.out.println("------------------------------------");
	            System.out.print("Please, enter the number corresponding to your choice: ");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                    walkIn();                         
	                	break;
	                case 2:
	                    reservationCheckIn();
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
        	System.out.println("Invaild Input, please try again.");
        }
    }
    
    public void walkIn() {                              
    	Customer g = null;
    	int choice;
    	do {
			System.out.println("---------------------");
    		System.out.println("Are you a...:");
            System.out.println("1.New Customer?");
            System.out.println("2.Returning Customer?");
			System.out.println("0.Go back");
			System.out.println("---------------------");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                	g = CustomerBound.getInst().createnewCustomer();
	                	if(g!=null)
	                		walkIn(g);
						break;
					case 2:
						String gName;
						System.out.println("Enter the Customer's name: ");
						gName = sc.next();
						g = CustomerBound.getInst().searchCustomer(gName);
						if (g != null)
							walkIn(g);
	                    break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Invalid Choice");
	                    break;
	            }
	        } while (choice > 0);
    }
    
    
    public void reservationCheckIn() {                           
    	ArrayList<RoomStatus> statList = new ArrayList<>();
    	sc = new Scanner(System.in);
    	System.out.println("Please enter your booking code");
        String bcode = sc.nextLine();
        Reservation redet = ReservationController.getInst().getReservation(bcode);
        if (redet == null) {
        	System.out.println("Your booking code is invalid. Please try again.");
        }
        else {
	        for (RoomStatus rmStat : redet.getStatusList()) {
	        	RoomStatus reRoom = new RoomStatus(rmStat.getRoomBookingsID(), redet.getCustomer().getID_customer(),
						rmStat.getRoomFloorNumber(), rmStat.getDateFrom(), rmStat.getDateTo(), "Checked-In");
	        	RoomStatusController.getInst().updateStatustoCheckedIn(reRoom);
	        	statList.add(reRoom);
	        }
	        CheckInCheckOut c = new CheckInCheckOut(redet.getCustomer(),redet.getNumberOfAdults(),redet.getNumberOfChildren(),statList,"Checked-In",null);
	        if(statList != null) {
	        	 double total = 0;
	             SimpleDateFormat dfS = new SimpleDateFormat("EEE");
	             for(RoomStatus rmStat : statList) {
	            	 
	             	int days = (int) ((rmStat.getDateTo().getTime() - rmStat.getDateFrom().getTime()) / (1000 * 60 * 60 * 24)-1);
	             	Date chkdate = rmStat.getDateFrom();
	             	
	             	for(int i = 0; i <= days ; i++) {
	                 	Room rm = RoomController.getInst().getRoom(rmStat.getRoomFloorNumber());
	                 	RoomType rT = RoomTypeController.getInst().getRoomType(rm.getRoomType());
						if (chkdate.equals(rmStat.getDateFrom()) || chkdate.equals(rmStat.getDateTo())
								|| (chkdate.after(rmStat.getDateFrom()) && chkdate.before(rmStat.getDateTo()))) {
							String chkdatestr = dfS.format(chkdate);
							if (chkdatestr.equals("Sat") || chkdatestr.equals("Sun"))
								total += rT.getWeekEndRate();
							else
								total += rT.getWeekDayRate();
						}
						chkdate = new Date(chkdate.getTime() + TimeUnit.DAYS.toMillis(1));
					}
				}
				printConfirmation(c, total);
				CheckInCheckOutController.getInst().insertCheckIn(c);
				redet.setStatus("Checked-In");
				ReservationController.getInst().updateReservation(redet);
			}
		}
	}

	public void walkIn(Customer g) { 
		sc = new Scanner(System.in);
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat dftf = new SimpleDateFormat("dd/MM/yy HH:mm"); 
		ArrayList<RoomStatus> statList = new ArrayList<>();
		String dateTo;
		Date dateFrom = new Date();
		Date dateStored = new Date();
		String sD = df.format(dateFrom);
		try {
			dateFrom = df.parse(sD);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		try {
			dateStored = dftf.parse(sD + " 12:00");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		Date endDate = null;
		int no_Child;
		int no_Adult;
		int rm_Type;
		String wifi, smoke, view;

		System.out.print("Enter Number of Adults: ");
		no_Adult = sc.nextInt();
		System.out.print("Enter Number of Childrens: ");
		no_Child = sc.nextInt();
		System.out.print("Enter Date To (dd/MM/yy): ");
		dateTo = sc.next();
		try {
			endDate = df.parse(dateTo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (dateFrom.equals(endDate)) {
			System.out.println("I'm sorry, but the minumum duration is one night. Please try again.");
			return;
		} else {
			try {
				endDate = dftf.parse(dateTo + " 12:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		Boolean chkAddRoom = null;
		ArrayList<String> rmNumList = new ArrayList<>();
		String rmNum = null;
		Boolean noroom = false;

		do {
			ArrayList<Room> tempRoomList = new ArrayList<>();
			do {
				RoomTypeController.getInst().displayAllRoomType();
				System.out.println("Which type of room would you prefer? ");
				rm_Type = sc.nextInt();
				RoomTypeController.getInst().getRoomType(rm_Type);

				tempRoomList = checkExisting(dateStored, endDate, rmNumList, rm_Type);

				if (tempRoomList == null) {
					System.out.println("Sorry, there are no more rooms available for your chosen dates");
					noroom = true;
				} else
					noroom = false;
			} while (noroom);
			System.out.print("Would you like to filter the list? (Y/N) :");
			char filter = sc.next().charAt(0);
			if (filter == 'Y' || filter == 'y') {
				ArrayList<Room> filterList = filterRoom(tempRoomList, rmNumList, rm_Type);
				System.out.println("------------------------- ROOMS AVAILABLE ------------------------");
				System.out.println(String.format("%3s %12s %15s %7s %15s", "Room Number", "Room Type", "Window View",
						"Wifi", "Smoking Room")); 
				if (!filterList.isEmpty()) {
					for (Room rm : filterList) {
						if (!rm.getRoomFloorNo().equals(rmNum)) {
							RoomType rT = RoomTypeController.getInst().getRoomType(rm.getRoomType());
							if (rm.isView() == true) {
								view = "Y";
							} else {
								view = "X";
							}
							if (rm.isWifi() == true) {
								wifi = "Y";
							} else {
								wifi = "X";
							}
							if (rm.isSmoking() == true) {
								smoke = "Y";
							} else {
								smoke = "X";
							}
							System.out.println(String.format("%3s %12s %14s %8s %12s", rm.getRoomFloorNo(),
									rT.getRoomType(), view, wifi, smoke)); 
						}
					}
				} else {
					System.out.println("Sorry, there are no more rooms available for your chosen dates");
					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					return;
				}
			} else if (filter == 'N' || filter == 'n') {
				
				
			} else {
				System.out.println("Invalid Input");
			}
			Boolean chk = true;
			do {
	            System.out.print("Please Select a Room No(xx-xx): ");
	            Boolean proceed = false;
	            rmNum = sc.next();
				for (Room tList : tempRoomList) {
					if (tList.getRoomFloorNo().equals(rmNum)) {
	            		proceed=true;
	            		chk = true;
						break;
					} else {
						proceed = false;
					}
				}
				if (!proceed) {
					System.out.println("Invalid Input! Please Try Again.");
					chk = false;
				}
			} while (!chk);

			rmNumList.add(rmNum);
			RoomStatus rmStat = new RoomStatus(rmNum, g.getID_customer(), endDate, dateStored, "Checked-In");
			statList.add(rmStat);
			System.out.println(rmStat.getStatus());

			System.out.print("Do you wish to add additional rooms? (Y/N): ");
			char addRoom = sc.next().charAt(0);
			if (addRoom == 'Y' || addRoom == 'y')
				chkAddRoom = true;
			else if (addRoom == 'N' || addRoom == 'n')
				chkAddRoom = false;
		} while (chkAddRoom);

		if (statList != null) {
			double total = 0;
			SimpleDateFormat dfS = new SimpleDateFormat("EEE");
			for (RoomStatus rmStat : statList) {
				int days = (int) ((rmStat.getDateTo().getTime() - rmStat.getDateFrom().getTime())
						/ 86400000 - 1);
				Date chkdate = rmStat.getDateFrom();
				for (int i = 0; i <= days; i++) {
					Room rm = RoomController.getInst().getRoom(rmStat.getRoomFloorNumber());
					RoomType rT = RoomTypeController.getInst().getRoomType(rm.getRoomType());
					if (chkdate.equals(rmStat.getDateFrom()) || chkdate.equals(rmStat.getDateTo())
							|| (chkdate.after(rmStat.getDateFrom()) && chkdate.before(rmStat.getDateTo()))) {
						String chkdatestr = dfS.format(chkdate);
						if (chkdatestr.equals("Sat") || chkdatestr.equals("Sun"))
							total += rT.getWeekEndRate();
						else
							total += rT.getWeekDayRate();
					}
					chkdate = new Date(chkdate.getTime() + TimeUnit.DAYS.toMillis(1));
				}
			}

			CheckInCheckOut c = new CheckInCheckOut(g, no_Adult, no_Child, statList, "Checked-In", null);
			printConfirmation(c, total);
			CheckInCheckOutController.getInst().insertCheckIn(c);

			for (RoomStatus s : statList) {
				RoomStatusController.getInst().addRoomStatus(s);
			}
		}
	}

	private ArrayList<Room> checkExisting(Date start, Date end, ArrayList<String> rmNum, int roomtype) {
		String view, wifi, smoke;
		ArrayList<Room> checkRoom = new ArrayList<>();
		ArrayList<Room> rList = RoomController.getInst().getAllRoom(start, end);
		if (!rList.isEmpty()) {
			for (Room r : rList) {
				if (!rmNum.contains(r.getRoomFloorNo())) {
					if (r.getRoomType() == roomtype) {
						checkRoom.add(r);
					}
				}
			}
		}
		System.out.println("------------------------ ROOMS AVAILABLE ------------------------");
		System.out.println(String.format("%5s %15s %15s %8s %15s ", "Room Number", "Room Type", "Window View", "Wifi",
				"Smoking Room"));
		if (!checkRoom.isEmpty()) {
			for (Room room : checkRoom) {
				if (room.getRoomType() == roomtype) {
					RoomType rT = RoomTypeController.getInst().getRoomType(room.getRoomType());
					if(room.isView() == true) {
						view = "Y";
					}else {
						view = "X";
					}
					if(room.isWifi() == true) {
						wifi = "Y";
					}else {
						wifi = "X";
					}
					if(room.isSmoking() == true) {
						smoke = "Y";
					}else {
						smoke = "X";
					}
					
					System.out.println(String.format("%5s %15s %14s %9s %12s", room.getRoomFloorNo(), rT.getRoomType(), view, wifi, smoke));
				}
			}
		}
		else if(checkRoom.isEmpty()){
			System.out.println("Sorry, there are no more rooms available for your chosen dates");
		}
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return checkRoom;
    }
    
    private ArrayList<Room> filterRoom(ArrayList<Room> roomList, ArrayList<String> rmNum, int roomtype){
		ArrayList<Room> filterRoomList = new ArrayList<>();
		Boolean hasWifi = null;
		Boolean canSmoke = null;
		Boolean hasView = null;
    	System.out.print("Wifi (Y/N) :");
    	char wifiFilter = sc.next().charAt(0);
    	if(wifiFilter =='Y' || wifiFilter =='y')
    		hasWifi = true;
    	else if(wifiFilter == 'N' || wifiFilter == 'n')
    		hasWifi = false;
    	System.out.print("Smoking Room (Y/N) :");
    	char smokeFilter = sc.next().charAt(0);
    	if(smokeFilter =='Y' || smokeFilter =='y')
    		canSmoke = true;
    	else if(smokeFilter == 'N' || smokeFilter == 'n')
    		canSmoke = false;
    	System.out.print("Scenery View (Y/N) :");
    	char viewFilter = sc.next().charAt(0);
    	if(viewFilter =='Y' || viewFilter =='y')
    		hasView = true;
    	else if(viewFilter == 'N' || viewFilter == 'n')
    		hasView = false;
    	for(Room room : roomList) {
    		if(!rmNum.contains(room.getRoomFloorNo()) && room.getRoomType() == roomtype) {
    			if(hasWifi==room.isWifi() && canSmoke==room.isSmoking() && hasView==room.isView())
        			filterRoomList.add(room);
    		}
    	}
    	return filterRoomList;
	}
    

    private void printConfirmation(CheckInCheckOut c, double total) {
    	Date from = null;
    	Date to = null;
        DecimalFormat mf = new DecimalFormat("#.00"); 
		System.out.println("---------------- CONFIRMATION -----------------------");
		
		System.out.println("Name of Customer: " + c.getCustomer().getName());
		System.out.print("Rooms checked-in: ");
        for (RoomStatus status : c.getRm_stat()) {
        	System.out.print("#"+status.getRoomFloorNumber() + " ");
        	from = status.getDateFrom();
        	to = status.getDateTo();
        }
        System.out.println("");
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Number of adults: " + c.getNo_adults());
        System.out.println("Number of children: " + c.getNo_children());
        System.out.println("TOTAL CHARGE: $" + mf.format(total));
        System.out.println("---------------------------------------------------");
	}
}