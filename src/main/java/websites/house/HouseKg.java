package websites.house;

import gui.swing.view.MainFrame;

import javax.swing.*;
import java.io.IOException;

public class HouseKg implements Runnable {

    private MainFrame mainFrame;

    public HouseKg(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        JTextPane textPane = mainFrame.getTextPane();
        textPane.setText("Загрузка данных house.");
    }

    @Override
    public void run() {
        try {
            new HouseParser(mainFrame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
