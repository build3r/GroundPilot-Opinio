package builder.groundcontrol;

import com.google.android.gms.fitness.data.WorkoutExercises;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import builder.groundcontrol.messaging.Worker;

/**
 * Created by dpallagolla on 5/15/2016.
 */
public class gsonUtil {

    public static String tojson(Object obj)
    {
        String json=null;

        Gson gson = new GsonBuilder().create();

        json = gson.toJson(obj);

        return json;
    }

    public static Object tojava(String json,Class c)
    {
        Gson gson = new GsonBuilder().create();
        Object obj = gson.fromJson(json, c);
        return obj;
    }

    public static void sendCurrentLocation(LatLng l)
    {
        String s = tojson(l);
        Worker w = Worker.getWorker();
        w.sendMessageToCustomer(s);
    }

    public static void sendData(customerDraw d)
    {
        String s = tojson(d);
        Worker w = Worker.getWorker();
        w.sendMessageToPilot(s);
    }

}
