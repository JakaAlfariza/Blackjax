public class Next implements InterfaceNext {
    public void lanjutkan() {
        System.out.print("\n\nContinue in... 3");
        sleepThread(1000);

        System.out.print("\nContinue in... 2");
        sleepThread(1000);

        System.out.print("\nContinue in... 1");
        sleepThread(1000);
    }

    public void sleepThread(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
