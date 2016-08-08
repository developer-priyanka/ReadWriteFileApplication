package my.assignment.readwritefileapplication;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.jar.Attributes;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {
    private EditText eText;
    private TextView tv;
    private Button add,delete;
    private String tag="FILE_READWRITE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eText = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.displayTxt);
        add = (Button) findViewById(R.id.addBtn);
        delete = (Button) findViewById(R.id.deleteBtn);

        new readFile().execute();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (canWriteOnExternalStorage()) {
                        File sdcard = Environment.getExternalStorageDirectory();
                        File dir = new File(sdcard.getAbsolutePath() + "/priyanka/");    // to this path add a new directory path
                        dir.mkdir();                                                    // create this directory if not already created
                        Log.d(tag,dir.toString());
                        File file = new File(dir,"testFile.txt");                       // create the file in which we will write the contents
                        FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(eText.getText().toString()+"\n");
                        bw.close();
                        eText.setText("");
                    }
                    new readFile().execute();

                }catch (IOException e){
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File sdcard = Environment.getExternalStorageDirectory();
                File dir = new File(sdcard.getAbsolutePath() + "/priyanka/");
                File file=new File(dir,"testFile.txt");
                file.delete();
                tv.setText("");

            }
        });
    }


        public  boolean canWriteOnExternalStorage() {

            String state = Environment.getExternalStorageState(); // get the state of your external storage
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                Log.d(tag,"YES External Storage is avialable");   // if storage is mounted return true
                return true;
            }
            return false;
        }

    public String readFileandDisplayonTextview() {

        String aBuffer = "";
        try {
            if (canWriteOnExternalStorage()) {
                Log.d(tag, "YES External Storage is avialable for reading.");
                String sCurrentLine;
                File sdcard = Environment.getExternalStorageDirectory();
                File dir = new File(sdcard.getAbsolutePath() + "/priyanka/");
                File file = new File(dir, "testFile.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));
                while ((sCurrentLine = br.readLine()) != null) {
                    aBuffer = aBuffer + sCurrentLine + "\n";
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return aBuffer;

    }

     private class readFile extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
           return readFileandDisplayonTextview();

        }
         @Override
         protected void onPostExecute(String text){
             tv.setText("");
             tv.setText(text);

         }
    }

}
