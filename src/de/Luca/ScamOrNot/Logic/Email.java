package de.Luca.ScamOrNot.Logic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static de.Luca.ScamOrNot.Logic.Logic.loaded_mails;

public class Email {
    @JsonProperty
    private int mail_id;
    @JsonProperty
    private int mail_typ;
    @JsonProperty
    private String from_name;
    @JsonProperty
    private String from_mail;
    @JsonProperty
    private String to;
    @JsonProperty
    private String subject;
    @JsonProperty @JsonFormat(pattern = "dd.MM.yyyy-HH:mm")
    private Date date_sent;
    @JsonProperty
    private String text;
    @JsonProperty
    private String[] attachments;
    @JsonProperty
    private String explanation;

    public int getID() {
        return this.mail_id;
    }

    public int getTyp() {
        return this.mail_typ;
    }

    public String getFromName() {
        return this.from_name;
    }
    public String getFromMail() {
        return this.from_mail;
    }
    public String getTo() {
        return this.to;
    }
    public String getSubject() {
        return this.subject;
    }
    public String getDateSentString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd. MMMM yyyy, HH:mm");
        return formatter.format(this.date_sent);
    }
    public String getText() {
        return this.text;
    }

    public String[] getAttachments() {
        return this.attachments;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public static void init() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ClassLoader classLoader = main.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("ScamOrNot/maildata.json");

            if (inputStream != null) {
                List<Email> emails = objectMapper.readValue(inputStream, new TypeReference<>() {
                });
                loaded_mails.addAll(emails);
                inputStream.close();
            } else {
                System.err.println("maildata.json file not found!");
            }
        } catch (IOException e) {
            System.err.println("An error was occured: " + e.getMessage());
        }

        System.out.println("Gefundene Mails: " + loaded_mails.size());
    }


}
