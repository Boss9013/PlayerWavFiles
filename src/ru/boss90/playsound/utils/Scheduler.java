package ru.boss90.playsound.utils;

import java.util.TimerTask;

public class Scheduler extends TimerTask{

	@Override
	public void run() {
	
		SystemUtils.clearingRAM();
		
	}
}