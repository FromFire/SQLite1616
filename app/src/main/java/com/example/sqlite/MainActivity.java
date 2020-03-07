package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mnameet,memailet,mnumberet;
    private TextView msqltv;
    private Button mwritebtn,mreadbtn,mupdatebtn,mremovebtn;
    MyHelper myHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mnameet=findViewById(R.id.nameet);
        memailet=findViewById(R.id.emailet);
        mnumberet=findViewById(R.id.numberet);
        msqltv=findViewById(R.id.sqltv);
        mwritebtn=findViewById(R.id.writebtn);
        mreadbtn=findViewById(R.id.readbtn);
        mupdatebtn=findViewById(R.id.updatebtn);
        mremovebtn=findViewById(R.id.removebtn);

        mwritebtn.setOnClickListener(this);
        mreadbtn.setOnClickListener(this);
        mupdatebtn.setOnClickListener(this);
        mremovebtn.setOnClickListener(this);
       myHelper=new MyHelper(this);
    }

    @Override
    public void onClick(View view) {
        String namestr,emailstr,numberstr;
        SQLiteDatabase db;
        ContentValues values;
        namestr=String.valueOf(mnameet.getText());
        emailstr=String.valueOf(memailet.getText());
        numberstr=String.valueOf(mnumberet.getText());
        switch(view.getId())
        {
            case R.id.writebtn:
                db=myHelper.getWritableDatabase();
                values=new ContentValues();
                values.put("name",namestr);
                values.put("email",emailstr);
                values.put("number",numberstr);
                db.insert("t_user",null,values);
                Toast.makeText(this,"写入数据库信息成功！",Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.readbtn:
                db=myHelper.getWritableDatabase();
                Cursor cursor=db.query("t_user",null,null,null,null,null,null);
                if(cursor.getCount()==0)
                {
                    msqltv.setText("--No records--");
                    Toast.makeText(this,"没有数据！",Toast.LENGTH_SHORT).show();
                }
                else {
                    cursor.moveToFirst();
                    msqltv.setText("NAME:"+cursor.getString(1)+"\n"+"EMAIL:"+cursor.getString(2)+"\n"+"MOBILE NUMBER:"+cursor.getString(3)+"\n");
                    Toast.makeText(this, "读取数据库信息成功！", Toast.LENGTH_SHORT).show();
                }
                    while(cursor.moveToNext())
                {
                    msqltv.append("\n"+"NAME:"+cursor.getString(1)+"\n"+"EMAIL:"+cursor.getString(2)+"\n"+"MOBILE NUMBER:"+cursor.getString(3));
                }
                cursor.close();
                db.close();
                break;
            case R.id.updatebtn:
                db=myHelper.getWritableDatabase();
                values=new ContentValues();
                values.put("name",namestr);
                values.put("email",emailstr);
                db.update("t_user",values,"number=?",new String[]{numberstr});
                Toast.makeText(this,"更新数据库信息成功！",Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.removebtn:
                db=myHelper.getWritableDatabase();
                db.delete("t_user",null,null);
                Toast.makeText(this,"删除数据库信息成功！",Toast.LENGTH_SHORT).show();
                db.close();
                msqltv.setText("");
                break;
        }

    }
}
