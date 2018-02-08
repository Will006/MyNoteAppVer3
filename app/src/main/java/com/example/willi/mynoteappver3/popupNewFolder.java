package com.example.willi.mynoteappver3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;


/**
 * Created by willi on 1/14/2018.
 */

public class popupNewFolder extends Activity
{
    private String fileName = "";
    private EditText etFileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        //Creates the window
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_folder);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;


        getWindow().setLayout(width,height);

        etFileName = (EditText)findViewById(R.id.add_folder_file_name);

        //configures the buttons
        Button confirm = (Button)findViewById(R.id.add_folder_y);
        Button cancle = (Button)findViewById(R.id.add_folder_n);

        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fileName=etFileName.getText().toString();
                if(Utilities.makeDir(getApplicationContext(),fileName))
                {
                    finish();
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

    }
}
