public enum Type {
	ZERO,
	ONE,
	TWO,
	THREE,
	FOUR,
	FIVE,
	SIX,
	SEVEN,
	EIGHT,
	NINE,
	SKIP,
	DRAW_TWO,
	WILD_FOUR,
	WILD;
	
	private static final Type[] types = Type.values();
	
	//getType()
	public static Type getType(int i){
		return Type.types[i];
	}
}