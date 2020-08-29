package hotelsystem.entity;

import java.util.ArrayList;
import java.io.Serializable;

@SuppressWarnings("serial")
public class CheckInCheckOut implements Serializable{
	private static int max = 1;
	private int ID_CICO;   
	private Customer g;
	private int no_adults;
	private int no_children;
	private String stat;
	private ArrayList<RoomStatus> rm_stat = new ArrayList<>();
	private BillPayment bill;

	public CheckInCheckOut(int ID_CICO, Customer g, int no_adults, int no_children, ArrayList<RoomStatus> rm_stat, String stat, BillPayment bill) {
		ID_CICO = max;
		this.g = g;
		this.no_adults = no_adults;
		this.no_children = no_children;
		this.rm_stat = rm_stat;
		this.stat = stat;
		this.bill = bill;
		max++;
	}
	
	public CheckInCheckOut(Customer g, int no_adults, int no_children, ArrayList<RoomStatus> rm_stat, String stat, BillPayment bill) {
		ID_CICO = max;
		this.g = g;
		this.no_adults = no_adults;
		this.no_children = no_children;
		this.rm_stat = rm_stat;
		this.stat = stat;
		this.bill = bill;
		max++;
	}
	
	public static int getID_max() { 
		return max; 
	}

	public static void setID_max(int m) { 
    	max = m; 
    }

	public int getID_CICO() { 
		return ID_CICO; 
	}

	public void setID_CICO(int c) { 
		ID_CICO = c; 
	}

	public Customer getCustomer() { 
		return g; 
	}

	public void setCustomer(Customer g) { 
		this.g = g; 
	}

	public int getNo_adults() { 
		return no_adults;
	}

	public void setNo_adults(int na) { 
		this.no_adults = na; 
	}
	
	public int getNo_children() { 
		return no_children; 
	}

	public void setNo_children(int n) { 
		this.no_children = n; 
	}

	public ArrayList<RoomStatus> getRm_stat() {   
		return rm_stat; 
	}

	public void setRm_stat(ArrayList<RoomStatus> rs) {  
		this.rm_stat = rs; 
	}

	public String getStatus() { 
		return stat; 
	}

	public void setStatus(String s) { 
		this.stat = s; 
	}

	public BillPayment getBill() { 
		return bill;
	}

	public void setBill(BillPayment b) { 
		this.bill = b; 
	}
}