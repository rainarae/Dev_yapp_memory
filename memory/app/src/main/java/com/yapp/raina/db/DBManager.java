package com.yapp.raina.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yapp.raina.dao.AnniversaryDao;

//	dto를 관리하는 dao들을 static하게 관
public class DBManager {
	private static volatile DBManager instance = null;
	public static DBManager getInstance(Context context) 
	{

		if (instance == null) 
		{
			synchronized (DBManager.class)
			{
				if (instance == null) 
				{
					instance = new DBManager(context);
				}
			}
		}

		return instance;
	}

	private static final String DB_NAME = "yapp.sqlite";
	private static final int DB_VER = 8;

	private DBOpenHelper opener;
	private SQLiteDatabase _db;

	private Context context;

	public AnniversaryDao anniversaryDao = null;


	private DBManager(Context context) 
	{       
		this.context = context;
		this.opener = new DBOpenHelper(context, DB_VER);
		_db = this.opener.getWritableDatabase();
		anniversaryDao = new AnniversaryDao(_db);
	}

	@Override
	protected void finalize() throws Throwable 
	{
		_db.close();

		super.finalize();
	}
}
