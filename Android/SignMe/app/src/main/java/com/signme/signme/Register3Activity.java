package com.signme.signme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Retrofit;

public class Register3Activity extends AppCompatActivity {

    EditText idinput, passinput, passinput2, emailinput;
    Button register2, emailcheck;

    String url = "";
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

  /*      idinput = (EditText) findViewById(R.id.idinput);
        passinput = (EditText) findViewById(R.id.passinput);
        passinput2 = (EditText) findViewById(R.id.passinput2);
        emailinput = (EditText) findViewById(R.id.emailinput);

        register2 = (Button) findViewById(R.id.register2);
        register2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSignupData(idinput.getText().toString(), passinput.getText().toString(), passinput2.getText().toString(), emailinput.getText().toString());
                if (emailinput.getText().toString().length() == 0) {
                    Toast.makeText(Register3Activity.this, "Email을 입력하세요", Toast.LENGTH_SHORT).show();
                    emailinput.requestFocus();
                    return;
                }

                if (passinput.getText().toString().length() == 0) {
                    Toast.makeText(Register3Activity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    passinput.requestFocus();
                    return;
                }

                if (passinput2.getText().toString().length() == 0) {
                    Toast.makeText(Register3Activity.this, "비밀번호를 한 번더 입력하세요", Toast.LENGTH_SHORT).show();
                    passinput2.requestFocus();
                    return;
                }

                if (!passinput.getText().toString().equals(passinput2.getText().toString())) {
                    Toast.makeText(Register3Activity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    passinput.setText("");
                    passinput2.setText("");
                    passinput.requestFocus();
                    return;
                }
                ;


            }

            public void postSignupData(final String name, String id, String password, final String phone) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .build();

                Map map = new HashMap();
                map.put("name", name);
                map.put("id", id);
                map.put("password", password);
                map.put("phone", phone);

                retrofitService = retrofit.create(RetrofitService.class);
                Call<ResponseBody> call = retrofitService.getSignup(map);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            Intent intent = new Intent(Register3Activity.this, LoginActivity.class);
                            Toast.makeText(getApplicationContext(), "회원가입 성공하셨습니다!!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        } else if (response.code() == 201) {
                            Toast.makeText(getApplicationContext(), "아이디가 중복됩니다. 아이디를 다시 입력해주세요!", Toast.LENGTH_SHORT).show();
                            idinput.setText("");

                        } else if (response.code() == 400) {
                            Toast.makeText(getApplicationContext(), "일시적인 서버 오류입니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            idinput.setText("");
                            passinput.setText("");
                            passinput2.setText("");
                            emailinput.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            idinput.setText("");
                            passinput.setText("");
                            passinput2.setText("");
                            emailinput.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        idinput.setText("");
                        passinput.setText("");
                        passinput2.setText("");
                        emailinput.setText("");
                    }
                });

                emailcheck = (Button) findViewById(R.id.emailcheck);
                emailcheck.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            Pattern p = Pattern.compile("^[a-zA-X0-9]@[a-zA-Z0-9].[a-zA-Z0-9]");
                            Matcher m = p.matcher((emailinput).getText().toString());

                            if (!m.matches()) {
                                Toast.makeText(Register3Activity.this, "Email형식으로 입력하세요", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });*/
    }
    public void Registerok(View view) {
        Intent rsok = new Intent(Register3Activity.this, RegisterfinActivity.class);
        startActivity(rsok);
    }
}
