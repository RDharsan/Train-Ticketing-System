package com.example.mad;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

//Driver class
public class Traveller extends AppCompatActivity {

    DB_Traveller mydb;
    Button logout,search;
    Spinner spinner1;
    EditText Did,Dname,Daddress,Dphone,Dnation,Dgender,Dtype,Dsalary;
    Button viewbtn,addbtn,editbtn,deletebtn;
    String[] Type = {"Fulltime", "Partime","Weekend"};
    AwesomeValidation awesomeValidation;

    //create driver
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_d__employee);
        logout=findViewById(R.id.log);
        mydb = new DB_Traveller(this);
        Did=(EditText)findViewById(R.id.emp1);
        Dname=(EditText)findViewById(R.id.emp2);
        Daddress=(EditText)findViewById(R.id.emp3);
        Dphone=(EditText)findViewById(R.id.emp4);
        Dnation=(EditText)findViewById(R.id.emp5);
        Dgender=(EditText)findViewById(R.id.emp6);
        addbtn=(Button)findViewById(R.id.btn2);
        viewbtn=(Button)findViewById(R.id.btn1);
        editbtn=(Button)findViewById(R.id.btn3);
        deletebtn=(Button)findViewById(R.id.btn4);
        search=(Button)findViewById(R.id.btn5);
        AddData();
        UpdateDetail();
        DeleteDetail();
        ViewDetail();
        SearchData();
        awesomeValidation = new AwesomeValidation( ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.emp1, RegexTemplate.NOT_EMPTY, R.string.ivalid_Id);
        awesomeValidation.addValidation(this, R.id.emp3, RegexTemplate.NOT_EMPTY, R.string.ivalid_address);
        awesomeValidation.addValidation(this,R.id.emp4,".{10}",R.string.invalid_phoneno);
        awesomeValidation.addValidation(this, R.id.emp6, RegexTemplate.NOT_EMPTY, R.string.invalid_gender);
        awesomeValidation.addValidation(this, R.id.emp5, RegexTemplate.NOT_EMPTY, R.string.invalid_nation);
    }

    //Delete Traveller Profile Details
    public void DeleteDetail() {
        deletebtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = mydb.deleteDetail(Did.getText().toString());
                        if (deletedRows > 0) {
                            Toast.makeText( Traveller.this, "Data deleted", Toast.LENGTH_LONG ).show();
                            clearControls();

                        } else
                            Toast.makeText(Traveller.this, "Data Not deleted", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    //update Traveller Profile code
    public void UpdateDetail() {
        editbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = mydb.updateDetail(Did.getText().toString(), Dname.getText().toString(), Daddress.getText().toString(), Dphone.getText().toString(), Dnation.getText().toString(),Dgender.getText().toString());
                        if (isUpdate == true && awesomeValidation.validate()) {
                            Toast.makeText( Traveller.this, "Data updated", Toast.LENGTH_LONG ).show();
                            clearControls();
                        }else
                            Toast.makeText(Traveller.this, "Data Not updated", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    //Create Traveller profile
    public void AddData() {

        addbtn.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean isInserted = mydb.insertData(Did.getText().toString(), Dname.getText().toString(), Daddress.getText().toString(), Dphone.getText().toString(), Dnation.getText().toString(), Dgender.getText().toString());
                        if (isInserted == true && awesomeValidation.validate()) {
                            Toast.makeText( Traveller.this, "Data Inserted", Toast.LENGTH_LONG ).show();
                            clearControls();
                        }else
                            Toast.makeText(Traveller.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(Traveller.this, com.example.mad.Home.class);
                Toast.makeText(getApplicationContext(),"LOGOUT",Toast.LENGTH_SHORT).show();
                startActivity(logout);
            }
        });

    }
    //view Traveller profile details
    public void ViewDetail() {
        viewbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mydb.getAllData();
                        Cursor res = mydb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("View is Empty !!!", "No Data Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("\nTraveler ID :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Address :" + res.getString(2) + "\n");
                            buffer.append("Telephone :" + res.getString(3) + "\n");
                            buffer.append(" Nation :" + res.getString(4) + "\n");
                            buffer.append(" Gender :" + res.getString(5) + "\n");
                        }
                        showMessage("Driver Details", buffer.toString());

                    }
                }
        );
    }
//Alert Box
    private void showMessage(String title, String toString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(toString);
        builder.show();
    }

 //Search Traveller Profile details
    public void SearchData(){

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = mydb.searchData(Did.getText().toString());
                if (data.getCount() == 0) {
                    //Show Message
                    showMessage("Error ", "Nothing Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (data.moveToNext()) {
                    Did.setText( data.getString(0));
                    Dname.setText( data.getString(1));
                    Daddress.setText( data.getString(2));
                    Dphone.setText( data.getString(3));
                    Dnation.setText( data.getString(4));
                    Dgender.setText(data.getString(5));

                }
            }
        });

    }
    private void clearControls(){
        Did.setText("");
        Dname.setText("");
        Daddress.setText("");
        Dphone.setText("");
        Dnation.setText("");
        Dgender.setText("");

    }







}
