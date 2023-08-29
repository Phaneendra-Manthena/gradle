package com.bhargo.user.adapters;

/*nagendra*/
/*Delete:
 * MainTable with rowid or transid and SubForm Tables if exist */
/*Display:
 * First 3 cols in the MainTable*/

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargo.user.R;
import com.bhargo.user.custom.CustomTextView;
import com.bhargo.user.pojos.OfflineHybridDeletePojo;
import com.bhargo.user.utils.ImproveDataBase;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class OfflineHybridDeleteAdapter extends RecyclerView.Adapter<OfflineHybridDeleteAdapter.MyViewHolder> implements Filterable {

    List<OfflineHybridDeletePojo> list, listFilter;
    Activity context;
    ImproveDataBase improveDataBase;
    CustomTextView tv_nodata;
    OfflineHybridDeleteListener offlineHybridDeleteListener;
    int fixedColIndex;

    public OfflineHybridDeleteAdapter(List<OfflineHybridDeletePojo> list, Activity context, CustomTextView tv_nodata,
                                      OfflineHybridDeleteListener offlineHybridDeleteListener) {
        this.list = list;
        this.listFilter = list;
        this.context = context;
        this.tv_nodata = tv_nodata;
        this.offlineHybridDeleteListener = offlineHybridDeleteListener;
        improveDataBase = new ImproveDataBase(context);

    }

    @Override
    public OfflineHybridDeleteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offline_hybrid_delete_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfflineHybridDeleteAdapter.MyViewHolder holder, int position) {

        final OfflineHybridDeletePojo model = listFilter.get(position);
        if (model.getImagePath()!=null && !model.getImagePath().equals("")) {
            if (model.getImagePath().contains("http")) {
                Glide.with(context).load(model.getImagePath()).placeholder(ContextCompat.getDrawable(context, R.drawable.allapps)).into(holder.iv_image);
            } else {
                setImageFromSDCard(holder.iv_image, model.getImagePath());
            }
        }
        holder.tv_col1.setText(model.getColValue1()!=null?model.getColValue1():"");
        holder.tv_col2.setText(model.getColValue2()!=null?model.getColValue2():"");
        holder.tv_col3.setText(model.getColValue3()!=null?model.getColValue3():"");
        holder.iv_image.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return listFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFilter = list;
                } else {
                    List<OfflineHybridDeletePojo> filteredList = new ArrayList<>();
                    for (OfflineHybridDeletePojo row : list) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getColValue1().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    listFilter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFilter = (List<OfflineHybridDeletePojo>) filterResults.values;
                if (listFilter.size() > 0) {
                    tv_nodata.setVisibility(View.GONE);
                } else {
                    tv_nodata.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        };
    }

    private void setImageFromSDCard(ImageView imageView, String strImagePath) {
        try {
            File imgFile = new File(strImagePath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }
        } catch (Exception e) {

        }
    }

    public interface OfflineHybridDeleteListener {

        void onSelectedDeleteItem(OfflineHybridDeletePojo selectedRecord, int selectedPostion);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_col1, tv_col2, tv_col3, tv_delete;
        ImageView iv_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_col1 = itemView.findViewById(R.id.tv_col1);
            tv_col2 = itemView.findViewById(R.id.tv_col2);
            tv_col3 = itemView.findViewById(R.id.tv_col3);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            iv_image = itemView.findViewById(R.id.iv_image);

            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    offlineHybridDeleteListener.onSelectedDeleteItem(listFilter.get(getAdapterPosition()), getAdapterPosition());
                }
            });


        }
    }
}
