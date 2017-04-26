package com.homefixer.homefixer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView countryName;
    public ImageView countryPhoto;
    RecyclerView recyclerView;
    public  static  int pos = 0;
    private final int SID = 111001;

    public RecyclerViewHolders(final View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        countryName = (TextView) itemView.findViewById(R.id.service_name);
        countryPhoto = (ImageView) itemView.findViewById(R.id.service_photo);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Count Position = " + getPosition(), Toast.LENGTH_SHORT).show();

        try {
            RecyclerViewHolders.pos = getPosition();
            Intent intent = new Intent(view.getContext(), ExpandbleList.class);
            intent.putExtra("SID",SID+getPosition());
            view.getContext().startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

