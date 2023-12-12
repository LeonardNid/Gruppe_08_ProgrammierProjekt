package de.Luca.ScamOrNot.UI.CustomElements;

import de.Luca.ScamOrNot.UI.EndScreenUI;
import de.Luca.ScamOrNot.UI.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class AttachmentButton extends JButton {
    public AttachmentButton(String name, int width, int height) {
        String[] splited = name.split("\\.");

        StringBuilder fileName = new StringBuilder(name);
        if(splited.length > 1) {
            fileName = new StringBuilder();
            for(int i = 0; i < splited.length-1; i++) {
                fileName.append(splited[i]);
                if(i != splited.length-2) {
                    fileName.append(".");
                }
            }

            String fileEnding = splited[splited.length-1];
            try {
                ImageIcon icon = new ImageIcon(loadImage("ScamOrNot/Icons/Attachments/" + fileEnding + ".png"));
                Image img = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(img));
            } catch (IOException ignored) { }
            setHorizontalTextPosition(SwingConstants.RIGHT);
            setIconTextGap(10);
        }

        setPreferredSize(new Dimension(width, height));
        setText(fileName.toString());

        addActionListener(e -> {
            String[] fileNameParts = name.split("\\.");
            if (fileNameParts.length > 1) {
                String fileExtension = fileNameParts[fileNameParts.length - 1].toLowerCase();
                if (MainFrame.dangerousExtension.contains(fileExtension)) {
                    EndScreenUI.init(1);
                } else {
                    JOptionPane.showMessageDialog(null, "Gerade nochmal gut gegangen! Klicke nicht einfach so auf Anhänge! Auch in 'vermeintlich' guten Anhängen können gefährliche Viren o.Ä. verbaut sein.");
                    //TODO: evtl. für verschiedene Anhangstypen auch bei Bildern halt auch Bilder anzeigen etc.
                }
            }
        });
    }

    private static Image loadImage(String path) throws IOException {
        ClassLoader classLoader = AttachmentButton.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        assert inputStream != null;
        return ImageIO.read(inputStream);
    }
}
