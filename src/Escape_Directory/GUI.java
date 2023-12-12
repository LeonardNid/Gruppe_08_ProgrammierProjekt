package Escape_Directory;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class GUI implements ActionListener {
	JButton b1;
	JButton b2;
	JButton b3;
	JFrame meinFrame = new JFrame("Beispiel JFrame");
	ImageIcon image1 = new ImageIcon(getClass().getResource(FilePaths.tutorialButton));
	ImageIcon image2 = new ImageIcon(getClass().getResource(FilePaths.playButton));
	ImageIcon image3 = new ImageIcon(getClass().getResource(FilePaths.exitButton));
	ImageIcon image4 = new ImageIcon(getClass().getResource(FilePaths.mainScreen));
	JPanel p = new JPanel();
	JLabel l;

	public GUI() {
		b1 = new JButton(image1);
		b2 = new JButton(image2);
		b3 = new JButton(image3);
		l = new JLabel(image4);
		meinFrame.add(b3);
		meinFrame.add(b2);
		meinFrame.add(b1);
		meinFrame.add(l);
		p.setOpaque(false);
		meinFrame.add(p);
		meinFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		meinFrame.setSize(image4.getIconWidth(), image4.getIconHeight());
		meinFrame.setLayout(null);
		meinFrame.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b3.setBounds(500, 300, image3.getIconWidth(), image3.getIconHeight());
		b2.setBounds(400, 200, image2.getIconWidth(), image2.getIconHeight());
		b1.setBounds(300, 100, image1.getIconWidth(), image1.getIconHeight());
		l.setBounds(0, 0, image4.getIconWidth(), image4.getIconHeight());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			meinFrame.dispose();
			meinFrame = new frame();
		} else if (e.getSource() == b2) {
			meinFrame.dispose();
			meinFrame = new frame();
		} else if (e.getSource() == b3) {
			meinFrame.dispose();
		}
	}

}
