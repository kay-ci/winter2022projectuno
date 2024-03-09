import java.util.*;
public class Game {
	final int cardsPerPlayer = 7;
		private CardPile deck;
		private CardPile discardPile;
		private CardPile hand1;
		private CardPile hand2;

	public Game(){
		
		//making deck
		deck = new CardPile();
		deck.initialize();
		deck.shuffle();
		
		//discardPile
		discardPile = new CardPile();
		
		hand1 = new CardPile();
		hand2 = new CardPile();
		
		//giving 7 cards to each player
		for (int i=0; i<cardsPerPlayer; i++){
			hand1.addCard(deck.takeFromTop());
			deck.removeFromPile(deck.length());
			hand2.addCard(deck.takeFromTop());
			deck.removeFromPile(deck.length());
		}
		
	}
	public void playGame(){
		boolean isItSpecial = false;
		Scanner scan = new Scanner(System.in);
		
		//explaining the rules
		System.out.println("Welcome to the game Uno!");
		System.out.println("This will be a two player game! You will each be given 7 cards to start.");
		System.out.println("Your objective is to have Zero cards in your hand. After you choose a card please look away and allow player 2 to make their selection.");
		System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------\n");
		
		//get random card that isnt a special card 
		Random rand = new Random();
		do{
			int randomIndex = rand.nextInt(deck.length());
			Card firstCard = deck.getCard(randomIndex);
			isItSpecial = deck.specialCard(firstCard);
			
			if (isItSpecial==false){
				discardPile.addCard(firstCard);
				deck.removeFromPile(randomIndex);
				System.out.println("The first card on the discard pile is: " + firstCard);
			}
			else{
				isItSpecial = true;
			}
		}while(isItSpecial);
		
		

		//setting our next turn to start with player 2
		int player = 1;
		
		//making game end if deck or either players hand is == 0
		do {
			playARound(player);
			if (player == 2){
				player = 1;
			}
			else{
				player = 2;
			}
			
		}while(deck.length() > 0 && hand1.length() > 0 && hand2.length() > 0);
		gameOver();
		
	}
	public void gameOver(){
		Scanner scan = new Scanner(System.in);
		
		//printing results and asking if player wants to play again
		if (deck.length() <= 1){
			System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.println("GAME OVER\nIt's a draw ! There are no more cards in the deck :(");
		
		}
		else if (hand1.length() == 0){
			System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.println("GAME OVER\nPlayer 1 has won the game!!");
		}
		else{
			System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.println("GAME OVER\nPlayer 2 has won the game!!");
		}
		
		//asking if user wants to play again
		System.out.println("Do You want to play again? [yes] / [no]");
		String answer = scan.nextLine();
		String myAnswer = answer.toUpperCase();
			
		//reseting everything if they want to play again
		if (myAnswer.equals("YES")){
			Game myGame = new Game();
			myGame.playGame();
		}
		//Exit program if no
		else{
			System.out.println("Goodbye!!");
			System.exit(0);
		}
		
	}
	public void playARound (int player) {
		Scanner scan = new Scanner(System.in);
		boolean isCardValid = false;
		boolean isValidColor = false;
		
		if (player == 2){
			
			//validate
			if (deck.length() < 1 || hand2.length() < 1) {
				gameOver();
			}
			System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.println("Player 2's turn: ");
			System.out.println("!!Player 1 Please look away!!\n");
			Card cardInDeck = discardPile.takeFromTop();
			System.out.println("Here is the card on the Discard Pile:\n"+cardInDeck+"\n");
			System.out.println("Select a card that matches either the color or value or use a wild card\n");
			
			//hand output
			System.out.println("Here is your hand:\n"+hand2+"\n");
			System.out.println("Which card would you like to use? Type in number corresponding to the card\nTo draw from deck type [222]: ");
				
			do{
				int selectedCard = scan.nextInt();
				scan.nextLine();
			
				//validating card
				if (selectedCard <= hand2.length() && selectedCard>=0 ){
					//if they chose a wild card
					Card wildCard = hand2.getCard(selectedCard);
					
					isCardValid = discardPile.validCard(cardInDeck, wildCard);
					if (isCardValid == true){	
					
						
						if (wildCard.getColor().equals(Color.WILD)){
							System.out.println("You have selected a wild card! What colour would you like to change the deck to?");
							System.out.println("Please type in desired colour [Blue] [Red] [Yellow] [Green]: ");
							
							//loop here until a valid color 
							do{ 
								String color = scan.nextLine();
								String colorUpper = color.toUpperCase();
								isValidColor = hand2.validColor(colorUpper);
								
								if (isValidColor == true){
									discardPile.addCard(hand2.chosenColor(colorUpper)); 
									hand2.removeFromPile(selectedCard);
									break;
								}
								else{
									System.out.println("Error, please input a valid color:");
									isValidColor = false;
								}
							}while (!isValidColor);//while(newColor.equals(null));
								
							//if WILD_FOUR adding 4 cards to opponent
							if (wildCard.getType().equals(Type.WILD_FOUR)){
								System.out.println("+4 cards to opponent");
								for (int i=0;i<4;i++){
									hand1.addCard(deck.takeFromTop());
									deck.removeFromPile(deck.length());
								}
								break;
							}
						}
							
						//if a skip card is used
						else if(wildCard.getType().equals(Type.SKIP)){
							System.out.println("You have selected a skip next player card. You can play again!");
							discardPile.addCard(hand2.getCard(selectedCard));
							hand2.removeFromPile(selectedCard);
							player = 2;
							playARound(2);
							break;
						}
							
						//if DRAW_TWO card 
						else if (wildCard.getType().equals(Type.DRAW_TWO )) {
							System.out.println("+2 cards to opponent");	
							//discarding chosen card
							discardPile.addCard(wildCard);
							hand2.removeFromPile(selectedCard);
							
							//player draws 2 cards and removes 2 cards from deck if the current card is DRAW_TWO
							for (int i=0;i<2;i++){
								hand1.addCard(deck.takeFromTop());
								deck.removeFromPile(deck.length());
							}
							break;
						
						}
						//any other card
						else{
							discardPile.addCard(wildCard);
							hand2.removeFromPile(selectedCard);
							player = 2;
							break;
						}
					}
					
					else {
						System.out.println("Cannot use this card, Please input a valid card number");
						isCardValid = false;
						player = 1;
					}
				}
				else if (selectedCard == 222){
					hand2.addCard(deck.takeFromTop());
					deck.removeFromPile(deck.length());
					playARound(2);
					break;
				}
				else{
					System.out.println("Please input a valid card number");
					isCardValid = false;
				}
			}while (!isCardValid);
			
			//uno
			if (hand2.length() == 1){
				System.out.println("Player 2 yells out UNO!");
			}
				
			
		}
		
		//player 1
		else{
			//validate
			if (deck.length() < 1 || hand2.length() < 1) {
				gameOver();
			}
			System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.println("Player 1's turn: \n");
			System.out.println("!!Player 2 Please look away!!\n");
			Card cardInDeck = discardPile.takeFromTop();
			System.out.println("Here is the top card on the discardPile:\n"+cardInDeck+"\n");
			System.out.println("Select a card that matches either the color or value or use a wild card\n");
			
			
			//hand output
			System.out.println("Here is your hand:\n"+hand1+"\n");
			System.out.println("Whick card would you like to use? Type in number corresponding to the card\nTo draw from deck type [222]: ");
			do {
				int selectedCard = scan.nextInt();
				scan.nextLine();
				//validating card
				if (selectedCard <= hand1.length() && selectedCard>=0){
					//the players chosen card
					Card wildCard = hand1.getCard(selectedCard);
					isCardValid = deck.validCard(cardInDeck, wildCard);
					
					if (isCardValid == true){
						
						//if player chose a wild card
						if (wildCard.getColor().equals(Color.WILD)){
							System.out.println("You have selected a wild card! What colour would you like to change the deck to?");			
							System.out.println("Please type in desired colour [Blue] [Red] [Yellow] [Green]: ");
							
							//loop until a valid color 
							do{ 
								String color = scan.nextLine();
								String colorUpper = color.toUpperCase();
								isValidColor = hand1.validColor(colorUpper);
								
								if (isValidColor == true){
									discardPile.addCard(hand1.chosenColor(colorUpper)); 
									hand1.removeFromPile(selectedCard);
									break;
								}
								else{
									System.out.println("Error, please input a valid color:");
									isValidColor = false;
								}
							}while (!isValidColor);//while(newColor.equals(null));
								
							// if WILD FOUR, adding 4 cards to opponent
							if (wildCard.getType().equals(Type.WILD_FOUR)){
								System.out.println("+4 cards to opponent");
								for (int i=0;i<4;i++){
									hand2.addCard(deck.takeFromTop());
									deck.removeFromPile(deck.length());
								}
								break;
							}
						}
						//if a skip card is used
						else if(wildCard.getType().equals(Type.SKIP)){
							System.out.println("You have selected a skip next player card. You can play again!");
							discardPile.addCard(wildCard);
							hand1.removeFromPile(selectedCard);
							player = 1;
							playARound(1);
							break;
						}
							
						//evaluating if the card on top is a special card
						else if (wildCard.getType().equals(Type.DRAW_TWO )) {
							System.out.println("+2 cards to opponent");
							
							//discarding chosen card
							discardPile.addCard(hand1.getCard(selectedCard));
							hand1.removeFromPile(selectedCard);
							
							//player draws 2 cards and removes 2 cards from deck if the current card is DRAW_TWO
							for (int i=0;i<2;i++){
								hand2.addCard(deck.takeFromTop());
								deck.removeFromPile(deck.length());
								}
								break;
						}
							
						//any other card
						else{
							discardPile.addCard(wildCard);
							hand1.removeFromPile(selectedCard);
							player = 1;
							break;
						}
					}
				
					else {
						System.out.println("Please input a valid card number");
						isCardValid = false;
						player = 2;
					}
				}
				else if (selectedCard == 222){
					hand1.addCard(deck.takeFromTop());
					deck.removeFromPile(deck.length());
					playARound(1);
					break;
				}
				else{
					System.out.println("Please input a valid card number");
					isCardValid = false;
				}
			}while (!isCardValid);//while (!isCardValid);		
			
			if ( hand1.length() == 1) {
					System.out.println("Player 1 yells out UNO!");
				}
		}
	
	}
}