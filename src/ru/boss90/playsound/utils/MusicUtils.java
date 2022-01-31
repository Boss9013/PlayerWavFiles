package ru.boss90.playsound.utils;

import java.io.*;
import javax.sound.sampled.*;

public class MusicUtils implements AutoCloseable{
	
		private boolean released = false;
		private AudioInputStream stream = null;
		public static Clip clip = null;
		private FloatControl volumeControl = null;
		public static boolean playing = false;
		
		public MusicUtils(File f) {
			try {
				stream = AudioSystem.getAudioInputStream(f);
				clip = AudioSystem.getClip();
				clip.open(stream);
				clip.addLineListener(new Listener());
				volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				released = true;
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
				exc.printStackTrace();
				released = false;
				
				close();
			}
		}

		// true если звук успешно загружен, false если произошла ошибка
		public boolean isReleased() {
			return released;
		}
		
		// проигрывается ли звук в данный момент
		public boolean isPlaying() {
			return playing;
		}

		// Запуск
		 
		public void play() {
			if (released) {
					clip.stop();
					clip.setFramePosition(0);
					clip.start();
					playing = true;
				}else if (isPlaying() == true) {
					clip.stop();
					clip.close();
					playing = false;
				}
			}
		
		// Останавливает воспроизведение
		public void stop() {
			if (playing) {
				clip.stop();
			}
		}
		
		public void close() {
			if (clip != null)
				clip.close();
			
			if (stream != null)
				try {
					stream.close();
				} catch (IOException exc) {
					exc.printStackTrace();
				}
		}

		// Установка громкости
		 
		public void setVolume(float x) {
			if (x<0) x = 0;
			if (x>1) x = 1;
			float min = volumeControl.getMinimum();
			float max = volumeControl.getMaximum();
			volumeControl.setValue((max-min)*x+min);
		}
		
		// Возвращает текущую громкость (число от 0 до 1)
		public float getVolume() {
			float v = volumeControl.getValue();
			float min = volumeControl.getMinimum();
			float max = volumeControl.getMaximum();
			return (v-min)/(max-min);
		}

		// Дожидается окончания проигрывания звука
		public void join() {
			if (!released) return;
			synchronized(clip) {
				while (playing) {
					try {
						clip.wait();
					} catch (InterruptedException ignored) {}
				}
			}
		}
		
		public synchronized static MusicUtils playSound(String path) {
			File f = new File(path);
			MusicUtils snd = new MusicUtils(f);
			snd.play();
			return snd;
		}

		private class Listener implements LineListener {
			public void update(LineEvent ev) {
				if (ev.getType() == LineEvent.Type.STOP) {
					playing = false;
					synchronized(clip) {
						clip.notify();
						
					}
				}
			}
		}
}