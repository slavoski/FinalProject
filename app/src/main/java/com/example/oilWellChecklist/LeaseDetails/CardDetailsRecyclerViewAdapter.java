package com.example.oilWellChecklist.LeaseDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oilWellChecklist.Helpers.FirebaseHelper;
import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.CardModel;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.List;

public class CardDetailsRecyclerViewAdapter extends RecyclerView.Adapter<CardDetailsRecyclerViewAdapter.CardDetailsViewHolder> {

    private final List<String> _cardKeyList;
    private final HashMap<String, CardModel> _cards;
    final FirebaseHelper _firebaseHelper;
    final Context _context;

    public CardDetailsRecyclerViewAdapter(Context context, HashMap<String, CardModel> cards, List<String> cardKeyList)
    {
        _cards = cards;
        _cardKeyList = cardKeyList;
        _firebaseHelper = new FirebaseHelper();
        _context = context;
    }


    @NonNull
    @Override
    public CardDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_card, parent, false);

        return new CardDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardDetailsViewHolder holder, int position) {
        final CardModel card = _cards.get(_cardKeyList.get(position));

        try {
            if (card != null) {
                DocumentReference cardReference = _firebaseHelper.fire_store.collection("Cards").document(card.Id);
                holder._cardDescription.setText(card.Description);
                //holder._cardToggleButton.setO
                //holder._cardImage.setImageResource();
            }
        }
        catch (Exception ex){
            Toast.makeText(_context, ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return _cardKeyList.size();
    }

    public static class CardDetailsViewHolder extends RecyclerView.ViewHolder {

        public final MaterialTextView _cardDescription;
        public final MaterialButtonToggleGroup _cardToggleButton;
        public ImageView _cardImage;

        public CardDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            _cardDescription = itemView.findViewById(R.id.card_description);
            _cardToggleButton = itemView.findViewById(R.id.card_toggle);
            //_cardImage = itemView.findViewById(R.id.lease_description);
        }
    }
}
