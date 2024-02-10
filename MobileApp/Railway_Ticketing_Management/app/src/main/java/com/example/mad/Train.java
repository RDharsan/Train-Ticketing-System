package com.example.mad;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;

import java.util.Calendar;

//Bus functions
public class Train extends AppCompatActivity {
    DB_Train myDb;
    private EditText TicketId, Nic, Pname, TravCode, TravSrc, TravDest, TravCost;
    private TextView TravDate;
    Button add,search;
    Button view;
    Button update;
    Button delete;
    Button date;
    AwesomeValidation awesomeValidation;
    Button log;
    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 1;
    int cur = 0;



    //Create Reservation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_dvehicle);
        myDb=new DB_Train(this);
        TicketId = findViewById(R.id.id);
        Nic = findViewById(R.id.nic);
        Pname = findViewById(R.id.pname);
        TravCode = findViewById(R.id.code);
//        TravDate = findViewById(R.id.date);
        TravSrc = findViewById(R.id.src);
        TravDest = findViewById(R.id.dest);
        TravCost = findViewById(R.id.cost);
        TravDate = findViewById(R.id.datet);
        add = findViewById(R.id.add);
        view = findViewById(R.id.view );
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        search = findViewById(R.id.search);
        date = findViewById(R.id.date);
        log=findViewById(R.id.log);
        AddData();
        viewAll();
        updateDetail();
        deleteDetail();
        searchDetail();

        log.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout =new Intent( Train.this, com.example.mad.Home.class );
                Toast.makeText(getApplicationContext(),"LOGOUT",Toast.LENGTH_SHORT).show();
                startActivity( logout );
            }
        } );
        //Adding Validations
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.id, RegexTemplate.NOT_EMPTY,R.string.invalid_id);
        awesomeValidation.addValidation(this,R.id.nic, RegexTemplate.NOT_EMPTY,R.string.invalid_nic);
        awesomeValidation.addValidation(this,R.id.pname, RegexTemplate.NOT_EMPTY,R.string.invalid_pname);
        awesomeValidation.addValidation(this,R.id.code, RegexTemplate.NOT_EMPTY,R.string.invalid_code);
        awesomeValidation.addValidation(this,R.id.date, RegexTemplate.NOT_EMPTY,R.string.invalid_date);
        awesomeValidation.addValidation(this,R.id.src, RegexTemplate.NOT_EMPTY,R.string.invalid_src);
        awesomeValidation.addValidation(this,R.id.dest, RegexTemplate.NOT_EMPTY,R.string.invalid_dest);
        awesomeValidation.addValidation(this,R.id.cost, Range.closed(50,20000),R.string.invalid_cost);
        addListenerOnButton();






    }

    final AdapterView.OnItemSelectedListener onItemSelectedListener1 =
            new AdapterView.OnItemSelectedListener() {

                //Performing action onItemSelected and onNothing selected
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }


            };



//Delete Reservation
    public void deleteDetail(){
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows =myDb.deleteDetail(TicketId.getText().toString());
                        if(deletedRows >0) {
                            Toast.makeText( Train.this, "Data deleted", Toast.LENGTH_LONG ).show();
                            clearControls();
                        }else
                            Toast.makeText(Train.this,"Data Not deleted",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    //Update Reservation
    public void updateDetail() {
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean isUpdate = myDb.updateDetail(TicketId.getText().toString(), Nic.getText().toString(), Pname.getText().toString(), TravCode.getText().toString(),  TravDate.getText().toString(), TravSrc.getText().toString(), TravDest.getText().toString(), TravCost.getText().toString());
                        if (isUpdate == true &&awesomeValidation.validate()) {
                            Toast.makeText( Train.this, "Data updated", Toast.LENGTH_LONG ).show();
                            clearControls();
                        }else
                            Toast.makeText(Train.this, "Data Not updated", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }



    //add Reservation details
    public void AddData(){
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertDetail(TicketId.getText().toString(),Nic.getText().toString(),Pname.getText().toString(),TravCode.getText().toString(), TravDate.getText().toString(),TravSrc.getText().toString(),TravDest.getText().toString(),TravCost.getText().toString());
                        if(isInserted == true &&awesomeValidation.validate() ) {
                            clearControls();
                            Toast.makeText( Train.this, "Data Inserted", Toast.LENGTH_LONG ).show();

                        }else
                            Toast.makeText(Train.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    //View Reservation Details
    public void viewAll(){
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDb.getAllData();
                        Cursor res =myDb.getAllData();
                        if(res.getCount()==0){
                            showMessage("View is Empty !!!","No Data Found");
                            return;
                        }
                        StringBuffer buffer =new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Ticket ID :"+res.getString(0)+"\n");
                            buffer.append("NIC :"+res.getString(1)+"\n");
                            buffer.append("Passenger Name :"+res.getString(2)+"\n");
                            buffer.append("Travel Code:"+res.getString(3)+"\n");
                            buffer.append("Travel Date:"+res.getString(4)+"\n");
                            buffer.append("Travel Source:"+res.getString(5)+"\n\n");
                            buffer.append("Travel Destination:"+res.getString(6)+"\n\n");
                            buffer.append("Travel Cost:"+res.getString(7)+"\n\n");
                        }
                        showMessage("Reservation Details",buffer.toString());

                    }
                }
        );
    }
    // display current date
    public void setCurrentDateOnView() {

        TravDate = (TextView) findViewById(R.id.date);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        TravDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
    }
    //Show message
    public void showMessage(String title,String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

    //Search Reservation data
    public void searchDetail(){

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = myDb.searchDetail(TicketId.getText().toString());
                if (data.getCount() == 0) {
                    //Show Message
                    showMessage("Error ", "Nothing Found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (data.moveToNext()) {
                    TicketId.setText( data.getString(0));
                    Nic.setText( data.getString(1));
                    Pname.setText( data.getString(2));
                    TravCode.setText( data.getString(3));
                    TravDate.setText( data.getString(4));
                    TravSrc.setText( data.getString(5));
                    TravDest.setText( data.getString(6));
                    TravCost.setText( data.getString(7));

                }
            }
        });

    }


    private void addListenerOnButton() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                cur = DATE_DIALOG_ID;
                return new DatePickerDialog(this, datePickerListener, year, month, day);
            default:
                return null;
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            if (cur == DATE_DIALOG_ID) {
                TravDate.setText("" + new StringBuilder().append(day).append("-").append(month + 1)
                        .append("-").append(year)
                        .append(" "));
            }
        }
    };

    private void clearControls() {
        TicketId.setText("");
        Nic.setText("");
        Pname.setText("");
        TravCode.setText("");
        TravDate.setText("");
        TravSrc.setText("");
        TravDest.setText("");
        TravCost.setText("");
    }
}