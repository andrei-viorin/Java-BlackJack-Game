package blackJack;
import java.lang.Character;
import java.util.Scanner;

public class TestBlackJack {                                  // The class that tests the BlackJack game

	public static void main(String[] args) {
		Scanner input= new Scanner (System.in);
		char hitorstand = 0;                                 // Stores player's decision to hit or stand
		int bank=100;
		int stake=0;
		
		for (int i = 0; i < DeckofCards.deck.length; i++)    // Initializes the deck
		      DeckofCards.deck[i] = i;
		 while (bank>4) {                                    // Performs the instructions while the bank is greater than 4 
			BlackJack.setupNewGame();                        // Sets a new game
			BlackJack.isPlayer=false;
			BlackJack.facedown=0;                            // Sets facedown to 0 so that next time the dealer will have a card faced down
			System.out.println("Bank is "+bank+".");
			System.out.print("How much would you like to bet? (Please enter a multiple of 5; the maximum stake is 25) ");
			stake=input.nextInt();
			while ((stake<5)||(stake>25)||(stake%5!=0)) {     // Check if the user has set a correct stake and if negative asks the user to enter a correct stake
				System.out.print("The stake should be a multiple of 5, between 5 and 25: ");
				stake=input.nextInt();
			}
				
			System.out.print("Dealer's cards: ");              // Prints dealer's and player's first cards
			BlackJack.printHand(BlackJack.dealerhand, BlackJack.isPlayer);
			System.out.println("");
			System.out.print("Player's cards: ");
			BlackJack.isPlayer=true;
			BlackJack.printHand(BlackJack.playerhand,BlackJack.isPlayer);
			System.out.println("");
			BlackJack.calculateScore(BlackJack.playerhand); 
			do {                                              // Performs the instructions for hit
				System.out.print("What would you like to do? ('h' to hit, 's' to stand) ");
				hitorstand=input.next().charAt(0);
				if (hitorstand=='h') {
					BlackJack.hit();
					System.out.print("Player's cards: ");
					BlackJack.printHand(BlackJack.playerhand, BlackJack.isPlayer);
					System.out.println("");
					BlackJack.calculateScore(BlackJack.playerhand);
					}
			}  while ((hitorstand=='h')&&(BlackJack.playerscore<=21));
				if (BlackJack.playerscore>21) {                        // Sets the dealer as winner if the player has a score greater than 21
					System.out.println("Dealer wins!");
					System.out.println();
					bank=bank-stake;
				}
				else { 
					BlackJack.facedown=1;                             // Prints dealer's cards and draws cards until dealer's score is >=17
					BlackJack.isPlayer=false;
					System.out.print("Dealer's cards: ");
					BlackJack.printHand(BlackJack.dealerhand, BlackJack.isPlayer);
					System.out.println("");
					//BlackJack.calculateScore(BlackJack.dealerhand);
					if ((BlackJack.calculateScore(BlackJack.playerhand)==21)&&(BlackJack.playercount==2)&&(BlackJack.calculateScore(BlackJack.dealerhand)<21)) {
						System.out.println("Player wins!");           // If the player has a blackjack from 2 cards and the dealer has not, the player wins
						System.out.println();
						bank=bank+stake;
					}
					else if ((BlackJack.calculateScore(BlackJack.dealerhand)==21)&&(BlackJack.dealercount==2)&&(BlackJack.playercount!=2)) {
						System.out.println("Dealer wins!");          // If the dealer has black jack out of 2 cards and the player has not, the dealer wins
						System.out.println();
						bank=bank-stake; }
					else {
						
						BlackJack.finishDealerPlay();               // The dealer gets cards until dealerscore > = 17
							
						if (BlackJack.calculateWinnings()==1) {     // Checks the result of the game, prints the winner and recalculates the bank
							System.out.println("Player wins!");
							System.out.println();
							bank = bank + stake;
						}
						else if (BlackJack.calculateWinnings()==-1) {
							System.out.println("Dealer wins!");
							System.out.println();
							bank = bank - stake;
						}
						else 
							System.out.println("It's a push!");
						System.out.println();
					}
						
				}
		 }
		 System.out.println("You lost all your money! You should go home!");
		 }
}
