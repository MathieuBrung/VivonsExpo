package fr.mbr.vivonsexpo.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.mbr.vivonsexpo.R;
import fr.mbr.vivonsexpo.controller.UniversActivity;

public class AdapterUnivers extends RecyclerView.Adapter<AdapterUnivers.UniversViewHolder> {

    private Context mContext;
    private ArrayList<Integer> mLogo = new ArrayList<Integer>();
    private ArrayList<String> mLibelle = new ArrayList<String>();

    public AdapterUnivers (Context context, ArrayList<Integer> logo, ArrayList<String> libelle) {
        mContext = context;
        mLogo = logo;
        mLibelle = libelle;
    }

    @NonNull
    @Override
    public UniversViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.menu_univers, parent, false);
        return new UniversViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversViewHolder holder, int position) {
        holder.mImageButton.setImageResource(mLogo.get(position));
        holder.mLibelleUnivers.setText(mLibelle.get(position));

        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UniversActivity.class);
                intent.putExtra("Libelle univers", mLibelle.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLogo.size();
    }

    public class UniversViewHolder extends RecyclerView.ViewHolder {
        ImageButton mImageButton;
        TextView mLibelleUnivers;

        public UniversViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageButton = itemView.findViewById(R.id.imageButton_menu_univers);
            mLibelleUnivers = itemView.findViewById(R.id.textView_universLibelle);
        }
    }
}
