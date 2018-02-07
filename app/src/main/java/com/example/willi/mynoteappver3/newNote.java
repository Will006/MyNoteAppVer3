package com.example.willi.mynoteappver3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by willi on 12/13/2017.
 */


public class newNote extends AppCompatActivity
{

    public static final String FILE_EXTENSION = ".bin";

    private Note loadedNote=null;
    private EditText etContext;
    private EditText etTitle;
    private String fileIn;


    public static String tempStr="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        //window creation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        //create the title field and text field
        etTitle= (EditText)findViewById(R.id.note_title_lo);
        etContext= (EditText) findViewById(R.id.note_conext_lo);



//Reading in the note to be edited

        //creates a reffrence to the intent to check for extras
        Intent intentReff = getIntent();
        Bundle bundleReff = intentReff.getExtras();

        //If there was a extra linked to this window
        if(bundleReff!=null)
        {

        //Note being edited, NOTE_FILE extra will contain the Note's file name
            fileIn = (String)bundleReff.get("NOTE_FILE");

            //creates a note by reading in the fileName
            loadedNote = Utilities.getNoteByName(this, fileIn);


            if(loadedNote!=null)
            {
                etTitle.setText(loadedNote.getTitle());
                etContext.setText(loadedNote.getContent());
            }

            //Prints the read i
            Toast.makeText(this, "Extra that has been read in: " + fileIn, Toast.LENGTH_SHORT).show();//---------DELETE
        }

//Create a Save button

    //Attempt to shink the button size
            //Drawable dr = getResources().getDrawable(R.drawable.addflowersmall);
            //Bitmap bitmapTemp = ((BitmapDrawable)dr).getBitmap();

            //Drawable scaledAddFlower = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmapTemp, 50,50,true));

        //Creates the button
        Button saveNote = (Button)findViewById(R.id.save_note_lo);

        //Creates a listener for the Button
        saveNote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveNote();
                finish();
            }
        });
    }

    private void saveNote()
    {
        Note noteTemp;
        if(loadedNote == null)
        {
            noteTemp = new Note(System.currentTimeMillis(),etTitle.getText().toString(),
                    etContext.getText().toString());
        }
        else
        {
            noteTemp= new Note(loadedNote.getTimeCreated(), etTitle.getText().toString(),
                    etContext.getText().toString());
        }

        if(Utilities.saveNote(this,noteTemp))
        {
            Toast.makeText(this,"Your note was saved "+tempStr, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Unable to save note", Toast.LENGTH_SHORT).show();
        }
        finish();
    }




}
