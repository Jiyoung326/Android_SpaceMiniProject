package kr.or.womanup.nambu.hjy.spaceminiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MarsInfoAdapter extends RecyclerView.Adapter<MarsInfoAdapter.ViewHolder>{
    Context context;
    ArrayList<Mars> marsdatas;
    int layout;

    public MarsInfoAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
        this.marsdatas = new ArrayList<>();
    }
    public void addItem(Mars m){ marsdatas.add(m);}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView =  inflater.inflate(layout,parent,false);
        MarsInfoAdapter.ViewHolder holder = new MarsInfoAdapter.ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mars m = marsdatas.get(position);
        holder.txtRoverName.setText(m.rover_name);
        holder.txtCameraName.setText(m.cameraName);
        holder.txtEarthDate.setText(m.earth_date);
        Glide.with(context).load(m.img_src).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return marsdatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtRoverName;
        TextView txtCameraName;
        TextView txtEarthDate;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_mars);
            txtCameraName = itemView.findViewById(R.id.txt_camera_name);
            txtRoverName = itemView.findViewById(R.id.txt_rover_name);
            txtEarthDate = itemView.findViewById(R.id.txt_earth_date);
        }
    }
}
