package de.Luca.ScamOrNot.UI.CustomElements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class EdgedSlider extends JSlider {
    public EdgedSlider(int min, int max, int width, int heigth) {

        setMinimum(min);
        setMaximum(max);
        setPreferredSize(new Dimension(width, heigth));

        setUI(new BasicSliderUI(this) {
            @Override
            public void paintThumb(Graphics g) {
                Rectangle thumbRect = new Rectangle();
                g.setColor(Color.RED);
                g.fillRect(thumbRect.x, thumbRect.y, 10, 10);
            }
        });

        // Eckige Bar erstellen
        setUI(new BasicSliderUI(this) {
            @Override
            public void paintTrack(Graphics g) {
                Rectangle trackBounds = new Rectangle();
                g.setColor(Color.BLUE);
                g.fillRect(trackBounds.x, trackBounds.y, 5, 5);
            }
        });
    }
}
