package com.gochain.gochainandroid.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gochain.gochainandroid.R;
import com.gochain.gochainandroid.activities.DetailsFragment;
import com.gochain.gochainandroid.model.Poll;
import com.gochain.gochainandroid.model.PollDetails;
import com.gochain.gochainandroid.services.DateConverter;
import com.gochain.gochainandroid.vo.CampaignVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by vinguyen on 07/02/2017.
 */

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.MyViewHolder> {
    private Context mContext;
    private List<CampaignVo> itemList;
    private Fragment parent;
    private int[] photos = new int[]{
            R.drawable.publicparksmall,
            R.drawable.swimmingpoolsmall,
            R.drawable.youthcentersmall,
            R.drawable.amuseparksmall,
            R.drawable.footballfieldsmall};

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, daysRemained;
        public ImageView image, overflow;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.item_name);
            daysRemained = (TextView) view.findViewById(R.id.daysRemained);

            image = (ImageView) view.findViewById(R.id.item_photo);
            overflow = (ImageView) view.findViewById(R.id.overflow);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsFragment fragment = new DetailsFragment();
                    int i = getPosition();
                    fragment.setPoll(itemList.get(i));
                    fragment.setEditable(true);

                    parent.getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_body, fragment).addToBackStack("poll_fragment")
                            .commit();

                }
            });
        }
    }


    public PollAdapter(Fragment parent, List<CampaignVo> itemList) {
        this.parent = parent;
        this.mContext = parent.getContext();
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poll_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CampaignVo item = itemList.get(position);
        holder.name.setText(item.getCampaignId());
        holder.image.setImageResource(photos[ThreadLocalRandom.current().nextInt(0, 5)]);
        holder.daysRemained.setText(new DateConverter().getDaysTillExpireDate(item.getExpiryDate()) + " days remained");

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_item, popup.getMenu());
        popup.show();
    }

    public void add(List<CampaignVo> itemList) {
        this.itemList.addAll(itemList);
    }

    public void clear() {
        this.itemList.clear();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}