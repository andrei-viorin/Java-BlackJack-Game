package blackJack;
import java.util.Scanner;          

 
public class DeckofCards {        
	static int[] deck = new int[52];    // Static variables
	static int drawcard=0;		
    static String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
    static String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9",
      "10", "Jack", "Queen", "King"};
    
	  public static int drawCard() {      // Returns the card on the drawcard position, and after the position increments by 1
		  return deck[drawcard++];
		  }
	  
	  public static void shuffle() {          // Shuffles the deck
		  for (int i=0; i<deck.length; i++) {
			  int index=(int) (Math.random()*deck.length);
			  int temp=deck[i];
			  deck[i]=deck[index];
			  deck[index]=temp;
	  } }
	}

 class BlackJack {
	 static int playerscore;         // Static variables for storing the scores and number of cards drew for each player
	 static int dealerscore;
	 static int playercount;
	 static int dealercount;
	 static int facedown=0;          // Checks whether it's the dealer first hand and a cards needs to be put with the face down 
	 static int[] dealerhand=new int[14];
	 static int[] playerhand=new int[14];
	 static boolean isPlayer;
	 
	 public static void setupNewGame () {      // When calling this method everything must be reseted 
		 DeckofCards.drawcard=0;
		 playerscore=0;
		 dealerscore=0;
		 playercount=0;
		 dealercount=0;
		 for(int i=0;i<dealerhand.length;i++) {
			 dealerhand[i]=0;
			 playerhand[i]=0;
		 }			 
		DeckofCards.shuffle();                          // Performs a deck shuffle 
		while (dealercount<2) {
			dealerhand[dealercount++]=DeckofCards.drawCard();       // Draws 2 cards for the dealer 
		}
		
		while (playercount<2) {
			playerhand[playercount++]=DeckofCards.drawCard();       // Draws 2 cards for the player 
		}
	}
	 
	 public static void hit() {
		 playerhand[playercount++] = DeckofCards.drawCard();         // Draws cards for the player
	 }
	 
	 public static void printHand(int[] array, boolean isPlayer) {                     // Prints the cards in the given array
		 if (!isPlayer) {                                    // Prints dealer's cards
			 if (BlackJack.facedown==0) {				                         // Prints one card and the other card face down 
					 String rank = DeckofCards.ranks[array[0]%13];
					 String suit = DeckofCards.suits[array[0]/13];
					 System.out.print(rank+" of "+suit+" | Facedown card"); 
			 		}
			 else {                                                  // Prints all the cards
				 for (int i=0;i<dealercount;i++) {                   
					 String rank = DeckofCards.ranks[array[i]%13];
					 String suit = DeckofCards.suits[array[i]/13];
					 System.out.print(rank+" of "+suit+" | ");
				 }
			 }
		 }
		 else {                                                      // Prints player's cards
			 for (int i=0;i<playercount;i++) {
				 String rank = DeckofCards.ranks[array[i]%13];
				 String suit = DeckofCards.suits[array[i]/13];
				 System.out.print(rank+" of "+suit+" | "); 
			 }
		 }
	}
	 
	 public static int calculateScore(int[] hand) {                 // Calculates the score
		 if (hand==dealerhand) {                                    // Calculates dealer's score
			 dealerscore=0;
			 int acedealer=0;                                       // Stores the number of aces that have been added as 11 to the score
			 for (int i=0; i<dealercount;i++) {
				 if (hand[i]%13>0 && hand[i]%13<10)
					 dealerscore=dealerscore+hand[i]%13+1;
				 else if (hand[i]%13>=10)
					 dealerscore=dealerscore+10;
				 else if (hand[i]%13==0) {
					 if (dealerscore<11) {                          
						 dealerscore+=11;                           // Increments if an ace is added as 11
						 acedealer++;
					 }
					 else 
						 dealerscore+=1;
				 } 
				 while ((acedealer!=0)&&(dealerscore>21)) {         // Checks if an ace has been added as 11 and the score is higher than 21
					 	dealerscore-=10;                            // If yes, it substracts 10 until the score is <=21 
					 	acedealer--;
				 	}
			 }
			 return dealerscore;                                     // Returns dealer's score
		 }	
		 else {
			 playerscore=0;                                           // Calculates player's score, in the same way as for dealer's score
			 
			 int aceplayer=0;
			 for (int i=0; i<playercount;i++) {
				 if (hand[i]%13>0 && hand[i]%13<10)
					 playerscore=playerscore+hand[i]%13+1;
				 else if (hand[i]%13>=10)
					 playerscore=playerscore+10;
				 else if (hand[i]%13==0) {
					
					 if (playerscore<11) {
						 playerscore+=11;
						  aceplayer++;
					 }
					 else 
						 playerscore+=1;
				 }
			 }	 
			 while ((aceplayer!=0)&&(playerscore>21)) {
				 	playerscore-=10;
				 	aceplayer--;
			 	}
			 }
			 return playerscore;
		}
	 
	 public static void finishDealerPlay() {                         // Draws cards until dealer's score is >=17 and prints dealer's hand
		 	while (calculateScore(dealerhand)<17) {
		 		dealerhand[dealercount++] = DeckofCards.drawCard();
		 		System.out.print("Dealer's cards: ");
		 		printHand(dealerhand, isPlayer);
		 		System.out.println("");
		 	}
	}
	 
	 public static int calculateWinnings() {                        // Calculates who should win the game
		if ((playerscore==21)&& (DeckofCards.drawcard==4)&&(dealerscore!=21))
			return 1;
		else if ((playerscore<=21)&&(dealerscore<playerscore))
			return 1;
		else if ((dealerscore>21)&&(playerscore<=21))
			return 1;
		else if ((dealerscore==playerscore)&&(dealerscore<=21))
			return 0;
		else 
			return -1;
	 }	 
	 }
