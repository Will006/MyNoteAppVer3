package com.example.willi.mynoteappver3;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by willi on 12/17/2017.
 */

public class Utilities
{


    //private static final String dirStr = "NotesFolder/";
    private static final String FILE_EXTENSION = ".dat";

    public static boolean makeDir(Context contextIn, String fileNameIn)
    {

        boolean tempBool=false;
        if(fileNameIn.isEmpty()||fileNameIn==null)
        {
            Toast.makeText(contextIn,"File Name can not be empty",Toast.LENGTH_SHORT).show();
            tempBool=false;
        }
        else if(fileNameIn.contains("/"))
        {
            Toast.makeText(contextIn,"File Name can not contain" +"/",Toast.LENGTH_SHORT).show();
            tempBool=false;
        }
        else
        {
            try
            {

                //File rootFile = new File(Environment.getDataDirectory())
                //adds the needed / for the dir

                fileNameIn=fileNameIn;
                File newFolder = new File(contextIn.getFilesDir(),fileNameIn);
                if(newFolder.mkdirs())
                {
                    Toast.makeText(contextIn,"Dir " + newFolder + " created",Toast.LENGTH_SHORT).show();
                    tempBool = true;
                }
                else
                {
                    Toast.makeText(contextIn,"Invalid file name",Toast.LENGTH_SHORT).show();
                    tempBool = false;
                }
            }catch (Exception e)
            {
                Toast.makeText(contextIn,"Error creating file",Toast.LENGTH_SHORT).show();
                tempBool = false;
            }

        }
        return tempBool;
    }



    public static boolean saveNote(Context contextIn, Note noteIn)
    {
        Note tempNote = new Note(noteIn);




        //The file name will be the string version of the time it was created + the file extension
        String fileName = (String.valueOf(tempNote.getTimeCreated()) + FILE_EXTENSION);

        newNote.tempStr = fileName;

        FileOutputStream fos;
        ObjectOutputStream oos;

        try
        {
            fos = contextIn.openFileOutput(fileName, contextIn.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(tempNote);
            oos.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return false;
        }
        finally
        {

            return true;
        }
    }


    public static boolean deleteSavedNoteList(Context contextIn, ArrayList<String> filesToDeleteIn)
    {
        for(String fileTemp : filesToDeleteIn)
        {
            if(fileTemp.endsWith(FILE_EXTENSION))
            {
                contextIn.deleteFile(fileTemp);
                Toast.makeText(contextIn,"File "+ fileTemp + " Deleted",Toast.LENGTH_SHORT);
            }
            else
            {
                Toast.makeText(contextIn,"Error deleting file "+ fileTemp,Toast.LENGTH_SHORT);
            }
        }
        return true;
    }




    public static boolean deleteAllSavedNotes(Context contextIn)
    {
        File filesDir = contextIn.getFilesDir();
        for(String fileTemp : filesDir.list())
        {
            if(fileTemp.endsWith(FILE_EXTENSION))
            {
                contextIn.deleteFile(fileTemp);
                Toast.makeText(contextIn,"File "+ fileTemp + " Deleted",Toast.LENGTH_SHORT);
            }
            else
            {
                Toast.makeText(contextIn,"Error deleting file "+ fileTemp,Toast.LENGTH_SHORT);
            }
        }
        return true;
    }


    public static ArrayList<Note> getAllSavedNotes(Context contextIn)
    {
        ArrayList<Note> notes = new ArrayList<>();
        File filesDir = contextIn.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();

        for(String fileStr : filesDir.list())
        {
            File tempFile = new File(fileStr);

            if(fileStr.endsWith((FILE_EXTENSION)))
            {
                noteFiles.add(fileStr);
            }
            else if(tempFile.exists() && tempFile.isDirectory())
            {
                Toast.makeText(contextIn, "Folder "+ fileStr + " Found", Toast.LENGTH_LONG).show();
            }
        }

        FileInputStream fis;
        ObjectInputStream ois;

        for(int i=0; i<noteFiles.size();i++)
        {
            try
            {
                fis=contextIn.openFileInput(noteFiles.get(i));
                ois=new ObjectInputStream(fis);

                notes.add((Note)ois.readObject());

                fis.close();
                ois.close();

            }catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
                return null;
            }
        }
        return notes;

    }

    public static Note getNoteByName(Context contextIn, String fileName)
    {
        File file = new File(contextIn.getFilesDir(), fileName);
        Note tempNote = new Note();
        if(file.exists())
        {
            FileInputStream fis;
            ObjectInputStream ois;

            try
            {
                fis= contextIn.openFileInput(fileName);
                ois = new ObjectInputStream(fis);

                tempNote = new Note((Note)ois.readObject());
                fis.close();
                ois.close();


            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
                return null;
            } catch (IOException e)
            {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
                return null;
            }


        }
        return tempNote;
    }

    static void refreshActionBar(Activity activity)
    {
        activity.invalidateOptionsMenu();
    }


}
