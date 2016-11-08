package wsit.rentguru.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.model.CategoryModel;
import wsit.rentguru.utility.AnimatedExpandableListView;

/**
 * Created by Tomal on 11/8/2016.
 */

public class CategoryExpandableListViewAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private final ArrayList<CategoryModel> categoryList;
    private LayoutInflater layoutInflater;
    private Activity activity;


    public CategoryExpandableListViewAdapter(Activity activity,ArrayList<CategoryModel> categoryList) {
        this.categoryList = categoryList;
        this.activity = activity;
        layoutInflater=activity.getLayoutInflater();
    }

    private class ViewHolder {

        public TextView nameTextView;
        public TextView indicatorTextView;
    }



    @Override
    public int getRealChildrenCount(int groupPosition) {
        return categoryList.get(groupPosition).getSubcategory().size();
    }

    @Override
    public int getGroupCount() {
        return this.categoryList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categoryList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.categoryList.get(groupPosition).getSubcategory();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.category_parent_list_single_item, null);
            viewHolder = new ViewHolder();

            viewHolder.nameTextView = (TextView) convertView
                    .findViewById(R.id.lblListItem);
            viewHolder.indicatorTextView=(TextView)convertView.findViewById(R.id.indicator_second_level);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.nameTextView.setText(categoryList.get(groupPosition).getName());
        if (categoryList.get(groupPosition).getSubcategory().isEmpty()){
            viewHolder.indicatorTextView.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.indicatorTextView.setVisibility(View.VISIBLE);
        }

        if (isExpanded){

            viewHolder.indicatorTextView.setText("-");

        }else {

            viewHolder.indicatorTextView.setText("+");
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondLevelViewHolder secondLevelViewHolder;
        final CategoryModel category=this.categoryList.get(groupPosition).getSubcategory().get(childPosition);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.categoy_second_level_single_item, null);
            secondLevelViewHolder=new SecondLevelViewHolder();
            secondLevelViewHolder.secondLevelTextView=(TextView) convertView.findViewById(R.id.child_of_parent);
            convertView.setTag(secondLevelViewHolder);
        }else {
            secondLevelViewHolder=(SecondLevelViewHolder) convertView.getTag();
        }

        secondLevelViewHolder.secondLevelTextView.setText(category.getName());

        return convertView;
    }



    private class SecondLevelViewHolder{
        TextView secondLevelTextView;
    }
}
