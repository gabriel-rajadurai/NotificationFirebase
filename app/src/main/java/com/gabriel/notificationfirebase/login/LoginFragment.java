package com.gabriel.notificationfirebase.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gabriel.notificationfirebase.LoginActivity;
import com.gabriel.notificationfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnLoginClickListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment {

    private OnLoginClickListener mListener;
    private View rootView;
    private EditText etEmail, etPass;
    private Context mContext;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        etEmail = rootView.findViewById(R.id.email_et);
        etPass = rootView.findViewById(R.id.pass_et);

        TextView tvReg = rootView.findViewById(R.id.reg_tv);
        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRegisterClicked();
            }
        });

        Button btLogin = rootView.findViewById(R.id.login_bt);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin();
            }
        });
    }

    private void validateLogin() {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError("Please enter username!!");
            return;
        } else if (pass.isEmpty()) {
            etPass.setError("Please enter password!!");
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Log.d("Login", "validateLogin: " + FirebaseInstanceId.getInstance().getToken());
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mListener.onLoginClicked();
                        } else {
                            Toast.makeText(mContext, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnLoginClickListener) {
            mListener = (OnLoginClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLoginClickListener {
        // TODO: Update argument type and name
        void onLoginClicked();

        void onRegisterClicked();
    }
}
