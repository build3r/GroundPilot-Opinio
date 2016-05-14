package builder.groundcontrol.floatingNotification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Shabaz on 14-May-16.
 */
public class FloatingPilot extends Service
{
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
