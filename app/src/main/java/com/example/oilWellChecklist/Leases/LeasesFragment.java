package com.example.oilWellChecklist.Leases;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.oilWellChecklist.Dialogs.NewLeaseDialog;
import com.example.oilWellChecklist.Helpers.FirebaseHelper;
import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.LeaseModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeasesFragment extends Fragment {

    private LeasesRecyclerViewAdapter _leasesRecyclerViewAdapter;
    private final HashMap<String, LeaseModel> _leases = new HashMap<>();
    private final List<String> _LeasesKeyList = new ArrayList<>();

    private FirebaseHelper _firebaseHelper;

    private FloatingActionButton _newLeaseButton;

    private  Button button;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        _firebaseHelper =  new FirebaseHelper();
    }

    private void openNewLeaseDialog(View view)
    {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogFragment newLeaseFragment = new NewLeaseDialog();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newLeaseFragment).addToBackStack(null).commit();


        newLeaseFragment.show(getChildFragmentManager(), "New Lease Fragment");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_leases, container, false);


        _newLeaseButton = (FloatingActionButton)view.findViewById(R.id.new_leases_button);
        _newLeaseButton.setOnClickListener(this::openNewLeaseDialog);

        return view;
    }

    private void getTest()
    {
        int x = 0;

    }

    public void newLease(LeaseModel leaseModel)
    {
        DatabaseReference databaseReference = _firebaseHelper.database.getReference("lease");
        String newLeaseKey = databaseReference.push().getKey();
        databaseReference.child(newLeaseKey).setValue(leaseModel);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String postKey = snapshot.getKey();
                if(!_leases.containsKey(postKey))
                {



                }

                //for(DataSnapshot item : snapshot.getChildren()() )
                //{
                //    LeaseViewModel leaseViewModel = snapshot.getValue(LeaseViewModel.class);
                //    leasesList.add(leaseViewModel);
                //}

                //_leasesRecyclerViewAdapter.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
