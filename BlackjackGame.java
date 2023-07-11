import java.util.List;

public class BlackjackGame extends AbstractBlackjack{
    public String nama;
    
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

    public void menu() {
        clearScreen();
        opening();

        System.out.print("\n\nPilih Menu (1/2/3): ");
        int pilMenu = scanner.nextInt();

        Taruhan taruhan = new Taruhan();
        switch (pilMenu) {
            case 1:
                clearScreen();
                nama();
                startGame(taruhan);
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                clearScreen();
                menu();
                break;
        }
    }

    public void nama() {
        System.out.print("Masukan nama: ");
        nama = scannerNama.nextLine();

        System.out.print("Hallo " + nama + "! Get Ready!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lanjutkan();
    }
    
    public void bagikanKartu() {
        for (int i = 0; i < 2; i++) {
            kartuPlayer.add(ambilKartu());
            kartuDealer.add(ambilKartu());
        }
    }

    public void playGame() {
        clearScreen();
        System.out.println("Giliran "+nama+" Bermain");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        clearScreen();
        // Show player's hand and score
        System.out.println("Kartu "+ nama +": " + kartuPlayer);
        System.out.println(nama+" score: " + calculateScore(kartuPlayer));

        kartuDealer.get(0).setHidden(true);
        // Show dealer's visible card
        System.out.println("\nKartu Dealer: " + kartuDealer);

        // Player's turn
        while (true) {
            System.out.print("Pilih aksi (1 - Hit, 2 - Stand): ");
            int action = scanner.nextInt();

            if (action == 1) {
                clearScreen();
                System.out.println(nama + " Menambah Kartu");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                clearScreen();
                kartuPlayer.add(ambilKartu());
                int kartuPlayerScore = calculateScore(kartuPlayer);

                System.out.println("Kartu " + nama + ": " + kartuPlayer);
                System.out.println(nama + " score: " + calculateScore(kartuPlayer));

                kartuDealer.get(0).setHidden(true);
                System.out.println("\nKartu Dealer: " + kartuDealer);

                if (kartuPlayerScore > 21) {
                    System.out.println("Bust! kamu kalah!");
                    return;
                }

            } else if (action == 2) {
                clearScreen();
                System.out.println(nama + " Mengakhiri Giliran Bermain");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

        // Dealer's turn
        clearScreen();
        System.out.println("Giliran Dealer Bermain");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        clearScreen();

        System.out.println("Kartu " + nama + ": " + kartuPlayer);
        System.out.println(nama + " score: " + calculateScore(kartuPlayer));

        kartuDealer.get(0).setHidden(false); // Reveal the dealer's hidden card
        System.out.println("\nKartu Dealer: " + kartuDealer);
        System.out.println("Dealer score: " + calculateScore(kartuDealer));

        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (calculateScore(kartuDealer) > calculateScore(kartuPlayer) || calculateScore(kartuDealer) >= 17) {
                break;
            }

            clearScreen();
            System.out.println("\nDealer Menambah kartu");
            // Pause for a moment before the next dealer's action
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clearScreen();
            System.out.println("Kartu " + nama + ": " + kartuPlayer);
            System.out.println(nama + " score: " + calculateScore(kartuPlayer));
            
            kartuDealer.add(ambilKartu());
            System.out.println("\nKartu Dealer: " + kartuDealer);
            System.out.println("Dealer score: " + calculateScore(kartuDealer));

            if (calculateScore(kartuDealer) > 21) {
                System.out.println("Dealer busts! Kamu Menang");
                return;
            }

        }
    }

    public void tentukanMenang(Taruhan taruhan) {
        int kartuPlayerScore = calculateScore(kartuPlayer);
        int kartuDealerScore = calculateScore(kartuDealer);

        if (kartuPlayerScore > 21) {
            System.out.println(nama+" busted! kamu kalah!.");
            taruhan.setChip(taruhan.getChip());
            System.out.println("\nSisa Chip: " + taruhan.getChip());

            if (taruhan.getChip() < 50) {
                System.out.println("\nNilai chip kurang dari 50. Game over!");
                System.exit(0);
            }
        }
        
        else if (kartuDealerScore > 21) {
            System.out.println("Dealer busted! kamu menang!");
            taruhan.setChip(taruhan.getChip() + (taruhan.getBet() * 2));
            System.out.println("\nSisa Chip: " + taruhan.getChip());
        } 
        
        else if (kartuPlayerScore > kartuDealerScore) {
            System.out.println("kamu menang!");
            taruhan.setChip(taruhan.getChip() + (taruhan.getBet() * 2));
            System.out.println("\nSisa Chip: " + taruhan.getChip());
        } 
        
        else if (kartuPlayerScore < kartuDealerScore) {
            System.out.println("Kamu kalah!");
            taruhan.setChip(taruhan.getChip());
            System.out.println("\nSisa Chip: " + taruhan.getChip());

            if (taruhan.getChip() < 50) {
                System.out.println("\nNilai chip kurang dari 50. Game over!");
                System.exit(0);
                // tampilkan highscore
            }
        } 
        
        else {
            System.out.println("Score seri, tidak ada pemenang!");
            taruhan.setChip(taruhan.getChip() + taruhan.getBet());
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
}