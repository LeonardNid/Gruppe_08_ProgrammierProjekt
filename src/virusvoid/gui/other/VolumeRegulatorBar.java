package virusvoid.gui.other;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

/**
 * Custom volume regulator bar component for setting music or sound effects volume.
 */
public class VolumeRegulatorBar extends JPanel {

    private int adjustableWidth;
    private final int volumeBarType;
    private final int height;
    private final int width;

    /**
     * Constructs a VolumeRegulatorBar with the specified width, height, and volume bar type.
     *
     * @param width          The width of the volume bar.
     * @param height         The height of the volume bar.
     * @param volumeBarType  The type of the volume bar (0 for music, 1 for sound effects).
     */
    public VolumeRegulatorBar(int width, int height, int volumeBarType) {
        this.volumeBarType = volumeBarType;
        this.height = height;
        this.width = width;

        this.adjustableWidth = getNewWidth();

        this.setPreferredSize(new Dimension(width, height));
        this.setOpaque(false);

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                setVolumeToMouseX(e.getX());
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setVolumeToMouseX(e.getX());
            }
        });
    }

    /**
     * Sets the volume based on the mouse X coordinate.
     *
     * @param mouseX The X coordinate of the mouse event.
     */
    private void setVolumeToMouseX(int mouseX) {
        int newWidth = Math.min(Math.max(0, mouseX), width);
        if (newWidth != adjustableWidth) {

            if (volumeBarType == 0) {
                GuiController.setMusicVolume(((float) newWidth) / width);
            } else {
                GuiController.setSoundEffectsVolume(((float) newWidth) / width);
            }
            
            adjustableWidth = newWidth;
            repaint();
        }
    }

    /**
     * Custom painting of the volume bar component.
     *
     * @param g The Graphics context for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);

        g.setColor(new Color(0, 102, 204));

        int fillWidth = getNewWidth();

        g.fillRect(0, 0, fillWidth, height);
    }

    /**
     * Gets the adjusted width of the volume bar based on its type.
     *
     * @return The adjusted width of the volume bar.
     */
    private int getNewWidth() {
        if (volumeBarType == 0) {
            return (int) (width * GuiController.getMusicVolume());
        } else {
            return (int) (width * GuiController.getSoundEffectsVolume());
        }
    }
}