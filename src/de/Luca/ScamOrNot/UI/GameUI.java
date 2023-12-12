package de.Luca.ScamOrNot.UI;

import de.Luca.ScamOrNot.Logic.Email;
import de.Luca.ScamOrNot.Logic.Logic;
import de.Luca.ScamOrNot.UI.CustomElements.AttachmentButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;

import static de.Luca.ScamOrNot.Logic.Logic.rightChoice;

public class GameUI {
    //TODO: Explanation
    //TODO: before go, open popup with explanation etc.
    //TODO: timer for difficulty
    public static void init(Email mail) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel topPanel = createTopPanel(mail);
        JPanel emailPanel = createEmailPanel(mail);
        JPanel bottomPanel = createBottomPanel(panel, mail);

        panel.add(topPanel);
        panel.add(emailPanel);
        panel.add(bottomPanel);

        MainFrame.addScreen(panel, "GameUI"+mail.getID());
        MainFrame.showScreen("GameUI"+mail.getID());
    }

    private static JPanel createTopPanel(Email mail) {
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setPreferredSize(new Dimension(1920, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel subjectLabel = new JLabel(mail.getSubject());
        subjectLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subjectLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridwidth = 3;
        topPanel.add(subjectLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel fromLabel = new JLabel(mail.getFromName() + " <" + mail.getFromMail() + ">");
        fromLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        fromLabel.setForeground(Color.GRAY);
        fromLabel.setHorizontalAlignment(SwingConstants.LEFT);
        topPanel.add(fromLabel, gbc);

        gbc.gridx = 2;
        JLabel dateLabel = new JLabel(mail.getDateSentString());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setForeground(Color.GRAY);
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        topPanel.add(dateLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        JLabel toLabel = new JLabel("To: " + mail.getTo());
        toLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        toLabel.setForeground(Color.GRAY);
        toLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        topPanel.add(toLabel, gbc);

        return topPanel;
    }

    private static JPanel createEmailPanel(Email mail) {
        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.setPreferredSize(new Dimension(1920, 750));
        emailPanel.setBackground(Color.LIGHT_GRAY);

        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setPreferredSize(new Dimension(1200, 750));
        outerPanel.setBackground(Color.LIGHT_GRAY);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        int padding = 50;
        centerPanel.setBorder(new EmptyBorder(0, padding, 0, padding));


        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);

        editorPane.setText("<div style='text-align:center'>" + mail.getText() + "</div>");
        editorPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                String href = e.getDescription();
                String html = editorPane.getText();
                int linkIndex = html.indexOf(href);
                if(Logic.isBadEmail(html, linkIndex)) {
                    EndScreenUI.init(4);
                }
                else {
                    //TODO: ist ein guter link
                }
            } else if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
                String href = e.getDescription();
                editorPane.setToolTipText(href);
            } else if (e.getEventType() == HyperlinkEvent.EventType.EXITED) {
                editorPane.setToolTipText(null);
            }
        });

        centerPanel.add(editorPane, BorderLayout.CENTER);

        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.setPreferredSize(new Dimension(800, 750));
        innerPanel.setBackground(Color.WHITE);
        innerPanel.add(centerPanel, BorderLayout.CENTER);

        outerPanel.add(Box.createHorizontalStrut(400), BorderLayout.WEST);
        outerPanel.add(innerPanel, BorderLayout.CENTER);
        outerPanel.add(Box.createHorizontalStrut(400), BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(outerPanel);
        emailPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel attachmentsPanel = new JPanel();
        attachmentsPanel.setLayout(new BoxLayout(attachmentsPanel, BoxLayout.X_AXIS));

        for (String attachment : mail.getAttachments()) {
            attachmentsPanel.add(new AttachmentButton(attachment, 100, 100));
        }

        emailPanel.add(attachmentsPanel, BorderLayout.SOUTH);

        return emailPanel;
    }

    private static JPanel createBottomPanel(JPanel p, Email mail) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(1920, 100));
        bottomPanel.setBackground(Color.DARK_GRAY);

        JButton normalBtn = new JButton("Claim as Valid"); //code = 0
        normalBtn.addActionListener(e -> {
            boolean right = rightChoice(0, mail);
            if(right) {
                if(!Logic.newMailAvailabe()) {
                    GameUI.init(Logic.getRandomMail());
                }
                else {
                    EndScreenUI.init(0);
                }
            }
            else if(mail.getTyp() == 1){
                EndScreenUI.init(2);
            }
            else {
                EndScreenUI.init(3);
            }
        });

        JButton phishingBtn = new JButton("Claim as Phishing"); //code = 1
        phishingBtn.addActionListener(e -> {
            boolean right = rightChoice(1, mail);
            if(right) {
                if(!Logic.newMailAvailabe()) {
                    GameUI.init(Logic.getRandomMail());
                }
                else {
                    EndScreenUI.init(0);
                }
            }
            else if(mail.getTyp() == 0) {
                EndScreenUI.init(2);
            }
            else {
                //TODO: Popup das es der falsche typ war, aber immerhin als gefährlich erkannt und dann weiter
            }
        });

        JButton scamBtn = new JButton("Claim as Scam"); //code = 2
        scamBtn.addActionListener(e -> {
            boolean right = rightChoice(2, mail);
            if(right) {
                if(!Logic.newMailAvailabe()) {
                    GameUI.init(Logic.getRandomMail());
                }
                else {
                    EndScreenUI.init(0);
                }
            }
            else if(mail.getTyp() == 0) {
                EndScreenUI.init(3);
            }
            else {
                //TODO: Popup das es der falsche typ war, aber immerhin als gefährlich erkannt und dann weiter
            }
        });


        bottomPanel.add(normalBtn);
        bottomPanel.add(phishingBtn);
        bottomPanel.add(scamBtn);

        return bottomPanel;
    }
}
