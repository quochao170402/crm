package cybersoft.javabackend.java18.crm.utils.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {
    private static Gson gson = null;

    private GsonHelper() {

    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .serializeNulls()
                    .setDateFormat("dd-MM-yyyy")
                    .setPrettyPrinting()
                    .create();
        }
        return gson;
    }

}
