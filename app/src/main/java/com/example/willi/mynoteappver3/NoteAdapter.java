package com.example.willi.mynoteappver3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by willi on 11/18/2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    //passes in the window that creates the NoteAdapter
    //passes through the window that created the NoteAdapter, the layout style, and the arrayList of the Notes
    public NoteAdapter(Context context, int resource, ArrayList<Note> notes)
    {
        super(context, resource, notes);
    }

    //when called to display the Note
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if(convertView == null)
        {
            convertView= LayoutInflater.from(getContext())
                    .inflate(R.layout.item_note,null);
        }

        //Imports the note from the arraly list that was created in MainActivity
        // but then was coppied via the super method. SO the getItem method calls to get the object at the position in the super
        Note note = getItem(position);
        if(note!=null)
        {
            //get vars to repersent the values of the sample note
            TextView title = (TextView) convertView.findViewById(R.id.item_note_title);
            TextView date = (TextView) convertView.findViewById(R.id.item_note_date);
            TextView content = (TextView) convertView.findViewById(R.id.item_note_content);

        //Sets the info
            //sets the tile by getting it from the import note
            title.setText(note.getTitle());
            //set the date using the format described in the Note.class
            date.setText(note.getDateTimeFormatted(getContext()));

            //Get the content and limits it to 50 chars
            if(note.getContent().length()>50)
            {
                content.setText(note.getContent().substring(0,50));
            }
            else
            {
                content.setText(note.getContent());
            }
        }
        return convertView;
    }


}
