package wsit.rentguru.utility;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Tomal on 10/24/2016.
 */

public class MakeToast {

    public static void ShowToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

    }
}
