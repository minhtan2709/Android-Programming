package com.example.assigment2_22h1120116;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import  android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assigment2_22h1120116.DATABASE.DbHelper;
import com.example.assigment2_22h1120116.dao.NguoiDungDao;

import org.w3c.dom.Text;

public class screen2 extends AppCompatActivity {
    private NguoiDungDao nguoiDungDao;
private EditText edtusername,edtpassword,edtrepassword,edtemail;
private Button btntrove,btndangky;
private TextView txtusername,txtpassword,txtrepassword,txtemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2);

        edtusername = findViewById(R.id.edtusername);
        edtpassword =findViewById(R.id.edtpassword);
        edtrepassword  =findViewById(R.id.edtrepassword);
        edtemail = findViewById(R.id.edtemail);
        txtusername = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword);
        txtrepassword = findViewById(R.id.txtrepassword);
        txtemail = findViewById(R.id.txtemail);
        btndangky = findViewById(R.id.btndangky);
        btntrove = findViewById(R.id.btntrove);
        nguoiDungDao = new NguoiDungDao(this);

        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(screen2.this, screen1.class));

            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtusername.getText().toString();
                String password = edtpassword.getText().toString();
                String repassword = edtrepassword.getText().toString();
                String email = edtemail.getText().toString();


                //xac nhan thong tin
                if (user.isEmpty() || password.isEmpty() || repassword.isEmpty()||email.isEmpty()) {
                    if (user.isEmpty()) {
                        edtusername.setError("");
                        txtusername.setText("(*)Vui lòng nhập username");

                    } else {
                        txtusername.setText(null);
                    }


                    if (password.isEmpty()) {
                        edtpassword.setError(" ");
                        txtpassword.setText("(*)Vui lòng nhập password");

                    } else {
                        txtpassword.setText(null);
                    }


                    if (repassword.isEmpty()) {
                        edtrepassword.setError("");
                        txtrepassword.setText("(*)Vui lòng nhập xác nhận password");

                    } else {

                        txtrepassword.setText(null);
                    }
                    if (email.isEmpty()) {
                        edtemail.setError("");
                        txtemail.setText("(*)Vui lòng nhập email");

                    } else {

                        txtemail.setText(null);
                    }
                }
                else{
                    if (password.equals(repassword)) {
                    Boolean checkusername = nguoiDungDao.Checkuser(user);
                    if (checkusername == false) {
                        Boolean insert = nguoiDungDao.Sign_up(user,password,email);
                        if(insert == true){
                            Toast.makeText(screen2.this, "Đăng ký thành công >.<", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),screen1.class);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(screen2.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(screen2.this, "Người dùng đã tồn tại! Hãy đăng nhập!", Toast.LENGTH_SHORT).show();
                    }


                } else{
                        Toast.makeText(screen2.this, "Nhập lại mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    }
            }

            }
        });
    }
}
