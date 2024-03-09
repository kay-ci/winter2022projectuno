public enum Color{
	BLUE,
	GREEN,
	RED,
	YELLOW,
	WILD;
	
	private static final Color [] colors = Color.values();
	//getColor()
	public static Color getColor (int i){
		return Color.colors[i];
	}
}