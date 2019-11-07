package excel;

import model.Flat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XLSFile {

    private static String[] columns = {"Цена $", "Телефон", "Комнаты","Серия", "Площадь (м'2')","Этаж", "Дата создания","Дата продления","Описание"};
    String fileName = "data";
    Workbook workbook = new XSSFWorkbook();

    private static List<Flat> flats = new ArrayList<Flat>();
    Sheet sheet;

    public XLSFile(String sheetName, List<Flat> flats  ) throws IOException {

        XLSFile.flats = flats;
        createSheet(sheetName);
        createRows(XLSFile.flats);

    }


    public void createSheet(String sheetName){

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

    public void createXLSXFile(String fileName) throws IOException {

        System.out.println("args =  creATE XLSX File" );

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + fileName+".xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);

        workbook.close();

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
            row.createCell(colNum++).setCellValue(flat.getHeadText());

            System.out.println(flat.getPrice()+ flat.getPhoneNumber()+ flat.getRooms()+ flat.getSeries()+ flat.getPlace()+ flat.getFloor()+ flat.getCreateDate()
            + flat.getUpdateDate()+ flat.getHeadText());


        }

        for(int i=0; i<columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        createXLSXFile(fileName);

    }



}