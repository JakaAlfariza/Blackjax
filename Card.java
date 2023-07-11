public class Card {
    private Nilai Nilai;
    private Lambang Lambang;
    private boolean hidden;

    public Card(Nilai Nilai, Lambang Lambang) {
        this.Nilai = Nilai;
        this.Lambang = Lambang;
        this.hidden = false; //False: kartu terbuka
    }

    public Nilai getNilai() {
        return Nilai;
    }

    public Lambang getLambang() {
        return Lambang;
    }

    public boolean getHidden() {
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
            return "\n┌─────────┐\n" +
                    "│" + Nilai.getSymbol() + "       │\n" +
                    "│         │\n" +
                    "│    " + Lambang.getSymbol() + "    │\n" +
                    "│         │\n" +
                    "│       " + Nilai.getSymbol() + "│\n" +
                    "└─────────┘";
        }
    }
}
