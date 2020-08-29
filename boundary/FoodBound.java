package hotelsystem.boundary;

import java.util.InputMismatchException;
import java.util.Scanner;
import hotelsystem.controller.FoodController;
import hotelsystem.entity.Food;


public class FoodBound {
	private static FoodBound inst = null;
	private Scanner sc;

	
	private FoodBound() {
		sc = new Scanner(System.in);
	}


	public static FoodBound getInst() {
		if (inst == null) {
			inst = new FoodBound();
		}
		return inst;
	}


	public void displayOptions() {
		int choice;
		try {
			do {
				System.out.println("-------------------------------------");
				System.out.println("1.Create Room Service Food Menu Items");
				System.out.println("2.Update Room Service Food Menu Items");
				System.out.println("3.Remove Room Service Food Menu Items");
				System.out.println("0.Back to previous level");
				System.out.println("-------------------------------------");
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					createFoodItems();
					break;
				case 2:
					updateFoodItems();
					break;
				case 3:
					removeFoodItems();
					break;
				case 0:
					break;
				default:
					System.out.println("Sorry, invalid choice"); 
					break;
				}
			} while (choice > 0);
			FoodController.getInst().SaveDB();
		} catch (InputMismatchException e) {
			System.out.println("Invaild Choice, enter choice again.");
		}
	}


	private void createFoodItems() {
		sc = new Scanner(System.in);
		String foodName = null;
		Double foodPrice = 0.0;
		String foodDescription = null;

		try{
			System.out.println("Enter food name: "); 
			foodName = sc.nextLine();
			if (checkExistingFood(foodName) != null) {
				System.out.println("Food already exists in the system"); 
				return;
			} else {
				System.out.println("Enter food price: $ "); 
				foodPrice = sc.nextDouble();
				sc.nextLine();
				System.out.println("Food preparation description: ");
				foodDescription = sc.nextLine();
				Food food = new Food(foodName, foodPrice, foodDescription);
				FoodController.getInst().addFood(food);
				System.out.println(food.getfoodName() + " has been created.");
			} }
		
			catch(InputMismatchException e){System.out.println("Sorry, invalid input! Please enter again."); }
		
	}


	public void removeFoodItems() {
		Food food = null;
		do
			food = searchFood();
		while (food == null);
		System.out.println("Do you want to delete the food " + food.getfoodName() + " ? (Y-Yes, N-No)"); //changed
		String foodName = food.getfoodName();
		try {
			char reply = sc.next().charAt(0);
			if (reply == 'Y' || reply == 'y') {
				FoodController.getInst().removeFood(food);
				System.out.println(foodName + "has been removed!");
			}
			else
				System.out.println("Invaild input, enter choice again."); //changed
		}
		catch (InputMismatchException e) {
			System.out.println("Invaild input, enter choice again."); //changed
		}

	}


	private Food searchFood() {
		String foodName;
		sc = new Scanner(System.in);
		System.out.println("Enter food name: "); //changed
		foodName = sc.nextLine().toUpperCase();

		Food rFood = FoodController.getInst().getFood(foodName);
		if (rFood != null) {
			return rFood;
		} else {
			System.out.println("Food not found. Please enter again."); //changed
			return null;
		}
	}

	private Food checkExistingFood(String foodName) {
		Food rFood = FoodController.getInst().getFood(foodName);
		if (rFood != null) {
			return rFood;
		} else {
			return null;
		}
	}

	private void updateFoodItems() {
		int choice2;
		Food food = null;
		do
			food = searchFood();
		while (food == null);

		sc = new Scanner(System.in);
		String name = food.getfoodName();
		Double price = food.getfoodPrice();
		String description = food.getfoodDescription();

		try {
			do {
				System.out.println("---------------------------------------"
									+"Select Field to Update (Enter 0 to end)\n"  //swapped
									+ "1. Name\n"
									+ "2. Description\n"
									+ "3. Price\n"
									+ "---------------------------------------");

				choice2 = sc.nextInt();
				switch (choice2) {
					case 1:
						System.out.println("Enter New Name: ");
						if (sc.nextLine() != null) {
							name = sc.nextLine();
							if (checkExistingFood(name) != null) {
								System.out.println("Name already exists in the system.");
								break;
							} else {
								food.setfoodName(name);
								System.out.println("New food name updated");
							}
						}
						break;
					case 2:
						System.out.println("Enter new food description: "); //swapped with price
						if (sc.nextLine() != null) {
							description = sc.nextLine();
							food.setfoodDescription(description);
							System.out.println("New food description saved");
						}
						
						break;
					case 3:
						System.out.println("Enter new price: ");
						if (sc.nextLine() != null) {
							price = sc.nextDouble();
							food.setfoodPrice(price);
							System.out.println("New price saved"); 
						}
						break;
					case 0:
						FoodController.getInst().updateFood(food.getfoodName(), price, description);
						System.out.println("Food Details Updated!");
						break;
				}
			} while (choice2 > 0 && choice2 <= 3);
		} catch (InputMismatchException e) {
			System.out.println("Invaild input, enter choice again."); //changed
		}
	}
}