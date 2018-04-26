package com.andela.art.root;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.andela.art.R;
import com.andela.art.settings.SettingsActivity;

/**
 * Created by lewismbogo on 25/04/2018.
 */

public class BaseMenuActivity extends AppCompatActivity {

    /**
     * Create a menu.
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent settings = new Intent(BaseMenuActivity.this,
                SettingsActivity.class);
        startActivity(settings);
        return true;
    }
}
