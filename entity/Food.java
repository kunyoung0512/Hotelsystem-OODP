package hotelsystem.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Food implements Serializable{
	private static int foodID = 1; 

	private String foodName; 
	private Double foodPrice; 
	private String foodDescription; 

	public Food(String foodName, Double foodPrice, String foodDescription) {
		
		this.foodName = foodName;
		this.foodPrice = foodPrice;
		this.foodDescription = foodDescription;
		foodID++;
	}

	public String getfoodName() {
		return foodName;
	}

	public static int  getfoodID() {
		return foodID;
	}
	
	public static void setfoodID(int ID) { foodID = ID; }
	
	public Double getfoodPrice() { return foodPrice; }

	public void setfoodPrice(Double foodPrice) { this.foodPrice =foodPrice; }
	
	

	
	

	public void setfoodName(String foodName) { this.foodName = foodName; }

	
	public String getfoodDescription() { return foodDescription; }

	public void setfoodDescription(String foodDescription) { this.foodDescription =foodDescription; }

	public String getName() { return null; }

	public static int getMaxID() { return 0; }

    
}