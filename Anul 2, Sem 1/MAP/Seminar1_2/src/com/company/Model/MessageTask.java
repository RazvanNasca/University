package com.company.Model;

import com.company.Utils.Constants;

import java.time.LocalDateTime;

class MessageTask extends Task {

    private String mesaj;
    private String from;
    private String to;
    private LocalDateTime date;

    public MessageTask(String taskID, String description, String message, String to, String from, LocalDateTime date)
    {
        super(taskID, description);
        this.mesaj = message;
        this.to = to;
        this.from = from;
        this.date = date;
    }

    public String toString() {
        return super.toString() + " From: " + this.from + " To: " + this.to + " Date: " + this.date.format(Constants.DATE_TIME_FORMATER);
    }

    @Override
    public void execute() {
        ///return mesaj + " " + date.toString();

        System.out.println(toString());
    }

}
