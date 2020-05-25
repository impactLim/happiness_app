package com.example.happiness;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {
    private List<ExampleItem> exampleList;
    private List<ExampleItem> exampleListFull;

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView title ;
        TextView contents;
        TextView dateNow;
        ImageView imageView;

//        public View mmView;

        ExampleViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
//            photo = itemView.findViewById(R.id.photo);
            this.title = itemView.findViewById(R.id.item_title);
            this.contents = itemView.findViewById(R.id.item_contents);
            this.dateNow = itemView.findViewById(R.id.item_date);
            this.imageView = itemView.findViewById(R.id.item_image);
//            mmView = itemView;
        }
    }

    ExampleAdapter(List<ExampleItem> exampleList, myfeed_searchActivity myfeed_searchActivity) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,
                parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = exampleList.get(position);

        //  holder.photo.setImageDrawable(item.getPhoto());
        holder.title.setText(currentItem.getTitleStr());
        Log.v("search adapter title 알림","title position" + position );
        Log.v("search adapter title 알림","adapter" + exampleList.size() + exampleList.toString());

        holder.contents.setText(currentItem.getContentsStr());
        Log.v("search adapter contents 알림","contents position" + position );
        Log.v("search adapter contents 알림","adapter" + exampleList.size() + exampleList.toString());

        holder.dateNow.setText(currentItem.getDateStr());
        Log.v("search adapter dateNow 알림","datenow position" + position );
        Log.v("search adapter dateNow 알림","adapter" + exampleList.size() + exampleList.toString());

        //실제 각 뷰 홀더에 들어갈 데이터를 연결해줌.

        if(Uri.parse(currentItem.getImageStr()) != null){
            holder.imageView.setImageURI(Uri.parse(currentItem.getImageStr()));

        }

    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ExampleItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ExampleItem item : exampleListFull) {
                    if (item.getTitleStr().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}