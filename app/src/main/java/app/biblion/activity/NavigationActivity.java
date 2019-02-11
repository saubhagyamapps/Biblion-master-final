package app.biblion.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import java.util.HashMap;

import app.biblion.R;
import app.biblion.fragment.AccountFragment;
import app.biblion.fragment.ArticlesFragment;
import app.biblion.fragment.BibleBookFragment;
import app.biblion.fragment.ELibraryFragment;
import app.biblion.fragment.HomeBookFragment;
import app.biblion.fragment.LeaderBoardFragment;
import app.biblion.fragment.QuizFragment;
import app.biblion.fragment.SettingFragment;
import app.biblion.fragment.SongBookFragment;
import app.biblion.sessionmanager.SessionManager;
import de.hdodenhof.circleimageview.CircleImageView;


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "NavigationActivity";
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;
    // BottomBar bottomBar;
    SessionManager session;
    TextView txtName, txtEmail;
    CircleImageView imgUser;
    private SpaceNavigationView spaceNavigationView;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initialization(savedInstanceState);
        displaySelectedScreen(R.id.nav_home);


    }

    private void initialization(Bundle savedInstanceState) {
        //bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        session = new SessionManager(getApplicationContext());
        // session.checkLogin();
        user = session.getUserDetails();
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.account));
        spaceNavigationView.addSpaceItem(new SpaceItem("BookMark", R.drawable.account));
        spaceNavigationView.addSpaceItem(new SpaceItem("My Library", R.drawable.account));
        spaceNavigationView.addSpaceItem(new SpaceItem("Account", R.drawable.account));
        spaceNavigationView.shouldShowFullBadgeText(false);

        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Log.d("onCentreButtonClick ", "onCentreButtonClick");
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Fragment fragment = null;
                Log.d("onItemClick ", "" + itemIndex + " " + itemName);
                switch (itemIndex) {
                    case 0:
                        fragment = new HomeBookFragment();
                        break;
                    case 1:
                        fragment = new QuizFragment();
                        break;
                    case 2:
                        fragment = new ELibraryFragment();
                        break;
                    case 3:
                        fragment = new AccountFragment();
                        break;
                }

                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.contant_frame, fragment);
                    ft.commit();
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Log.d("onItemReselected ", "" + itemIndex + " " + itemName);

            }
        });

        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                Toast.makeText(NavigationActivity.this, "onCentreButtonLongClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                Toast.makeText(NavigationActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.drawable.toolbar_gradient);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setnavigationHeader();
        //  bottomNavigation();
    }

    private void setnavigationHeader() {
        View header = navigationView.getHeaderView(0);
        txtName = (TextView) header.findViewById(R.id.txtName);
        txtEmail = (TextView) header.findViewById(R.id.txtEmail);
        imgUser = header.findViewById(R.id.imgUser);
        txtName.setText(user.get(session.KEY_NAME));
        txtEmail.setText(user.get(session.KEY_EMAIL));
        Glide.with(getApplicationContext()).load(user.get(session.KEY_IMAGE))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgUser);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }

    private void bottomNavigation() {

    /*    bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                Fragment fragment = null;
                switch (tabId) {
                    case R.id.tab_home:
                        fragment = new HomeBookFragment();
                        break;
                    case R.id.tab_quiz:
                        fragment = new QuizFragment();
                        break;
                    case R.id.tab_song_book:
                        fragment = new SongBookFragment();
                        break;
                    case R.id.tab_elibrary:
                        fragment = new ELibraryFragment();
                        break;
                    case R.id.tab_articles:
                        fragment = new ArticlesFragment();
                        break;
                }

                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.contant_frame, fragment);
                    ft.commit();
                }
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeBookFragment();
                break;
            case R.id.nav_bible_book:
                fragment = new BibleBookFragment();
                break;
            case R.id.nav_quiz:
                fragment = new QuizFragment();
                break;
            case R.id.nav_song_book:
                fragment = new SongBookFragment();
                break;
            case R.id.nav_elibary:
                fragment = new ELibraryFragment();
                break;
            case R.id.nav_articles:
                fragment = new ArticlesFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingFragment();
                break;
            case R.id.nav_logout:
                session.logoutUser();
                finish();
                break;
            case R.id.nav_leaderboard:
                fragment = new LeaderBoardFragment();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contant_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            onCreate(savedInstanceState);
        } catch (Exception e) {

        }
    }

}