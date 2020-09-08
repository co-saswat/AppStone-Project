package api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API_Interface {

    @GET("/v2/top-headlines?country=in&category=health&apiKey=982679c1932a4c71a109373918d8efa4")
    Call<String> getHealth();
}
