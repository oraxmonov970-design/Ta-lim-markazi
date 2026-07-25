package uz.talim.markaz.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import uz.talim.markaz.R;
import uz.talim.markaz.ui.auth.LoginActivity;
import uz.talim.markaz.utils.SessionManager;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SessionManager session = new SessionManager(requireContext());

        TextView tvName = view.findViewById(R.id.tvProfileName);
        TextView tvRole = view.findViewById(R.id.tvProfileRole);

        tvName.setText(session.getFullName());
        tvRole.setText(session.isTeacher() ? getString(R.string.role_teacher) : getString(R.string.role_student));

        view.findViewById(R.id.btnLogout).setOnClickListener(v ->
                new AlertDialog.Builder(requireContext())
                        .setMessage("Hisobdan chiqmoqchimisiz?")
                        .setPositiveButton("Ha", (dialog, which) -> {
                            session.logout();
                            Intent intent = new Intent(requireContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            requireActivity().finish();
                        })
                        .setNegativeButton("Yo'q", null)
                        .show());

        return view;
    }
}
