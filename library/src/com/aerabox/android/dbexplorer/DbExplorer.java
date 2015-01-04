package com.aerabox.android.dbexplorer;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

/**
 * Your application should implement this interface.
 * 
 * @author Aera
 */
public class DbExplorer {

	public static void start(Context applicationContext) {
		Intent intent = new Intent(applicationContext, TableInfoListActivity.class);
		applicationContext.startActivity(intent);
	}

	public interface Configuration {

		SQLiteDatabase getDatabase();

	}

	private static Configuration sConfiguration;

	public static synchronized void setConfiguration(Configuration cfg) {
		sConfiguration = cfg;
	}

	public static Configuration getConfiguration() {
		return sConfiguration;
	}

}
