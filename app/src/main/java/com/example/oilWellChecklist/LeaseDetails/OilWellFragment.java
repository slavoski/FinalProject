package com.example.oilWellChecklist.LeaseDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oilWellChecklist.Helpers.FirebaseHelper;
import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.CardModel;
import com.example.oilWellChecklist.database_models.OilWellModel;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OilWellFragment extends Fragment {


    OilWellModel _oilWellModel;

    private CardDetailsRecyclerViewAdapter _cardDetailsRecyclerViewAdapter;
    private final HashMap<String, CardModel> _cards = new HashMap<>();
    private final List<String> _cardKeyList = new ArrayList<>();
    private FirebaseHelper _firebaseHelper;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _firebaseHelper = new FirebaseHelper();

        _firebaseHelper.fire_store.collection("Cards")
                .where(Filter.and( Filter.equalTo("TypeId", "OIL_WELL"),
                        Filter.or(
                        Filter.equalTo("Default", true),
                        Filter.equalTo("Id", _oilWellModel.Id))
                ))
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        for(DocumentSnapshot document : task.getResult()) {
                            CardModel cardModel = new CardModel( document.get("Id").toString(), document.get("Description").toString() );
                            _cardKeyList.add(document.getId());
                            _cards.put(document.getId(), cardModel);

                            int size = _cardKeyList.size() - 1;
                            _cardDetailsRecyclerViewAdapter.notifyItemInserted(size);
                            recyclerView.scrollToPosition(size);
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public  OilWellFragment(OilWellModel oilWellModel)
    {
        _oilWellModel = oilWellModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.oil_well_details_view, container, false);

        try {
            MaterialTextView _oilWellDescription = view.findViewById(R.id.oil_well_description);
            _oilWellDescription.setText(_oilWellModel.Description);

            MaterialTextView _oilWellName = view.findViewById(R.id.oil_well_name);
            _oilWellName.setText(_oilWellModel.Name);

            MaterialCheckBox _checkbox = view.findViewById(R.id.capped_check_box);
            _checkbox.setChecked(_oilWellModel.IsCapped);

            _cardDetailsRecyclerViewAdapter = new CardDetailsRecyclerViewAdapter(getContext(), _cards, _cardKeyList);

            recyclerView = view.findViewById(R.id.oil_well_checklist);

            LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            layoutManager.scrollToPosition(0);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(_cardDetailsRecyclerViewAdapter);

        }
        catch (Exception ex)
        {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}
