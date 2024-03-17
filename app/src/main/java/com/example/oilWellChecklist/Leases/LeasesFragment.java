package com.example.oilWellChecklist.Leases;

import static com.example.oilWellChecklist.Constants.Constants.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oilWellChecklist.Dialogs.NewLeaseDialog;
import com.example.oilWellChecklist.Helpers.FirebaseHelper;
import com.example.oilWellChecklist.HomePageActivity;
import com.example.oilWellChecklist.LeaseDetails.LeaseDetailsFragment;
import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.LeaseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LeasesFragment extends Fragment implements NewLeaseDialog.NewLeaseModelDialogListener {

    private LeasesRecyclerViewAdapter _leasesRecyclerViewAdapter;
    private final HashMap<String, LeaseModel> _leases = new HashMap<>();
    private final List<String> _leasesKeyList = new ArrayList<>();
    private FirebaseHelper _firebaseHelper;
    private FloatingActionButton _newLeaseButton;
    RecyclerView recyclerView;
    Context _context;

    LeaseItemClickListener _leaseItemClickListener;

    public LeasesFragment(LeaseItemClickListener leaseItemClickListener) {
        _leaseItemClickListener = leaseItemClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        _firebaseHelper =  new FirebaseHelper();

        _context = getContext();

        _firebaseHelper.fire_store.collection("Leases")
                .whereEqualTo("UserId", _firebaseHelper.currentUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        for(DocumentSnapshot document : task.getResult())
                        {
                            LeaseModel leaseModel = new LeaseModel(document.get("LeaseName").toString(), document.get("Description").toString(), document.get("Id").toString(), null, document.get("UserId").toString() );
                            _leasesKeyList.add(document.getId());
                            _leases.put(document.getId(), leaseModel);

                            int size = _leasesKeyList.size() - 1;
                            _leasesRecyclerViewAdapter.notifyItemInserted(size);
                            recyclerView.scrollToPosition(size);
                        }
                    }
                    else
                    {
                        Toast.makeText(_context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void openNewLeaseDialog(View view)
    {
        DialogFragment newLeaseFragment = new NewLeaseDialog();
        newLeaseFragment.show(getChildFragmentManager(), "New Lease Fragment");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_leases, container, false);

        _newLeaseButton = (FloatingActionButton)view.findViewById(R.id.new_leases_button);
        _newLeaseButton.setOnClickListener(this::openNewLeaseDialog);

        _leasesRecyclerViewAdapter = new LeasesRecyclerViewAdapter(_leases, _leasesKeyList, _leaseItemClickListener);

        recyclerView = view.findViewById(R.id.leases_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(_leasesRecyclerViewAdapter);

        return view;
    }

    public void addNewLease(LeaseModel leaseModel)
    {
        _firebaseHelper.fire_store.collection("Leases").add(leaseModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String postKey = documentReference.getId();

                if(!_leases.containsKey(postKey))
                {

                    Log.i(TAG, documentReference.toString());
                    try {
                        String id = documentReference.getId();
                        _leases.put(id, leaseModel);
                        _leasesKeyList.add(id);
                        int size = _leasesKeyList.size() - 1;
                        _leasesRecyclerViewAdapter.notifyItemInserted(size);
                        recyclerView.scrollToPosition(size);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(_context, ex.getMessage(), Toast.LENGTH_SHORT);
                    }
                }
            }
        }).addOnFailureListener(ex ->
            {
                Log.e(TAG, Objects.requireNonNull(ex.getMessage()));
            });
    }

    @Override
    public void onFinishNewLease(NewLeaseModel newLease) {
        // public LeaseModel(String lease_name, String description, String id, String url, String userId )
        //
        LeaseModel leaseModel = new LeaseModel(newLease.Name, newLease.Description, java.util.UUID.randomUUID().toString(), null, _firebaseHelper.currentUser.getUid());

        addNewLease(leaseModel);
    }
}
