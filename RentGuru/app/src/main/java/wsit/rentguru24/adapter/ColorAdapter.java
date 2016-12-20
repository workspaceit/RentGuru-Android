package wsit.rentguru24.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wsit.rentguru24.R;

/**
 * Created by workspaceinfotech on 7/29/16.
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private String[] mDataset;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;

        public ViewHolder(View v) {
            super(v);
            this.mView = (View) v.findViewById(R.id.color_view);

        }


    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public ColorAdapter(String[] myDataset) {
        mDataset = myDataset;
    }


    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_color_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.setLayoutParams(new RecyclerView.LayoutParams(150, 180));
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ColorAdapter.ViewHolder holder, int position) {

        holder.mView.setBackgroundColor(Color.parseColor(mDataset[position]));

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }




}
