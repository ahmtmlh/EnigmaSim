package frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import enigma.RotorName;
import enigma.EnigmaMachine;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class MainFrame {
	
	private class SpinnerOnChangeListener implements ChangeListener{
		private void updateRotorPositions() {
			machine.setRotorPositions((int)rotor1PositionSpinner.getValue(),
					(int)rotor2PositionSpinner.getValue(), 
					(int)rotor3PositionSpinner.getValue());
		}
		@Override
		public void stateChanged(ChangeEvent e) {
			updateRotorPositions();
			frame.requestFocus();
		}
	}
	
	private class ComboBoxOnChangeListener implements ActionListener{

		private void updateRotors() {
			int[] currentPositions = machine.getRotorPositions();
			String rt3str = rotor3SelectorComboBox.getSelectedItem().toString();
			String rt2str = rotor2SelectorComboBox.getSelectedItem().toString();
			String rt1str = rotor1SelectorComboBox.getSelectedItem().toString();
			machine.setRotors(RotorName.valueOf(rt1str), RotorName.valueOf(rt2str), RotorName.valueOf(rt3str));
			machine.setRotorPositions(currentPositions[0], currentPositions[1], currentPositions[2]);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			updateRotors();				
			frame.requestFocus();
		}
	}
	
	private class FrameKeyListener implements KeyListener{

		private boolean keyPressed = false;
		private KeyEvent pressedKey;
		
		private void updateSpinners() {
			int[] currentPos = machine.getRotorPositions();
			rotor1PositionSpinner.setValue(currentPos[0]);
			rotor2PositionSpinner.setValue(currentPos[1]);
			rotor3PositionSpinner.setValue(currentPos[2]);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(!keyPressed && isValidChar(e.getKeyChar())) {
				pressedKey = e;
				keyPressed = true;
				String res = machine.encrypt(String.valueOf(e.getKeyChar()));
				updateSpinners();
				// UPDATE TEXT FIELD
				resultTextField.setText(resultTextField.getText()+res);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(pressedKey != null && e.getKeyCode() == pressedKey.getKeyCode()) {
				keyPressed = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			//NOT USED
		}
	}
	
	private EnigmaMachine machine;
	
	private JFrame frame;
	
	private JSpinner rotor1PositionSpinner;
	private JSpinner rotor2PositionSpinner;
	private JSpinner rotor3PositionSpinner;
	
	private JComboBox<RotorName> rotor1SelectorComboBox;
	private JComboBox<RotorName> rotor2SelectorComboBox;
	private JComboBox<RotorName> rotor3SelectorComboBox;
	
	private JTextField rotor3Specifier;
	private JTextField rotor2Specifier;
	private JTextField rotor1Specifier;
	private JTextField txtPosition;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField resultTextField;
	
	private JTextField[] wiringTextFields;
	private JLabel lblPlugboard;
	private JLabel lblWiringChangeStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		machine = new EnigmaMachine();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Enigma Sim Len Garda��m");
		frame.setBounds(100, 100, 568, 420);
		frame.setFocusable(true);
		frame.addKeyListener(new FrameKeyListener());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		// TOP PANEL
		
		ComboBoxOnChangeListener comboListener = new ComboBoxOnChangeListener();
		rotor3SelectorComboBox = new JComboBox<>();
		rotor3SelectorComboBox.setModel(new DefaultComboBoxModel<>(RotorName.values()));
		rotor3SelectorComboBox.setSelectedIndex(0);
		rotor3SelectorComboBox.setBounds(22, 38, 109, 22);
		rotor3SelectorComboBox.addActionListener(comboListener);
		panel.add(rotor3SelectorComboBox);
		
		rotor3Specifier = new JTextField();
		rotor3Specifier.setBackground(Color.WHITE);
		rotor3Specifier.setEditable(false);
		rotor3Specifier.setText("         Rotor 3");
		rotor3Specifier.setBounds(22, 13, 109, 22);
		panel.add(rotor3Specifier);
		rotor3Specifier.setColumns(10);
		
		rotor2Specifier = new JTextField();
		rotor2Specifier.setText("         Rotor 2");
		rotor2Specifier.setEditable(false);
		rotor2Specifier.setColumns(10);
		rotor2Specifier.setBackground(Color.WHITE);
		rotor2Specifier.setBounds(216, 13, 109, 22);
		panel.add(rotor2Specifier);
		
		rotor2SelectorComboBox = new JComboBox<RotorName>();
		rotor2SelectorComboBox.setModel(new DefaultComboBoxModel(RotorName.values()));
		rotor2SelectorComboBox.setSelectedIndex(1);
		rotor2SelectorComboBox.setBounds(216, 38, 109, 22);
		rotor2SelectorComboBox.addActionListener(comboListener);
		panel.add(rotor2SelectorComboBox);
		
		rotor1Specifier = new JTextField();
		rotor1Specifier.setText("         Rotor 1");
		rotor1Specifier.setEditable(false);
		rotor1Specifier.setColumns(10);
		rotor1Specifier.setBackground(Color.WHITE);
		rotor1Specifier.setBounds(419, 13, 109, 22);
		panel.add(rotor1Specifier);
		
		rotor1SelectorComboBox = new JComboBox<RotorName>();
		rotor1SelectorComboBox.setModel(new DefaultComboBoxModel(RotorName.values()));
		rotor1SelectorComboBox.setSelectedIndex(2);
		rotor1SelectorComboBox.setBounds(419, 38, 109, 22);
		rotor1SelectorComboBox.addActionListener(comboListener);
		panel.add(rotor1SelectorComboBox);

		// ROTOR POSITIONS
		
		txtPosition = new JTextField();
		txtPosition.setEditable(false);
		txtPosition.setText("Position:");
		txtPosition.setBounds(22, 73, 55, 22);
		panel.add(txtPosition);
		txtPosition.setColumns(10);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setText("Position:");
		textField.setColumns(10);
		textField.setBounds(216, 73, 55, 22);
		panel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setText("Position:");
		textField_1.setColumns(10);
		textField_1.setBounds(419, 73, 55, 22);
		panel.add(textField_1);
		
		SpinnerOnChangeListener listener = new SpinnerOnChangeListener();
		
		rotor1PositionSpinner = new JSpinner();
		rotor1PositionSpinner.setModel(new SpinnerNumberModel(0,0,26,1));
		rotor1PositionSpinner.setBounds(473, 73, 55, 22);
		rotor1PositionSpinner.addChangeListener(listener);
		((JSpinner.DefaultEditor)rotor1PositionSpinner.getEditor()).getTextField().setEditable(false);
		panel.add(rotor1PositionSpinner);
		
		rotor2PositionSpinner = new JSpinner();
		rotor2PositionSpinner.setModel(new SpinnerNumberModel(0,0,26,1));
		rotor2PositionSpinner.setBounds(270, 73, 55, 22);
		rotor2PositionSpinner.addChangeListener(listener);
		((JSpinner.DefaultEditor)rotor2PositionSpinner.getEditor()).getTextField().setEditable(false);
		panel.add(rotor2PositionSpinner);
		
		rotor3PositionSpinner = new JSpinner();
		rotor3PositionSpinner.setModel(new SpinnerNumberModel(0, 0, 26, 1));
		rotor3PositionSpinner.setBounds(76, 73, 55, 22);
		rotor3PositionSpinner.addChangeListener(listener);
		((JSpinner.DefaultEditor)rotor3PositionSpinner.getEditor()).getTextField().setEditable(false);
		panel.add(rotor3PositionSpinner);
		
		// OUTPUT AREA
		
		resultTextField = new JTextField();
		resultTextField.setEditable(false);
		resultTextField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		resultTextField.setBounds(22, 151, 506, 50);
		panel.add(resultTextField);
		resultTextField.setColumns(10);
		
		JLabel lblEncryprtedText = new JLabel("Encryprted Text");
		lblEncryprtedText.setBounds(22, 122, 109, 16);
		panel.add(lblEncryprtedText);
		
		lblPlugboard = new JLabel("Plugboard");
		lblPlugboard.setBounds(22, 214, 110, 16);
		panel.add(lblPlugboard);
		
		// PLUGBOARD
		
		JButton btnChangeWirings = new JButton("Change Wirings");
		btnChangeWirings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				for (int i = 0; i < wiringTextFields.length; i++) {
					wiringTextFields[i].setEditable(!wiringTextFields[i].isEditable());
				}
				if(btnChangeWirings.getText().equals("Apply")) {
					if(checkValidWiring()) {
						String delimiter = " ";
						StringBuilder newWirings = new StringBuilder("");
						for (JTextField wiring : wiringTextFields) {
							// Convert text to upper case to make it look stable
							wiring.setText(wiring.getText().toUpperCase());
							newWirings.append(wiring.getText() + delimiter);
						}
						machine.setPlugBoardWirings(newWirings.toString(), delimiter);
						lblWiringChangeStatus.setForeground(Color.black);
						lblWiringChangeStatus.setText("Changes Applied");
					} else {
						String[] currentWirings = machine.getPlugBoardWirings();
						for (int i = 0; i < wiringTextFields.length; i++) {
							wiringTextFields[i].setText(currentWirings[i]);
						}
						lblWiringChangeStatus.setForeground(Color.red);
						lblWiringChangeStatus.setText("Invalid Character or Repeated Character");
					}
					btnChangeWirings.setText("Change Wirings");
				} else {
					btnChangeWirings.setText("Apply");
				}
				frame.requestFocus();
			}
		});
		btnChangeWirings.setBounds(401, 335, 137, 25);
		panel.add(btnChangeWirings);
		
		lblWiringChangeStatus = new JLabel("");
		lblWiringChangeStatus.setForeground(Color.RED);
		lblWiringChangeStatus.setBounds(22, 335, 367, 25);
		panel.add(lblWiringChangeStatus);
		
		//WIRING PAIRS	
		
		int x = 22;
		int y = 248;
		wiringTextFields = new JTextField[10];
		String[] texts = machine.getPlugBoardWirings();
		for (int i = 0; i < wiringTextFields.length; i++) {
			wiringTextFields[i] = new JTextField();
			wiringTextFields[i].setEditable(false);
			wiringTextFields[i].setText(texts[i]);
			wiringTextFields[i].setBackground(Color.WHITE);
			
			wiringTextFields[i].setBounds(x, y, 85, 25);
			x+=101;
			if(x > 430) {
				x = 22;
				y += 40;
			}
			wiringTextFields[i].setColumns(10);
			panel.add(wiringTextFields[i]);
		}
		frame.requestFocus();
		
	}
	
	
	/**
	 * Checks if the given String for the plugboard settings are valid or not.
	 * @return True if valid, false if not.
	 */
	private boolean checkValidWiring() {
		Set<Character> charSet = new HashSet<>();
		for (JTextField wiring : wiringTextFields) {
			String temp = wiring.getText();
			if(temp.length() != 2) {
				return false;
			} else if (isValidChar(temp.charAt(0)) && isValidChar(temp.charAt(1))){
				for (int i = 0; i < temp.length(); i++) {
					if(charSet.contains(temp.charAt(i))) {
						return false;
					} else {
						charSet.add(temp.charAt(i));
					}
				}
			}
		}
		return true;
	}
	
	
	/**
	 * Check if pressed key is one of the valid characters
	 * @param c Char code of pressed key
	 * @return True if pressed key is valid, false if not
	 */
	private boolean isValidChar(char c) {
		c = Character.toUpperCase(c);
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(String.valueOf(c));
	}
	
}
