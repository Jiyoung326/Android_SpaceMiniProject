package kr.or.womanup.nambu.hjy.spaceminiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    Context context;
    ArrayList<APOD> apods;
    int layout;

    public InfoAdapter(Context context, int layout) {
        this.context = context;
        this.apods = new ArrayList<>();
        this.layout = layout;
    }
    public void addItem(APOD apod){ apods.add(apod);}

    public void clear(){apods.clear();}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView =  inflater.inflate(layout,parent,false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        APOD apod = apods.get(position);
        holder.txtTitle.setText(apod.title);
        holder.txtDate.setText(apod.date);
        holder.txtExplanation.setText(apod.explanation);
        if(apod.media_type.equals("image")) {
            Glide.with(context).load(apod.url).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {return apods.size(); }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView txtDate;
        TextView txtExplanation;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtExplanation = itemView.findViewById(R.id.txt_explanation);
            imageView = itemView.findViewById(R.id.image_item);
        }
    }
}
