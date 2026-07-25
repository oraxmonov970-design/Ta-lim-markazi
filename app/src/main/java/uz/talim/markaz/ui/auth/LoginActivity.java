package uz.talim.markaz.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import uz.talim.markaz.R;
import uz.talim.markaz.model.User;
import uz.talim.markaz.ui.main.MainActivity;
import uz.talim.markaz.utils.SessionManager;
import uz.talim.markaz.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private AuthViewModel viewModel;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(this);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(v -> attemptLogin());
        findViewById(R.id.tvGoRegister).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        viewModel.loginResult.observe(this, this::onLoginSuccess);
        viewModel.loginFailed.observe(this, failed -> {
            if (failed) {
                Toast.makeText(this, "Login yoki parol noto'g'ri", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attemptLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.login(username, password);
    }

    private void onLoginSuccess(User user) {
        if (user == null) return;
        session.createSession(user.id, user.fullName, user.role);
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }
}
