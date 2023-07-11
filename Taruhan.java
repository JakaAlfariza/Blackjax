public class Taruhan {
    private int bet;
    private int chip;

    public Taruhan() {
        this.chip = 200;
    }

    //Overloading
    public Taruhan(int bet) {
        this.bet = bet;
    }

    public int getChip() {
        return chip;
    }

    public void setChip(int chip) {
        this.chip = chip;
    }
    
    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

}