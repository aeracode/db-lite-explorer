package com.aerabox.android.dbexplorer.model;

import java.util.List;

public abstract class TableInfo {

	public final String name;
	public final List<ColumnInfo> columns;

	public TableInfo(String name, List<ColumnInfo> columns) {
		super();
		this.name = name;
		this.columns = columns;
	}

	@Override
	public String toString() {
		return name;
	}
	
	abstract public List<List<String>> fetchData();


}
