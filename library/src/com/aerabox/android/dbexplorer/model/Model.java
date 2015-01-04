package com.aerabox.android.dbexplorer.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.aerabox.android.dbexplorer.DbExplorer;

public final class Model {

	private static volatile Model sModel;

	public static Model getInstance(Context ctx) {
		if (sModel == null) synchronized (Model.class) {
			if (sModel == null) sModel = new Model(ctx.getApplicationContext());
		}
		return sModel;
	}

	private final Context mApplicationContext;
	private ArrayList<TableInfo> tabelInfos;

	public Model(Context ctx) {
		mApplicationContext = ctx;
	}


	public List<TableInfo> fetchTableInfos() {
		final SQLiteDatabase db = DbExplorer.getConfiguration().getDatabase();
		final ArrayList<TableInfo> arrayList = new ArrayList<TableInfo>();
		// SELECT name FROM schema.sqlite_master WHERE type='table';
		final Cursor c = db.query("sqlite_master", new String[] { "name" }, "type=?", new String[] { "table" }, null,
				null, null);
		while (c.moveToNext()) {
			final String tableName = c.getString(0);
			List<ColumnInfo> columns = fetchColumnInfos(db, tableName);
			arrayList.add(new TableInfo(tableName, columns) {

				@Override
				public List<List<String>> fetchData() {
					return Model.this.fetchData(db, tableName, makeColumnNames(columns));
				}

			});
		}
		c.close();
		setTabelInfos(arrayList);
		return arrayList;
	}

	private List<ColumnInfo> fetchColumnInfos(SQLiteDatabase db, String tableName) {
		final ArrayList<ColumnInfo> list = new ArrayList<ColumnInfo>();
		final Cursor c = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
		final int ci_cid = c.getColumnIndex("cid");
		final int ci_name = c.getColumnIndex("name");
		final int ci_type = c.getColumnIndex("type");
		final int ci_notnull = c.getColumnIndex("notnull");
		final int ci_dflt_value = c.getColumnIndex("dflt_value");
		final int ci_pk = c.getColumnIndex("pk");
		while (c.moveToNext()) {
			final String columnName = c.getString(ci_name);
			final ColumnType columnType = ColumnType.valueOf(c.getString(ci_type));
			list.add(new ColumnInfo(columnName, columnType));
		}
		c.close();
		return list;
	}

	private String[] makeColumnNames(List<ColumnInfo> columns) {
		final String[] names = new String[columns.size()];
		int i = 0;
		for (ColumnInfo columnInfo : columns) {
			names[i] = columnInfo.name;
			i++;
		}
		return names;
	}

	private List<List<String>> fetchData(SQLiteDatabase db, String tableName, String[] columns) {
		final ArrayList<List<String>> list = new ArrayList<List<String>>();
		final Cursor c = db.query(tableName, columns, null, null, null, null, null, null);

		final int length = columns.length;
		while (c.moveToNext()) {
			final ArrayList<String> row = new ArrayList<String>(length);
			for (int i = 0; i < length; i++) {
				row.add(c.getString(i));
			}
			list.add(row);
		}
		c.close();
		return list;
	}

	public Context getApplicationContext() {
		return mApplicationContext;
	}

	public ArrayList<TableInfo> getTabelInfos() {
		return tabelInfos;
	}

	public void setTabelInfos(ArrayList<TableInfo> tabelInfos) {
		this.tabelInfos = tabelInfos;
	}

}
