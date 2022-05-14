import java.util.Random;
public class CardPile{
	/*
		Since there are no reverse cards we have 100 cards 19 blue cards 0-9, 19 green cards 0-9, 
		19 red cards 0-9 (only 1 zero cards for all colors), 8 skip cards 2 in each color,
		8 draw cards 2 in each color, 4 wild cards, 4 wild draw 4 cards,
		
	*/
	
	private Card[] pile;
	private int cardsInPile;
	
	//constructor
	public CardPile(){
		pile = new Card [100];
	}
	
	//initialize deck
	public void initialize(){
	
		//creating array that contains all values of Color enum
		Color[] colors = Color.values();
		cardsInPile = 0;
		
		for (int i = 0; i < 4; i++){
			//going to cycle through colors starting with blue, excluding wild
			Color color = colors[i]; 
		
			//making all colors have only 1 zero card
			pile[cardsInPile] = new Card(color, Type.getType(0)); 
			cardsInPile ++;
			//setting each number TWICE for numbers 0-9 to one color
			for (int j = 1; j < 10; j++){
				pile[cardsInPile] = new Card(color, Type.getType(j));
				cardsInPile ++;
				pile[cardsInPile] = new Card(color, Type.getType(j));
				cardsInPile ++;
			}
			//making an array for our special cards 
			Type[] types = new Type[] {Type.SKIP, Type.DRAW_TWO};
			
			//itterate over our special cards array
			for (Type type : types){
				pile[cardsInPile] = new Card(color, type);
				cardsInPile ++;
				pile[cardsInPile] = new Card(color, type);
				cardsInPile ++;
			}
		}
		//making an array for our special cards (wilds)
		Type[] types = new Type[] {Type.WILD, Type.WILD_FOUR};
		for (Type type : types){
			for (int i = 0; i < 4; i++){
				pile[cardsInPile] = new Card(Color.WILD, type);
				cardsInPile ++;
			}
		}
		
		//GOT AN ARRAY OUT OF BOUNDS WILL HAVE TO CHECK WHERE IM GOING OUT OF BOUNDS.
	}
	
	//shuffle deck
	public void shuffle(){
		Random rand = new Random();
		int maxValue = cardsInPile;
		
		for(int i=0; i<= 10000;i++){
		int index1 = rand.nextInt(maxValue);
		int index2 = rand.nextInt(maxValue);
		
		swap(this.pile, index1, index2);
		}
	}
	
	//helper method for shuffle()
	public static void swap(Card [] pile, int index1, int index2){
		Card temp = pile[index1];
		pile[index1] = pile[index2];
		pile[index2] = temp;
	}
	
	//add a card to a pile 
	public void addCard(Card c){
		if (this.cardsInPile >= pile.length){
			growPile();
		}
		this.pile[cardsInPile] = c;
		this.cardsInPile++;
	}
	
	//helper method for addCard() that will double array size
	public void growPile(){
		Card [] tempPile = new Card[this.pile.length *2];
		
		for (int i=0;i<this.pile.length;i++){
			tempPile[i] = this.pile[i];
		}

		this.pile = tempPile;
		
	}
	
	//get(int n) get card at a specific index
	public Card getCard(int i){
		if (i >= cardsInPile) {
			throw new ArrayIndexOutOfBoundsException("Invalid Index");
		}
		return this.pile[i];
	}
	
	//removeFromPile(int index)
	public void removeFromPile(int index){
		if (cardsInPile==this.pile.length){
			growPile();
		}
		 for (int i=index; i<cardsInPile-1;i++){
			this.pile[i] = this.pile[i+1];
		 }
		 this.pile[cardsInPile - 1] = null;
		 cardsInPile--;
	}
	
	//length method return the amount of cards in our hand or deck.
	public int length(){
		return this.cardsInPile;
	}
	
	//method that will validate if card chosen can be played
	public boolean validCard(Card cardInDeck, Card cardToValidate){
		if (cardInDeck.getColor().equals(cardToValidate.getColor()) || cardInDeck.getType().equals(cardToValidate.getType())){
				return true;
		}
		
		else if (cardToValidate.getType().equals(Type.WILD) || cardToValidate.getType().equals(Type.WILD_FOUR)){
			return true;
		}
		
		return false;
	}

	//takeFromTop() -- returns card from the top
	public Card takeFromTop(){
		return this.pile[cardsInPile-1];
	}
	
	//this method will take a color and return a card that represents the color
	public Card chosenColor(String color){
		
		if (color.equals("BLUE")){
			Card bluec = new Card(Color.BLUE,Type.WILD);
			return bluec;
		}
		else if (color.equals("RED")){
			Card redc = new Card(Color.RED,Type.WILD);
			return redc;
		}
		else if(color.equals("YELLOW")){
			Card yellowc = new Card(Color.YELLOW,Type.WILD);
			return yellowc;
		}
		else if(color.equals("GREEN")){
			Card greenc = new Card(Color.GREEN,Type.WILD);
			return greenc;
		}
		
		return null;
	}

	//returns boolean if the color is valid
	public boolean validColor(String color){
	
		if (color.equals("BLUE")){
			return true;
		}
		else if (color.equals("RED")){
			return true;
		}
		else if(color.equals("YELLOW")){
			return true;
		}
		else if(color.equals("GREEN")){
			return true;
		}
		return false;
	}
	
	//methode takes a card and will return true or false if it is a special card
	public boolean specialCard(Card c){
		if (c.getColor().equals(Color.getColor(4)) || c.getType().equals(Type.getType(10)) || c.getType().equals(Type.getType(11))){
				return true;
		}
		return false;
	}
	//overriding to string 
	public String toString(){
		String builder="";
		for (int i = 0; i<cardsInPile;i++){
			builder += "|"+i+":" + pile[i] +"|" + " ";
		}
			return builder;
	}
	
}