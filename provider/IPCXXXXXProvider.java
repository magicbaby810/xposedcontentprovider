
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.Nullable;

public class IPCXXXXXProvider extends ContentProvider {

    private final String TAG = this.getClass().getSimpleName();
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String AUTHORITY = "com.sk.provider.IPCXXXXXProvider";  //授权
    public static final Uri XXXXX_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/xxxxx");

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private String mTable;

    private static final int TABLE_CODE_XXXXX = 2;

    static {
        //关联不同的 URI 和 code，便于后续 getType
        mUriMatcher.addURI(AUTHORITY, "xxxxx", TABLE_CODE_XXXXX);
    }

    @Override
    public boolean onCreate() {
        initProvider();
        return true;
    }

    /**
     * 初始化时清除旧数据，插入一条数据
     */
    private void initProvider() {
        mTable = DbOpenHelper.TABLE_NAME;
        mContext = getContext();
        mDatabase = new DbOpenHelper(getContext()).getWritableDatabase();
    }

    /**
     * 初始化时清除旧数据，插入一条数据
     */
    public void initProvider(Context context) {
        mTable = DbOpenHelper.TABLE_NAME;
        mDatabase = new DbOpenHelper(context).getWritableDatabase();
    }

    @Nullable
    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        String tableName = getTableName(uri);
        return mDatabase.query(tableName, projection, selection, selectionArgs, null, sortOrder, null);
    }

    @Nullable
    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        String tableName = getTableName(uri);
        mDatabase.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        String tableName = getTableName(uri);
        int deleteCount = mDatabase.delete(tableName, selection, selectionArgs);
        if (deleteCount > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return deleteCount;
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        String tableName = getTableName(uri);
        int updateCount = mDatabase.update(tableName, values, selection, selectionArgs);
        if (updateCount > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }

    /**
     * CRUD 的参数是 Uri，根据 Uri 获取对应的表名
     *
     * @param uri
     * @return
     */
    private String getTableName(final Uri uri) {
        String tableName = "";
        int match = mUriMatcher.match(uri);
        switch (match){
            case TABLE_CODE_XXXXX:
                tableName = DbOpenHelper.TABLE_NAME;
        }
        return tableName;
    }

    @Nullable
    @Override
    public String getType(final Uri uri) {
        return null;
    }

    private void showLog(final String s) {
    }
}