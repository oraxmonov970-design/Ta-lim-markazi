package uz.talim.markaz.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import uz.talim.markaz.R;
import uz.talim.markaz.ui.auth.LoginActivity;
import uz.talim.markaz.ui.profile.ProfileFragment;
import uz.talim.markaz.ui.progress.ProgressFragment;
import uz.talim.markaz.ui.subject.SubjectsFragment;
import uz.talim.markaz.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager session;
    private BottomNavigationView bottomNav;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(this);
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNav = findViewById(R.id.bottomNav);

        if (session.isTeacher()) {
            bottomNav.inflateMenu(R.menu.bottom_nav_teacher);
        } else {
            bottomNav.inflateMenu(R.menu.bottom_nav_student);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_subjects) {
                openFragment(new SubjectsFragment(), getString(R.string.nav_subjects));
                return true;
            } else if (id == R.id.nav_progress) {
                openFragment(new ProgressFragment(), getString(R.string.nav_progress));
                return true;
            } else if (id == R.id.nav_profile) {
                openFragment(new ProfileFragment(), getString(R.string.nav_profile));
                return true;
            }
            return false;
        });

        // Boshlang'ich ekran
        bottomNav.setSelectedItemId(R.id.nav_subjects);
    }

    private void openFragment(Fragment fragment, String title) {
        toolbar.setTitle(title);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
