import gui.swing.controller.MainFrameController;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("args =  Main started!!!" );

        MainFrameController mainFrameController = new MainFrameController();
        mainFrameController.showMainFrameWindow();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

      executorService.shutdown();



    }




}
