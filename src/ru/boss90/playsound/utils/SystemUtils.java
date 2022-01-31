package ru.boss90.playsound.utils;

import java.io.IOException;

public class SystemUtils {

	public static void clearingRAM() {
		
		long usedMBytes = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) / 1048576;
		
		if(usedMBytes >= 150) {
			System.gc();
		}
		if(usedMBytes >= 200) {
			try {
			   Runtime.getRuntime().exec("cmd /c start cmd.exe /K && cd /D d:/ && java -Xmx100M -jar PlaySound.jar");
			} catch (IOException e) {}
			System.exit(0);
		}
	}
}
