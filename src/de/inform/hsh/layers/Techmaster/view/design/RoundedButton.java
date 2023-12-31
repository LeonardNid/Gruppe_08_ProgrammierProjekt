package de.inform.hsh.layers.Techmaster.view.design;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.border.Border;

public class RoundedButton extends JButton {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3963212209513976771L;

	public RoundedButton(String label) {
        super(label);
        setFocusPainted(false);
        setBorder(new RoundedBorder(10)); // Hier können Sie den Radius für die Rundung anpassen
        setForeground(Color.GREEN);
        setBackground(Color.BLACK);

        // Fügen Sie einen MouseListener hinzu, um den Text zu unterstreichen, wenn die Maus darüber bewegt wird
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorderPainted(true);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setFont(getFont().deriveFont(Font.BOLD | Font.ITALIC));
                setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                setFont(getFont().deriveFont(Font.PLAIN));
                setForeground(Color.GREEN);
            }
        });
    }
}

class RoundedBorder implements Border {
    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}