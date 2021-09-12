package net.sourov.kitbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import net.sourov.kitbox.auth.AuthActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    Toast myToast;
    Intent goToPPG, goToPosts;
    Sourov sourov;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();

        sourov = new Sourov(Dashboard.this);
        sourov.checkUpdate();

        TextView textView = findViewById(R.id.pointTxtOnDash);
        sourov.getCurrentPoints(value -> textView.setText("points: " + value));

        CircleImageView userImageOnDash = findViewById(R.id.userImageOnDash);
        Glide.with(Dashboard.this)
                .load(firebaseAuth.getCurrentUser().getPhotoUrl())
               .placeholder(R.drawable.loading).error(R.drawable.profile)
                .into(userImageOnDash);


        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbarOnDash);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayoutOnDash);
        NavigationView navigationView = findViewById(R.id.navViewOnDashboard);
        navigationView.setItemIconTintList(null);

        //navigation toggle
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_deawer_open, R.string.navigation_deawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        goToPPG = new Intent(Dashboard.this, PageGenerator.class);
        goToPosts = new Intent(Dashboard.this, PostsContainer.class);


        findViewById(R.id.newspaperActivatorOnDash).setOnClickListener(view -> startActivity(new Intent(Dashboard.this, NewspaperActivator.class)));


        findViewById(R.id.PointContainerOnDash).setOnClickListener(view -> sourov.showPointPopup());


        findViewById(R.id.youtubeThumbDownloadOnDash).setOnClickListener(view -> {
            startActivity(new Intent(Dashboard.this, ThumbDownloader.class));
        });
        findViewById(R.id.wordpressTipsOnDash).setOnClickListener(view -> {
            goToPosts.putExtra("intention", "wordpress");
            startActivity(goToPosts);
        });
        findViewById(R.id.bloggerTipsOnDash).setOnClickListener(view -> {
            goToPosts.putExtra("intention", "blogger");
            startActivity(goToPosts);
        });


        findViewById(R.id.ppGeneratorOnDash).setOnClickListener(view -> {
            goToPPG.putExtra("intention", "ppg");
            startActivity(goToPPG);
        });

        findViewById(R.id.tacGeneratorOnDash).setOnClickListener(view -> {
            goToPPG.putExtra("intention", "tac");
            startActivity(goToPPG);
        });

        findViewById(R.id.disclaimerGeneratorOnDash).setOnClickListener(view -> {
            goToPPG.putExtra("intention", "disclaimer");
            startActivity(goToPPG);
        });

        findViewById(R.id.dmcaGeneratorOnDash).setOnClickListener(view -> {
            goToPPG.putExtra("intention", "dmca");
            startActivity(goToPPG);
        });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int itemId = item.getItemId();
        if (itemId == R.id.ppgMenu) {
            goToPPG.putExtra("intention", "ppg");
            startActivity(goToPPG);

        } else if (itemId == R.id.tacMenu) {
            goToPPG.putExtra("intention", "tac");
            startActivity(goToPPG);
        } else if (itemId == R.id.disclaimerMenu) {
            goToPPG.putExtra("intention", "disclaimer");
            startActivity(goToPPG);
        } else if (itemId == R.id.dmcaMenu) {
            goToPPG.putExtra("intention", "dmca");
            startActivity(goToPPG);
        } else if (itemId == R.id.aboutDeveloper) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/spa.sourov"));
            startActivity(i);
        } else if (itemId == R.id.ContactMenu) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.me/spa.sourov"));
            startActivity(i);
        } else if (itemId == R.id.log_out_menu) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Dashboard.this, AuthActivity.class));
            finish();
        }
        return true;
    }
}