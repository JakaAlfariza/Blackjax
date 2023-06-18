import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BlackjackGame {
    private List<Card> originalDeck; // Keep a copy of the original deck
    private List<Card> deck;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private int playerScore;
    private int dealerScore;
    private Scanner scanner;
    private Random random;

    public BlackjackGame() {
        originalDeck = new ArrayList<>(); // Initialize the original deck
        deck = new ArrayList<>();
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        playerScore = 0;
        dealerScore = 0;
        scanner = new Scanner(System.in);
        random = new Random();
    }

    public void startGame() {
        System.out.println("Welcome to Blackjack!");

        // Initialize deck
        initializeDeck();

        // Shuffle the deck
        shuffleDeck();

        // Deal initial cards
        dealInitialCards();

        // Play the game
        playGame();

        // Determine the winner
        determineWinner();

        // Ask to play again
        askToPlayAgain();
    }

    private void initializeDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = new Card(rank, suit);
                originalDeck.add(card); // Add card to the original deck
                deck.add(card); // Add card to the current deck
            }
        }
    }

    private void shuffleDeck() {
        for (int i = deck.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    private void dealInitialCards() {
        for (int i = 0; i < 2; i++) {
            playerHand.add(drawCard());
            dealerHand.add(drawCard());
        }
    }

    private Card drawCard() {
        return deck.remove(deck.size() - 1);
    }

    // Rest of the methods remain unchanged...

    private void playGame() {
        // Show player's hand and score
        System.out.println("Your hand: " + playerHand);
        System.out.println("Your score: " + calculateScore(playerHand));
        
        dealerHand.get(0).setHidden(true);
        // Show dealer's visible card
        System.out.println("Dealer's visible card: " + dealerHand);

        // Player's turn
        while (true) {
            System.out.print("Choose an action (1 - Hit, 2 - Stand): ");
            int action = scanner.nextInt();

            if (action == 1) {
                playerHand.add(drawCard());
                int playerHandScore = calculateScore(playerHand);
                System.out.println("Your hand: " + playerHand);
                System.out.println("Your score: " + playerHandScore);

                if (playerHandScore > 21) {
                    System.out.println("Bust! You lose.");
                    return;
                }
            } else if (action == 2) {
                break;
            }
        }

        // Dealer's turn
        System.out.println("\nDealer's turn:");
        dealerHand.get(0).setHidden(false); // Reveal the dealer's hidden card
        System.out.println("Dealer's hand: " + dealerHand);
        System.out.println("Dealer's score: " + calculateScore(dealerHand));

        while (true) {
            if (calculateScore(dealerHand) >= 17) {
                break;
            }

            System.out.println("\nDealer draws a card.");
            // Pause for a moment before the next dealer's action
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dealerHand.add(drawCard());
            System.out.println("Dealer's hand: " + dealerHand);
            System.out.println("Dealer's score: " + calculateScore(dealerHand));

            if (calculateScore(dealerHand) > 21) {
                System.out.println("Dealer busts! You win.");
                return;
            }

            
        }
    }

    private int calculateScore(List<Card> hand) {
        int score = 0;
        int numAces = 0;

        for (Card card : hand) {
            if (card.getRank() == Rank.ACE) {
                score += 11;
                numAces++;
            } else {
                score += card.getRank().getValue();
            }
        }

        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }

    private void determineWinner() {
        int playerHandScore = calculateScore(playerHand);
        int dealerHandScore = calculateScore(dealerHand);

        if (playerHandScore > 21) {
            System.out.println("You busted! You lose.");
        } else if (dealerHandScore > 21) {
            System.out.println("Dealer busted! You win.");
        } else if (playerHandScore > dealerHandScore) {
            System.out.println("You win!");
        } else if (playerHandScore < dealerHandScore) {
            System.out.println("You lose.");
        } else {
            System.out.println("It's a tie!");
        }
    }

    private void askToPlayAgain() {
        System.out.print("Do you want to play again? (y/n): ");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("y")) {
            resetGame();
            startGame();
        } else {
            System.out.println("Thank you for playing! Goodbye.");
        }
    }

    private void resetGame() {
        deck.clear();
        playerHand.clear();
        dealerHand.clear();
        playerScore = 0;
        dealerScore = 0;
    }

}

enum Suit {
    SPADES("♠"), HEARTS("♥"), CLUBS("♣"), DIAMONDS("♦");
    
    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

enum Rank {
    TWO("2 ", 2), THREE("3 ", 3), FOUR("4 ", 4), FIVE("5 ", 5), SIX("6 ", 6), SEVEN("7 ", 7), EIGHT("8 ", 8), NINE("9 ", 9),
    TEN("10", 10), JACK("J ", 10), QUEEN("Q ", 10), KING("K ", 10), ACE("A ", 11);

    private final String symbol;
    private final int value;

    Rank(String symbol, int value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }
}

class Card {
    private Rank rank;
    private Suit suit;
    private boolean hidden;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.hidden = false; // By default, cards are not hidden
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        if (hidden) {
            return "\n┌─────────┐\n"
                   + "│░░░░░░░░░│\n"
                   + "│░░░░░░░░░│\n"
                   + "│ H I D E │\n"
                   + "│░░░░░░░░░│\n"
                   + "│░░░░░░░░░│\n"
                   + "└─────────┘";
        } else {
            return "\n┌─────────┐\n"+
                     "│" + rank.getSymbol() + "       │\n" +
                     "│         │\n" +
                     "│    " + suit.getSymbol() + "    │\n" +
                     "│         │\n"+
                     "│       " + rank.getSymbol() + "│\n"+
                     "└─────────┘";
        }
    }
}
