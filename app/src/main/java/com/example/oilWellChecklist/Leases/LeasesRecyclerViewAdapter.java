package com.example.oilWellChecklist.Leases;

import static com.example.oilWellChecklist.Constants.Constants.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oilWellChecklist.Helpers.FirebaseHelper;
import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.LeaseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.List;

public class LeasesRecyclerViewAdapter extends RecyclerView.Adapter<LeasesRecyclerViewAdapter.LeasesViewHolder> {

    private final List<String> _leasesKeyList;
    private final HashMap<String, LeaseModel> _leases;
    FirebaseHelper _firebaseHelper;

    private final LeaseItemClickListener _leaseItemClickListener;

    public LeasesRecyclerViewAdapter(HashMap<String, LeaseModel> leases, List<String> leasesKeyList, LeaseItemClickListener leaseItemClickListener)
    {
        _leases = leases;
        _leasesKeyList = leasesKeyList;
        _firebaseHelper = new FirebaseHelper();
        _leaseItemClickListener = leaseItemClickListener;
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
            holder._leasesDescription.setText(lease.Description);
            holder._leasesImage.setImageResource(R.drawable.pump_jack_icon);
            holder.itemView.setOnClickListener(v -> {
                if(_leaseItemClickListener != null)
                {
                    _leaseItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return _leasesKeyList.size();
    }

    public static class LeasesViewHolder extends RecyclerView.ViewHolder {

        public TextView _leasesName;
        public TextView _leasesDescription;
        public ImageView _leasesImage;
        public DatabaseReference _userReference;

        public LeasesViewHolder(@NonNull View itemView) {
            super(itemView);

            _leasesName = itemView.findViewById(R.id.lease_name);
            _leasesImage = itemView.findViewById(R.id.lease_image);
            _leasesDescription = itemView.findViewById(R.id.lease_description);
        }
    }

}
