package com.gutotech.narutogame.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.data.model.CharOn;
import com.gutotech.narutogame.data.model.Character;
import com.gutotech.narutogame.utils.StorageUtil;

import java.util.List;

public class CharsInPositionAdapter extends RecyclerView.Adapter<CharsInPositionAdapter.ViewHolder> {

    public interface OnBattleClickListener {
        void onBattleClick(Character opp);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView charInfoTextView;
        private ImageButton battleImageButton;
        private ImageView spriteImageView;
        private ImageView backgroundImageView;
        private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            charInfoTextView = itemView.findViewById(R.id.charInfoTextView);
            battleImageButton = itemView.findViewById(R.id.battleImageButton);
            spriteImageView = itemView.findViewById(R.id.spriteImageView);
            backgroundImageView = itemView.findViewById(R.id.backgroundImageView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    private Context mContext;
    private List<Character> mCharacters;
    private OnBattleClickListener mOnBattleClickListener;

    public CharsInPositionAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_map_position, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mCharacters != null) {
            Character character = mCharacters.get(position);

            if (character.getNick().equals(CharOn.character.getNick())) {
                holder.backgroundImageView.setImageResource(R.drawable.layout_map_me2);
            } else if (character.getVillage() == CharOn.character.getVillage()) {
                holder.backgroundImageView.setImageResource(R.drawable.layout_map_green2);
            } else {
                holder.backgroundImageView.setImageResource(R.drawable.layout_map_red2);
            }

            StorageUtil.downloadSprite(holder.spriteImageView, character.getNinja().getId());

            holder.charInfoTextView.setText(mContext.getString(R.string.map_char_info,
                    character.getNick(), character.getLevel(), character.getVillage().name));

            holder.battleImageButton.setOnClickListener(v ->
                    mOnBattleClickListener.onBattleClick(character));

            holder.backgroundImageView.setVisibility(View.VISIBLE);
            holder.spriteImageView.setVisibility(View.VISIBLE);
            holder.battleImageButton.setVisibility(View.VISIBLE);
            ConstraintSet set = new ConstraintSet();
            set.clone(holder.constraintLayout);
            set.constrainWidth(R.id.charInfoTextView, 180);
            set.applyTo(holder.constraintLayout);
        } else {
            holder.charInfoTextView.setText(R.string.this_place_is_empty);
            holder.backgroundImageView.setImageResource(R.drawable.layout_map_blank2);
            holder.backgroundImageView.setVisibility(View.GONE);
            holder.spriteImageView.setVisibility(View.GONE);
            holder.battleImageButton.setVisibility(View.GONE);
            ConstraintSet set = new ConstraintSet();
            set.clone(holder.constraintLayout);
            set.constrainWidth(R.id.charInfoTextView, ConstraintSet.WRAP_CONTENT);
            set.applyTo(holder.constraintLayout);
        }
    }

    @Override
    public int getItemCount() {
        return mCharacters != null ? mCharacters.size() : 1;
    }

    public void setCharacters(List<Character> characters) {
        mCharacters = characters;
        notifyDataSetChanged();
    }

    public void setBattleClickListener(OnBattleClickListener listener) {
        mOnBattleClickListener = listener;
    }
}
