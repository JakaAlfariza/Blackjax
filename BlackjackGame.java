import java.util.List;

public class BlackjackGame extends AbstractBlackjack{
    
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

        switch (pilMenu) {
            case 1:
                clearScreen();
                nama();
                startGame();
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
        String nama = scanner2.nextLine();

        System.out.print("Hallo " + nama + "! Get Ready!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
    
    public void bagikanKartu() {
        for (int i = 0; i < 2; i++) {
            kartuPlayer.add(ambilKartu());
            kartuDealer.add(ambilKartu());
        }
    }

    public void playGame() {
        clearScreen();
        // Show player's hand and score
        System.out.println("Your hand: " + kartuPlayer);
        System.out.println("Your score: " + calculateScore(kartuPlayer));

        kartuDealer.get(0).setHidden(true);
        // Show dealer's visible card
        System.out.println("Dealer's visible card: " + kartuDealer);

        // Player's turn
        while (true) {
            System.out.print("Choose an action (1 - Hit, 2 - Stand): ");
            int action = scanner.nextInt();

            if (action == 1) {
                kartuPlayer.add(ambilKartu());
                int kartuPlayerScore = calculateScore(kartuPlayer);
                System.out.println("Your hand: " + kartuPlayer);
                System.out.println("Your score: " + kartuPlayerScore);

                if (kartuPlayerScore > 21) {
                    System.out.println("Bust! You lose.");
                    return;
                }
            } else if (action == 2) {
                break;
            }
        }

        // Dealer's turn
        System.out.println("\nDealer's turn:");
        kartuDealer.get(0).setHidden(false); // Reveal the dealer's hidden card
        System.out.println("Dealer's hand: " + kartuDealer);
        System.out.println("Dealer's score: " + calculateScore(kartuDealer));

        while (true) {
            if (calculateScore(kartuDealer) >= 17) {
                break;
            }

            System.out.println("\nDealer draws a card.");
            // Pause for a moment before the next dealer's action
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kartuDealer.add(ambilKartu());
            System.out.println("Dealer's hand: " + kartuDealer);
            System.out.println("Dealer's score: " + calculateScore(kartuDealer));

            if (calculateScore(kartuDealer) > 21) {
                System.out.println("Dealer busts! You win.");
                return;
            }

        }
    }

    public void tentukanMenang() {

        int kartuPlayerScore = calculateScore(kartuPlayer);
        int kartuDealerScore = calculateScore(kartuDealer);

        if (kartuPlayerScore > 21) {
            System.out.println("You busted! You lose.");
        } else if (kartuDealerScore > 21) {
            System.out.println("Dealer busted! You win.");
        } else if (kartuPlayerScore > kartuDealerScore) {
            System.out.println("You win!");
        } else if (kartuPlayerScore < kartuDealerScore) {
            System.out.println("You lose.");
        } else {
            System.out.println("It's a tie!");
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