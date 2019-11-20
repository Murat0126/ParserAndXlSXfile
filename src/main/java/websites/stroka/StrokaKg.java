package websites.stroka;

import gui.swing.view.MainFrame;

import java.io.IOException;

public class StrokaKg implements Runnable {

    private MainFrame mainFrame;

    public StrokaKg(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {

        try {
            System.out.println("Started class.Stroka");
            new Parser(mainFrame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
