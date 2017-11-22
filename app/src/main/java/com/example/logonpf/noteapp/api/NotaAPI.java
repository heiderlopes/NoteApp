package com.example.logonpf.noteapp.api;

import com.example.logonpf.noteapp.model.Nota;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotaAPI {

    @GET("/nota/titulo/{titulo}")
    Call<Nota> findNota(@Path("titulo")String titulo);

    @POST("/nota")
    Call<Void> salvar(@Body Nota nota);
}
