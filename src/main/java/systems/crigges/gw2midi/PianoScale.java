package systems.crigges.gw2midi;

public enum PianoScale {
	C_Major("C Major", 0, true), D_Major("D Major", 2, true), E_Major("E Major", 4, true), F_Major("F Major", 5, true),
	G_Major("G Major", 7, true), A_Major("A Major", 9, true), B_Major("B Major", 11, true),
	CS_Major("C# Major", 1, true), DS_Major("D# Major", 3, true), FS_Major("F# Major", 6, true),
	GS_Major("G# Major", 8, true), AS_Major("A# Major", 10, true), C_Minor("C Minor", 0, false),
	D_Minor("D Minor", 2, false), E_Minor("E Minor", 4, false), F_Minor("F Minor", 5, false),
	G_Minor("G Minor", 7, false), A_Minor("A Minor", 9, false), B_Minor("B Minor", 11, false),
	CS_Minor("C# Minor", 1, false), DS_Minor("D# Minor", 3, false), FS_Minor("F# Minor", 6, false),
	GS_Minor("G# Minor", 8, false), AS_Minor("A# Minor", 10, false);

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
		return name + " +" + startOffset;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public boolean isMajor() {
		return isMajor;
	}

}
