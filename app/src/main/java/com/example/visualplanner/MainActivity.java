package com.example.visualplanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.visualplanner.repository.FakeEventRepository;
import com.example.visualplanner.repository.IEventRepository;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static IEventRepository repo;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(), this::onSignInResult
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;

        NavController controller = navHostFragment.getNavController();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, controller);

        initFirestore();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        auth.removeAuthStateListener(authStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            AuthUI.getInstance().signOut(this).
                    addOnCompleteListener(
                            task -> Toast.makeText(getApplicationContext(),
                                    "Successfully logged out.", Toast.LENGTH_LONG).show()
                    );
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize an authentication process with a prebuilt UI.
     */
    private void initFirestore() {
        auth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser == null) {
                //launchLoginUI();

                launchLoginUIWithDefaultEmail();
                Toast.makeText(getApplicationContext(),
                "OBS: password is \"testuser\"", Toast.LENGTH_LONG).show();

            }
        };
    }

    private void launchLoginUI() {
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.Theme_VisualPlanner)
                .build();
        signInLauncher.launch(signInIntent);
    }

    /**
     * Function used for UI testing.
     * Password for default email is "testuser"
     */
    private void launchLoginUIWithDefaultEmail() {
        String email = "test@gmail.com";
        // password is "testuser"

        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.EmailBuilder().setDefaultEmail(email).build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.Theme_VisualPlanner)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();

        if (result.getResultCode() == RESULT_OK) {
            FirebaseUser currentUser = auth.getCurrentUser();
            assert currentUser != null;
            Toast.makeText(getApplicationContext(), "Signed in with " + currentUser.getEmail(), Toast.LENGTH_LONG).show();
        } else {
            assert response != null;
            Log.d(TAG, String.valueOf(Objects.requireNonNull(response.getError()).getErrorCode()));
        }
    }

    public void openDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    public static IEventRepository getRepo() {
        if (repo == null) {
            repo = new FakeEventRepository();
        }
        return repo;
    }
}