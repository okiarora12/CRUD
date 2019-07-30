package com.oki.responsi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    //Deklarasi variable
    SQLiteDatabase myDB = null;
    String TableName = "Mahasiswa";
    String Data = "";

    TextView dtMhs;

    Button bSimpan, bEdit, bHapus;
    EditText tNim, tNama, tAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dtMhs = (TextView)findViewById(R.id.txtDataMhs);

        bSimpan = (Button)findViewById(R.id.btnSimpan);
        bEdit = (Button)findViewById(R.id.btnEdit);
        bHapus = (Button)findViewById(R.id.btnHapus);

        tNim = (EditText)findViewById(R.id.txtNim);
        tNama = (EditText)findViewById(R.id.txtNama);
        tAlamat = (EditText)findViewById(R.id.txtAlamat);

        createDB();
        tampilData();

        bSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });
        bHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus();
            }
        });
    }

    //Method Clear TextField
    public void clearField(){
        tNim.setText("");
        tNama.setText("");
        tAlamat.setText("");
    }
    //Buat Method Create Database
    public void createDB(){
        try{
            myDB = this.openOrCreateDatabase("DBMHS",MODE_PRIVATE,null);

            myDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    TableName + "(NIM VARCHAR PRIMARY KEY, NAMA VARCHAR, ALAMAT VARCHAR);");


            //myDB.execSQL("Insert Into " + TableName + " Values('126','HusnulM','Lombok');");

        }catch (Exception e){
            //Log.e("Error", "Error", e);
        }
    }

    //Buat Method Tampilkan Data
    public void tampilData(){
        try{
            Data = "";
            clearField();
            Cursor c = myDB.rawQuery("Select * From " + TableName, null);

            int col1 = c.getColumnIndex("NIM");
            int col2 = c.getColumnIndex("NAMA");
            int col3 = c.getColumnIndex("ALAMAT");

            c.moveToFirst();

            if (c!= null){
                do{
                    String nimMhs = c.getString(col1);
                    String nmMhs = c.getString(col2);
                    String almtMhs = c.getString(col3);
                    Data = Data + nimMhs + " | " + nmMhs+ " | " + almtMhs + "\n";
                }
                while (c.moveToNext());
            }
            dtMhs.setText(Data);
        }catch (Exception e){
            dtMhs.setText(Data);
        }
    }

    //Method Simpan Data
    public void simpan(){
        myDB.execSQL("Insert Into " + TableName + " Values('" + tNim.getText() + "','" + tNama.getText() + "','" + tAlamat.getText() + "');");
        tampilData();
    }

    //Method Edit
    public void edit(){
            myDB.execSQL("Update " + TableName + " Set NAMA = '"+ tNama.getText() +"', ALAMAT = '"+ tAlamat.getText() +"' Where NIM = '"+ tNim.getText() +"';");
            tampilData();

    }

    //Method Hapus
    public void hapus(){
        myDB.execSQL("Delete From " + TableName + " Where NIM = '" + tNim.getText() + "';");
        tampilData();
    }

}
