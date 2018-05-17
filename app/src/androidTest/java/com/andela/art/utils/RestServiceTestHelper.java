package com.andela.art.utils;

/**
 * Created by zack on 4/18/18.
 */

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Rest test helper.
 */
public class RestServiceTestHelper {

    /**
     *
     * @param is - inputstream
     * @return string
     * @throws Exception - Exception
     */
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    /**
     *
     * @param context - context
     * @param filePath - filePath
     * @return string
     * @throws Exception - Exception
     */
    public static String getStringFromFile(Context context, String filePath) throws Exception {
        final InputStream stream = context.getResources().getAssets().open(filePath);

        String ret = convertStreamToString(stream);
        stream.close();
        return ret;
    }
}
