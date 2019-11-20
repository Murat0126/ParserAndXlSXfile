package gui.swing.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final int WEIGHT = 700;
    private static final int HEIGHT = 500;

    public JTextField getTextField() {
        return textField;
    }

    private JTextField textField;


    private JButton stopButton;
    private JButton runButton;
    private JProgressBar progressBar;
    private JPanel openDir;
    private JScrollPane scrolPane;
    private JTextPane textPane;



    private JTextField pathFileTextField;
    private JButton openDirButton;

    private JTextField pagesOfHouse;
    private JTextField pagesOfDoska;
    private JTextField pagesOfStroka;


    private JLabel errorTextLabel;
    private JButton showInFolderButton;


    public MainFrame(){
        setSize(WEIGHT,HEIGHT);
        setMinimumSize(new Dimension(600, 500));
        setContentPane(openDir);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        runButton.setSize(100, 20);
        runButton.setForeground(Color.WHITE);
        runButton.setBackground(Color.gray);
        stopButton.hide();
        progressBar = new JProgressBar();
    }


    public JTextPane getTextPane() {
        return textPane;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getRunButton() {
        return runButton;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JButton getOpenDirButton() {
        return openDirButton;
    }

    public JTextField getPathFileTextField() {
        return pathFileTextField;
    }

    public JLabel getErrorTextLabel() {
        return errorTextLabel;
    }

    public JTextField getPagesOfHouse() {
        return pagesOfHouse;
    }

    public JTextField getPagesOfDoska() {
        return pagesOfDoska;
    }

    public JTextField getPagesOfStroka() {
        return pagesOfStroka;
    }

    public JButton getShowInFolderButton() {
        return showInFolderButton;
    }






}
