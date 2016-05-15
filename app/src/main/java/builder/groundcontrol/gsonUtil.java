package builder.groundcontrol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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



}
