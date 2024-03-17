package com.example.oilWellChecklist.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.oilWellChecklist.Constants.EntityType;
import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.EntityModel;

public class NewEntityDialog extends DialogFragment {

    public interface NewEntityModelDialogListener
    {
        void onFinishNewEntity(EntityModel newEntity);
    }

    private NewEntityModelDialogListener newEntityModelDialogListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Fragment parent = getParentFragment();

        try {
            newEntityModelDialogListener = (NewEntityModelDialogListener)parent;
        }
        catch (Exception ex)
        {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_entity_dialog, container, false);

        EntityType[] entities = EntityType.values();
        ArrayAdapter<EntityType> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, entities);
        Spinner selector = view.findViewById(R.id.entity_selector);
        selector.setAdapter(adapter);

        View oilWellView = view.findViewById(R.id.new_oil_well_dialog);
        View tankView = view.findViewById(R.id.new_oil_tank_dialog);
        selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(entities[position])
                {
                    case OIL_WELL:
                        oilWellView.setVisibility(View.VISIBLE);
                        tankView.setVisibility(View.GONE);
                        break;
                    case TANK:
                        oilWellView.setVisibility(View.GONE);
                        tankView.setVisibility(View.VISIBLE);
                        break;
                    default:
                        oilWellView.setVisibility(View.GONE);
                        tankView.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }


    private void CreateNewEntity(View view)
    {
        try
        {

            //Send based on selection
            //newEntityModelDialogListener.onFinishNewEntity(new NewLeaseModel(_newLeaseName.getText().toString(), _newLeaseDescription.getText().toString()));
        }
        catch (Exception ex)
        {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        dismiss();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
