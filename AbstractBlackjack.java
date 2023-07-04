import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

abstract class AbstractBlackjack {
    private List<Card> deck;
    public List<Card> kartuPlayer;
    public List<Card> kartuDealer;
   
    private Random random;
    public Scanner scanner;
    public Scanner scanner2;

    public AbstractBlackjack() {
        deck = new ArrayList<>();
        kartuPlayer = new ArrayList<>();
        kartuDealer = new ArrayList<>();
        scanner = new Scanner(System.in);
        scanner2 = new Scanner(System.in);
        random = new Random();
    }

    // Method Inti

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception ex) {
            System.err.println("Gagal Clear");
        }
    }

    public void startGame() {

        clearScreen();

        inisialisasiDeck();

        acakDeck();
        
        bagikanKartu();

        playGame();

        tentukanMenang();

        pertanyaan();
    }

    private void inisialisasiDeck() {
        for (Lambang Lambang : Lambang.values()) {
            for (Nilai Nilai : Nilai.values()) {
                Card card = new Card(Nilai, Lambang);
                deck.add(card); // Add card to the current deck
            }
        }
    }

    private void acakDeck() {
        for (int i = deck.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    public Card ambilKartu() {
        return deck.remove(deck.size() - 1);
    }

    private void pertanyaan() {
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
        kartuPlayer.clear();
        kartuDealer.clear();
    }

    //Method Abstrak

    public abstract void bagikanKartu();

    public abstract void playGame();

    public abstract int calculateScore(List<Card> hand);

    public abstract void tentukanMenang();
}

//Struktur Kartu
enum Lambang {
    SEKOP("S" ), HATI("H"), KERITING("K"), WAJIK("W");
    
    private final String symbol;

    Lambang(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

enum Nilai {
    DUA("2 ", 2), TIGA("3 ", 3), EMPAT("4 ", 4), LIMA("5 ", 5), ENAM("6 ", 6), TUJUH("7 ", 7), DELAPAN("8 ", 8), SEMBILAN("9 ", 9),
    SEPULUH("10", 10), JACK("J ", 10), QUEEN("Q ", 10), KING("K ", 10), AS("A ", 11);

    private final String symbol;
    private final int value;

    Nilai(String symbol, int value) {
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

