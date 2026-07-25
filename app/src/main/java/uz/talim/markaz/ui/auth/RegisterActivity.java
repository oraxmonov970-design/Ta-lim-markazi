package uz.talim.markaz.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import uz.talim.markaz.R;
import uz.talim.markaz.model.User;
import uz.talim.markaz.ui.main.MainActivity;
import uz.talim.markaz.utils.SessionManager;
import uz.talim.markaz.viewmodel.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etUsername, etPassword;
    private TextView btnRoleStudent, btnRoleTeacher;
    private String selectedRole = SessionManager.ROLE_STUDENT;

    private AuthViewModel viewModel;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new SessionManager(this);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRoleStudent = findViewById(R.id.btnRoleStudent);
        btnRoleTeacher = findViewById(R.id.btnRoleTeacher);

        btnRoleStudent.setOnClickListener(v -> selectRole(SessionManager.ROLE_STUDENT));
        btnRoleTeacher.setOnClickListener(v -> selectRole(SessionManager.ROLE_TEACHER));

        findViewById(R.id.btnRegister).setOnClickListener(v -> attemptRegister());
        findViewById(R.id.tvGoLogin).setOnClickListener(v -> finish());

        viewModel.registerSuccess.observe(this, success -> {
            String msg = viewModel.registerMessage.getValue();
            if (msg != null) Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        viewModel.registeredUser.observe(this, this::onRegisterSuccess);
    }

    private void selectRole(String role) {
        selectedRole = role;
        btnRoleStudent.setSelected(role.equals(SessionManager.ROLE_STUDENT));
        btnRoleTeacher.setSelected(role.equals(SessionManager.ROLE_TEACHER));
    }

    private void attemptRegister() {
        String fullName = etFullName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.register(fullName, username, password, selectedRole);
    }

    private void onRegisterSuccess(User user) {
        if (user == null) return;
        session.createSession(user.id, user.fullName, user.role);
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }
}
