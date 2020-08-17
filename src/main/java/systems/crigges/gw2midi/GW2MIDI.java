package systems.crigges.gw2midi;

import javax.sound.midi.*;

public class GW2MIDI {
	private MidiDevice currentDevice = null;
	private MainFrame gui;
	private KeyEmulator keyEmulator;

	public GW2MIDI(MainFrame gui) {
		this.gui = gui;
		keyEmulator = new KeyEmulator(gui);
	}

	public void setCurrentDevice(MidiDevice.Info newDevice) throws MidiUnavailableException {
		if (currentDevice != null) {
			currentDevice.close();
		}
		currentDevice = MidiSystem.getMidiDevice(newDevice);
		Transmitter trans = currentDevice.getTransmitter();
		trans.setReceiver(new MidiInputReceiver());
		currentDevice.open();
	}

//	public static void listenthrows InterruptedException {
//		MidiEmulator emu = new MidiEmulator();
//		MidiDevice device;
//		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
//		for (int i = 0; i < infos.length; i++) {
//			try {
//				device = MidiSystem.getMidiDevice(infos[i]);
//				System.out.println(infos[i]);
//
//				List<Transmitter> transmitters = device.getTransmitters();
//
////				for (int j = 0; j < transmitters.size(); j++) {
////					// create a new receiver
////					transmitters.get(j).setReceiver(
////							// using my own MidiInputReceiver
////							new MidiInputReceiver(device.getDeviceInfo().toString()));
////				}
//
//				Transmitter trans = device.getTransmitter();
//				trans.setReceiver(new MidiInputReceiver(device.getDeviceInfo().toString()));
//
//				// open each device
//				device.open();
//				// if code gets this far without throwing an exception
//				// print a success message
//				System.out.println(device.getDeviceInfo() + " Was Opened");
//
//			} catch (MidiUnavailableException e) {
//				System.out.println("Failed to Open");
//				//e.printStackTrace();
//			}
//		}
//	}

	int[] majorMap = { 0, 1000, 1, 1000, 2, 3, 1000, 4, 1000, 5, 1000, 6 };
	int[] minorMap = { 0, 1000, 1, 2, 1000, 3, 1000, 4, 5, 1000, 6, 1000 };

	//According to some sources middle c is always 60 so low c should be 48
	
	class MidiInputReceiver implements Receiver {

		public void send(MidiMessage msg, long timeStamp) {
			byte[] arr = msg.getMessage();
			if (arr[0] != -112 || arr[2] == 0) {
				return;
			}
			PianoScale scale = gui.getStartKey();
			int startKey = scale.getStartOffset() + 48;
			int offset = (arr[1]-startKey) / 12 * 7;
			int key;
			if(scale.isMajor()) {
				key = offset + majorMap[(arr[1] - startKey) % 12];
			}else {
				key = offset + minorMap[(arr[1] - startKey) % 12];
			}
			gui.log("Received Key " + arr[1] + " which translates to " + key);
			keyEmulator.play(key);
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub

		}

	}
}
