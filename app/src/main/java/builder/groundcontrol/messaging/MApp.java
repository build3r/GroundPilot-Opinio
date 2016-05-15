package builder.groundcontrol.messaging;

import android.app.Application;
import android.content.Context;

import com.quickblox.core.QBSettings;

/**
 * Created by dpallagolla on 5/15/2016.
 */
public class MApp extends Application
{

    public static final boolean CUSTOMER =true;
    public static final boolean PILOT =false;
    public static Context ctx;
    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
        QBSettings.getInstance().init(this, Constants.APP_ID, Constants.AUTH_KEY, Constants.AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(Constants.ACCOUNT_KEY);
        /*//who = CUSTOMER;
        who = PILOT; //Means Pilot*/
    }
    public static boolean whoAmI()
    {
        return CUSTOMER;
    }
}
