package com.example.oilWellChecklist.Leases;

import static com.example.oilWellChecklist.Constants.Constants.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oilWellChecklist.Helpers.FirebaseHelper;
import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.LeaseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.List;

public class LeasesRecyclerViewAdapter extends RecyclerView.Adapter<LeasesViewHolder> {


    private final Context _context;
    private final List<String> _leasesKeyList;
    private final HashMap<String, LeaseModel> _leases;
    FirebaseHelper _firebaseHelper;

    public LeasesRecyclerViewAdapter(Context context, HashMap<String, LeaseModel> leases, List<String> leasesKeyList)
    {
        _context = context;
        _leases = leases;
        _leasesKeyList = leasesKeyList;
        _firebaseHelper = new FirebaseHelper();
    }

    @NonNull
    @Override
    public LeasesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leases_card_view, parent, false);
        final LeasesViewHolder leasesViewHolder = new LeasesViewHolder(view);
        return leasesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeasesViewHolder holder, int position) {
        final LeaseModel lease = _leases.get(_leasesKeyList.get(position));
        DocumentReference leaseReference = _firebaseHelper.fire_store.collection("Leases").document(lease.Id);

        if(lease != null) {
            holder._userReference = _firebaseHelper.database.getReference("Users").child(lease.UserId);

            holder._userReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    holder._leasesName.setText(snapshot.child("LeaseName").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            leaseReference.addSnapshotListener((snapshot, exception) ->
            {
               if(exception == null)
               {
                    LeaseModel leaseModel = snapshot.toObject(LeaseModel.class);

                    if(leaseModel == null)
                    {
                        //Delete Lease from here
                    }
               }
               else
               {
                   Log.w(TAG, "", exception);
               }
            });

            holder._leasesName.setText(lease.LeaseName);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
