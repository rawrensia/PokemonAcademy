package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokemonacademy.Entity.Account;
import com.example.pokemonacademy.R;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtCourseIndex;
    Button btnLogin;
    IAccountDAO mydb;
    boolean add_data = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mydb = new SQLiteDBHelper(this);
        txtUsername = (EditText) findViewById(R.id.username);
        txtEmail = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.password);
        txtPassword.setTransformationMethod(new AsteriskPassword());
        txtCourseIndex = (EditText) findViewById(R.id.course_index);
        btnLogin = (Button) findViewById(R.id.button_login);


        //DUMMY DATA
        String username1 = "boham3003";
        String email1 = "boham3003@e.ntu.edu.sg";
        String password1 = "boham3003";
        String course_index1 = "3003";

        if (add_data) {
            createNewAccount(username1, email1, password1, course_index1);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                //Check user input is correct or not
                if (validate()) {
                    //Authenticate user
                    Account currentUser = mydb.Authenticate(txtUsername.getText().toString(), txtPassword.getText().toString());

                    //Check Authentication is successful or not
                    if (currentUser != null) {

                        Toast.makeText(LoginActivity.this, "Successfully signed in", Toast.LENGTH_LONG).show();
                        Intent Layer = new Intent(LoginActivity.this, World.class);
                        startActivity(Layer);
                    } else {
                        //User Logged in Failed
                        Toast.makeText(LoginActivity.this, "Failed to log in , please try again", Toast.LENGTH_LONG).show();
                    }
                }*/
                Intent Layer = new Intent(LoginActivity.this, WorldActivity.class);
                startActivity(Layer);
            }
        });
    }


    public boolean validate(){
        boolean valid = false;
        //Get values from EditText fields
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        //Handling validation for Email field
        if (username.isEmpty()) {
            valid = false;
            txtUsername.setError("Please enter valid NTU email!");
        } else {
            valid = true;
            txtUsername.setError(null);
        }

        //Handling validation for Password field
        if (password.isEmpty()) {
            valid = false;
            txtPassword.setError("Please enter your NTU account password!");
        } else {
            if (password.length() < 8) {
                valid = false;
            } else {
                char c;
                int dCount = 0, lCount = 0, uCount = 0, sCount = 0;
                for (int i = 0; i < password.length(); i++) {
                    c = password.charAt(i);
                    if (Character.isDigit(c)) {
                        dCount++;
                    } else if (Character.isLowerCase(c)) {
                        lCount++;
                    } else if (Character.isUpperCase(c)) {
                        uCount++;
                    } else if (c >= 33 && c <= 46 || c == 64) {
                        sCount++;
                    }
                    if ((dCount > 0) && (lCount > 0) && (uCount > 0) && (sCount > 0)) {
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    public void createNewAccount(String username, String email, String password, String course_index){
        Account user = new Account(username, email, password, course_index);
        mydb.addUser(user);
    }


}
