import java.io.IOException;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class BlackjackGame extends AbstractBlackjack{
    String nama;
    int highscore = 0;
    
    public void opening() {
        System.out.println("\n ██████╗░██╗░░░░░░█████╗░░█████╗░██╗░░██╗░░░░░██╗░█████╗░██╗░░██╗\n"
                           + " ██╔══██╗██║░░░░░██╔══██╗██╔══██╗██║░██╔╝░░░░░██║██╔══██╗╚██╗██╔╝\n"
                           + " ██████╦╝██║░░░░░███████║██║░░╚═╝█████═╝░░░░░░██║███████║░╚███╔╝░\n"
                           + " ██╔══██╗██║░░░░░██╔══██║██║░░██╗██╔═██╗░██╗░░██║██╔══██║░██╔██╗░\n"
                           + " ██████╦╝███████╗██║░░██║╚█████╔╝██║░╚██╗╚█████╔╝██║░░██║██╔╝╚██╗\n"
                           + " ╚═════╝░╚══════╝╚═╝░░╚═╝░╚════╝░╚═╝░░╚═╝░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝\n\n"
                           + "\t\t      ╔═════════════════╗\n"
                           + "\t\t      ║  1. START       ║\n"
                           + "\t\t      ║  2. HIGH SCORE  ║\n"
                           + "\t\t      ║  3. EXIT        ║\n"
                           + "\t\t      ╚═════════════════╝");
    }

    public void pertanyaan() throws IOException {
        System.out.print("\nKembali ke menu (y): ");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("y")) {
            menu();
        } else {
            pertanyaan();
        }
    }

    public void menu() throws IOException {
        clearScreen();
        opening();

        System.out.print("\n\nPilih Menu (1/2/3): ");
        int pilMenu = scanner.nextInt();

        Taruhan taruhan = new Taruhan();
        switch (pilMenu) {
            case 1:
                clearScreen();
                String nama = nama();
                startGame(nama, taruhan);
                break;
            case 2:
                tampilkanHighScore();
                pertanyaan();
                break;
            case 3:
                break;
            default:
                clearScreen();
                menu();
                break;
        }
    }

    public String nama() {
        
        System.out.print("Masukan nama: ");
        nama = scannerNama.nextLine();

        System.out.print("Hallo " + nama + "! Selamat Bermain!");
        sleepThread(1000);
        Next next = new Next();
        next.lanjutkan();
        return nama;
    }
    
    public void bagikanKartu() {
        for (int i = 0; i < 2; i++) {
            kartuPlayer.add(ambilKartu());
            kartuDealer.add(ambilKartu());
        }
    }

    public void playGame() {
        //Player Bermain
        clearScreen();
        System.out.println("Giliran "+nama+" Bermain");
        sleepThread(2000);

        //Menampilkan Kartu Player
        clearScreen();
        System.out.println("Kartu "+ nama +": " + kartuPlayer);
        System.out.println(nama+" score: " + calculateScore(kartuPlayer));

        // Menampilkan Kartu Dealer
        kartuDealer.get(0).setHidden(true);
        System.out.println("\nKartu Dealer: " + kartuDealer);

        //Aksi Player
        while (true) {
            System.out.print("Pilih aksi (1 - Hit, 2 - Stand): ");
            int action = scanner.nextInt();

            if (action == 1) {
                clearScreen();
                System.out.println(nama + " Menambah Kartu");
                sleepThread(2000);

                clearScreen();
                kartuPlayer.add(ambilKartu());
                int kartuPlayerScore = calculateScore(kartuPlayer);

                // Menampilkan Kartu Player
                System.out.println("Kartu " + nama + ": " + kartuPlayer);
                System.out.println(nama + " score: " + calculateScore(kartuPlayer));

                // Menampilkan Kartu Dealer
                kartuDealer.get(0).setHidden(true);
                System.out.println("\nKartu Dealer: " + kartuDealer);

                //Score Lebih
                if (kartuPlayerScore > 21) {
                    System.out.println("Score "+nama+" melebihi 21");
                    return;
                }
            } else if (action == 2) {
                //Mengakhiri Giliran
                clearScreen();
                System.out.println(nama + " Mengakhiri Giliran Bermain");
                sleepThread(2000);
                break;
            }
        }

        //Dealer Bermain
        clearScreen();
        System.out.println("Giliran Dealer Bermain");
        sleepThread(2000);

        // Menampilkan Kartu Player
        clearScreen();
        System.out.println("Kartu " + nama + ": " + kartuPlayer);
        System.out.println(nama + " score: " + calculateScore(kartuPlayer));

        // Menampilkan Kartu Dealer & membuka hidden card
        kartuDealer.get(0).setHidden(false); // Reveal the dealer's hidden card
        System.out.println("\nKartu Dealer: " + kartuDealer);
        System.out.println("Dealer score: " + calculateScore(kartuDealer));

        //Logika Dealer
        while (true) {
            sleepThread(3000);
            if (calculateScore(kartuDealer) > calculateScore(kartuPlayer) || calculateScore(kartuDealer) >= 17) {
                break;
            }

            clearScreen();
            System.out.println("\nDealer Menambah kartu");
            sleepThread(2000);

            // Menampilkan Kartu Player
            clearScreen();
            System.out.println("Kartu " + nama + ": " + kartuPlayer);
            System.out.println(nama + " score: " + calculateScore(kartuPlayer));
            
            // Menampilkan Kartu Dealer
            kartuDealer.add(ambilKartu());
            System.out.println("\nKartu Dealer: " + kartuDealer);
            System.out.println("Dealer score: " + calculateScore(kartuDealer));

            //Jika Score Lebih
            if (calculateScore(kartuDealer) > 21) {
                System.out.println("Score Dealer melebihi 21");
                return;
            }

        }
    }

    public void hitungHighscore(Taruhan taruhan){
        int chip = taruhan.getChip();

        if (chip > highscore) {
            highscore = chip;
        }
    }
    
    public void tentukanMenang(Taruhan taruhan) throws IOException {
        int kartuPlayerScore = calculateScore(kartuPlayer);
        int kartuDealerScore = calculateScore(kartuDealer);

        if (kartuPlayerScore > 21) {
            System.out.println(nama+" busted! kamu kalah!.");
            taruhan.setChip(taruhan.getChip());
            System.out.println("\nSisa Chip: " + taruhan.getChip());

            if (taruhan.getChip() < 50) {
                System.out.println("\nNilai chip kurang dari 50. Game over!");
                inputScore(nama, taruhan);
                tampilkanHighScore();
                System.exit(0);
            }
        }
        
        else if (kartuDealerScore > 21) {
            System.out.println("Dealer busted! kamu menang!");
            taruhan.setChip(taruhan.getChip() + (taruhan.getBet() * 2));
            hitungHighscore(taruhan);
            System.out.println("\nSisa Chip: " + taruhan.getChip());
        } 
        
        else if (kartuPlayerScore > kartuDealerScore) {
            System.out.println("kamu menang!");
            taruhan.setChip(taruhan.getChip() + (taruhan.getBet() * 2));
            hitungHighscore(taruhan);
            System.out.println("\nSisa Chip: " + taruhan.getChip());
        } 
        
        else if (kartuPlayerScore < kartuDealerScore) {
            System.out.println("Kamu kalah!");
            taruhan.setChip(taruhan.getChip());
            System.out.println("\nSisa Chip: " + taruhan.getChip());

            if (taruhan.getChip() < 50) {
                System.out.println("\nNilai chip kurang dari 50. Game over!");
                inputScore(nama, taruhan);
                tampilkanHighScore();
                System.exit(0);
            }
        } 
        
        else {
            System.out.println("Score seri, tidak ada pemenang!");
            taruhan.setChip(taruhan.getChip() + taruhan.getBet());
            hitungHighscore(taruhan);
            System.out.println("\nSisa Chip: " + taruhan.getChip());
        }
    }

    public int calculateScore(List<Card> hand) {
        int score = 0;
        int numAces = 0;

        for (Card card : hand) {
            if (card.getNilai() == Nilai.AS) {
                score += 11;
                numAces++;
            } else {
                score += card.getNilai().getValue();
            }
        }

        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }

    public void inputScore(String nama, Taruhan taruhan) throws IOException {
        String file = "highscore.txt";

        try (FileWriter fileOutput = new FileWriter(file, true);
            BufferedWriter bufferOutput = new BufferedWriter(fileOutput)) {

            String dataHighscore = nama + "," + highscore;
            bufferOutput.write(dataHighscore);
            bufferOutput.newLine();
        } catch (IOException e) {
            System.out.println("Error input ke file highscore.");
        }
    }
}