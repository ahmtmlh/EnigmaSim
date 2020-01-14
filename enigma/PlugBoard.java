package enigma;

public class PlugBoard {

	private char[][] wirings;
	private String config;
	private String delimiter;
	
	public PlugBoard(String pairs, String delimiter) {
		this.config = pairs.toUpperCase().trim();
		this.delimiter = delimiter;
		setWirings();
	}
	
	/**
	 * Standart plugboard settings
	 */
	public PlugBoard() {
		this("AE BJ KZ WQ YS PM LO IC NV DT" , " ");
	}
	
	public String[] getWirings() {
		return config.split(delimiter);
	}
	
	public void setWirings(String pairs, String delimiter) {
		this.config = pairs;
		this.delimiter = delimiter;
		setWirings();
	}

	/**
	 * Sets the plugboard as requested
	 * @param pairs		Character pairs for the wirings.
	 * @param delimiter	Delimiter for splitting the pairs string into pairs.
	 */
	private void setWirings() {
		if(wirings == null)
			this.wirings = new char[10][2];
		int i = 0;
		for(String pair : config.split(delimiter)) {
			wirings[i][0] = pair.charAt(0);
			wirings[i][1] = pair.charAt(1);
			i++;
		}
	}
	
	/**
	 * Applies the plugboard
	 * @param c Character to apply 
	 * @return Swapped character
	 */
	public char runThrough(char c) {
		char retVal = c;
		for (int i = 0; i < wirings.length; i++) {
			if(wirings[i][0] == c || wirings[i][1] == c) {
				retVal = (c == wirings[i][0]) ? wirings[i][1] : wirings[i][0];
			}
		}
		return retVal;
	}
	
	
	
}
