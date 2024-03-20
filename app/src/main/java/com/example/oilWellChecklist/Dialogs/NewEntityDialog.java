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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.oilWellChecklist.Constants.EntityType;
import com.example.oilWellChecklist.LeaseDetails.NewEntityModel;
import com.example.oilWellChecklist.LeaseDetails.NewOilWellModel;
import com.example.oilWellChecklist.LeaseDetails.NewTankModel;
import com.example.oilWellChecklist.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewEntityDialog extends DialogFragment {

    TextView _newOilWellName, _newOilWellDescription, _newTankName, _newTankDescription;
    SwitchMaterial _newIsCapped;

    EntityType selectedEntity;

    public interface NewEntityModelDialogListener
    {
        void onFinishNewEntity(NewEntityModel newEntity);
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

        List<String> entitiesDescriptions = Arrays.stream(EntityType.values()).map(EntityType::getDescription).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, entitiesDescriptions);

        Spinner selector = view.findViewById(R.id.entity_selector);
        selector.setAdapter(adapter);

        View oilWellView = view.findViewById(R.id.new_oil_well_dialog);
        View tankView = view.findViewById(R.id.new_oil_tank_dialog);
        selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEntity = EntityType.values()[position];
                switch(selectedEntity)
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

        Button oilWellButton = view.findViewById(R.id.button_create_new_oil_well);
        oilWellButton.setOnClickListener(this::CreateNewEntity);

        Button tankButton = view.findViewById(R.id.button_create_new_tank);
        tankButton.setOnClickListener(this::CreateNewEntity);

        try {
            _newIsCapped = oilWellView.findViewById(R.id.is_capped);
            _newOilWellDescription = oilWellView.findViewById(R.id.new_oil_well_description);
            _newOilWellName = oilWellView.findViewById(R.id.new_oil_well_name);

            _newTankDescription = tankView.findViewById(R.id.new_tank_description);
            _newTankName = tankView.findViewById(R.id.new_tank_name);

        }
        catch (Exception ex)
        {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        selector.setSelection(0);

        return view;
    }


    private void CreateNewEntity(View view)
    {
        try
        {
            switch(selectedEntity)
            {
                case OIL_WELL:
                    newEntityModelDialogListener.onFinishNewEntity( new NewOilWellModel(_newOilWellName.getText().toString(), _newOilWellDescription.getText().toString(), _newIsCapped.isChecked()));
                    break;
                case TANK:
                    newEntityModelDialogListener.onFinishNewEntity( new NewTankModel(_newTankName.getText().toString(), _newTankDescription.getText().toString() ));
                    break;
                default:
                    Toast.makeText(this.getContext(),"Unknown Entity: " + selectedEntity.getDescription(), Toast.LENGTH_SHORT).show();
                    break;
            }
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
