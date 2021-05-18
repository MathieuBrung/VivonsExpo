package fr.mbr.vivonsexpo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;

import fr.mbr.vivonsexpo.R;

public class AdapterExposant extends RecyclerView.Adapter<AdapterExposant.ExposantViewHolder> {

    private Context mContext;
    private ArrayList<String> mRaisonSociale = new ArrayList<String>();
    private ArrayList<String> mActivite = new ArrayList<String>();
    private ArrayList<String> mStand = new ArrayList<String>();
    private ArrayList<String> mAllee = new ArrayList<String>();
    private ArrayList<String> mTravee = new ArrayList<String>();
    private ArrayList<String> mHall = new ArrayList<String>();
    private ArrayList<Integer> mLogo = new ArrayList<Integer>();

    public AdapterExposant (Context context, ArrayList<String> raisonSociale, ArrayList<String> activite, ArrayList<String> stand, ArrayList<String> allee, ArrayList<String> travee, ArrayList<String> hall, ArrayList<Integer> logo) {
        mContext = context;
        mRaisonSociale = raisonSociale;
        mActivite = activite;
        mStand = stand;
        mAllee = allee;
        mTravee = travee;
        mHall = hall;
        mLogo = logo;
    }

    @NonNull
    @Override
    public ExposantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.liste_exposant, parent, false);
        return new ExposantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExposantViewHolder holder, int position) {
        holder.mTextViewRaisonSociale.setText(mRaisonSociale.get(position));
        holder.mTextViewActivite.setText(mActivite.get(position));
        holder.mTextViewStand.setText(mStand.get(position));
        holder.mTextViewAllee.setText(mAllee.get(position));
        holder.mTextViewTravee.setText(mTravee.get(position));
        holder.mTextViewHall.setText(mHall.get(position));
        holder.mImageViewLogo.setImageResource(mLogo.get(position));
    }

    @Override
    public int getItemCount() {
        return mRaisonSociale.size();
    }

    public class ExposantViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewRaisonSociale, mTextViewActivite, mTextViewStand, mTextViewAllee, mTextViewTravee, mTextViewHall;
        ImageView mImageViewLogo;

        public ExposantViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewRaisonSociale = itemView.findViewById(R.id.textView_raisonSociale);
            mTextViewActivite = itemView.findViewById(R.id.textView_activite);
            mTextViewStand = itemView.findViewById(R.id.textView_value_stand_visiteur);
            mTextViewAllee = itemView.findViewById(R.id.textView_value_allee_visiteur);
            mTextViewTravee = itemView.findViewById(R.id.textView_value_travee_visiteur);
            mTextViewHall = itemView.findViewById(R.id.textView_value_hall_visiteur);
            mImageViewLogo = itemView.findViewById(R.id.imageView_logo);
        }
    }
}
