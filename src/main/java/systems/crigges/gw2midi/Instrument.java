package systems.crigges.gw2midi;

public enum Instrument {
	HARP(3, 9, 0);
	
	private int octaves;
	private int up;
	private int down;
	
	private Instrument(int octaves, int up, int down) {
		this.octaves = octaves;
		this.up = up;
		this.down = down;
	}

	public int getOctaves() {
		return octaves;
	}

	public int getUp() {
		return up;
	}

	public int getDown() {
		return down;
	}
	
	
	
	
}
