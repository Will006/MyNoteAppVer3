package com.example.willi.mynoteappver3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by willi on 12/12/2017.
 */

public class popUp extends Activity
{
    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;


        getWindow().setLayout(width,height);
        //getWindow().setLayout((int)(width*.8),(int)(height*.2));

    //NEW FOLDER
        Button newFile = (Button)findViewById(R.id.new_folder_button);
        newFile.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(popUp.this, popupNewFolder.class);

                startActivity(intent);
                finish();

            }
        });


    //NEW NOTE
        final Button newNote = (Button) findViewById(R.id.new_note_button);
        newNote.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Open a newNote window
                Intent intent = new Intent(popUp.this, newNote.class);
                startActivity(intent);

                //After the newNote is closed, this pop up window will close
                finish();
            }
        });


    //NEW LIST
        Button newList = (Button) findViewById(R.id.new_list_button);
        newList.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

            }

        });
    }
}
