package hotelsystem.boundary;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import hotelsystem.controller.CustomerController;
import hotelsystem.entity.Card;
import hotelsystem.entity.Customer;


public class CustomerBound {
	private static CustomerBound inst = null;
    private Scanner sc;

 
    private CustomerBound() {
        sc = new Scanner(System.in);
    }
    

    public static CustomerBound getInst() {
        if (inst == null) {
            inst = new CustomerBound();
        }
        return inst;
    }


    public void displayOptions() {
        int choice;
	        do {
	            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~ Customer MENU ~~~~~~~~~~~~~~~~~~~~~~~~\n"
						 + "                                                              \n"
						 + "  1. Create Customer                                             \n"
						 + "  2. Retrieve Customer Details                                   \n"
						 + "  3. Update Customer Details                                     \n"
						 + "  4. Remove Customer Particulars                                 \n"
						 + "| 0. Back to previous level                                   \n"
						 + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	            choice = sc.nextInt();
	            
	            switch (choice) {
	                case 1:
	                    createCustomerDetails();
	                	break;
	                case 2:
	                    retrieveCustomerDetails();
	                    break;
	                case 3:
	                	updateCustomerDetails();
	                    break;
	                case 4:
	                	deleteCustomerDetails();
	                	break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Invalid Choice");
	                    break;
	            }
	        } while (choice > 0);
        
    }
    
   
    private void createCustomerDetails() {
        sc = new Scanner(System.in);
        String customerName;
        String identityNo;
        String address;
        long contactNo;
        String country;
        char gender;
        String nationality;
        String ccName = null,ccDate =null;
        long ccNum = 0;
        int ccCVV= 0;
        
	        System.out.println("Enter Customer Name: ");
	        customerName = sc.nextLine();
		    System.out.println("Enter Customer Identity Number: ");
		    identityNo = sc.nextLine();
		   if (checkExistingCustomerByIC(identityNo)!=null) {
				System.out.println("Identity Numnber exist in the system. Please Try Again.");
				return;
		   }
		   else {
		        System.out.println("Enter Address:");
		        address = sc.nextLine();
		        System.out.println("Enter Contact Number:");
		        contactNo = sc.nextLong();
		        sc.nextLine();
		        System.out.println("Enter Country:");
		        country = sc.nextLine();
		        System.out.println("Enter Gender (M-Male, F-Female)");
		        gender = sc.nextLine().toUpperCase().charAt(0);
		        if (gender!='M') {
		        	if (gender!='F') {
		        		System.out.println("Invaild Input! Please insert again.");
		        		return;
		        	}
		       }
		        System.out.println("Enter Nationality:");
		        nationality = sc.nextLine();
		        
		        System.out.println("Do you want to add credit card details for Customer? (Y-Yes, N-No)");
		        char reply = sc.next().charAt(0);
		        sc.nextLine();
		    	if (reply=='Y' || reply=='y') {
			        System.out.println("Enter Card Full Name:");
			        ccName = sc.nextLine();
			        System.out.println("Enter Card Number:");
			        ccNum = sc.nextLong();
			        sc.nextLine();
			        System.out.println("Enter Expiry Date (MM/YY):");
			        ccDate = sc.nextLine();
			        System.out.println("Enter CVV:");
			        ccCVV = sc.nextInt();
		    	}
		        
		        Card newcc = new Card(ccNum, ccName, ccDate, ccCVV);
		        Customer Customer = new Customer(identityNo, customerName, address, contactNo, country, gender, nationality,newcc);
		        CustomerController.getInst().addCustomer(Customer);
		        System.out.println("Customer Name " + Customer.getName() +  " has been created.");  
	        }
        
    }
    
    public Customer createnewCustomer() {
        sc = new Scanner(System.in);
        String customerName;
        String identityNo;
        String address;
        long contactNo;
        String country;
        char gender;
        String nationality;
        String ccName = null,ccDate =null;
        long ccNum = 0;
        int ccCVV= 0;
        Customer rCustomer = null;
        
        try {
	        System.out.println("Enter Customer Name: ");
	        customerName = sc.nextLine();
		        System.out.println("Enter Customer Identity Number: ");
		        identityNo = sc.nextLine();
		   if (checkExistingCustomerByIC(identityNo)!=null) {
		       System.out.println("Identity Numnber exist in the system. Please Try Again.");
		       rCustomer= null;
		   }
		   else {
		        System.out.println("Enter Address:");
		        address = sc.nextLine();
		        System.out.println("Enter Contact Number:");
		        contactNo = sc.nextLong();
		        sc.nextLine();
		        System.out.println("Enter Country:");
		        country = sc.nextLine();
		        System.out.println("Enter Gender (M-Male, F-Female)");
		        gender = sc.nextLine().toUpperCase().charAt(0);		       
		       if (gender!='M') {
		        	if (gender!='F') {
		        		System.out.println("Invaild Input! Please insert again.");
		        		rCustomer= null;
		        	}
		       }
		       else {
		        System.out.println("Enter Nationality:");
		        nationality = sc.nextLine();
		        
		        System.out.println("Do you want to add credit card details for Customer? (Y-Yes, N-No)");
		        char reply = sc.next().charAt(0);
		        sc.nextLine();
		    	if (reply=='Y' || reply=='y') {
			        System.out.println("Enter Card Full Name:");
			        ccName = sc.nextLine();
			        System.out.println("Enter Card Number:");
			        ccNum = sc.nextLong();
			        sc.nextLine();
			        System.out.println("Enter Expiry Date (MM/YY):");
			        ccDate = sc.nextLine();
			        System.out.println("Enter CVV:");
			        ccCVV = sc.nextInt();
		    	}
		        
		        Card newcc = new Card(ccNum, ccName, ccDate, ccCVV);
		        Customer Customer = new Customer(identityNo, customerName, address, contactNo, country, gender, nationality,newcc);
		        rCustomer = CustomerController.getInst().insertCustomerReturn(Customer);
		        System.out.println("Customer Name " + Customer.getName() +  " has been created.");  
		       }
	        }
        }
        catch (InputMismatchException e) {
        	System.out.println("Invaild Input! Please insert again.");
        }
        return rCustomer;
    }
    
   
    private Customer checkExistingCustomerByIC(String identity_No) {
        Customer rCustomer = CustomerController.getInst().getCustomerById(identity_No);
        if (rCustomer!=null) {
        	return rCustomer;
        }
        else {
        	return null;
        }
    }
    
  
    protected Customer searchCustomer(String customerName) {
    	ArrayList<Customer> sCustomer = new ArrayList<>();
    	sCustomer = CustomerController.getInst().searchCustomerList(customerName);
        if (sCustomer.size()!=0) {
        	System.out.println(sCustomer.size() + " Customer Found");
        	System.out.println("Customer ID	Customer Name	Customer Identity Number");
        	for(Customer Customer : sCustomer){
        		System.out.println(Customer.getID_customer() +"		"+ Customer.getName() +"		"+ Customer.getID_customer());
        	}
        	System.out.println("Select a Customer (Enter Customer ID)");
        	try {
	        	sc = new Scanner(System.in);
	        	int sid = sc.nextInt();
	        	for(Customer Customer : sCustomer){
	        		if(Customer.getID_customer() == sid) {
	        			return Customer;
	        		}
	        	}
	        	System.out.println("Invaild Customer ID");
	        	return null;
        	}
        	catch (InputMismatchException e) {
            	System.out.println("Invaild Input! Please insert again.");
            	return null;
            }
        }
        else {
        	System.out.println("No such Customer. Please try again.");
        	return null;
        }
    }
    
   
    private void retrieveCustomerDetails(){
    	Customer Customer = null;
    	String gender = null, cc = null;
    	
    	String customerName;
        sc = new Scanner(System.in);
        System.out.println("Enter Customer Name: ");
        customerName = sc.nextLine();
        Customer = searchCustomer(customerName);
		
		if (Customer!=null) {
			char g = Customer.getGender();
			if (g=='M') {
				gender ="Male";
			}
			else if (g=='F') {
				gender ="Female";
			}
			if (Customer.getCard_Info().getName()==null) {
				cc="Not Found";
			}
			else {
				cc ="vaild";
			}
			System.out.println("Customer Details:\n"
					 + "Customer ID: " + Customer.getID_customer() +"\n"
					 + "Name: " + Customer.getName() +"\n"
					 + "Identity Number: " + Customer.getID_customer() +"\n"
					 + "Address: " + Customer.getAddress() +"\n"
					 + "Contact Number: " + Customer.getContact() +"\n"
					 + "Country: " + Customer.getCountry() +"\n"
					 + "Gender: " + gender +"\n"
					 + "Nationality: " + Customer.getNationality() +"\n"
					 + "Credit Card: " + cc +"\n"
					 + "---------------------------------------");
		}
    }
    
  
    private void updateCustomerDetails() {
    		int choice2;
    		Customer Customer = null;
    		String customerName;
            sc = new Scanner(System.in);
            System.out.println("Enter Customer Name: ");
            customerName = sc.nextLine();
            Customer = searchCustomer(customerName);
        	
    		try {
	    		do {
	    			System.out.println( "--------------------------------------"
									 +  "Select Field to Update (Enter 0 to end)\n"
	    							 + "1. Name\n"
	    							 + "2. Identity Number\n"
	    							 + "3. Address\n"
	    							 + "4. Contact Number\n"
	    							 + "5. Country\n"
	    							 + "6. Gender\n"
	    							 + "7. Nationality\n"
	    							 + "8. Credit Card Details\n"
	    							 + "--------------------------------------");
	    			
	    			choice2 = sc.nextInt();
	    			switch(choice2) {
	    				case 1:	
	    					System.out.println("Enter New Name: ");
	    					if (sc.nextLine() != null) {
	    						String name = sc.nextLine();
	    						Customer.setName(name);
	    						System.out.println("Name Updated");
	    					}
	    						break;
	    				case 2:
	    					System.out.println("Enter New Identity Number: ");
	    					if (sc.nextLine() != null) {
	    						String idNo = sc.nextLine();
	    						 if (checkExistingCustomerByIC(idNo)!=null) {
	    						       System.out.println("Identity Number exist in the system. Please Try Again.");
	    						       break;
	    						 }
	    						 else {
	    						Customer.setNric(idNo);
	    						System.out.println("Identity Number Saved");
	    						 }
	    					}
	    					break;
	    				case 3:
	    					System.out.println("Enter New Address: ");
	    					if (sc.nextLine() != null) {
	    						String address = sc.nextLine();
	    						Customer.setAddress(address);
	    						System.out.println("Address Saved");
	    					}
	    					break;
	    				case 4:
	    					System.out.println("Enter New Contact Number: ");
	    					int contactNo = sc.nextInt();
	    					Customer.setContact(contactNo);
	    					System.out.println("Contact Number Saved");
	    					break;
	    				case 5:
	    					System.out.println("Enter New Country: ");
	    					if (sc.nextLine() != null) {
	    						String country = sc.nextLine();
	    						Customer.setCountry(country);
	    						System.out.println("Country Saved");
	    					}
	    					break;
	    				case 6:
	    					System.out.println("Enter Gender (M-Male, F-Female)");
	    					char gender = sc.next().charAt(0);
	    					Customer.setGender(gender);
	    					System.out.println("Gender Saved");
	    					break;
	    				case 7:
	    					System.out.println("Enter Nationality");
	    					if (sc.nextLine() != null) {
	    						String nationality = sc.nextLine();
	    						Customer.setNationality(nationality);
	    						System.out.println("Nationality Saved");
	    					}
	    					break;
	    				case 8:
	    					System.out.println("Enter Card Full Name:");
	    			        String ccName = sc.nextLine();
	    			        sc.nextLine();
	    			        System.out.println("Enter Card Number:");
	    			        Long ccNum = sc.nextLong();
	    			        sc.nextLine();
	    			        System.out.println("Enter Expiry Date (MM/YY):");
	    			        String ccDate = sc.nextLine();
	    			        System.out.println("Enter CVV:");
	    			        int ccCVV = sc.nextInt();
	    			        Card newcc = new Card(ccNum, ccName, ccDate, ccCVV);
	    			        Customer.setCard_Info(newcc);
	    			        System.out.println("Credit Card Details Saved");
	    					break;
	    				case 0:
	    					CustomerController.getInst().updateCustomer(Customer);
	    					System.out.println("Customer Details Updated!");
	    					break;
	    				}
	    			}while(choice2 > 0 && choice2 <= 8);
    		}
    		catch (InputMismatchException e) {
            	System.out.println("Invaild Input! Please insert again.");
            	displayOptions();
            }
    }
    
    public void deleteCustomerDetails() {
    	String customerName;
        sc = new Scanner(System.in);
        System.out.println("Enter Customer Name: ");
        customerName = sc.nextLine();
        Customer Customer = searchCustomer(customerName);
        if (Customer != null) {
	    	System.out.println("Are you sure you want to delete the Customer name " + Customer.getName() + " ? (Y-Yes, N-No)");
	    	try{
		    	char reply = sc.next().charAt(0);
		    	if (reply=='Y' || reply=='y') {
			    	CustomerController.getInst().deleteCustomer(Customer);
			    	System.out.println("Customer Removed");
		    	}
		    	else
		    		System.out.println("Invaild Input! Please insert again.");
		    	}
	    	catch (InputMismatchException e) {
	        	System.out.println("Invaild Input! Please insert again.");
	        	displayOptions();
	        }
        }
    }
}