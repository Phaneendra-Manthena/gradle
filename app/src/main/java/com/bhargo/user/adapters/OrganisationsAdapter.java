package com.bhargo.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.interfaces.ItemClickListener;
import com.bhargo.user.pojos.OrgList;

import java.util.List;

public class OrganisationsAdapter extends RecyclerView.Adapter<OrganisationsAdapter.OperatorsHolder> {

    Context context;
    List<OrgList> stringList;
    private ItemClickListener clickListener;

    public OrganisationsAdapter(Context context, List<OrgList> stringList) {
        this.context = context;
        this.stringList = stringList;

    }

    @NonNull
    @Override
    public OrganisationsAdapter.OperatorsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_org_list, parent, false);
        // set the view's size, margins, paddings and layout_sample_app parameters
        OrganisationsAdapter.OperatorsHolder vh = new OrganisationsAdapter.OperatorsHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrganisationsAdapter.OperatorsHolder holder, int position) {

        holder.ce_OrgID.setText(stringList.get(position).getOrgID());
        holder.ce_OrgName.setText(stringList.get(position).getOrgName());

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public void setCustomClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class OperatorsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CustomTextView ce_OrgID, ce_OrgName;

        public OperatorsHolder(@NonNull View itemView) {
            super(itemView);

            ce_OrgID = itemView.findViewById(R.id.ce_OrgID);
            ce_OrgName = itemView.findViewById(R.id.ce_OrgName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onCustomClick(context,v, getAdapterPosition(),stringList.get(getAdapterPosition()).getOrgID());
        }
    }

}
