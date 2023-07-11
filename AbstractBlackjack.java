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
    public Scanner scannerNama;
    public Scanner scannerBet;

    public AbstractBlackjack() {
        deck = new ArrayList<>();
        kartuPlayer = new ArrayList<>();
        kartuDealer = new ArrayList<>();
        scanner = new Scanner(System.in);
        scannerNama = new Scanner(System.in);
        scannerBet = new Scanner(System.in);
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

    public void lanjutkan(){
        System.out.print("\n\nContinue in... 3");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("\nContinue in... 2");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("\nContinue in... 1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startGame(Taruhan taruhan) {       
        
        clearScreen();
        
        inputTaruhan(taruhan);

        clearScreen();

        inisialisasiDeck();
        acakDeck();

        
        bagikanKartu();

        playGame();

        tentukanMenang(taruhan);

        pertanyaan(taruhan);
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

    private void pertanyaan(Taruhan taruhan) {
        System.out.print("Do you want to play again? (y/n): ");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("y")) {
            resetGame();
            startGame(taruhan);
        } else {
            System.out.println("Thank you for playing! Goodbye.");
        }
    }

    public void inputTaruhan(Taruhan taruhan) {
        clearScreen();
        System.out.print("Jumlah Chip: " + taruhan.getChip());

        System.out.print("\nMasukkan jumlah taruhan (Min. 50): ");
        int bet = scannerBet.nextInt();

        if (bet < 50) {
            System.out.println("\nInput salah");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            inputTaruhan(taruhan);
        }else if (bet > taruhan.getChip()) {
            System.out.println("\nChip kurang");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            inputTaruhan(taruhan);
        } else {
            taruhan.setBet(bet);
            taruhan.setChip(taruhan.getChip() - taruhan.getBet());
            System.out.println("\nSisa Chip: " + taruhan.getChip());
            lanjutkan();
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

    public abstract void tentukanMenang(Taruhan taruhan);
}

//Struktur Kartu
enum Lambang {
    SEKOP   ("S" ),
    HATI    ("H"), 
    KERITING("K"),
    WAJIK   ("W");
    
    private final String symbol;

    Lambang(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

enum Nilai {
    DUA     ("2 ", 2),
    TIGA    ("3 ", 3),
    EMPAT   ("4 ", 4),
    LIMA    ("5 ", 5),
    ENAM    ("6 ", 6),
    TUJUH   ("7 ", 7),
    DELAPAN ("8 ", 8),
    SEMBILAN("9 ", 9),
    SEPULUH ("10", 10),
    JACK    ("J ", 10),
    QUEEN   ("Q ", 10),
    KING    ("K ", 10),
    AS      ("A ", 11);

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

