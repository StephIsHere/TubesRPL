package com.example.TubesRPL.sidang;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.io.*;
import java.sql.*;
import java.util.*;

@Service
public class GenerateBAP {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, String> fetchSidangData(int idSidang) {
        String query = """
                SELECT s.judul, s.topik, s.tanggal, s.waktu, s.tempat, s.status,
                       m.nama AS namaMahasiswa, m.nik AS nikMahasiswa,
                       k.nama AS namaKoordinator, k.nik AS nikKoordinator
                FROM sidang s
                JOIN users m ON s.idMahasiswa = m.idUser
                JOIN users k ON s.idKoordinator = k.idUser
                WHERE s.idSidang = ?
                """;

        return jdbcTemplate.queryForObject(query, new Object[]{idSidang}, (rs, rowNum) -> {
            Map<String, String> data = new HashMap<>();
            data.put("judul", rs.getString("judul"));
            data.put("topik", rs.getString("topik"));
            data.put("tanggal", rs.getDate("tanggal").toString());
            data.put("waktu", rs.getTime("waktu").toString());
            data.put("tempat", rs.getString("tempat"));
            data.put("status", rs.getString("status"));
            data.put("namaMahasiswa", rs.getString("namaMahasiswa"));
            data.put("nikMahasiswa", rs.getString("nikMahasiswa"));
            data.put("namaKoordinator", rs.getString("namaKoordinator"));
            data.put("nikKoordinator", rs.getString("nikKoordinator"));
            return data;
        });
    }

    public void generateWordDocument(Map<String, String> data) {
        String templatePath = "src/main/resources/templates/Sidang_Template.docx";
        String filledTemplatePath = "src/main/resources/templates/Sidang_FilledTemplate.docx";

        try (XWPFDocument doc = new XWPFDocument(new FileInputStream(templatePath))) {
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                String paragraphText = paragraph.getText();
                if (paragraphText != null) {
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        paragraphText = paragraphText.replace("${" + entry.getKey() + "}", entry.getValue());
                    }

                    for (XWPFRun run : paragraph.getRuns()) {
                        run.setText("", 0);
                    }
                    XWPFRun newRun = paragraph.createRun();
                    newRun.setText(paragraphText);
                }
            }

            try (FileOutputStream out = new FileOutputStream(filledTemplatePath)) {
                doc.write(out);
                System.out.println("Word document successfully updated at: " + filledTemplatePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("File error: " + e.getMessage(), e);
        }

        convertToPDF(filledTemplatePath);
    }

    public void convertToPDF(String wordPath) {
        String pdfOutputPath = "src/main/resources/static/Sidang_FilledTemplate.pdf";

        try {
            com.aspose.words.Document document = new com.aspose.words.Document(wordPath);
            document.save(pdfOutputPath, com.aspose.words.SaveFormat.PDF);
            System.out.println("PDF successfully generated at: " + pdfOutputPath);
        } catch (Exception e) {
            throw new RuntimeException("PDF conversion error: " + e.getMessage(), e);
        }
    }

    public void generate(int idSidang) {
        Map<String, String> data = fetchSidangData(idSidang);
        generateWordDocument(data);
    }
}
