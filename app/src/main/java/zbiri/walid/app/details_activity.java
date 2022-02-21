package zbiri.walid.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zbiri.walid.app.apiservice.DetailsMovieResponse;
import zbiri.walid.app.apiservice.Genre;
import zbiri.walid.app.apiservice.MovieService;

public class details_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ApplicationContext app=(ApplicationContext)getApplicationContext();

        ImageView image=(ImageView) findViewById(R.id.poster_image_details);
        TextView telephone=(TextView) findViewById(R.id.telephone);
        TextView nom=(TextView) findViewById(R.id.nom);
        TextView adresse=(TextView) findViewById(R.id.adresse);

        Button appeler=(Button) findViewById(R.id.appeler);
        Button ouvrirMaps=(Button) findViewById(R.id.ouvrirMaps);
        ImageView linkedin=(ImageView)findViewById(R.id.linkedin);


        MovieService movieService=new Retrofit.Builder()
                .baseUrl(MovieService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService.class);
        Call<DetailsMovieResponse> call=null;
        call=movieService.getMovieDetail(app.getMovie_id(),"550ebedfa3fe2247c5d7c21a9342b7c0",1,"fr");




        call.enqueue(new Callback<DetailsMovieResponse>() {
            @Override
            public void onResponse(Call<DetailsMovieResponse> call, Response<DetailsMovieResponse> response) {
                DetailsMovieResponse repo_response = (DetailsMovieResponse) response.body();
                Picasso.get().load("https://image.tmdb.org/t/p/original"+repo_response.getPoster_path()).into(image);
                telephone.setText(repo_response.getRelease_date());
                nom.setText(repo_response.getRelease_date());
                adresse.setText(repo_response.getOverview().substring(0,10));

                appeler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:123 456789"));
                        startActivity(intent);
                    }
                });
                ouvrirMaps.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String uri = String.format(Locale.FRENCH, "geo:0,0?q=%s", repo_response.getOverview());
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }
                });
                linkedin.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/"+repo_response.getTitle()));
                        startActivity(browserIntent);
                    }
                });
            }

            @Override
            public void onFailure(Call<DetailsMovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage()
                        ,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}