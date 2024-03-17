package com.example.oilWellChecklist.LeaseDetails;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.oilWellChecklist.Constants.EntityType;
import com.example.oilWellChecklist.database_models.EntityModel;
import com.example.oilWellChecklist.database_models.OilWellModel;
import com.example.oilWellChecklist.database_models.TankModel;

import java.util.ArrayList;

public class LeaseDetailsViewPagerAdapter extends FragmentStateAdapter {

    ArrayList<EntityModel> _entities;
    public LeaseDetailsViewPagerAdapter(@NonNull Fragment fragment, ArrayList<EntityModel> entities)
    {
        super(fragment);
        _entities = entities;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        EntityModel currEntity = _entities.get(position);
        EntityType type = currEntity.TypeId;
        Fragment fragment;

        switch (type)
        {
            case TANK:
                fragment = new TankFragment((TankModel) currEntity);

                break;
            case OIL_WELL:
                fragment = new OilWellFragment((OilWellModel) currEntity);

                break;
            default:
                fragment = new EntityFragment(currEntity);
                break;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return _entities.size();
    }
}
