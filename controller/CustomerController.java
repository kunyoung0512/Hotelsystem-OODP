package hotelsystem.controller;

import java.io.*;
import java.util.ArrayList;
import hotelsystem.entity.Customer;

@SuppressWarnings("serial")
public class CustomerController implements Serializable{
	private static CustomerController inst = null;
	private final ArrayList<Customer> CustomerList = new ArrayList<>();
	
	private CustomerController() {}
	
	public static CustomerController getInst() {
        if (inst == null) {
            inst = new CustomerController();
        }
        return inst;
    }
	
	public void addCustomer(Customer Customer) {
        CustomerList.add(Customer);
        storeData();
    }
	
	public Customer getCustomer(int ID_Customer) {
        for (Customer g : CustomerList) {
            if (g.getID_customer() == ID_Customer)
                return g;
        }
        return null;
    }

    public Customer getCustomer(String name) {
        String checkName = name.toUpperCase();
        for (Customer Customer : CustomerList) {
            if (Customer.getName().toUpperCase().equals(checkName)) {
                return Customer;
            }
        }
        return null;
    }

    public void updateCustomer(Customer Customer) {
        CustomerList.remove(Customer);
        CustomerList.add(Customer);
        storeData();
    }

    public Customer getCustomerById(String id) {
        for (Customer Customer : CustomerList) {
            if (Customer.getNric().toUpperCase().equals(id)) {
                return Customer;
            }
        }
        return null;
    }

    public void deleteCustomer(Customer Customer) {
        CustomerList.remove(Customer);
        storeData();
    }

    public Customer insertCustomerReturn(Customer Customer) {
        CustomerList.add(Customer);
        storeData();
        return Customer;
    }

    public ArrayList<Customer> searchCustomerList(String name) {
        String NameUpper = name.toUpperCase();
        ArrayList<Customer> result = new ArrayList<>();
        for (Customer g : CustomerList) {
            if (g.getName().toUpperCase() != null && g.getName().toUpperCase().contains(NameUpper)) {
                result.add(g);
            }
        }
    	return result;
    }
    
    public void loadData () {
        ObjectInputStream objinpstr;
        try {
        	objinpstr = new ObjectInputStream(new FileInputStream("DB/Customer.ser"));

            int no_Records = objinpstr.readInt();
            Customer.setID_Max(objinpstr.readInt());
            System.out.println("CustomerController: " + no_Records + " Entries Loaded");
            for (int i = 0; i < no_Records; i++) {
                CustomerList.add((Customer) objinpstr.readObject());
            }
        } catch (IOException | ClassNotFoundException ex2) {
            ex2.printStackTrace();
        }
    }
    
    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/Customer.ser"));
            out.writeInt(CustomerList.size());
            out.writeInt(Customer.getID_Max());
            for (Customer Customer : CustomerList)
                out.writeObject(Customer);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

}