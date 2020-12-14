package id.co.sm.usingretrofit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<Example>, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                loadRecyclerViewData("");
            }
        });
    }

    private void loadRecyclerViewData(String query) {
        mSwipeRefreshLayout.setRefreshing(true);

        AccountInterface apiService = ServiceOauthGenerator.createServiceAccount(AccountInterface.class);
        Call<Example>call;
        if(query.isEmpty()){
            call = apiService.getEverything(
                    "","soompi.com,koreboo.com,allkpop.com,kpopmap.com,dkpopnews.net",
                    "en", "publishedAt", "100","1875df5d1fc1476db1f5777508b4ea65");
        }else{
            call = apiService.getEverything(
                    query,"soompi.com,koreboo.com,allkpop.com,asiaone.com,kpopmap.com,dkpopnews.net",
                    "en", "publishedAt", "100","1875df5d1fc1476db1f5777508b4ea65");
        }
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Example> call, Response<Example> response) {
        if(response.isSuccessful()) {
            generateEmployeeList(response.body().getArticles());
            mSwipeRefreshLayout.setRefreshing(false);
            System.out.println(response.body().getArticles().get(0).getTitle());
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<Example> call, Throwable t) {
        t.printStackTrace();
    }

    private void generateEmployeeList(ArrayList<News> empDataList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_news);
        adapter = new NewsAdapter(this, empDataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        loadRecyclerViewData("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_boys:
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
                builderSingle.setTitle("Выберите группу:-");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("BTS");
                arrayAdapter.add("EXO");
                arrayAdapter.add("WannaOne");
                arrayAdapter.add("BTOB");
                arrayAdapter.add("JBJ");
                arrayAdapter.add("SEVENTEEN");
                arrayAdapter.add("SuperJunior");
                arrayAdapter.add("Highlight");
                arrayAdapter.add("INFINITE");
                builderSingle.setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Вы выбрали...");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                mSwipeRefreshLayout.setRefreshing(true);
                                loadRecyclerViewData(strName.toLowerCase().toString());
                            }
                        });
                        builderInner.show();
                    }
                });

                builderSingle.show();

                return true;
            case R.id.menu_girls:
                AlertDialog.Builder builderGirls = new AlertDialog.Builder(this);
                builderGirls.setTitle("Выберите группу:-");

                final ArrayAdapter<String> arrayAdapterGirls = new ArrayAdapter<String>(this,
                        android.R.layout.select_dialog_singlechoice);
                arrayAdapterGirls.add("Blackpink");
                arrayAdapterGirls.add("HyunA");
                arrayAdapterGirls.add("Twice");
                arrayAdapterGirls.add("RedVelvet");
                arrayAdapterGirls.add("GFriend");
                arrayAdapterGirls.add("Mamamoo");
                arrayAdapterGirls.add("Apink");
                builderGirls.setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); }});
                builderGirls.setAdapter(arrayAdapterGirls, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String strName = arrayAdapterGirls.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Вы выбрали...");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                mSwipeRefreshLayout.setRefreshing(true);
                                loadRecyclerViewData(strName.toLowerCase().toString());
                            }
                        });
                        builderInner.show(); }}); builderGirls.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
