import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;


import java.util.HashMap;
import java.util.Random;


public class ContentProviderUtils {

    public final static String PREF_FILE_NAME = "xxxxx_config";
    private ContentResolver contentResolver;
    private Context context;
    private HashMap<String, String> maps;
    private static ContentProviderUtils instance;

    public final static String KEY_NAME = "name";
    public final static String KEY_VALUE = "value";

    public static ContentProviderUtils getInstance(Context paramContext) {
        if (null == instance) {
            synchronized (ContentProviderUtils.class) {
                if (null == instance) {
                    instance = new ContentProviderUtils(paramContext);
                }
            }
        }
        return instance;
    }

    public ContentProviderUtils(Context paramContext) {
        this.context = paramContext;
        contentResolver = paramContext.getContentResolver();
        maps = getContentFromContentProvider(paramContext);
    }

    public HashMap<String, String> getMaps() {
        return maps;
    }


    public float getFloatValue(String key) {
        return TextUtils.isEmpty(maps.get(key)) ? 0 : Float.parseFloat(maps.get(key));
    }

    public double getDoubleValue(String key) {
        return TextUtils.isEmpty(maps.get(key)) ? 0 : Double.parseDouble(maps.get(key));
    }

    public boolean getBooleanValue(String key) {
        return !TextUtils.isEmpty(maps.get(key)) && Boolean.parseBoolean(maps.get(key));
    }

    public String getValue(String key) {
        return TextUtils.isEmpty(maps.get(key)) ? "" : maps.get(key);
    }

    public int getIntValue(String key) {
        return TextUtils.isEmpty(maps.get(key)) ? 0 : Integer.parseInt(maps.get(key));
    }

    public void setValue(String type, String param) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_VALUE, param);

        if (null == maps.get(type)) {
            contentValues.put(KEY_NAME, type);
            contentResolver.insert(IPCXXXXXProvider.XXXXX_CONTENT_URI, contentValues);
        } else {
            contentResolver.update(IPCXXXXXProvider.XXXXX_CONTENT_URI, contentValues, KEY_NAME + "=?", new String[]{type});
        }

        maps.put(type, param);
    }


    public HashMap<String, String> getContentFromContentProvider(Context context) {
        HashMap<String, String> map = new HashMap<>();
        Cursor cursor = null;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            cursor = contentResolver.query(IPCXXXXXProvider.XXXXX_CONTENT_URI, new String[]{"name", "value"}, null, null, null, null);
            if (cursor == null) {
                return map;
            }
            while (cursor.moveToNext()) {
                String key = cursor.getString(0);
                String value = cursor.getString(1);
                map.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return map;
    }

}
