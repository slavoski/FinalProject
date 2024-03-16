package com.example.oilWellChecklist.LeaseDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oilWellChecklist.Helpers.FirebaseHelper;
import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.LeaseModel;
import com.google.firebase.firestore.DocumentSnapshot;

public class LeaseDetailsFragment extends Fragment {

    private int _leaseID;
    private FirebaseHelper _firebaseHelper;

    public LeaseDetailsFragment(int leaseId)
    {
        leaseId = _leaseID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        _firebaseHelper =  new FirebaseHelper();

        _firebaseHelper.fire_store.collection("LeaseEntities")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        for(DocumentSnapshot document : task.getResult())
                        {
                            //add in lease code
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lease_details_view, container, false);



        return view;
    }
}
