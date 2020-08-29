package hotelsystem.entity;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Card implements Serializable{
	private static int max = 1;
	private int ID_card;
	private long cardno;
	private int cvc;
	private String name;
	private String exp;
	
	public Card(long cardno, String name, String exp, int cvc)
	{
		this.ID_card= max;
		this.cardno = cardno;
		this.exp = exp;
		this.name = name;
		this.cvc = cvc;
		max++;
	}
	public static int getID_max() { 
		return max; 
	}
    public static void setID_max(int m) { 
    	max = m; 
    }
    public int getID_Card() { 
    	return ID_card;
    }
	public void setID_Card(int c) { 	
		this.ID_card = c; 
	}
	public long getCardno() {
		return cardno;
	}
	public void setCardno(long cardno) {
		this.cardno = cardno;
	}
	public int getCvc() {
		return cvc;
	}
	public void setCvc(int cvc) {
		this.cvc = cvc;
	}
	public String getName() {
		return name;
	}
	public void setName(String n) {
		this.name = n;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
}