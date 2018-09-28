package com.mostafa.fci.androidtask.View;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
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


public class SignUpFragment extends Fragment {

    ImageView imageView;
    EditText email, name, pass;

    Button signUp ;
    FragmentListenter listenter;
    RoomDB.RoomManager mRoomManager;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_sign_up, container, false);
        imageView = view.findViewById(R.id.image_view);
        email = view.findViewById(R.id.emailSignUpEditText);
        name = view.findViewById(R.id.nameSignUpEditText);
        pass = view.findViewById(R.id.passSignUpEditText);
        signUp = view.findViewById(R.id.signUpBtn);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenter.onImageClicked(Event.SHOW_SIGN_IN);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        return view;
    }

    private void saveUser() {

        String emailString = email.getText().toString().trim();
        String passString = pass.getText().toString().trim();
        String nameString = name.getText().toString().trim();
        if (emailString == null || emailString == ""
                || passString == null || passString == ""
                || nameString == null || nameString == ""){
            Toast.makeText(getContext(),"Please fill the Fields",Toast.LENGTH_SHORT).show();
        }else {
            try {
                mRoomManager.saveUser(new User(emailString, nameString, passString));
                Toast.makeText(getContext(), "Sign in is Successful"
                        , Toast.LENGTH_SHORT).show();
                listenter.onSuccessfulLogin();
            }catch (SQLiteConstraintException e){
                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }catch (Exception e){e.printStackTrace();}
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListenter) {
            listenter = (FragmentListenter) context;
        }
        mRoomManager = new RoomDB.RoomManager(getContext());
    }

}
