package com.yapp.raina.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Raina on 16. 2. 27..
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    static CursorFactory factory;
    private final String TABLE_NAME = "anniversary_tb";
    private static final String DB_PATH = "/data/data/com.yapp.raina.memory/databases/";
    private static String DB_NAME = "yapp.sqlite";


    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
        try {
            boolean bResult = isCheckDB(context);  // DB가 있는지?
            if (!bResult) {   // DB가 없으면 복사
                copyDB(context);
            } else {

            }
        } catch (Exception e) {

        }
    }



    // DB가 있나 체크하기
    public boolean isCheckDB(Context mContext) {
        String filePath = DB_PATH + DB_NAME;
        File file = new File(filePath);

        if (file.exists()) {
            Log.d("exist", "exist");
            return true;
        }

        return false;

    }

    // DB를 복사하기
// assets의 /db/xxxx.db 파일을 설치된 프로그램의 내부 DB공간으로 복사하기
    public void copyDB(Context mContext) {
        AssetManager manager = mContext.getAssets();
        String folderPath = DB_PATH;
        String filePath = DB_PATH + DB_NAME;
        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            InputStream is = manager.open(DB_NAME);
            BufferedInputStream bis = new BufferedInputStream(is);

            if (folder.exists()) {
            } else {
                folder.mkdirs();
            }


            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, read);
            }

            bos.flush();

            bos.close();
            fos.close();
            bis.close();
            is.close();

        } catch (IOException e) {
            Log.e("ErrorMessage : ", e.getMessage());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
