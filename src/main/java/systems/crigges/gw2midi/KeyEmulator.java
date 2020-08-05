package systems.crigges.gw2midi;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyEmulator {
	private static int[] keyMap = {	KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD1,KeyEvent.VK_NUMPAD2,
									KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD4,KeyEvent.VK_NUMPAD5,
									KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD7,KeyEvent.VK_NUMPAD8, 
									KeyEvent.VK_NUMPAD9};
	
	private Robot robot;
	private int currentOctave = 1;
	private Instrument instrument = Instrument.HARP;
	private MainFrame gui;
	
	public KeyEmulator(MainFrame gui) {
		this.gui = gui;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			gui.log("Failed to create KeyEmulator: " + e.getMessage());
		}
	}
	
	public void play(int note) {
		if(note < 0 || note > instrument.getOctaves() * 7 + 1) {
			gui.log("Unplayable Note");
			return;
		}
		int neededOctave = note / 7;
		int key = note % 7;
		while(true) {
			if(neededOctave == currentOctave) {
				press(key + 1);
				break;
			}else if(neededOctave - 1 == currentOctave && key == 0) {
				press(8);
				break;
			}else if(neededOctave > currentOctave) {
				up();
			}else {
				down();
			}
		}
		
		
	}
	
	private void press(int num) {
		robot.keyPress(keyMap[num]);
		robot.keyRelease(keyMap[num]);
	}
	
	private void up() {
		robot.keyPress(KeyEvent.VK_NUMPAD9);
		robot.keyRelease(KeyEvent.VK_NUMPAD9);
		currentOctave++;
	}
	
	private void down() {
		robot.keyPress(KeyEvent.VK_NUMPAD0);
		robot.keyRelease(KeyEvent.VK_NUMPAD0);
		currentOctave--;
	}
	
}
