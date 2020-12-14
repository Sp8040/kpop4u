package id.co.sm.usingretrofit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<News> dataList;
    private Context context;

    public NewsAdapter(Context con, ArrayList<News> dataList) {
        this.dataList = dataList;
        this.context = con;

    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsViewHolder holder, final int position) {
        Picasso.get().load(dataList.get(position).getUrlToImage()).into(holder.imageNews);
        holder.tvTitle.setText(dataList.get(position).getTitle());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                        dataList.get(position).getUrl()
                )));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvTitle;
        private final ImageView imageNews;

        public NewsViewHolder(View itemView) {
            super(itemView);
            imageNews = (ImageView)itemView.findViewById(R.id.image_news);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
        }
    }
}
