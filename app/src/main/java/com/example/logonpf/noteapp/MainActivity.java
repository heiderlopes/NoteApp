package com.example.logonpf.noteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logonpf.noteapp.api.NotaAPI;
import com.example.logonpf.noteapp.model.Nota;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView etTitulo;
    private EditText etDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTitulo = (EditText) findViewById(R.id.etTitulo);
        etDescricao = (EditText) findViewById(R.id.etDescricao);
    }

    public void pesquisar(View v) {
        NotaAPI api = getRetrofit().create(NotaAPI.class);

        api.findNota(etTitulo.getText().toString())
                .enqueue(new Callback<Nota>() {
                    @Override
                    public void onResponse(Call<Nota> call, Response<Nota> response) {
                        etDescricao.setText(response.body().getDescricao());
                    }

                    @Override
                    public void onFailure(Call<Nota> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                "Deu erro", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void salvar(View v) {
        NotaAPI api = getRetrofit().create(NotaAPI.class);

        Nota nota = new Nota();
        nota.setDescricao(etDescricao.getText().toString());
        nota.setTitulo(etTitulo.getText().toString());
        api.salvar(nota)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(MainActivity.this,
                                "Gravado com sucesso!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                "Deu ruim", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private Retrofit getRetrofit() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl("https://notepadcloudshiftheider.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
