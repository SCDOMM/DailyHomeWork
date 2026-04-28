package repository;

import model.LoginDTO;
import model.LoginData;
import model.RegisterDTO;
import model.RegisterData;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.YAMLconfig;

public class ReversoService {
    private static Retrofit retrofit= new Retrofit.Builder().baseUrl(YAMLconfig.parseYAML()).addConverterFactory(GsonConverterFactory.create()).build();
    private static InterfaceLogin loginService=retrofit.create(InterfaceLogin.class);
    private static InterfaceRegister registerService=retrofit.create(InterfaceRegister.class);
    public static Call<LoginDTO> loginService(LoginData data){
        return loginService.postData(data);
    }
    public static Call<RegisterDTO> registerService(RegisterData data){
        return registerService.postData(data);
    }
}
