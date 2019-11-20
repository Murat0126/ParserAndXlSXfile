package websites.doska;

import gui.swing.controller.MainFrameController;
import gui.swing.model.Model;
import gui.swing.view.MainFrame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;

public class DoskaKg implements Runnable {

    private String pathFile;
    private JTextPane textPane;
    private MainFrame mainFrame;

    public DoskaKg(MainFrame mainFrame) throws IOException, ParseException {
        this.mainFrame = mainFrame;
        System.out.println("Started class.Doska");
        textPane = mainFrame.getTextPane();
        textPane.setText( "\r\n"+"Загрузка данных doska.");
//        MainFrameController mainFrameController = new MainFrameController();
//        mainFrameController.setMainFrame("Загрузка данных doska.");
  }


    @Override
    public void run() {
//        Model model = new Model();
//        model.setTextOfThreadStatus("Загрузка данных doska.");
        try {
            new ParseDoska(mainFrame);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
