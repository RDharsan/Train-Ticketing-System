package com.example.mad;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
//Login page functions
public class Login extends AppCompatActivity {
    EditText username,password_;
    Button b1,reg;
    DatabaseHelper db;
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);
        username = (EditText) findViewById(R.id.l1);
        password_ = (EditText) findViewById(R.id.l2);
        b1 = (Button) findViewById(R.id.search );
        reg = (Button) findViewById(R.id.reg);
        db = new DatabaseHelper(this);
        awesomeValidation.addValidation(Login.this,R.id.l1, android.util.Patterns.EMAIL_ADDRESS,R.string.invalid_email);
               b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(awesomeValidation.validate()) {
                   //check validations for Travellers
                    String email = username.getText().toString();
                    String password = password_.getText().toString();
                    Boolean chkemailpass = db.emailpassword(email, password);
                    if (chkemailpass == true) {
                        Intent log=new Intent( Login.this,Home.class );
                        startActivity( log );

                        Toast.makeText(getApplicationContext(), "Sucessfully Login", Toast.LENGTH_SHORT).show();
                        clearControls();


                    } else
                        Toast.makeText(getApplicationContext(), " Login fail", Toast.LENGTH_SHORT).show();


                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg=new Intent(Login.this, com.example.mad.MainActivity.class);
                Toast.makeText(getApplicationContext(),"Railway Ticket Management ",Toast.LENGTH_SHORT).show();
                startActivity(reg);
            }
        });


    }
    private void clearControls(){
        username.setText("");
        password_.setText("");


    }
}