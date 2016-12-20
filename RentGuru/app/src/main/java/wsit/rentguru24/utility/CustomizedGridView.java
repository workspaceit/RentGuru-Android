package wsit.rentguru24.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;

/**
 * Created by workspaceinfotech on 7/28/16.
 */
public class CustomizedGridView extends StaggeredGridView {

    boolean expanded = false;
    public CustomizedGridView(Context context) {
        super(context);
    }


    public CustomizedGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomizedGridView(Context context, AttributeSet attrs,
                                    int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // HACK! TAKE THAT ANDROID!
        if (isExpanded())
        {
            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.
            int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }

}
