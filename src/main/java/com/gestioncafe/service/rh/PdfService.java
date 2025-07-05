package com.gestioncafe.service.rh;

import com.gestioncafe.model.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.File;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;
import java.sql.Date;

import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

@Service
public class PdfService {

    // Couleurs du thème
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);      // Bleu professionnel
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);      // Gris foncé
    private static final Color ACCENT_COLOR = new Color(231, 76, 60);        // Rouge accent
    private static final Color LIGHT_GRAY = new Color(236, 240, 241);        // Gris clair
    private static final Color DARK_GRAY = new Color(127, 140, 141);         // Gris moyen
    private static final Color WHITE = Color.WHITE;

    // Constantes de mise en page
    private static final float PAGE_WIDTH = PDRectangle.A4.getWidth();
    private static final float PAGE_HEIGHT = PDRectangle.A4.getHeight();
    private static final float MARGIN = 40;
    private static final float HEADER_HEIGHT = 120;
    private static final float SIDEBAR_WIDTH = 200;

    public void generateCandidatPdf(
            Candidat candidat,
            List<SerieBac> series,
            List<Formation> formations,
            List<Langue> langues,
            List<Experience> experiences,
            Genre genre,
            HttpServletResponse response
    ) throws Exception {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"CV_" + 
        cleanFileName(candidat.getNom()) + "_" + candidat.getId() + ".pdf\"");


        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            // 1. Dessiner l'arrière-plan et les sections colorées
            drawBackground(content);

            // 2. En-tête avec nom et photo
            drawHeader(content, candidat, document);

            // 3. Sidebar gauche avec informations personnelles
            drawSidebar(content, candidat, genre, series, langues);

            // 4. Contenu principal à droite
            drawMainContent(content, candidat, formations, experiences);

            // 5. Pied de page
            drawFooter(content);

            content.close();

            OutputStream out = response.getOutputStream();
            document.save(out);
        }
    }

    private void drawBackground(PDPageContentStream content) throws Exception {
        // Fond blanc global
        content.setNonStrokingColor(WHITE);
        content.addRect(0, 0, PAGE_WIDTH, PAGE_HEIGHT);
        content.fill();

        // Barre d'en-tête colorée
        content.setNonStrokingColor(PRIMARY_COLOR);
        content.addRect(0, PAGE_HEIGHT - HEADER_HEIGHT, PAGE_WIDTH, HEADER_HEIGHT);
        content.fill();

        // Sidebar colorée
        content.setNonStrokingColor(LIGHT_GRAY);
        content.addRect(0, 0, SIDEBAR_WIDTH, PAGE_HEIGHT - HEADER_HEIGHT);
        content.fill();

        // Ligne de séparation verticale élégante
        content.setStrokingColor(PRIMARY_COLOR);
        content.setLineWidth(2);
        content.moveTo(SIDEBAR_WIDTH, 0);
        content.lineTo(SIDEBAR_WIDTH, PAGE_HEIGHT - HEADER_HEIGHT);
        content.stroke();
    }

    private void drawHeader(PDPageContentStream content, Candidat candidat, PDDocument document) throws Exception {
        float headerY = PAGE_HEIGHT - 30;

        // Photo de profil (cercle)
        if (candidat.getImage() != null && !candidat.getImage().isEmpty()) {
            try {
                // Chemin relatif dans resources/static
                String imagePath = "static/uploads/employe/" + candidat.getImage();

                ClassPathResource imgFile = new ClassPathResource(imagePath);
                if (imgFile.exists()) {
                    InputStream is = imgFile.getInputStream();
                    PDImageXObject image = PDImageXObject.createFromByteArray(document, is.readAllBytes(), candidat.getImage());
                    is.close();

                    content.setNonStrokingColor(255, 255, 255); // blanc
                    content.addRect(50, headerY - 80, 80, 80);
                    content.fill();

                    content.drawImage(image, 55, headerY - 75, 70, 70);
                } else {
                    System.err.println("Image non trouvée dans classpath: " + imagePath);
                }
            } catch (Exception e) {
                System.err.println("Erreur chargement image: " + e.getMessage());
            }
        }

        // Nom en grand
        content.setNonStrokingColor(WHITE);
        content.setFont(PDType1Font.HELVETICA_BOLD, 28);
        content.beginText();
        content.newLineAtOffset(150, headerY - 25);
        content.showText(cleanText(candidat.getNom().toUpperCase()));
        content.endText();

        // Poste souhaité
        if (candidat.getGrade() != null) {
            content.setFont(PDType1Font.HELVETICA, 16);
            content.beginText();
            content.newLineAtOffset(150, headerY - 50);
            content.showText(cleanText(candidat.getGrade().getNom()));
            content.endText();
        }

        // Contact
        content.setFont(PDType1Font.HELVETICA, 12);
        content.beginText();
        content.newLineAtOffset(150, headerY - 70);
        content.showText("Tel: " + cleanText(candidat.getContact()));
        content.endText();
    }

    private void drawSidebar(PDPageContentStream content, Candidat candidat, Genre genre, 
                           List<SerieBac> series, List<Langue> langues) throws Exception {
        float currentY = PAGE_HEIGHT - HEADER_HEIGHT - 30;

        // INFORMATIONS PERSONNELLES
        currentY = drawSidebarSection(content, currentY, "INFORMATIONS PERSONNELLES", SECONDARY_COLOR);
        
        if (candidat.getDateNaissance() != null) {
            currentY = drawSidebarItem(content, currentY, "Date de naissance", 
                candidat.getDateNaissance().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        
        if (genre != null) {
            currentY = drawSidebarItem(content, currentY, "Genre", genre.getValeur());
        }
        
        currentY = drawSidebarItem(content, currentY, "Date candidature", 
            candidat.getDateCandidature().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        currentY -= 20;

        // DIPLÔMES
        if (series != null && !series.isEmpty()) {
            currentY = drawSidebarSection(content, currentY, "DIPLOMES", ACCENT_COLOR);
            for (SerieBac serie : series) {
                currentY = drawSidebarBulletPoint(content, currentY, "Baccalaureat " + serie.getValeur());
            }
            currentY -= 10;
        }

        // LANGUES
        if (langues != null && !langues.isEmpty()) {
            currentY = drawSidebarSection(content, currentY, "LANGUES", PRIMARY_COLOR);
            for (Langue langue : langues) {
                currentY = drawSidebarBulletPoint(content, currentY, langue.getValeur());
            }
        }

        // SALAIRE SOUHAITÉ
        if (candidat.getGrade() != null ) {
            currentY -= 20;
            currentY = drawSidebarSection(content, currentY, "SALAIRE SOUHAITE", ACCENT_COLOR);
            currentY = drawSidebarItem(content, currentY, "", 
                String.format("%,.0f Ar", candidat.getGrade().getSalaire()));
        }
    }

    private void drawMainContent(PDPageContentStream content, Candidat candidat, 
                               List<Formation> formations, List<Experience> experiences) throws Exception {
        float currentY = PAGE_HEIGHT - HEADER_HEIGHT - 30;
        float leftMargin = SIDEBAR_WIDTH + 30;

        // FORMATION
        if (formations != null && !formations.isEmpty()) {
            currentY = drawMainSection(content, currentY, leftMargin, "FORMATION", PRIMARY_COLOR);
            for (Formation formation : formations) {
                currentY = drawMainItem(content, currentY, leftMargin, formation.getValeur(), "");
            }
            currentY -= 20;
        }

        // EXPÉRIENCE PROFESSIONNELLE
        if (experiences != null && !experiences.isEmpty()) {
            currentY = drawMainSection(content, currentY, leftMargin, "EXPERIENCE PROFESSIONNELLE", SECONDARY_COLOR);
            for (Experience experience : experiences) {
                currentY = drawMainItem(content, currentY, leftMargin, experience.getValeur(), "");
            }
            currentY -= 20;
        }

        // PROFIL PROFESSIONNEL (si pas d'expérience, on met un profil générique)
        if (experiences == null || experiences.isEmpty()) {
            currentY = drawMainSection(content, currentY, leftMargin, "PROFIL PROFESSIONNEL", SECONDARY_COLOR);
            String profil = "Candidat(e) motive(e) et dynamique, pret(e) a relever de nouveaux defis. " +
                          "Excellentes capacites d'adaptation et d'apprentissage. Esprit d'equipe developpe " +
                          "et forte motivation pour evoluer dans le secteur de la restauration.";
            currentY = drawMainParagraph(content, currentY, leftMargin, profil);
        }
    }

    private float drawSidebarSection(PDPageContentStream content, float y, String title, Color color) throws Exception {
        // Ligne de titre colorée
        content.setNonStrokingColor(color);
        content.addRect(10, y - 5, 180, 20);
        content.fill();

        // Texte du titre
        content.setNonStrokingColor(WHITE);
        content.setFont(PDType1Font.HELVETICA_BOLD, 11);
        content.beginText();
        content.newLineAtOffset(15, y);
        content.showText(cleanText(title));
        content.endText();

        return y - 35;
    }

    private float drawSidebarItem(PDPageContentStream content, float y, String label, String value) throws Exception {
        content.setNonStrokingColor(SECONDARY_COLOR);
        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
        content.beginText();
        content.newLineAtOffset(15, y);
        if (!label.isEmpty()) {
            content.showText(cleanText(label + ":"));
        }
        content.endText();

        content.setNonStrokingColor(DARK_GRAY);
        content.setFont(PDType1Font.HELVETICA, 9);
        content.beginText();
        content.newLineAtOffset(15, y - 12);
        content.showText(cleanText(value));
        content.endText();

        return y - 25;
    }

    private float drawSidebarBulletPoint(PDPageContentStream content, float y, String text) throws Exception {
        content.setNonStrokingColor(PRIMARY_COLOR);
        content.setFont(PDType1Font.HELVETICA, 9);
        content.beginText();
        content.newLineAtOffset(15, y);
        content.showText("• " + cleanText(text));
        content.endText();

        return y - 15;
    }

    private float drawMainSection(PDPageContentStream content, float y, float x, String title, Color color) throws Exception {
        // Ligne sous le titre
        content.setStrokingColor(color);
        content.setLineWidth(3);
        content.moveTo(x, y - 5);
        content.lineTo(PAGE_WIDTH - MARGIN, y - 5);
        content.stroke();

        // Titre de section
        content.setNonStrokingColor(color);
        content.setFont(PDType1Font.HELVETICA_BOLD, 14);
        content.beginText();
        content.newLineAtOffset(x, y);
        content.showText(cleanText(title));
        content.endText();

        return y - 30;
    }

    private float drawMainItem(PDPageContentStream content, float y, float x, String title, String details) throws Exception {
        // Puce décorative
        content.setNonStrokingColor(PRIMARY_COLOR);
        content.addRect(x, y - 2, 4, 4);
        content.fill();

        // Titre de l'item
        content.setNonStrokingColor(SECONDARY_COLOR);
        content.setFont(PDType1Font.HELVETICA_BOLD, 11);
        content.beginText();
        content.newLineAtOffset(x + 10, y);
        content.showText(cleanText(title));
        content.endText();

        // Détails si fournis
        if (!details.isEmpty()) {
            content.setNonStrokingColor(DARK_GRAY);
            content.setFont(PDType1Font.HELVETICA, 10);
            content.beginText();
            content.newLineAtOffset(x + 10, y - 15);
            content.showText(cleanText(details));
            content.endText();
            return y - 35;
        }

        return y - 20;
    }

    private float drawMainParagraph(PDPageContentStream content, float y, float x, String text) throws Exception {
        content.setNonStrokingColor(DARK_GRAY);
        content.setFont(PDType1Font.HELVETICA, 10);
        
        // Découper le texte en lignes
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        float lineHeight = 15;
        float maxWidth = PAGE_WIDTH - x - MARGIN;

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            float textWidth = PDType1Font.HELVETICA.getStringWidth(testLine) / 1000 * 10;
            
            if (textWidth > maxWidth && currentLine.length() > 0) {
                content.beginText();
                content.newLineAtOffset(x, y);
                content.showText(cleanText(currentLine.toString()));
                content.endText();
                y -= lineHeight;
                currentLine = new StringBuilder(word);
            } else {
                currentLine = new StringBuilder(testLine);
            }
        }

        // Dernière ligne
        if (currentLine.length() > 0) {
            content.beginText();
            content.newLineAtOffset(x, y);
            content.showText(cleanText(currentLine.toString()));
            content.endText();
            y -= lineHeight;
        }

        return y;
    }

    private void drawFooter(PDPageContentStream content) throws Exception {
        content.setNonStrokingColor(PRIMARY_COLOR);
        content.setLineWidth(1);
        content.moveTo(MARGIN, 50);
        content.lineTo(PAGE_WIDTH - MARGIN, 50);
        content.stroke();

        content.setNonStrokingColor(DARK_GRAY);
        content.setFont(PDType1Font.HELVETICA, 8);
        content.beginText();
        content.newLineAtOffset(MARGIN, 35);
        content.showText("CV genere par CafeManager Pro - Systeme de gestion des ressources humaines");
        content.endText();
    }

    private String cleanText(String text) {
        if (text == null) return "";
        return text.replace("\u202F", " ")
                  .replaceAll("[^\\x20-\\x7EÀ-ÿ]", " ")
                  .trim();
    }

    private String cleanFileName(String name) {
        if (name == null) return "candidat";
        return name.replaceAll("[^a-zA-Z0-9\\-_]", "_");
    }
}