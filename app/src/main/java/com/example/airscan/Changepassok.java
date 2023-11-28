package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Changepassok extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassok);

        //GET USERID AND DISPLAY
        EditText editText = findViewById(R.id.edt_userID);
        editText.setEnabled(false);
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId"); // Replace "key" with the same unique identifier used in the sender activity
        editText.setText(userId);

        //FindViewByID
        EditText edt_newpass = findViewById(R.id.edt_newpass);
        EditText edt_renewpass = findViewById(R.id.edt_renewpass);
        EditText edt_username = findViewById(R.id.edt_username);
        Button btn_change = findViewById(R.id.btn_change);

        //SHOW PASSWORD
        CheckBox showpass = findViewById(R.id.showpass);
        // Set up a listener for the CheckBox
        showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPass(showpass, edt_newpass);
                showPass(showpass, edt_renewpass);
            }
        });


        //Onclick
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpass = String.valueOf(edt_newpass.getText());
                String renewpass = String.valueOf(edt_renewpass.getText());
                String username = String.valueOf(edt_username.getText());
                Log.d("ABC", newpass);
                Log.d("ABC", renewpass);

                if (newpass.compareTo(renewpass) == 0)
                {
                    Intent intent = new Intent(getApplicationContext(), LoadingPassChange.class);
                    intent.putExtra("username", username);
                    intent.putExtra("newpass", newpass);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Changepassok.this, "Passwords do NOT match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showPass(CheckBox showpass, EditText pass)
    {
        // Save cursor position
        int cursorPosition = pass.getSelectionStart();
        // Get the current left drawables
        Drawable leftDrawable = pass.getCompoundDrawables()[0];
        if (showpass.isChecked()) {
            // If the CheckBox is checked, show the password
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pass.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, getResources().getDrawable(R.drawable.visibility), null);
        } else {
            // If the CheckBox is unchecked, hide the password
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pass.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, getResources().getDrawable(R.drawable.visibility_off), null);
        }
        // Restore cursor position
        pass.setSelection(cursorPosition);
    }

}