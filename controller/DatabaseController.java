package hotelsystem.controller;

import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DatabaseController {
    protected static final String DATABASE_DIR = "DB/";
	protected static final String SEPARATOR = "|";
    private final List<String> Data;
    private File file;

    public DatabaseController() {
        Data = new ArrayList<>();

        file = new File(DATABASE_DIR);
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception ex) {
                System.out.println("Sorry, you do not have the corect authentication. Please run as an administrator");
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex); 
            }
        }
    }

    public boolean checkFileExist(String filepath) {
        file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception ex) {
                System.out.println("Sorry, you do not have the corect authentication. Please run as an administrator");
                return false;
            }
        }
        return file.exists();
    }

    public List<String> read(String fileName) throws IOException {
        Data.clear();

        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            while (scanner.hasNextLine()) {
                Data.add(scanner.nextLine());
            }
        }  catch(IOException io) {
        	throw io;
        }	
        return Data;
    }
    
    public void write(String fileName, List<String> d) {
        try (PrintWriter outWriter = new PrintWriter(new FileWriter(fileName))) {
            for (String i : d) {
                outWriter.println(i);
            }
        } catch (Exception io) {
            System.out.println("Failed to save to database");
        }
    }
    
    protected abstract boolean LoadDB();

    protected abstract void SaveDB();


}