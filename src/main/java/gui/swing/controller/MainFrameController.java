package gui.swing.controller;

import gui.swing.model.Model;
import gui.swing.view.MainFrame;
import sun.font.TextLabel;
import websites.doska.DoskaKg;
import websites.house.HouseKg;
import websites.stroka.StrokaKg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.*;

public class MainFrameController {

    private MainFrame mainFrame;
    private JProgressBar progressBar;
    private JButton stopButton;
    private JButton runButton;
    private JTextField pathFileTextField;
    private JTextPane textPane;
    private JButton openDirButton;
    private  JFileChooser fileChooser = null;
    private String pathFile;
    private  JButton showInFolderButton;
    private JLabel jLabel;
    private JTextField pagesOfHouse;


    public MainFrameController() {

        UIManager.put(
                "FileChooser.saveButtonText", "Сохранить");
        UIManager.put(
                "FileChooser.cancelButtonText", "Отмена");
        UIManager.put(
                "FileChooser.fileNameLabelText", "Наименование файла");
        UIManager.put(
                "FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put(
                "FileChooser.lookInLabelText", "Директория");
        UIManager.put(
                "FileChooser.saveInLabelText", "Сохранить в директории");
        UIManager.put(
                "FileChooser.folderNameLabelText", "Путь директории");

        new MainFrame();


        initComponents();
        initListeners();
    }

    public void showMainFrameWindow(){
        mainFrame.setVisible(true);
    }

    private void initComponents() {
        mainFrame = new MainFrame();

        runButton = mainFrame.getRunButton();
        stopButton = mainFrame.getStopButton();
        openDirButton = mainFrame.getOpenDirButton();
        textPane = mainFrame.getTextPane();
        pathFileTextField = mainFrame.getPathFileTextField();
        progressBar = mainFrame.getProgressBar();
        jLabel = mainFrame.getErrorTextLabel();
        pagesOfHouse = mainFrame.getPagesOfHouse();
        showInFolderButton = mainFrame.getShowInFolderButton();

    }

    private void initListeners() {
        runButton.addActionListener(new RunButton());
        stopButton.addActionListener(new StopButton());
        openDirButton.addActionListener(new OpenDirButton());
        showInFolderButton.addActionListener(new ShowInFolderButton());
        fileChooser = new JFileChooser();
    }

    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private class RunButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(pagesOfHouse.getText().matches("^\\d+$")){
                jLabel.setVisible(false);
                textPane.setText("Загружается ...");
                openDirButton.setVisible(false);


                if (runButton.getText().equals("Остановить")){

                    executorService.shutdownNow();
                    textPane.setText("Загрузка остановлено ...");
                    stopButton.setVisible(false);
                    runButton.setVisible(true);
                    openDirButton.setVisible(true);
                    showInFolderButton.setVisible(true);
                    runButton.setText("перезапустить");
                    return;
                }else{

                    showInFolderButton.setVisible(false);
                    runButton.setText("Остановить");

                    try {
                        executorService = Executors.newFixedThreadPool(3);
                        pathFile = pathFileTextField.getText();
                        DoskaKg doskaKg = new DoskaKg(mainFrame);
                        StrokaKg strokaKg = new StrokaKg(mainFrame);
                        HouseKg houseKg = new HouseKg(mainFrame);

                       CompletableFuture[] futures = new CompletableFuture[3];

                        futures[0] = CompletableFuture.runAsync(houseKg, executorService);
                        futures[1] = CompletableFuture.runAsync(strokaKg, executorService);
                        futures[2] = CompletableFuture.runAsync(doskaKg, executorService);
                        CompletableFuture.allOf(futures)
                                .thenRun(() -> {
                                    showInFolderButton.setVisible(true);
                                    runButton.setText("Перезапустить");
                                    textPane.setText("ЗАВЕРШЕНО ...");
                                    openDirButton.setVisible(true);

                                });


                    }  catch (IOException | ParseException e1) {
                        e1.printStackTrace();
                    }

                }

            }else{
                jLabel.setVisible(true);
                jLabel.setText("Пожалуйста введите цифровых значений!");
            }



        }
    }

    private class StopButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            executorService.shutdownNow();
//            textPane.setText("Загрузка остановлено ...");
//            stopButton.setVisible(false);
//            runButton.setVisible(true);
//            openDirButton.setVisible(true);
//            runButton.setText("перезапустить");

        }
    }

    private class OpenDirButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setDialogTitle("Выбор директории");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(mainFrame);
            if (result == JFileChooser.APPROVE_OPTION ) {
                JOptionPane.showMessageDialog(mainFrame,
                        fileChooser.getSelectedFile());

                pathFileTextField.setText(fileChooser.getSelectedFile().getPath());
                pathFile = fileChooser.getSelectedFile().getPath();

            }
        }
    }

    public void setMainFrame(String status) {
        textPane.setText("\n" + status);
    }


    public void openFileDirection(String path) throws IOException {
        File file = new File (path);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
    }


    private class ShowInFolderButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                openFileDirection(pathFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
