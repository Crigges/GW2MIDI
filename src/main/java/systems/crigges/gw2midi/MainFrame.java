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
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;

public class MainFrame {

	private JFrame frame;
	private JComboBox<MidiDevice.Info> midiSelectBox;
	private JSpinner startKeySpinner;
	private JTextArea logTextArea;
	private JScrollPane scrollPane;
	private GW2MIDI gw2Midi;
	private JComboBox comboBox;
	private JLabel lblNewLabel;

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
		new MidiEmulator();
		gw2Midi = new GW2MIDI(this);
		initialize();
		setAvailableMidiDevices();	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 772, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblMidiDevice = new JLabel("Midi Device:");

		midiSelectBox = new JComboBox<Info>();
		midiSelectBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED && event.getItem() != null) {
					try {
						gw2Midi.setCurrentDevice((Info) event.getItem());
					} catch (MidiUnavailableException e) {
						log("Failed to Use Selected Midi Device: " + e.getMessage());
					}
				}
			}
		});

		JLabel lblStartKey = new JLabel("Start Key (Low C)");

		startKeySpinner = new JSpinner();
		startKeySpinner.setModel(new SpinnerNumberModel(0, 0, 230, 1));

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(130, 135, 144)));

		JLabel lblReceivedInput = new JLabel("Console");
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(Instrument.values()));
		
		lblNewLabel = new JLabel("Instrument");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(midiSelectBox, 0, 263, Short.MAX_VALUE)
								.addComponent(lblMidiDevice, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblStartKey, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
								.addComponent(startKeySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)))
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
					.addComponent(lblStartKey)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(startKeySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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

	public void setAvailableMidiDevices() {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info deviceInfo : infos) {
			midiSelectBox.addItem(deviceInfo);
		}
	}

	public void log(String s) {
		logTextArea.setText(logTextArea.getText() + s + System.lineSeparator());
		JScrollBar bar = scrollPane.getVerticalScrollBar();
		bar.setValue(bar.getMaximum());
	}
	
	public int getStartKey() {
		return (int) startKeySpinner.getValue();
	}

	public int getInstrument() {
		// TODO Auto-generated method stub
		return 0;
	}

}
