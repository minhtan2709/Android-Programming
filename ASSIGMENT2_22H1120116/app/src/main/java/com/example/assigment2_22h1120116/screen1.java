package com.example.assigment2_22h1120116;

import static com.example.assigment2_22h1120116.R.id.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import  android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assigment2_22h1120116.dao.NguoiDungDao;
import com.example.assigment2_22h1120116.sendmail.SendMail;
import com.google.android.material.navigation.NavigationView;

public class screen1 extends AppCompatActivity {
    private NguoiDungDao nguoiDungDao;
    private EditText edtusername,edtpassword;
    private Button btndangnhap;
    private TextView txtforgot,txtdangky;
    private CheckBox chkremember;
    private String user_regis = "", password_regis ="";
    private SendMail sendMail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen1);
        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);
        btndangnhap = findViewById(R.id.btndangnhap);
        chkremember = findViewById(R.id.chkremember);
        txtforgot =findViewById(R.id.txtforgot);
        txtdangky = findViewById(R.id.txtdangky);
        nguoiDungDao = new NguoiDungDao(this);
        sendMail = new SendMail();

        SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);

        boolean isrember = sharedPreferences.getBoolean("isRemember",false);
        if(isrember){
            String user = sharedPreferences.getString("user_log","");
            String password = sharedPreferences.getString("password_log","");

            edtusername.setText(user);
            edtpassword.setText(password);
            chkremember.setChecked(isrember);

            user_regis = user;
            password_regis = password;

        }

        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(screen1.this,screen2.class);
               startActivity(intent);
            }
        });

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username =edtusername.getText().toString();
                String password = edtpassword.getText().toString();

                if(username.equals("")|| password.equals("")){
                    Toast.makeText(screen1.this,"Hãy điền đầy đủ thông tin để đăng nhập!",Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean check =nguoiDungDao.CheckLogin(username,password);
                    if(check == true){
                        if(chkremember.isChecked() == true){
                            SharedPreferences preferences = getSharedPreferences("INFO", MODE_PRIVATE);// goi editor ra de edit dong nay
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("user_log",username);
                            editor.putString("password_log",password);
                            editor.putBoolean("isRemember", chkremember.isChecked());
                            editor.apply();



                        }
                        else{
                            SharedPreferences preferences = getSharedPreferences("INFO", MODE_PRIVATE);// goi editor ra de edit dong nay
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.apply();
                        }
                        Toast.makeText(screen1.this,"Đăng nhập thành công >.<",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), user_screen1.class));


                    }
                    else{
                        Toast.makeText(screen1.this,"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForgot();
            }
        });

    }
    private void DialogForgot(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.forgot_password,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        // anh xa
        EditText edtemail = view.findViewById(R.id.edtemail);
        Button btnsend = view.findViewById(R.id.btnsend);
        Button btnback = view.findViewById(R.id.btnback);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtemail.getText().toString();
                String password = nguoiDungDao.ForgotPassword(email);
//                Toast.makeText(screen1.this, "Mật khẩu của bạn là: "+password, Toast.LENGTH_SHORT).show();

                if(password.equals("")){
                    Toast.makeText(screen1.this,"Không tìm thấy tài khoản",Toast.LENGTH_SHORT).show();
                }
                else{
                    sendMail.Send(screen1.this,email,"LẤY LẠI MẬT KHẨU","Mật khẩu của bạn là:"+password);
                    alertDialog.dismiss();
                }
            }
        });
    }






}
