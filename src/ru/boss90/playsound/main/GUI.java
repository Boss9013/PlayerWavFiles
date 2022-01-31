package ru.boss90.playsound.main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import ru.boss90.playsound.utils.*;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	public static JButton button = new JButton("Включить проигрывание альбома");
	public static JButton button2 = new JButton("Выключить проигрывание музыки");
	public static JButton button3 = new JButton("Отладка (Анти утечка ОЗУ)");
	public static JLabel nowPlaying = new JLabel("Сейчас ничего не играет");
	public static JLabel settings = new JLabel("Настройки:");
	public static JLabel music = new JLabel("Музыка:");
	public static JTextField countMusic = new JTextField(4);
	public static float volumeInt = 0.9f;
	public static String nowPlayingMusic;
	public static JLabel volume = new JLabel("Громкость: "+volumeInt);
	
	public GUI() throws FontFormatException, IOException{
		
		super("Плеер");
	    setBounds(100, 100, 250, 100);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    Container container = getContentPane();
	    container.setLayout(new GridLayout(3, 2, 2, 2));
	    container.setBackground(new Color(32, 33, 79));
	    Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("D:/Fonts/font.ttf")).deriveFont(15f);
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("D:/Fonts/font.ttf")));
	    countMusic.setToolTipText("Кол-во сколько нужно повторить музыку");
	    countMusic.setBackground(new Color(32, 33, 79));
	    countMusic.setForeground(Color.WHITE);
	    countMusic.setFont(customFont);
	    
	    StyleUtils.updateStyleText(settings, customFont);
	    StyleUtils.updateStyleButton(button, customFont);
	    StyleUtils.updateStyleButton(button2, customFont);
	    StyleUtils.updateStyleButton(button3, customFont);
	    StyleUtils.updateStyleText(music, customFont);
	    StyleUtils.updateStyleText(nowPlaying, customFont);
	    StyleUtils.updateStyleText(volume, customFont);
	    
	    container.add(settings);
	    container.add(countMusic);
	    container.add(button);
	    container.add(button2);
	    container.add(button3);
	    container.add(music);
	    
		File folder = new File("D:/Music/");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    
		     JButton button = new JButton(file.getName());
			 StyleUtils.updateStyleButton(button, customFont);
		     button.addActionListener(new ActionButtonEvent4());
		     container.add(button);
		     
		    }
		}
		
	    container.add(nowPlaying);
	    container.add(volume);
	    
	    button.addActionListener(new ActionButtonEvent());
	    button2.addActionListener(new ActionButtonEvent5());
	    button3.addActionListener(new ActionButtonEvent6());
	    
	}
	
	class ActionButtonEvent implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			
			File folder = new File("D:/Music/");
			File[] listOfFiles = folder.listFiles();

			SystemUtils.clearingRAM();
			
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			    	
			    	nowPlaying.setText("Сейчас играет: "+file.getName().replace(".wav", ""));
			    	MusicUtils.playSound("D:/Music/"+file.getName()).join();
			    	
			    }
			    
			}
			SystemUtils.clearingRAM();
			MusicUtils.clip.stop();
			MusicUtils.clip.close();
		}
	}

	class ActionButtonEvent4 implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			if(MusicUtils.playing) {
				MusicUtils.clip.stop();
		    	nowPlaying.setText("Воспроизведение музыки приостановлено вами.");
				MusicUtils.clip.close();
				MusicUtils.playing=false;
			}
			
			SystemUtils.clearingRAM();
			
			nowPlayingMusic = event.getActionCommand();
			
			if(Integer.valueOf(countMusic.getText()) == 0) {
				nowPlaying.setText("Сейчас играет: "+event.getActionCommand().replace(".wav", ""));
				MusicUtils.playSound("D:/Music/"+event.getActionCommand()).play();
				return;
			}
			
		    for(int i = 0; i<Integer.valueOf(countMusic.getText()); i++) {
			
				nowPlaying.setText("Сейчас играет: "+event.getActionCommand().replace(".wav", ""));
				MusicUtils.playSound("D:/Music/"+event.getActionCommand()).join();
	    	
			}
			
			SystemUtils.clearingRAM();
		}
	}
	
	class ActionButtonEvent5 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			
			if(MusicUtils.playing) {
				
				MusicUtils.clip.stop();
		    	nowPlaying.setText("Воспроизведение музыки приостановлено вами.");
				MusicUtils.clip.close();
				MusicUtils.playing=false;
				
				return;
				
			}
		}
	}
	
	class ActionButtonEvent6 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
		
			try {
			    Runtime.getRuntime().exec("cmd /c start cmd.exe /K && cd /D d:/ && java -jar PlaySound.jar & exit 0");
			} catch (IOException e) {}
			
			System.exit(1);
		}
	}
}