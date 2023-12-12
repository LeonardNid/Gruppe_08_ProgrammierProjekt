package de.Luca.ScamOrNot.UI.CustomElements;

import de.Luca.ScamOrNot.Logic.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageButton extends JButton {
    private BufferedImage backgroundImage;
    private String textToDisplay;
    private boolean pressed;

    private final float _fontSize;
    private final int margin = 15;

    public ImageButton(String imagePath, int width, int height, String text, float fontSize) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(imagePath);
            assert inputStream != null;
            BufferedImage originalImage = ImageIO.read(inputStream);

            backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = backgroundImage.createGraphics();
            g2d.drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            g2d.dispose();

            setPreferredSize(new Dimension(width, height));
            setMaximumSize(new Dimension(width, height));
            setFocusPainted(false);
            setMinimumSize(new Dimension(width, height));
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setBorder(BorderFactory.createEmptyBorder());
            this.textToDisplay = text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        _fontSize = fontSize;

        this.getModel().addChangeListener(e -> {
            if (this.getModel().isPressed() != pressed) {
                pressed = this.getModel().isPressed();
                repaint();
            }
        });
    }

    public void drawText(Graphics2D g2d) {
        if (textToDisplay != null && !textToDisplay.isEmpty()) {
            g2d.setColor(Color.WHITE);
            Font modifiedFont = main.pixelFont.deriveFont(_fontSize);
            g2d.setFont(modifiedFont);

            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(textToDisplay);
            int textX = (getWidth() - textWidth) / 2;
            int textY = getHeight() - fm.getDescent() - margin;

            g2d.drawString(textToDisplay, textX, textY);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            if (pressed) {
                float alpha = 0.5f;
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(alphaComposite);
            }

            int width = getWidth();
            int height = getHeight();

            double scaleX = (double) (width - 2 * margin) / backgroundImage.getWidth();
            double scaleY = (double) (height - 2 * margin) / backgroundImage.getHeight();

            double scale = Math.min(scaleX, scaleY);

            int scaledWidth = (int) (backgroundImage.getWidth() * scale);
            int scaledHeight = (int) (backgroundImage.getHeight() * scale);

            int x = (width - scaledWidth) / 2;
            int y = (height - scaledHeight) / 2;

            g2d.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, this);
            g2d.dispose();
        }

        drawText((Graphics2D) g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(backgroundImage.getWidth(), backgroundImage.getHeight());
    }
}
