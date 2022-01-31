package ru.boss90.playsound.main;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.Timer;
import ru.boss90.playsound.utils.Scheduler;

public class Main {
	
	public static void main(String[] args) throws FontFormatException, IOException {
			
		GUI app = new GUI();
		app.setVisible(true);
		
		Timer timer = new Timer();
	    Scheduler scheduler = new Scheduler();
	    timer.scheduleAtFixedRate(scheduler, 1000*20, 1000*20);
	
	}
}