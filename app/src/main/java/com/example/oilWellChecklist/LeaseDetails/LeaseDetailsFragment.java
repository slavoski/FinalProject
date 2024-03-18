package com.example.oilWellChecklist.LeaseDetails;

import static com.example.oilWellChecklist.Constants.Constants.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.oilWellChecklist.Constants.EntityType;
import com.example.oilWellChecklist.Dialogs.NewEntityDialog;
import com.example.oilWellChecklist.Helpers.FirebaseHelper;
import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.EntityModel;
import com.example.oilWellChecklist.database_models.OilWellModel;
import com.example.oilWellChecklist.database_models.TankModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class LeaseDetailsFragment extends Fragment implements NewEntityDialog.NewEntityModelDialogListener {

    private final String _leaseID;

    LeaseDetailsViewPagerAdapter _leaseDetailsAdapter;

    ViewPager2 _viewPager;

    private final ArrayList<EntityModel> _entities = new ArrayList<>();

    private FirebaseHelper _firebaseHelper;

    public LeaseDetailsFragment(String leaseId)
    {
         _leaseID = leaseId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        _firebaseHelper = new FirebaseHelper();


        _firebaseHelper.fire_store.collection("LeaseEntities")
                .whereEqualTo("LeaseId", _leaseID)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        for(DocumentSnapshot document : task.getResult())
                        {
                            EntityType type = EntityType.valueOf(document.get("TypeId").toString());

                            switch (type)
                            {
                                case TANK:
                                    _entities.add(new TankModel(document.get("Id").toString(), document.get("Name").toString(), document.get("Description").toString(), document.get("LeaseId").toString(), document.get("Url").toString(),
                                                                document.get("TotalVolume").toString(), document.get("Volume").toString(), document.get("Diameter").toString(), document.get("Circumference").toString()));
                                    break;
                                case OIL_WELL:
                                    _entities.add(new OilWellModel(document.get("Id").toString(), document.get("Name").toString(), document.get("Description").toString(), document.get("LeaseId").toString(), document.get("Url").toString(),
                                            (Boolean)document.get("IsCapped")));
                                    break;
                                default:
                                    Toast.makeText(this.getContext(), "Unknown Type: " + type,Toast.LENGTH_SHORT).show();
                                    break;
                            }

                            int size = _entities.size() - 1;
                            _leaseDetailsAdapter.notifyItemInserted(size);
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

        _leaseDetailsAdapter = new LeaseDetailsViewPagerAdapter(this, _entities);

        _viewPager = view.findViewById(R.id.lease_detail_pager);
        _viewPager.setAdapter(_leaseDetailsAdapter);
        _viewPager.setPageTransformer(new ZoomOutPageTransformer());

        TabLayout tabLayout = view.findViewById(R.id.lease_detail_tabs);
        new TabLayoutMediator(tabLayout, _viewPager, (tab, position) ->
                tab.setText(_entities.get(position).Name)).attach();

        FloatingActionButton actionButton = view.findViewById(R.id.new_entity_button);
        actionButton.setOnClickListener(this::openNewEntityDialog);

        return view;
    }

    private void openNewEntityDialog(View view)
    {
        DialogFragment newEntityFragment = new NewEntityDialog();
        newEntityFragment.show(getChildFragmentManager(), "New Entity Fragment");
    }

    @Override
    public void onFinishNewEntity(NewEntityModel newEntity) {

        EntityModel entityModel = null;
        switch (newEntity.Type) {
            case TANK:
                NewTankModel tankModel = (NewTankModel)newEntity;
                //TODO add all data
                entityModel = new TankModel(java.util.UUID.randomUUID().toString(), tankModel.Name,
                        tankModel.Description, String.valueOf(_leaseID),"", "2000", "0",
                        "50", "50");
                break;
            case OIL_WELL:
                NewOilWellModel oilWellModel = (NewOilWellModel)newEntity;
                entityModel = new OilWellModel(java.util.UUID.randomUUID().toString(), oilWellModel.Name, oilWellModel.Description,
                        String.valueOf(_leaseID), "", oilWellModel.IsCapped );
                break;
            default:
                Toast.makeText(this.getContext(), "Unknown Type: " + newEntity.Type, Toast.LENGTH_SHORT).show();
                break;
        }

        addNewEntity(entityModel);

    }

    public void addNewEntity(EntityModel entityModel)
    {
        if(entityModel != null)
        {
            _firebaseHelper.fire_store.collection("LeaseEntities").add(entityModel).addOnSuccessListener(documentReference -> {
                boolean alreadyExists = _entities.stream().anyMatch(entity -> Objects.equals(entity.Id, entityModel.Id));

                if(!alreadyExists)
                {
                    Log.i(TAG, documentReference.toString());
                    try {

                        _entities.add(entityModel);
                        int size = _entities.size() - 1;
                        _leaseDetailsAdapter.notifyItemInserted(size);

                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(ex ->
                    Log.e(TAG, Objects.requireNonNull(ex.getMessage())));
        }
    }


    public static class ZoomOutPageTransformer implements ViewPager2.PageTransformer {

        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            int pageWidth = page.getWidth();
            int pageHeight = page.getHeight();

            if(position < -1)
            {
                page.setAlpha(0f);
            }
            else if (position <= 1)
            {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float verticalMargin = pageHeight * (1 - scaleFactor) / 2;
                float horizontalMargin = pageWidth *  (1 - scaleFactor) / 2;

                if(position < 0)
                {
                    page.setTranslationX(horizontalMargin - (verticalMargin / 2));
                }
                else
                {
                    page.setTranslationX(-horizontalMargin + (verticalMargin / 2));
                }

                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            }
            else
            {
                page.setAlpha(0f);
            }
        }

    }

}


