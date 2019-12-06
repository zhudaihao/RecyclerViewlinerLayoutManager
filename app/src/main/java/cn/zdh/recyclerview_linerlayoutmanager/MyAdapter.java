package cn.zdh.recyclerview_linerlayoutmanager;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<VideoBean> list;
    private Context context;


    public MyAdapter(List<VideoBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vide, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
      myViewHolder.ivPreload.setImageResource(list.get(i).getImage());
      myViewHolder.videoView.setVideoURI(Uri.parse("android.resource://"+context.getPackageName()+"/"+list.get(i).getVideo()));

    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private MyVideoView videoView;
        private ImageView ivPreload;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            ivPreload = itemView.findViewById(R.id.iv_preload);


        }
    }


}
