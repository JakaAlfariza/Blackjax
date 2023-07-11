import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

abstract class AbstractBlackjack {
    public List<Card> deck;
    public List<Card> kartuPlayer;
    public List<Card> kartuDealer;
   
    public Scanner scanner;
    public Scanner scannerNama;
    public Scanner scannerBet;
    public Random random;

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

    public void sleepThread(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void lanjutkan(){
        System.out.print("\n\nContinue in... 3");
        sleepThread(1000);

        System.out.print("\nContinue in... 2");
        sleepThread(1000);

        System.out.print("\nContinue in... 1");
        sleepThread(1000);
    }

    private void pertanyaan(String nama, Taruhan taruhan) throws IOException {
        System.out.print("Ingin Main Lagi? (y/n): ");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("y")) {
            resetGame();
            startGame(nama, taruhan);
        } else {
            System.out.println("Terima Kasih Telah Bermain Blackjax!.");
        }
    }

    public void startGame(String nama, Taruhan taruhan) throws IOException {
        BlackjackGame blackjackGame = new BlackjackGame();

        clearScreen();

        inputTaruhan(taruhan);

        clearScreen();

        inisialisasiDeck();

        acakDeck();

        bagikanKartu();

        playGame();

        tentukanMenang(taruhan);

        blackjackGame.hitungHighscore(taruhan);

        pertanyaan(nama,taruhan);

        blackjackGame.inputScore(nama, taruhan);

        tampilkanHighScore();
    }

    public void inputTaruhan(Taruhan taruhan) {
        clearScreen();
        System.out.print("Jumlah Chip: " + taruhan.getChip());

        System.out.print("\nMasukkan jumlah taruhan (Min. 50): ");
        int bet = scannerBet.nextInt();

        if (bet < 50) {
            System.out.println("\nInput salah");
            sleepThread(1000);
            inputTaruhan(taruhan);
        }else if (bet > taruhan.getChip()) {
            System.out.println("\nChip kurang");
            sleepThread(1000);
            inputTaruhan(taruhan);
        } else {
            taruhan.setBet(bet);
            taruhan.setChip(taruhan.getChip() - taruhan.getBet());
            System.out.println("\nSisa Chip: " + taruhan.getChip());
            lanjutkan();
        }
    }

    public void inisialisasiDeck() {
        for (Lambang Lambang : Lambang.values()) {
            for (Nilai Nilai : Nilai.values()) {
                Card card = new Card(Nilai, Lambang);
                deck.add(card);
            }
        }
    }

    public void acakDeck() {
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


    public static void tampilkanHighScore() throws IOException{
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("highscore.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e) {
            System.out.println("File highscore tidak ada");
            return;
        }

        System.out.println("\n     TOP 10  BLACKJAX HIGHSCORE      ");
        System.out.println("=====================================");
        System.out.println("No | Nama\t\t| Jumlah Chip ");
        System.out.println("=====================================");

        String data = bufferInput.readLine();
        List<String> highscoreList = new ArrayList<>();
        
        while (data != null) {
            highscoreList.add(data);
            data = bufferInput.readLine();
        }

        // Sorting
        Comparator<String> chipCountComparator = Comparator.comparingInt(line -> Integer.parseInt(line.split(",")[1]));
        Collections.sort(highscoreList, chipCountComparator.reversed());

        //Tampilkan TOP 10
        for (int i = 0; i < 10; i++) {
            String highscore = highscoreList.get(i);
            StringTokenizer stringToken = new StringTokenizer(highscore, ",");
            System.out.printf(" %d |", i + 1);
            System.out.printf(" %s\t\t|", stringToken.nextToken());
            System.out.printf("    %s", stringToken.nextToken());
            System.out.println();
        }
    }

    public void resetGame() {
        deck.clear();
        kartuPlayer.clear();
        kartuDealer.clear();
    }

    //Method Abstrak

    public abstract void bagikanKartu();

    public abstract void playGame();

    public abstract int calculateScore(List<Card> hand);

    public abstract void tentukanMenang(Taruhan taruhan) throws IOException;
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

