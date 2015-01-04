package com.aerabox.dbexplorer.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestSqlDb extends SQLiteOpenHelper {

	private static final int VERSION = 1;
	private Context mContext;

	public TestSqlDb(Context context) {
		super(context, "test db explorer db", null, VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			runSqlScript(db, "data");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void runSqlScript(SQLiteDatabase db, final String assetfileName) throws IOException {
		final InputStream file = mContext.getAssets().open(assetfileName);
		final String sql = read(file);
		String[] lines = sql.split(";(\\s)*[\n\r]");
		for (String statement : lines) {
			db.execSQL(statement);
		}
	}

	public static String read(InputStream in) throws IOException {
		final StringBuilder sb = new StringBuilder();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			sb.append('\n');
		}
		return sb.toString();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
