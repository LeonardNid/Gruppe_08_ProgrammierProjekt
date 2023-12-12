package NanoTechDefenders.Help;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Die Klasse {@code MyFont} bietet Methoden zum Laden und Abrufen einer benutzerdefinierten Schriftart.
 */
public class MyFont {
    private static Font myFont;

    /**
     * Lädt eine benutzerdefinierte Schriftart aus einer TTF-Datei.
     * Wenn die Datei nicht gefunden wird, wird eine Meldung auf der Konsole ausgegeben.
     */
    private static void createFont() {
        try (InputStream inputStream = MyFont.class.getClassLoader().getResourceAsStream("NTD_Font.TTF")) {
            if (inputStream != null) {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(Font.BOLD, 60);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont); // Damit Font auf Linux angezeigt wird.
                myFont = customFont;
            } else {
                System.out.println("Schriftart-Datei nicht gefunden: NTD_Font.TTF");
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gibt die benutzerdefinierte Schriftart zurück.
     *
     * @return Die benutzerdefinierte Schriftart.
     */
    public static Font getMyFont() {
        if (myFont == null) {
            createFont();
        }
        return myFont;
    }
}
