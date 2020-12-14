package id.co.sm.usingretrofit;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("articles")
    @Expose
    private ArrayList<News> articles = null;

    public ArrayList<News> getArticles() {
        return articles;
    }
}
