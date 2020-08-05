package systems.crigges.gw2midi;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import de.tobiaserichsen.tevm.TeVirtualMIDI;

public class MidiEmulator {
	private TeVirtualMIDI virtualMidi;
	private ArrayList<Byte[]> notes;
	
	public MidiEmulator() {
		virtualMidi = new TeVirtualMIDI("MidiEmulator");
		notes = new ArrayList<>();
		
		addMidiCommand(40);
		addMidiCommand(41);
		addMidiCommand(42);
		addMidiCommand(43);
		addMidiCommand(44);
		addMidiCommand(45);
		addMidiCommand(46);
		addMidiCommand(47);
		addMidiCommand(48);
		addMidiCommand(49);
		addMidiCommand(50);
		addMidiCommand(51);
		addMidiCommand(52);
		
		loopedPlayback();
	}

	private void addMidiCommand(int note) {
		Byte[] temp = new Byte[3];
		temp[0] = (byte) 0x90;
		temp[1] = (byte) note;
		temp[2] = 1;
		notes.add(temp);
	}
	
	private void loopedPlayback() {
		Thread t = new Thread(() -> {
			while(true) {
				for(Byte[] arr : notes) {
					virtualMidi.sendCommand(ArrayUtils.toPrimitive(arr));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
}
