package enigma;

public class EnigmaMachine {
	
	private Rotor rotor1;
	private Rotor rotor2;
	private Rotor rotor3;
	private Rotor reflector;
	private PlugBoard plugBoard;
	
	
	/**
	 * Default constructor function.
	 */
	public EnigmaMachine() {
		this(1,2,3);
	}
	
	/**
	 * Initializes the machine
	 * @param rt1 Number of the rotor, for rotor1. Index base is 1
	 * @param rt2 Number of the rotor, for rotor2. Index base is 1
	 * @param rt3 Number of the rotor, for rotor3. Index base is 1
	 */
	public EnigmaMachine(int rt1, int rt2, int rt3) {
		RotorName[] values = RotorName.values();
		setRotors(values[rt1-1], values[rt2-1], values[rt3-1]);
		plugBoard = new PlugBoard();
		reflector = new Rotor();
	}
	
	/**
	 * Sets rotor.
	 * @param rt1 Name of the rotor from the enumerated class
	 * @param rt2 Name of the rotor from the enumerated class
	 * @param rt3 Name of the rotor from the enumerated class
	 */
	
	public void setRotors(RotorName rt1, RotorName rt2, RotorName rt3) {
		rotor1 = new Rotor(rt1);
		rotor2 = new Rotor(rt2);
		rotor3 = new Rotor(rt3);
	}
	
	/**
	 * This will encrypt the whole string
	 */
	public String encrypt(String str) {
		str = str.toUpperCase();
		char[] temp = new char[str.length()];
		for (int i = 0; i < str.length(); i++) {
			temp[i] = encrypt(str.charAt(i));
		}
		return new String(temp);
	}
	
	/**
	 * Encrypts the given character and return the encrypted character
	 * @param c Character to encrypt
	 * @return	Encrypted char
	 */
	private char encrypt(char c) {
		c = plugBoard.runThrough(c);
		c = rotor1.getOutput(c, false);
		c = rotor2.getOutput(c, false);
		c = rotor3.getOutput(c, false);
		char output = reflector.getOutput(c, false);
		output = rotor3.getOutput(output, true);
		output = rotor2.getOutput(output, true);
		output = rotor1.getOutput(output, true);
		output = plugBoard.runThrough(output);

		if (!Rotor.checkWhiteListChar(output))
			moveRotors();

		return output;
	}
	
	/**
	 * Increments rotor positions
	 */
	private void moveRotors() {
		rotor1.incrementPosition();
		if(rotor1.checkFullRev()) {
			rotor2.incrementPosition();
			if(rotor2.checkFullRev()) {
				rotor3.incrementPosition();
				rotor3.checkFullRev();
			}
		}
	}

	public boolean rollbackRotors() {

		if (rotor1.getPosition() == 0 && rotor2.getPosition() == 0 && rotor3.getPosition() == 0)
			return false;

		rotor1.decrementPosition();
		if (rotor1.checkFullRev()) {
			rotor2.decrementPosition();
			if(rotor2.checkFullRev()) {
				rotor3.decrementPosition();
				rotor3.checkFullRev();
			}
		}

		return true;
	}
	
	public int[] getRotorPositions() {
		return new int[]{rotor1.getPosition(), rotor2.getPosition(), rotor3.getPosition()};
	}
	
	public void setRotorPositions(int pos1, int pos2, int pos3) {
		rotor1.setPosition(pos1);
		rotor2.setPosition(pos2);
		rotor3.setPosition(pos3);
	}
	
	public String[] getPlugBoardWirings() {
		return plugBoard.getWirings();
	}
	
	public void setPlugBoardWirings(String pairs, String delimiter) {
		plugBoard.setWirings(pairs, delimiter);
	}
	
	/**
	 * DEBUG ONLY
	 */
	public void printRotorInfo() {
		System.out.println(rotor1);
		System.out.println(rotor2);
		System.out.println(rotor3);
		System.out.println("------------");
	}
	

}
