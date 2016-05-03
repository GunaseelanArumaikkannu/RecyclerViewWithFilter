package com.guna.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guna.entity.Number;
import com.guna.recyclerviewwithfilter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gunaseelan on 11-12-2015.
 * Simple adapter class, used for show all companies in list
 */
public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.ViewHolder> {

    ArrayList<Number> companies;

    public NumbersAdapter(List<Number> numbers) {
        this.companies = new ArrayList<>(numbers);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(companies.get(position));
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public void animateTo(List<Number> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Number> newModels) {
        for (int i = companies.size() - 1; i >= 0; i--) {
            final Number model = companies.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Number> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Number model = newModels.get(i);
            if (!companies.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Number> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Number model = newModels.get(toPosition);
            final int fromPosition = companies.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Number removeItem(int position) {
        final Number model = companies.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Number model) {
        companies.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Number model = companies.remove(fromPosition);
        companies.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView ONEs;
        private TextView TENs;
        private TextView HUNDREDs;
        private TextView textONEs;
        private TextView textTENs;
        private TextView textHUNDREDs;
        private LinearLayout llDescription;
        private LinearLayout llListItem;

        public ViewHolder(View v) {
            super(v);
            ONEs = (TextView) v.findViewById(R.id.ONEs);
            TENs = (TextView) v.findViewById(R.id.TENs);
            HUNDREDs = (TextView) v.findViewById(R.id.HUNDREDs);
            textONEs = (TextView) v.findViewById(R.id.textONEs);
            textTENs = (TextView) v.findViewById(R.id.textTENs);
            textHUNDREDs = (TextView) v.findViewById(R.id.textHUNDREDs);

            llDescription = (LinearLayout) v.findViewById(R.id.llDescription);
            llListItem = (LinearLayout) v.findViewById(R.id.llListItem);

            llListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked();
                }
            });
        }

        public void bindData(Number number) {
            ONEs.setText(number.getONEs());
            TENs.setText(number.getTENs());
            HUNDREDs.setText(number.getHUNDREDs());
            textONEs.setText(number.getTextONEs());
            textTENs.setText(number.getTextTENs());
            textHUNDREDs.setText(number.getTextHUNDREDs());
        }

        public void onItemClicked() {
            if (llDescription.getVisibility() == View.VISIBLE)
                llDescription.setVisibility(View.GONE);
            else llDescription.setVisibility(View.VISIBLE);
        }
    }

}