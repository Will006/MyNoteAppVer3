package com.example.willi.mynoteappver3;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private static final String dirStr = "NotesFolder/";

    private static final String fileType =".dat";
    //Accessing the file strings
    static String getFileStr() {return dirStr;}
    static String getFileType() {return fileType;}
    private static final String mainFileStr="NotesWithFolders";
    //Will populate to delete a group of Notes
    private ArrayList<String> filesToDelete = new ArrayList<String>();

    private ListView mListViewNotes;

    //private File localDir=getFilesDir();


    //private Menu mainMenu;
    private static final int MENU_ADD = Menu.FIRST;
    private static final int MENU_DELETE = Menu.FIRST + 1;
    private boolean enableDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListViewNotes = (ListView)findViewById(R.id.notes_List);
        enableDelete = false;

        File mainFolder = new File(getApplicationContext().getFilesDir(), mainFileStr);
        boolean success = true;

        if(!mainFolder.exists())
        {
            success=mainFolder.mkdir();
            if(success)
            {
                Toast.makeText(this,mainFolder.toString()+" was made",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this,mainFolder.toString()+" was not made",Toast.LENGTH_LONG).show();
            }

        }
        else if(success)
        {
            Toast.makeText(this,mainFolder.toString()+"already exsist",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,mainFolder.toString()+"does not already exsist",Toast.LENGTH_LONG).show();
        }

        //localDir=getFilesDir()
    }

   @Override
    public boolean onCreateOptionsMenu(Menu mainMenu)
    {
        getMenuInflater().inflate(R.menu.addnote, mainMenu);

        getMenuInflater().inflate(R.menu.sub_main_menu,mainMenu);
        return true;
    }

    /*@Override
    protected void onPause()
    {
        super.onPause();

        this.findViewById(R.id.activity_main_layout).setForeground
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {

            //Add a New Note button
            case R.id.add:
                startActivity(new Intent(MainActivity.this, popUp.class));
                break;
            case R.id.delete_all:
                Utilities.deleteAllSavedNotes(this);

                //refreshes the active main activity
                Utilities.refreshActionBar(MainActivity.this);
                break;
            case R.id.delete:
                if(enableDelete==true && !filesToDelete.isEmpty())
                {
                    Utilities.deleteSavedNoteList(this,filesToDelete);
                    enableDelete=false;

                    //refreshes the active main activity

                    mListViewNotes.setAdapter(null);
                    filesToDelete.clear();

                }
                else
                {

                    enableDelete=true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume()
    {
        super.onResume();
        //enableDelete = false;
        mListViewNotes.setAdapter(null);
        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);

        if(notes==null|| notes.size()==0)
        {
            Toast.makeText(this, "You have no saved notes!", Toast.LENGTH_SHORT).show();

            return;
        }
        else
        {
            NoteAdapter na = new NoteAdapter(this, R.layout.item_note, notes);
            mListViewNotes.setAdapter(na);



            mListViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    //Get the file name of the note to be edited or deleted
                    String fileName = ((Note)mListViewNotes.getItemAtPosition(position)).getTimeCreated()+fileType;

                    //if user is selecting notes to delete or unselect
                    if(enableDelete==true)
                    {
                        //If the note was already selected
                        if(filesToDelete.contains(fileName))
                        {
                            //unselect the note
                            filesToDelete.remove(fileName);
                            mListViewNotes.getChildAt(position).setBackgroundColor(Color.WHITE);

                            if(filesToDelete.isEmpty())
                            {
                                enableDelete=false;
                            }
                           //------------------------------------------ mListViewNotes.getItemAtPosition()
                        }
                        else
                        {
                            //else select the note to delete
                            filesToDelete.add(fileName);
                            mListViewNotes.getChildAt(position).setBackgroundColor(Color.RED);
                        }

                    }
                    //else edit the seleted note
                    else
                    {


                        Toast.makeText(MainActivity.this, fileName + "Short click",Toast.LENGTH_SHORT).show();

                        Intent viewNoteIntent = new Intent(MainActivity.this, newNote.class);

                        viewNoteIntent.putExtra("NOTE_FILE", fileName);

                        startActivity(viewNoteIntent);

                    }
                }


            });

            mListViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
                {
                    String fileName = ((Note)mListViewNotes.getItemAtPosition(position)).getTimeCreated()+fileType;
                    Toast.makeText(MainActivity.this, "long click",Toast.LENGTH_SHORT).show();


                    if(filesToDelete.contains(fileName))
                    {
                        //unselect the note
                        filesToDelete.remove(fileName);
                        mListViewNotes.getChildAt(position).setBackgroundColor(Color.WHITE);

                        if(filesToDelete.isEmpty())
                        {
                            enableDelete=false;
                        }
                        //------------------------------------------ mListViewNotes.getItemAtPosition()
                    }
                    else
                    {
                        //else select the note to delete
                        enableDelete=true;
                        filesToDelete.add(fileName);
                        mListViewNotes.getChildAt(position).setBackgroundColor(Color.RED);
                    }


                    //refreshes the active main activity
                    Utilities.refreshActionBar(MainActivity.this);

                    //return true to prevent the short click also being called
                    return true;
                }
            });
        }

    }


}
