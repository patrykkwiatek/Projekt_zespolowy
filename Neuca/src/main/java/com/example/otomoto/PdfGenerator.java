package com.example.otomoto;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfGenerator {

    private String safe(String text) {
        return text != null ? text : "";
    }

    public byte[] generujFakture(
            String nazwa, String NIP, String adres1, String adres2,
            List<ProduktKoszyk> produkty) throws IOException {

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDType0Font font = PDType0Font.load(document, getClass().getResourceAsStream("/fonts/arial.ttf"));

            PDPageContentStream content = new PDPageContentStream(document, page);
            content.setFont(font, 12);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;

            // Nagłówek faktury
            content.beginText();
            content.setFont(font, 20);
            content.newLineAtOffset(margin, yStart);
            content.showText("FAKTURA VAT");
            content.endText();

            // Dane sprzedającego
            float y = yStart - 40;
            content.beginText();
            content.setFont(font, 12);
            content.newLineAtOffset(margin, y);
            content.showText("Sprzedający:");
            content.endText();

            y -= 15;
            content.beginText();
            content.newLineAtOffset(margin, y);
            content.showText("Health Center");
            content.endText();

            y -= 15;
            content.beginText();
            content.newLineAtOffset(margin, y);
            content.showText("NIP: 1111111111");
            content.endText();

            y -= 15;
            content.beginText();
            content.newLineAtOffset(margin, y);
            content.showText("Adres: ul. Zdrowa 1, 00-000 Toruń");
            content.endText();

            // Dane kupującego
            y -= 40;
            content.beginText();
            content.newLineAtOffset(margin, y);
            content.showText("Kupujący:");
            content.endText();

            y -= 15;
            content.beginText();
            content.newLineAtOffset(margin, y);
            content.showText(nazwa);
            content.endText();

            y -= 15;
            content.beginText();
            content.newLineAtOffset(margin, y);
            content.showText("NIP: " + NIP);
            content.endText();

            y -= 15;
            content.beginText();
            content.newLineAtOffset(margin, y);
            content.showText("Adres: " + adres1 + ", " + adres2);
            content.endText();

            // Tabela produktów
            y -= 40;
            float tableTopY = y;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float rowHeight = 20f;
            float cellMargin = 5f;

            float[] columnWidths = {200, 50, 100, 100};
            String[] headers = {"Nazwa produktu", "Ilość", "Cena jedn.", "Cena całk."};

            float nextY = tableTopY;
            float textY = nextY - 15;

            // Nagłówek tabeli
            content.setLineWidth(1);
            content.moveTo(margin, nextY);
            content.lineTo(margin + tableWidth, nextY);
            content.stroke();

            nextY -= rowHeight;

            float textX = margin;
            for (int i = 0; i < headers.length; i++) {
                content.beginText();
                content.newLineAtOffset(textX + cellMargin, textY);
                content.showText(headers[i]);
                content.endText();
                textX += columnWidths[i];
            }


            // Dane produktów
            for (ProduktKoszyk produkt : produkty) {
                String nazwaProduktu = safe(produkt.getLek() != null ? produkt.getLek().getName() : "");
                String ilosc = String.valueOf(produkt.getIlosc());
                String cenaJedn = safe(produkt.getLek().cenaString());
                String cenaCalk = safe(produkt.ustawCene());

                String[] values = {nazwaProduktu, ilosc, cenaJedn, cenaCalk};

                textY = nextY - 15;
                float textXRow = margin;

                for (int i = 0; i < values.length; i++) {
                    content.beginText();
                    content.newLineAtOffset(textXRow + cellMargin, textY);
                    content.showText(values[i]);
                    content.endText();
                    textXRow += columnWidths[i];
                }

                content.moveTo(margin, nextY);
                content.lineTo(margin + tableWidth, nextY);
                content.stroke();

                nextY -= rowHeight;


            }
            content.moveTo(margin, nextY);
            content.lineTo(margin + tableWidth, nextY);
            content.stroke();

            // Linie pionowe
            float x = margin;
            for (float colWidth : columnWidths) {
                content.moveTo(x, tableTopY);
                content.lineTo(x, nextY);
                content.stroke();
                x += colWidth;
            }
            content.moveTo(margin + tableWidth, tableTopY);
            content.lineTo(margin + tableWidth, nextY);
            content.stroke();


            // SUMA KOŃCOWA
            y = nextY - 30;
            content.beginText();
            content.setFont(font, 14);
            content.newLineAtOffset(margin, y);
            Zamowienie z=produkty.get(0).getZamowienie();

            String sumaZ = z.cenaRazem();
            String sumaStr = "Suma do zapłaty: "+sumaZ;
            content.showText(sumaStr);
            content.endText();

            content.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        }
    }



}
