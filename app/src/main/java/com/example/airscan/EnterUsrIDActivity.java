package com.example.airscan;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.airscan.Interfaces.APIInterface;
import com.example.airscan.Models.ResetPasswordTask;
import com.example.airscan.Others.APIClient;

public class EnterUsrIDActivity extends AppCompatActivity {

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_usr_idactivity);
        EditText edt_userID = findViewById(R.id.edt_userID);
        Button btn_next = findViewById(R.id.btn_next);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usrID = String.valueOf(edt_userID.getText());
                String dfaultPass = "string";
                String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoREkwZ2hyVlJvaE5zVy1wSXpZeDBpT2lHMzNlWjJxV21sRk4wWGE1dWkwIn0.eyJleHAiOjE3MDI2MTAxMDAsImlhdCI6MTcwMjUyMzcwMSwiYXV0aF90aW1lIjoxNzAyNTIzNzAwLCJqdGkiOiIyNDZiZTNkOS0xMWEzLTRkMzktYmFhZi1hMzkyYjk2NDJhMWIiLCJpc3MiOiJodHRwczovL3Vpb3QuaXh4Yy5kZXYvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjRlM2E0NDk2LTJmMTktNDgxMy1iZjAwLTA5NDA3ZDFlZThjYiIsInR5cCI6IkJlYXJlciIsImF6cCI6Im9wZW5yZW1vdGUiLCJzZXNzaW9uX3N0YXRlIjoiMzQyMGNhNzAtNWM5MC00ZTcwLWFlNTAtOGZiZTEwYjdkYjY2IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwczovL3Vpb3QuaXh4Yy5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7Im9wZW5yZW1vdGUiOnsicm9sZXMiOlsicmVhZDptYXAiLCJyZWFkOnJ1bGVzIiwicmVhZDppbnNpZ2h0cyIsInJlYWQ6YXNzZXRzIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiIzNDIwY2E3MC01YzkwLTRlNzAtYWU1MC04ZmJlMTBiN2RiNjYiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJVc2VyIFVpdDEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyIiwiZ2l2ZW5fbmFtZSI6IlVzZXIiLCJmYW1pbHlfbmFtZSI6IlVpdDEiLCJlbWFpbCI6InVzZXJAaXh4Yy5kZXYifQ.SZkBewnyrl0th9iUoGXQ6nKR8svEfnA1YzfBDHNBKsADcHQlI5itG4mDpnBlBT7Ok43frayA-oy8Oi5MV76adnjco-537L13YfdduP7Ch-7knQLAiVqiIrAAze8NuWzwdyRTjE0BDdpm67s2G7mikshwOxvFgyfGqV8lq_2pNrGmR9GqnwtNj4ukK_rVJX1U00oP-E78qX2zKFRrLhXp-oTah4_mqznYu2OwTUIMpzZqZDvBtmjoJk1cNlt9tqEm-Ogej2RIlBDWgZ9CY2pquBLK9h--fHmgYEYH7Tx8v9bZGrx4oF4m1CQgCnR2sWiRkG-QadgRkAAkaNXSUG_kjA";
                ResetPasswordTask resetPasswordTask = new ResetPasswordTask(usrID, dfaultPass, token, EnterUsrIDActivity.this);
                resetPasswordTask.execute();
            }
        });

    }
}