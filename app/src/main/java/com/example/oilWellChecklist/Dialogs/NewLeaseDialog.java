package com.example.oilWellChecklist.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.oilWellChecklist.Leases.NewLeaseModel;
import com.example.oilWellChecklist.R;

public class NewLeaseDialog extends DialogFragment {

    Button actionButton;
    private EditText _newLeaseName, _newLeaseDescription;

    public interface NewLeaseModelDialogListener
    {
        void onFinishNewLease(NewLeaseModel newLease);
    }

    private NewLeaseModelDialogListener newLeaseModelDialogListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Fragment parent = getParentFragment();

        try {
            newLeaseModelDialogListener = (NewLeaseModelDialogListener)parent;
        }
        catch (Exception ex)
        {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public NewLeaseDialog() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_lease_dialog, container, false);

        Button actionButton = view.findViewById(R.id.button_create_new_lease);

        actionButton.setOnClickListener(this::CreateNewLease);

        _newLeaseName = view.findViewById(R.id.new_lease_name);
        _newLeaseDescription = view.findViewById(R.id.new_lease_description);

        return view;
    }

    private void CreateNewLease(View view)
    {
        try {
            newLeaseModelDialogListener.onFinishNewLease(new NewLeaseModel(_newLeaseName.getText().toString(), _newLeaseDescription.getText().toString()));
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
