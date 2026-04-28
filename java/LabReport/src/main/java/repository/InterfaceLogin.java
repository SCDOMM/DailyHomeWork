package repository;

import model.LoginDTO;
import model.LoginData;
import model.RegisterDTO;
import model.RegisterData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface InterfaceLogin {
    @POST("login")
    Call<LoginDTO> postData(@Body LoginData data);
}
interface InterfaceRegister {
    @POST("register")
    Call<RegisterDTO> postData(@Body RegisterData data);
}
