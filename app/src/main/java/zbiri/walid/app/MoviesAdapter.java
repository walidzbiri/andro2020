package zbiri.walid.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import zbiri.walid.app.apiservice.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private Context myActivity;
    private List<Movie> mMovieList;
    private Activity acti;
    private OnMovieListener mOnMovieListener;


    public MoviesAdapter(Context context, List<Movie> mMovieList,OnMovieListener onMovieListener) {
        this.myActivity = context;
        this.mMovieList = mMovieList;
        this.mOnMovieListener=onMovieListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView posterView;
        public TextView titleView;
        public TextView overviewView;
        public TextView popularityView;


        public OnMovieListener onMovieListener;

        public ViewHolder(@NonNull View itemView, Context context, OnMovieListener onMovieListener) {
            super(itemView);
            this.posterView = (ImageView) itemView.findViewById(R.id.poster_image);
            this.titleView = (TextView) itemView.findViewById(R.id.title);
            this.overviewView = (TextView) itemView.findViewById(R.id.overview);
            this.popularityView = (TextView) itemView.findViewById(R.id.popularity);
            this.onMovieListener=onMovieListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMovieListener.onMovieClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View repoView=inflater.inflate(R.layout.activity_eleve_item,parent,false);
        return new ViewHolder(repoView,context,mOnMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie repo =mMovieList.get(position);
        ImageView posterView=holder.posterView;
        holder.titleView.setText(repo.getTitle());
        holder.overviewView.setText(repo.getOverview().length()>0 ? repo.getOverview().substring(0,30): repo.getOverview());
        holder.popularityView.setText(repo.getPopularity());

        Picasso.get().load("https://image.tmdb.org/t/p/original"+repo.getPoster_path()).into(posterView);

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public interface OnMovieListener{
        void onMovieClick(int position);
    }
}
