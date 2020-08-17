package systems.crigges.gw2midi;

public enum PianoScale {
	C_Major("C Major", 0, true), D_Major("D Major", 2, true), E_Major("E Major", 4, true), 
	F_Major("F Major", 5, true), G_Major("G Major", 7, true), A_Major("A Major", 9, true),
	B_Major("B Major", 11, true), CS_Major("C# Major", 1, true), DS_Major("D# Major", 3, true),
	FS_Major("F# Major", 6, true), GS_Major("G# Major", 8, true), AS_Major("A# Major", 10, true);
	
	private String name;
	private int startOffset;
	private boolean isMajor;
	
	private PianoScale(String name, int startOffset, boolean isMajor) {
		this.name = name;
		this.startOffset = startOffset;
		this.isMajor = isMajor;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public boolean isMajor() {
		return isMajor;
	}

}
