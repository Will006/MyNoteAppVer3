package com.example.willi.mynoteappver3;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by willi on 12/17/2017.
 */

public class Note implements Serializable
{
    private long timeCreated;
    private String title, content;

//Constructors
    public Note()
    {
        title="";
        content="";
        timeCreated=0;
    }

    public Note(long timeCreatedIn, String titleIn, String contentIn)
    {
        title=titleIn;
        content=contentIn;
        timeCreated=timeCreatedIn;
    }
    public Note(Note objIn)
    {
        this.title       = objIn.getTitle();
        this.timeCreated = objIn.timeCreated;
        this.content     = objIn.getContent();
    }


//SETTERS
    public void setContnet(String contentIn){this.content = contentIn;}

    public void setTimeCreated(long timeIn) {
        this.timeCreated = timeIn;
    }

    public void setTitle(String titleIn) {
        this.title = titleIn;
    }

    public void set(String titleIn, String contentIn) {
        this.title       = titleIn;
        this.content     = contentIn;
    }
    public void set(long timeCrIn, String titleIn, String contentIn)
    {

        this.title       = titleIn;
        this.content     = contentIn;
    }

    public void set(Note objIn) {
        this.title       = objIn.getTitle();
        this.timeCreated = objIn.getTimeCreated();
        this.content     = objIn.getContent();
    }


//GETTERS
    public long getTimeCreated() {
        return timeCreated;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getDateTimeFormatted(Context context)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());

        return sdf.format(new Date(timeCreated));
    }



}
