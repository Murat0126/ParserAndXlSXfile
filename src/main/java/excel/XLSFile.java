package excel;

import gui.swing.controller.MainFrameController;
import gui.swing.view.MainFrame;
import model.Flat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XLSFile {

    private static String[] columns = {"Цена $", "Телефон", "Комнаты","Серия", "Площадь (м'2')",
            "Этаж", "Дата создания","Дата продления","Просмотры", "Описание"};
     private String fileName;
    private Workbook workbook = new XSSFWorkbook();

    private static List<Flat> flats = new ArrayList<>();
    private Sheet sheet;
    private MainFrame mainFrame;
    private JTextField pathFileTextField;
//    JTextPane textPane = mainFrame.getTextPane();

    public XLSFile(String sheetName, List<Flat> flats, String fileName, MainFrame mainFrame) throws IOException {

        XLSFile.flats = flats;
        this.fileName = fileName;
        this.mainFrame = mainFrame;


        createSheet(sheetName);
        createRows(XLSFile.flats);





    }

    private void createSheet(String sheetName){



        System.out.println("args =  creATE Sheet" );

        sheet = workbook.createSheet(sheetName);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        for(int i=0; i<columns.length; i++) {

            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(columns[i]);
            headerCell.setCellStyle(headerStyle);
            sheet.autoSizeColumn(i);

        }

    }

    public void createXLSXFile() throws IOException {

        System.out.println("args =  creATE XLSX File" );
        pathFileTextField = mainFrame.getPathFileTextField();
        String pathUri = pathFileTextField.getText();
        JTextPane textPane = mainFrame.getTextPane();

        try {

            File currDir = new File(pathUri);
            String path = currDir.getAbsolutePath();
            System.out.println("args =  File Name: " +  fileName);
            String fileLocation = path+"\\" + fileName+".xlsx";

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);

            workbook.close();


            textPane.setText("Завершено и создано" + fileName+".xlsx файл.");
            textPane.setText("Путь к созданным doc. файлам: " + fileLocation);


            System.out.println(fileLocation);

        }catch (FileNotFoundException f){
            JLabel jLabel = mainFrame.getErrorTextLabel();
            jLabel.setVisible(true);
            textPane.setText("Процесс не может получить доступ к" + fileName + "\n"+" так как этот файл занят другим процессом");
            jLabel.setText("Процесс не может получить доступ к" + fileName + "\n"+". , так как этот файл занят другим процессом");
        }


    }


    public void createRows(List<Flat> flats) throws IOException {

        System.out.print("   CreateROWS");

        int rowNum = 1;
        for(Flat flat: flats) {
            int colNum = 0;
            Row row = sheet.createRow(rowNum++);



            row.createCell(colNum++).setCellValue(flat.getPrice());
            row.createCell(colNum++).setCellValue(flat.getPhoneNumber());
            row.createCell(colNum++).setCellValue(flat.getRooms());
            row.createCell(colNum++).setCellValue(flat.getSeries());
            row.createCell(colNum++).setCellValue(flat.getPlace());
            row.createCell(colNum++).setCellValue(flat.getFloor());
            row.createCell(colNum++).setCellValue(flat.getCreateDate());
            row.createCell(colNum++).setCellValue(flat.getUpdateDate());
            row.createCell(colNum++).setCellValue(flat.getViews());
            row.createCell(colNum++).setCellValue(flat.getHeadText());

            System.out.println(flat.getPrice()+ flat.getPhoneNumber()+ flat.getRooms()+ flat.getSeries()+ flat.getPlace()+ flat.getFloor()+ flat.getCreateDate()
            + flat.getUpdateDate()+ flat.getHeadText());


        }

        for(int i=0; i<columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        createXLSXFile();

    }






}