package de.Luca.ScamOrNot.Logic;


import de.Luca.ScamOrNot.UI.EndScreenUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Logic {

    public static Timer diff_timer;
    public static List<Email> loaded_mails = new ArrayList<>();
    public static List<Email> played_mails = new ArrayList<>();

    public static boolean rightChoice(int choice, Email mail) {
        return rightChoicePriv(choice, mail);
    }

    private static boolean rightChoicePriv(int choice, Email mail) {
        return mail.getTyp() == choice;
    }

    public static Email getRandomMail() {
        return getRandomMailPriv();
    }

    private static void startTimerPriv() {
        int seconds = 0;
        System.out.println("Timer func start");
        System.out.println(seconds + "");
        System.out.println(Options.getDifficulty());
        switch (Options.getDifficulty()) {
            case "Normal":
                seconds = 90;
            case "Hard":
                seconds = 10;
        }

        if(seconds != 0) {
            diff_timer = new Timer(seconds * 1000, e -> {
                diff_timer.stop();
                EndScreenUI.init(5, "");
            });
            diff_timer.start();
            System.out.println("Timer start");
        }
    }

    private static void stopTimerPriv() {
        if(diff_timer != null) {
            diff_timer.stop();
        }
    }

    public static void stopTimer() {
        stopTimerPriv();
    }

    public static void startTimer() {
        startTimerPriv();
    }

    private static Email getRandomMailPriv() {
        Random r = new Random();
        int mail_index = r.nextInt(loaded_mails.size());
        Email ret = loaded_mails.get(mail_index);
        played_mails.add(ret);
        loaded_mails.remove(mail_index);
        return ret;
    }

    public static boolean newMailAvailabe() {
        return loaded_mails.isEmpty();
    }

    public static boolean isBadEmail(String html, int linkIndex) {
        return isBadEmailPriv(html, linkIndex);
    }

    private static boolean isBadEmailPriv(String html, int linkIndex) {
        if (linkIndex != -1) {
            int classStartIndex = html.lastIndexOf("class=\"", linkIndex);
            if (classStartIndex != -1) {
                int classEndIndex = html.indexOf("\"", classStartIndex + 7);
                if (classEndIndex != -1) {
                    String classValue = html.substring(classStartIndex + 7, classEndIndex);
                    if(classValue.equals("good")) {
                        return false;
                    }
                    else return classValue.equals("bad");
                }
            }
        }
        return false;
    }
}
