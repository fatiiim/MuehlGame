package Model;

/**
 * Game Thread
 */
public class PauseThread extends Thread  {
    private volatile boolean isPaused;
    /**
     * Diese Funktion wurde verwendet, um das Spiel anzuhalten.
     */
    public PauseThread() {
        this.isPaused = false;
    }

    /**
     * Führt das Starten des Threads.
     * Die Run-Methode des Objekts in diesem separat ausgeführten Thread aufgerufen wird.
     */
    @Override
    public synchronized void run() {
        while (isPaused) {
            // Warten, bis der Thread fortgesetzt wird
            try { wait(); }
            catch (InterruptedException ex) {ex.printStackTrace(); }
        } notify();/* Benachrichtigen, dass der Thread fortgesetzt wurde*/}
    /**
     * Legt den pausierten Zustand des Threads fest
     *
     * @param paused wenn wahr, wird der Thread angehalten, wenn falsch, wird der Thread fortgesetzt
     */
    public void setPaused(boolean paused) {this.isPaused = paused;}
    /**
     * Gibt einen booleschen Wert zurück, der angibt, ob das Spiel gerade pausiert.
     * @return true, wenn das Spiel pausiert, sonst false.
     */
    public boolean isPaused() {
        return isPaused;
    }
}
