package com.mostafa.fci.androidtask.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mostafa.fci.androidtask.Model.Database.RoomDB;
import com.mostafa.fci.androidtask.Model.Event;
import com.mostafa.fci.androidtask.Model.User;
import com.mostafa.fci.androidtask.R;


public class SignInFragment extends Fragment {


    ImageView imageView;
    EditText email,pass;

    Button signIn ;
    FragmentListenter listenter;
    RoomDB.RoomManager mRoomManager;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListenter) {
            listenter = (FragmentListenter) context;
        }
        mRoomManager = new RoomDB.RoomManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        imageView = view.findViewById(R.id.image_view);
        email = view.findViewById(R.id.emailSigninEditText);
        pass = view.findViewById(R.id.passSigninEditText);
        signIn = view.findViewById(R.id.signinBtn);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenter.onImageClicked(Event.SHOW_SIGN_UP);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });

        return view;
    }

    private void signInUser() {

        String emailString = email.getText().toString().trim();
        String passString = pass.getText().toString().trim();
        if (emailString == null || emailString == ""
                || passString == null || passString == ""){
            Toast.makeText(getContext(),"Please fill the Fields",Toast.LENGTH_SHORT).show();
        }else {
            User user = mRoomManager.getUser(emailString);
            if (user != null ) {
                if (emailString.equals( user.getEmail() ) ||passString.equals( user.getPass() ) ) {
                    Toast.makeText(getContext(), "Sign in is Successful"
                            , Toast.LENGTH_SHORT).show();
                    listenter.onSuccessfulLogin();
                }else
                    Toast.makeText(getContext(),"Sign in is Failure"
                            ,Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(getContext(),"Sign in is Failure"
                        ,Toast.LENGTH_SHORT).show();

        }
    }


}
