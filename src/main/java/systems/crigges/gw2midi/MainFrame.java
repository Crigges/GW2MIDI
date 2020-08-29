package systems.crigges.gw2midi;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import java.awt.event.ItemListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;

public class MainFrame implements VisibleLog {

	private JFrame frame;
	private JComboBox<Object> midiSelectBox;
	private JTextArea logTextArea;
	private JScrollPane scrollPane;
	private GW2MIDI gw2Midi;
	private JComboBox<Instrument> comboBox;
	private JLabel lblNewLabel;
	private JSpinner doubleSwapDelaySpinner;
	private JComboBox<PianoScale> pianoScaleSelector;

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		// new MidiEmulator();
		gw2Midi = new GW2MIDI(this);
		initialize();
		setAvailableMidiDevices();
		log("Welcome to GW2Midi by Crigges.8735 select an input Midi device to start");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(null);
		frame.setBounds(100, 100, 772, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblMidiDevice = new JLabel("Midi Device:");
		midiSelectBox = new JComboBox<Object>();
		midiSelectBox.addItem("Select Device:");
		midiSelectBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED && event.getItem() != null && event.getItem() instanceof Info) {
					try {
						gw2Midi.setCurrentDevice((Info) event.getItem());
						log("Listening for input from " + ((Info) event.getItem()).toString());
					} catch (MidiUnavailableException e) {
						log("Failed to Use Selected Midi Device: " + e.getMessage());
					}
				}
			}
		});

		JLabel lblStartKey = new JLabel("Piano Scale");

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(130, 135, 144)));

		JLabel lblReceivedInput = new JLabel("Console");

		comboBox = new JComboBox<Instrument>();
		comboBox.setModel(new DefaultComboBoxModel<Instrument>(Instrument.values()));

		lblNewLabel = new JLabel("Instrument");

		JLabel lblNewLabel_1 = new JLabel("Double Swap Delay");

		doubleSwapDelaySpinner = new JSpinner();
		doubleSwapDelaySpinner.setModel(new SpinnerNumberModel(100, 0, 1000, 1));

		pianoScaleSelector = new JComboBox<PianoScale>();
		pianoScaleSelector.setModel(new DefaultComboBoxModel<PianoScale>(PianoScale.values()));
		
		octaveTranspose = new JComboBox();
		octaveTranspose.setModel(new DefaultComboBoxModel(new String[] {"+0", "+12", "-12", "+24", "-24", "+36", "-36"}));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(midiSelectBox, 0, 263, Short.MAX_VALUE)
								.addComponent(lblMidiDevice, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblStartKey)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(pianoScaleSelector, 0, 205, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(octaveTranspose, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
							.addGap(193)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1)
								.addComponent(doubleSwapDelaySpinner, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblReceivedInput))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMidiDevice)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(midiSelectBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStartKey)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(doubleSwapDelaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pianoScaleSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(octaveTranspose, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblReceivedInput)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
					.addContainerGap())
		);

		logTextArea = new JTextArea();
		logTextArea.setFont(new Font("Consolas", Font.PLAIN, 16));
		logTextArea.setBorder(new EmptyBorder(3, 3, 3, 3));
		logTextArea.setEditable(false);
		scrollPane.setViewportView(logTextArea);
		frame.getContentPane().setLayout(groupLayout);
	}

	private List<MidiDevice.Info> midiDeviceInfos = new ArrayList<MidiDevice.Info>();
	private JComboBox octaveTranspose;
	
	public void setAvailableMidiDevices() {
		Thread refreshThread = new Thread(() -> {
			while(true) {
				MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
				for (MidiDevice.Info deviceInfo : infos) {
					if(!midiDeviceInfos.contains(deviceInfo)) {
						midiSelectBox.addItem(deviceInfo);
						midiDeviceInfos.add(deviceInfo);
					}
				}
				for (Iterator<Info> iterator = midiDeviceInfos.iterator(); iterator.hasNext();) {
					Info info = (Info) iterator.next();
					if(!Arrays.asList(infos).contains(info)) {
						midiSelectBox.removeItem(info);
						iterator.remove();
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		});
		refreshThread.setDaemon(true);
		refreshThread.start();
	}

	public void log(String s) {
		logTextArea.setText(logTextArea.getText() + s + System.lineSeparator());
		JScrollBar bar = scrollPane.getVerticalScrollBar();
		bar.setValue(bar.getMaximum());
	}

	public PianoScale getStartKey() {
		return (PianoScale) pianoScaleSelector.getSelectedItem();
	}

	public int getDoubleSwapDelay() {
		return (int) doubleSwapDelaySpinner.getValue();
	}
	
	public int getOctaveTranspose() {
		return Integer.parseInt(octaveTranspose.getSelectedItem().toString().substring(1));
	}

	public int getInstrument() {
		// TODO Auto-generated method stub
		return 0;
	}
}
