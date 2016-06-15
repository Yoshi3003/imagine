package com.jitrapon.imagine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jitrapon.imagine.ApplicationState;
import com.jitrapon.imagine.R;
import com.jitrapon.imagine.adapters.ImageCategoryAdapter;
import com.jitrapon.imagine.data.DataProvider;
import com.jitrapon.imagine.interfaces.ItemClickedListener;
import com.jitrapon.imagine.models.Category;

import java.util.List;

/**
 * This is the main entry activity of this application. It will display a list of photo categories.
 *
 * @author Jitrapon Tiachunpun
 */
public class MainActivity extends AppCompatActivity implements ItemClickedListener {

    private static final String TAG = "MainActivity";

    /********************************************************
     * ACTIVITY CALLBACKS
     ********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationState state = ApplicationState.getInstance(this);
        DataProvider dataProvider = DataProvider.getInstance(state);

        List<Category> categories = dataProvider.getPhotoCategories();
        ImageCategoryAdapter adapter = new ImageCategoryAdapter(this, categories, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(getString(R.string.choose_photo_category));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.image_category_list);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    /********************************************************
     * CATEGORY CLICK CALLBACK
     ********************************************************/

    @Override
    public void onItemClicked(Object obj) {
        if (obj instanceof Category) {
            Category category = (Category) obj;
            Log.d(TAG, "Viewing photos in category " + category.asQueryParameter());

            // send intent to open up the gallery activity with the selected category
            Intent intent = new Intent(this, GalleryActivity.class);
            intent.putExtra(getString(R.string.extra_category), category.name());
            startActivity(intent);
        }
    }
}
