package zbiri.walid.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zbiri.walid.app.apiservice.Movie;
import zbiri.walid.app.apiservice.MovieService;
import zbiri.walid.app.apiservice.PopularMovieResponse;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnMovieListener{

    private List<Movie> mMovies;
    private RecyclerView mRecyclerView;
    private MoviesAdapter mReposAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ApplicationContext app= (ApplicationContext) getApplicationContext();

        MovieService movieService=new Retrofit.Builder()
                .baseUrl(MovieService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService.class);
        Call<PopularMovieResponse> call=movieService.getPopularMovies("550ebedfa3fe2247c5d7c21a9342b7c0",1,"fr");
        mRecyclerView=findViewById(R.id.rvPopularMovies);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,1));

        call.enqueue(new Callback<PopularMovieResponse>() {
            @Override
            public void onResponse(Call<PopularMovieResponse> call, Response<PopularMovieResponse> response) {
                PopularMovieResponse repo_response = (PopularMovieResponse) response.body();
                mMovies=repo_response.getMovies();
                mReposAdapter=new MoviesAdapter(MainActivity.this,mMovies,MainActivity.this);
                mRecyclerView.setAdapter(mReposAdapter);
            }

            @Override
            public void onFailure(Call<PopularMovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage()
                        ,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent=new Intent(this,details_activity.class);
        ApplicationContext app= (ApplicationContext) getApplicationContext();
        app.setMovie_id(mMovies.get(position).getId());
        startActivity(intent);
    }
}