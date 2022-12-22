package finder;

public class Offsets {
	

	private int lineOffset;
	private int charOffset;
	
	public Offsets() {}
	
	
	public Offsets(int lineOffset, int charOffset) {
		super();
		this.lineOffset = lineOffset;
		this.charOffset = charOffset;
	}
	public int getLineOffset() {
		return lineOffset;
	}
	public void setLineOffset(int lineOffset) {
		this.lineOffset = lineOffset;
	}
	public int getCharOffset() {
		return charOffset;
	}
	public void setCharOffset(int charOffset) {
		this.charOffset = charOffset;
	}
	
	
	@Override
	public String toString() {
		return "[lineOffset=" + lineOffset + ", charOffset=" + charOffset + "]";
	}
}
