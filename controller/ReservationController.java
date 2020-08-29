package hotelsystem.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import hotelsystem.entity.Reservation;
import hotelsystem.entity.RoomStatus;

@SuppressWarnings("serial")
public class ReservationController implements Serializable{
	private static ReservationController inst = null;
	private final ArrayList<Reservation> reservationList = new ArrayList<>();
	
	
	private ReservationController() {}
	
	
	public static ReservationController getInst() {
		if (inst == null) {
            inst = new ReservationController();
        }
        return inst;
	}

	 
	public void updateReservation(Reservation reservation) {
		reservationList.remove(reservation);
		reservationList.add(reservation);
		storeData();
	}
	
	
	public Reservation getReservation(String rCode) {
		for (Reservation reservation : reservationList) {
            if (reservation.getReservationCode().equals(rCode))
                return reservation;
        }
        return null;
	}

     
	public Reservation getReservationByID(int rID) {
		for (Reservation reservation : reservationList) {
            if (reservation.getReservationID() == rID)
                return reservation;
        }
        return null;
	}


	public void addReservation(Reservation reservation) {
		reservationList.add(reservation);
        storeData();
	}
	
    
    
	public void checkExpiredRoom(){
		Date current = new Date();
		for (Reservation r : reservationList) {
			ArrayList<RoomStatus> rsList = r.getStatusList();
			for (RoomStatus rs : rsList) {
				Date newDate = new Date(rs.getDateFrom().getTime() + TimeUnit.HOURS.toMillis(1));
				if(current.after(newDate) && rs.getStatus().equals("Reserved")) {
					RoomStatusController.getInst().updateStatustoExpired(rs);
					r.setStatus("Expired");
					updateReservation(r);
					System.out.println("Reservation " + r.getReservationCode() + " already Expired");
				}
			}
		}
	}
	
	
    
	public ArrayList<Reservation> getAllReservation() {
		return reservationList;
	}
    
	
    public void loadData () {
        
        ObjectInputStream objinpstr;
        try {
        	objinpstr = new ObjectInputStream(new FileInputStream("DB/Reservation.ser"));

            int no_Records = objinpstr.readInt();
            Reservation.setIncID(objinpstr.readInt());
            System.out.println("ReservationController: " + no_Records + " Entries Loaded");
            for (int i = 0; i < no_Records; i++) {
            	reservationList.add((Reservation) objinpstr.readObject());
               }
        } catch (IOException | ClassNotFoundException ex2 ) {
        	ex2.printStackTrace();
        }
    }
    
	public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/Reservation.ser"));
            out.writeInt(reservationList.size());
            out.writeInt(Reservation.getIncID());
            for (Reservation reservation : reservationList)
                out.writeObject(reservation);
            out.close();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
}
