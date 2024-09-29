package com.example.controller;

import java.io.IOException;
import java.lang.invoke.VarHandle.AccessMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFChart.HSSFChartType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFBar3DChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFDoughnutChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDoughnutChartData.Series;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFScatterChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
@Transactional
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @GetMapping("/")
    public ModelAndView showChart() {
    	//Gson gsonObj = new Gson();
    	ModelAndView model = new ModelAndView();
    	Map<Object,Object> map = null;
    	List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
    	 
    	map = new HashMap<Object,Object>(); map.put("label", "A"); map.put("y", 1000); list.add(map);
    	map = new HashMap<Object,Object>(); map.put("label", "B"); map.put("y", 25000); list.add(map);
    	map = new HashMap<Object,Object>(); map.put("label", "C"); map.put("y", 7000); list.add(map);
    	map = new HashMap<Object,Object>(); map.put("label", "D"); map.put("y", 1900); list.add(map);
    	map = new HashMap<Object,Object>(); map.put("label", "E"); map.put("y", 1500); list.add(map);
      //   model.addAttribute("dataPoints", dataPoints);
               
         model.setViewName("userForm");
         model.addObject("chartData", list); 
         
    	
    	 
    	return model;
    	
    } 
    
    
    
   // @GetMapping("/")
   // @RequestMapping(value = "/", method = RequestMethod.GET)
 /*   @GetMapping("/")
    public String showForm() {
    	System.out.println("in controller.........");
    //	return "redirect:userForm";
        return "userForm";
    } */
   
  //   @GetMapping("/export")
   //  @ResponseBody
  //  @RequestMapping(value = "/export", method = RequestMethod.GET)
   
    @RequestMapping(value = "/above40", method = RequestMethod.GET)
    public void exportToExcelabove40(HttpServletResponse response) throws IOException {
    	System.out.println("in export.........");
        String sql = "SELECT * FROM users where age > 40";
        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> user : users) {
            System.out.println(user);
        }
        XSSFWorkbook workbook = new XSSFWorkbook(); 
        XSSFSheet sheet = workbook.createSheet("Users");

        XSSFRow headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Email", "Age"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Map<String, Object> user : users) {
        	XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((Integer) user.get("id"));
            row.createCell(1).setCellValue((String) user.get("name"));
            row.createCell(2).setCellValue((String) user.get("email"));
            row.createCell(3).setCellValue((Integer) user.get("age"));
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();

     //   XSSFSheet sheet = workbook.createSheet("Users");
    } 
    
    
    //testreport
    @RequestMapping(value = "/firstreport", method = RequestMethod.GET)
    public void exportToExcelfirstreport(HttpServletResponse response) throws IOException {
    	System.out.println("in export.........");
        String sql = "SELECT phase, activity_name, status, participants, activity_form, responsiblefor, objectiveomr FROM omr";
        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> user : users) {
            System.out.println(user);
        }
        XSSFWorkbook workbook = new XSSFWorkbook(); 
        XSSFSheet sheet = workbook.createSheet("Proposal Engagement");
        
        XSSFCellStyle wrapTextStyle = workbook.createCellStyle();
        wrapTextStyle.setAlignment(HorizontalAlignment.CENTER);
        
        
        
        Font font = workbook.createFont();
        font.setBold(true);
        wrapTextStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex()); // You can choose any color
        wrapTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        wrapTextStyle.setFont(font);
       
     /*   
     //   Font font = workbook.createFont();
      //  font.setFontHeight((short) 11);
      //  font.setFontName("Calibri");
      //  wrapTextStyle.setFont(font);
         sheet.addMergedRegion(new CellRangeAddress(1, 3, 6, 6));
        wrapTextStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex()); // You can choose any color
        wrapTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        wrapTextStyle.setBorderBottom(BorderStyle.THIN);
        wrapTextStyle.setBorderLeft(BorderStyle.THIN);
        wrapTextStyle.setBorderRight(BorderStyle.THIN);
        wrapTextStyle.setBorderTop(BorderStyle.THIN);
        */
        double cm = 10.0;
        double points = cm * 28.35;

        // Convert points to Excel units (1 unit = 1/256th of a character width)
        int widthUnits = (int) (points * 256 / 8.43);
       

        XSSFRow headerRow = sheet.createRow(0);
        String[] headers = {"Phases", "Activity name", "Status", "Participants", "Activity form", "Responsible for", "Objectives"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(wrapTextStyle);
            
           
            
            
        }

        int rowNum = 1;
        
        for (Map<String, Object> user : users) {
        	XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((Integer) user.get("phase"));
            row.createCell(1).setCellValue((String) user.get("activity_name"));
            row.createCell(2).setCellValue((String) user.get("status"));
            row.createCell(3).setCellValue((String) user.get("participants"));
            row.createCell(4).setCellValue((String) user.get("activity_form"));
            row.createCell(5).setCellValue((String) user.get("responsiblefor"));
            row.createCell(6).setCellValue((String) user.get("objectiveomr")); 
           // row.setHeight((short) (25 * 25));
            
     
                        
                        
                    }

        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
            
           
            if(i>0)
            {
            	sheet.setColumnWidth(i, widthUnits);
            }
          
            
         //   sheet.setDefaultColumnStyle(i, wrapTextStyle);
            
        }
       
       
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=proposalreport.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();

     //   XSSFSheet sheet = workbook.createSheet("Users");
    } 
    //
    
    
    // report
    /*
    @RequestMapping(value = "/firstreport", method = RequestMethod.GET)
    public void exportToExcelfirstreport(HttpServletResponse response) throws IOException {
    	System.out.println("in export.........");
        String sql = "SELECT phase, activity_name, status, participants, activity_form, responsiblefor, objectiveomr FROM omr";
        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> user : users) {
            System.out.println(user);
        }
        XSSFWorkbook workbook = new XSSFWorkbook(); 
        XSSFSheet sheet = workbook.createSheet("Proposal Engagement");
        
        XSSFCellStyle wrapTextStyle = workbook.createCellStyle();
        wrapTextStyle.setAlignment(HorizontalAlignment.CENTER);
        
        double cm = 10.0;
        double points = cm * 28.35;

        // Convert points to Excel units (1 unit = 1/256th of a character width)
        int widthUnits = (int) (points * 256 / 8.43);
       

        XSSFRow headerRow = sheet.createRow(0);
        String[] headers = {"Phases", "Activity name", "Status", "Participants", "Activity form", "Responsible for", "Objectives"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(wrapTextStyle);
            
           
            
            
        }

        int rowNum = 1;
        
        for (Map<String, Object> user : users) {
        	XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((Integer) user.get("phase"));
            row.createCell(1).setCellValue((String) user.get("activity_name"));
            row.createCell(2).setCellValue((String) user.get("status"));
            row.createCell(3).setCellValue((String) user.get("participants"));
            row.createCell(4).setCellValue((String) user.get("activity_form"));
            row.createCell(5).setCellValue((String) user.get("responsiblefor"));
            row.createCell(6).setCellValue((String) user.get("objectiveomr")); 
            
     
                        
                        
                    }

        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
            
           
            if(i>0)
            {
            	sheet.setColumnWidth(i, widthUnits);
            }
          
            
            sheet.setDefaultColumnStyle(i, wrapTextStyle);
            
        }
       

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();

     //   XSSFSheet sheet = workbook.createSheet("Users");
    } 
    */
    ///report end
    
    
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportToExcel(HttpServletResponse response) throws IOException {
    	System.out.println("in export.........");
        String sql = "SELECT name, age FROM users";
        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> user : users) {
            System.out.println(user);
        }
        XSSFWorkbook workbook = new XSSFWorkbook(); 
        XSSFSheet sheet = workbook.createSheet("Users");

        XSSFRow headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Email", "Age"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Map<String, Object> user : users) {
        	XSSFRow row = sheet.createRow(rowNum++);
          //  row.createCell(0).setCellValue((Integer) user.get("id"));
            row.createCell(1).setCellValue((String) user.get("name"));
         //   row.createCell(2).setCellValue((String) user.get("email"));
            row.createCell(3).setCellValue((Integer) user.get("age"));
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 5, 1, 15, 20);
        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleOverlay(false);
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("name");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("Age");
     //   leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
       // leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        
        
        XDDFDataSource<String> xs = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(1, rowNum-1, 1, 1));
        XDDFDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, rowNum-1, 3, 3));
        XDDFScatterChartData data = (XDDFScatterChartData) chart.createData(ChartTypes.SCATTER, bottomAxis, leftAxis);
        XDDFScatterChartData.Series series = (XDDFScatterChartData.Series) data.addSeries(xs, (XDDFNumericalDataSource<? extends Number>) ys);
      
        //  Series series = (Series) data.addSeries(xs, (XDDFNumericalDataSource<? extends Number>) ys);
        // XDDFDoughnutChartData.Series series  = (XDDFDoughnutChartData.Series) data.addSeries(xs, (XDDFNumericalDataSource<? extends Number>) ys);
       
      
       // data.setHoleSize(50);
        chart.plot(data);
        
        
        
        
        /* doughnut chart
        
        XDDFDataSource<String> xs = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(1, rowNum-1, 1, 1));
        XDDFDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, rowNum-1, 3, 3));
      

        XDDFDoughnutChartData data = (XDDFDoughnutChartData) chart.createData(ChartTypes.DOUGHNUT, bottomAxis, leftAxis);
      Series series = (Series) data.addSeries(xs, (XDDFNumericalDataSource<? extends Number>) ys);
        // XDDFDoughnutChartData.Series series  = (XDDFDoughnutChartData.Series) data.addSeries(xs, (XDDFNumericalDataSource<? extends Number>) ys);
        series.setTitle("Data Series", null); 
      
        data.setHoleSize(50);
        chart.plot(data);
        
        */
        //second
      /* bar chart
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 5, 1, 15, 20);
        XSSFChart chart = drawing.createChart(anchor);
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.BOTTOM);
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("x");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);

        leftAxis.setTitle("f(x)");
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        
        XDDFDataSource<String> xs = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(1, rowNum, 1, 1));
        XDDFDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, rowNum, 3, 3));
        XDDFBarChartData data = (XDDFBarChartData) chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
        XDDFBarChartData.Series series = (XDDFBarChartData.Series) data.addSeries(xs, (XDDFNumericalDataSource<? extends Number>) ys);
        series.setTitle("Age", null);
     // Plot chart:
     chart.plot(data);
     */
//sendend

        
       
       
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();

     //   XSSFSheet sheet = workbook.createSheet("Users");
    } 
    /*
    @GetMapping("/export")
    @ResponseBody 
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportToExcel(HttpServletResponse response) throws IOException {
        String sql = "SELECT * FROM users";
        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Users");

        XSSFRow headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Email", "Age"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Map<String, Object> user : users) {
        	XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((Integer) user.get("id"));
            row.createCell(1).setCellValue((String) user.get("name"));
            row.createCell(2).setCellValue((String) user.get("email"));
            row.createCell(3).setCellValue((Integer) user.get("age"));
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    } */

    @PostMapping("/saveUser")
    public ModelAndView saveUser(@RequestParam("name") String name,
                                 @RequestParam("email") String email,
                                 @RequestParam("age") int age) {
        String sql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, name, email, age);

        return new ModelAndView("redirect:/");
    } 
}
