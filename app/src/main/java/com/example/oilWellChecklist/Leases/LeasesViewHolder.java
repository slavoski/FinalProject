package com.example.oilWellChecklist.Leases;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oilWellChecklist.R;

public class LeasesViewHolder extends RecyclerView.ViewHolder {

    public TextView _leasesName;
    public ImageView _leasesImage;

    public TextView _leasesDescription;

    public LeasesViewHolder(@NonNull View itemView) {
        super(itemView);

        _leasesName = itemView.findViewById(R.id.lease_name);
        _leasesImage = itemView.findViewById(R.id.lease_image);
        _leasesDescription = itemView.findViewById(R.id.lease_description);
    }
}
