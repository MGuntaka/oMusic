package opendyne.vj.omusic;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout drawerLayout;
    private ListView listView;
    private String [] titles;
    private ActionBarDrawerToggle drawerToggle;
    private OmusicAdapter omusicAdapter;
    private  View mFragmentContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.oMusicListView);
        omusicAdapter = new OmusicAdapter(this);
        listView.setAdapter(omusicAdapter);
        listView.setOnItemClickListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_opened,
                R.string.drawer_closed ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        selectItem(position);
    }

    private void selectItem(int position) {

        android.app.FragmentManager manager = getFragmentManager();

        OMusicTracksFragment fragment = new OMusicTracksFragment();

        //TODO: change the fragment when the user clicks on navigationDrawer items.
        switch (position) {
            case 1:
                fragment = new OMusicTracksFragment();
                break;
            default:
                break;
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment, "TRACK");

        transaction.commit();
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(mFragmentContainerView);
        }


        listView.setItemChecked(position, true);
        setTitle(titles[position]);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}

//Adapter for assigning text and images for navigation drawer.

class OmusicAdapter extends BaseAdapter {
    private Context context;
    String [] menuTitles;
    int [] images= { R.drawable.omusic_home, R.drawable.omusic_track, R.drawable.omusic_album,
                    R.drawable.omusic_playlist, R.drawable.omusic_artists};

    public OmusicAdapter(Context context) {
        this.context = context;
        menuTitles = context.getResources().getStringArray(R.array.titles);

    }
    @Override
    public int getCount() {

        return menuTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return menuTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View row;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_row, parent, false);
        } else {
            row = view;

        }
        TextView titletextView = (TextView) row.findViewById(R.id.titleTextView);
        ImageView imageView = (ImageView) row.findViewById(R.id.titleImageView);

        titletextView.setText(menuTitles[position]);
        imageView.setImageResource(images[position]);

        return row;
    }
}