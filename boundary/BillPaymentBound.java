package hotelsystem.boundary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import hotelsystem.controller.BillPaymentController;
import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomServiceController;
import hotelsystem.controller.RoomTypeController;
import hotelsystem.entity.BillPayment;
import hotelsystem.entity.Card;
import hotelsystem.entity.CheckInCheckOut;
import hotelsystem.entity.Food;
import hotelsystem.entity.Room;
import hotelsystem.entity.RoomService;
import hotelsystem.entity.RoomStatus;
import hotelsystem.entity.RoomType;


public class BillPaymentBound {
	private static BillPaymentBound inst = null;
    private Scanner sc;

    
    private BillPaymentBound() {
        sc = new Scanner(System.in);
    }
    
   
    public static BillPaymentBound getInst() {
        if (inst == null) {
            inst = new BillPaymentBound();
        }
        return inst;
    }
    
    
    public void generateBill(CheckInCheckOut c) {
    	int days=0;
    	double roomTotal = 0;
    	double roomSerTotal = 0;
    	double totalPrice = 0;
    	double roomCost= 0;
    	double discountAmt = 0;
    	double discountTotalAmt = 0;
    	double taxAmt = 0;
    	double grandTotal = 0;
    	String paymentMode = null;
    	String paymentStatus = "Pending Payment";
    	ArrayList<RoomService> rSerList = new ArrayList<>();
    	Card card = null;
    	
    	System.out.println("=================================== \n"
    			+"------------Hotel System------------\n"
    			+"~~~~~~~~~~~~~~~~Rooms~~~~~~~~~~~~~~~\n");
        SimpleDateFormat dfS = new SimpleDateFormat("EEE");
        for(RoomStatus rS : c.getRm_stat()) {
        	days = (int) ((rS.getDateTo().getTime() - rS.getDateFrom().getTime()) / (1000 * 60 * 60 * 24));
        	Date dateCheck = rS.getDateFrom();
        	for(int i = 0; i < days ; i++) {
            	Room rm = RoomController.getInst().getRoom(rS.getRoomFloorNumber());
            	RoomType rT = RoomTypeController.getInst().getRoomType(rm.getRoomType());
            	if(dateCheck.equals(rS.getDateFrom()) || dateCheck.equals(rS.getDateTo()) || (dateCheck.after(rS.getDateFrom()) && dateCheck.before(rS.getDateTo()))) {
            		String dateCheckS = dfS.format(dateCheck);
            		if(dateCheckS.equals("Sat") || dateCheckS.equals("Sun")) {
            			roomCost += rT.getWeekEndRate();
            			roomTotal += rT.getWeekEndRate();
            		}
            		else {
            			roomCost += rT.getWeekDayRate();
            			roomTotal += rT.getWeekDayRate();
            		}
            	}
            	dateCheck = new Date(TimeUnit.MILLISECONDS.toDays(dateCheck.getTime()) + 1);
        	}
        	System.out.println("- " + rS.getRoomFloorNumber() + ":	 " + days +" Days		" + roomCost);
        	roomCost = 0.0;
        }
        System.out.println("-- Room Sub-Total: 		" + roomTotal);
        if(discountTotalAmt!=0.0) {
        	System.out.println("-- Discount: 			-" + discountTotalAmt);
        }
        roomTotal = roomTotal-discountTotalAmt;
        System.out.println("=======================================");
        System.out.println("-- Room Total Cost: 		" + (roomTotal));
        System.out.println("=======================================");
        
        
        for(RoomStatus rS : c.getRm_stat()) {
    		rSerList.addAll(RoomServiceController.getInst().getRSList(rS.getRoomBookingsID()));
    	}
    	
        if(!rSerList.isEmpty()) {
        	System.out.println("~~~~~~~~~~~~~Room Service~~~~~~~~~~~~~");
        	for(RoomService rmS : rSerList) {
        		if(rmS.getStatus()==false) {
	        		ArrayList<Food> foodList = rmS.getFoodList();
	        		for(Food f : foodList) {
	        			System.out.println("- " + f.getfoodName() + "			" + f.getfoodPrice());
	        		}
	        		roomSerTotal += rmS.getTotalPrice();
        		}
        	}
        	System.out.println("=======================================");
        	System.out.println("-- Room Service Sub-Total: 	" + roomSerTotal);
        	System.out.println("=======================================");
        }
        totalPrice= roomTotal+roomSerTotal;
        taxAmt = totalPrice*0.07;
        grandTotal = totalPrice+taxAmt;
        System.out.println("-- Total: 			" + totalPrice);
        System.out.println("-- Tax 7%: 			" + String.format( "%.2f", taxAmt));
        System.out.println("=======================================");
        System.out.println("---- Grand Total: 		" + String.format( "%.2f", grandTotal));
        System.out.println("=======================================");
        
        System.out.println("Select Payment Mode\n"
        		+ "1. Credit/Debit Card\n"
        		+ "2. Cash");
        
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice==1) {
                if (c.getCustomer().getCard_Info().getName()!=null) {
                	System.out.print("Card details found on customer's profile, would you like to use an existing card for payment? (Y/N)");
                	char reply = sc.next().charAt(0);
                	sc.nextLine();
				    if (reply=='Y' || reply=='y') {
					    card = c.getCustomer().getCard_Info();
					    paymentMode = "Card";
		                paymentStatus = "Completed";
				    }
				    else if (reply=='N' || reply=='n'){
				    	card = createCard();
				    }
				    else
				    	System.out.println("Invaild Input! Please insert again.");
		         	}
                else {
                	card = createCard();
                }
        }
        else if (choice==2) {
                paymentMode = "Cash";
                paymentStatus = "Completed";
        }
        else {
                System.out.println("Invalid Choice");
        }
        
        BillPayment bill = new BillPayment(c,rSerList,roomTotal,roomSerTotal,totalPrice,discountAmt,taxAmt,grandTotal,paymentMode,card,paymentStatus);
        BillPaymentController.getInst().addBillPayment(bill);
        
        System.out.println("-------Payment Successful-------");
        return;
    }
    
    private Card createCard() {
    	System.out.println("Enter Card Full Name:");
        String ccName = sc.nextLine();
        System.out.println("Enter Card Number:");
        Long ccNum = sc.nextLong();
        sc.nextLine();
        System.out.println("Enter Expiry Date (MM/YY):");
        String ccDate = sc.nextLine();
        System.out.println("Enter CVV:");
        int ccCVV = sc.nextInt();
        Card newCard = new Card(ccNum, ccName, ccDate, ccCVV);
        return newCard;
    }

}