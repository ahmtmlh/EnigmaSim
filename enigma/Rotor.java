package enigma;

public class Rotor {

	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private int[] config;
	private int position;
	private boolean fullTurn;
	
	/**
	 * Initializes the rotor
	 * @param name Rotor name, varying from I to V
	 */
	public Rotor(RotorName name) {
		this.position = 0;
		this.fullTurn = false;
		this.config = new int[26];
		char[] alphabet = ALPHABET.toCharArray();
		char[] code = null;
		switch (name) {
			case I:
				code = "VZBRGITYUPSDNHLXAWMJQOFECK".toCharArray();
				break;
			case II:
				code = "JPGVOUMFYQBENHZRDKASXLICTW".toCharArray();
				break;
			case III:
				code = "NZJHGRCXMYSWBOUFAIVLPEKQDT".toCharArray();
				break;
			case IV:
				code = "FKQHTLXOCBJSPDZRAMEWNIUYGV".toCharArray();
				break;
			case V:
				code = "LEYJVCNIXWPBQMDRTAKZGFUHOS".toCharArray();
				break;
			default:
				code = "EJMZALYXVBWFCRQUONTSPIKHGD".toCharArray();
				break;
		}
		for (int i = 0; i < alphabet.length; i++) {
			this.config[(int)(alphabet[i]-65)] = (code[i]-65);
		}
	}
	
	public Rotor() {
		this.position = 0;
		this.fullTurn = false;
		this.config = new int[26];
		char[] alphabet = ALPHABET.toCharArray();
		char[] code = "EJMZALYXVBWFCRQUONTSPIKHGD".toCharArray();
		for (int i = 0; i < alphabet.length; i++) {
			this.config[(int)(alphabet[i]-65)] = (code[i]-65);
		}
	}

	/**
	 * Increments the current position of the rotor.
	 */
	public void incrementPosition() {
		position++;
		if(position >= config.length) {
			fullTurn = true;
			position = 0;
		}
	}

	/**
	 * Get the correct character for the given character, from the configuration of
	 * this rotor.
	 * @param c Character to encrypt.
	 * @param backwards Flag to check if the output comes back from reflector. True if yes, false if not.
	 * @return Encrpyted character according to positioning and configuration of the rotor.
	 */
	
	public char getOutput(char c, boolean backwards) {
		if(backwards) {
			c = Character.toUpperCase(c);
			int idx = 0;
			while(config[idx]+65 != c) {
				idx++;
			}
			idx = idx - position;
			while(idx < 0) {
				idx += config.length;
			}
			return ALPHABET.charAt(idx);
		} else {
			int index = c-65;
			index = (index+position) % config.length;
			return (char)(config[index]+65);
		}
	}
	
	/**
	 * Checks if rotor completed a full revelotion.
	 * @return True if yes, false if not.
	 */
	public boolean checkFullRev() {
		if(fullTurn) {
			fullTurn = false;
			return true;
		}
		return false;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		String retVal = "";
		for (int i = 0; i < config.length; i++) {
			retVal += String.valueOf((char)(config[i]+65));
		}
		return retVal;
	}
}
