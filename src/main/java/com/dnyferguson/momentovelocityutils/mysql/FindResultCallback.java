package com.dnyferguson.momentovelocityutils.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FindResultCallback {
    public void onQueryDone(ResultSet result) throws SQLException;
}