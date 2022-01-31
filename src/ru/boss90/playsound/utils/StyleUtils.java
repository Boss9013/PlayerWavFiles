package ru.boss90.playsound.utils;

import java.awt.*;
import javax.swing.*;

public class StyleUtils {

	public static void updateStyleButton(JButton button, Font font) {
	    button.setBackground(new Color(22,26,30));
	    button.setForeground(Color.WHITE);
	    button.setFont(font);
	}
	
	public static void updateStyleText(JLabel button, Font font) {
	    button.setForeground(Color.WHITE);
	    button.setFont(font);
	}
}
