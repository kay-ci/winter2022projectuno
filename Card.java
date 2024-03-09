public class Card {
	private Color color;
	private Type type;
	
	//constructor
	public Card(Color color, Type type){
		this.color = color;
		this.type = type;
	}
	
	
	//get color
	public Color getColor() {	
		return this.color;
	}
	
	//get type
	public Type getType(){
		return this.type;
	}
	
	//toString()
	public String toString(){
		return color + " " + type;
	}
}