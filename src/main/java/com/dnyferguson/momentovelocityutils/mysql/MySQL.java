package com.dnyferguson.momentovelocityutils.mysql;

import com.dnyferguson.momentovelocityutils.MomentoVelocityUtils;
import com.dnyferguson.momentovelocityutils.config.MySQLConfig;
import com.dnyferguson.momentovelocityutils.utils.Async;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    private final MomentoVelocityUtils plugin;
    private final HikariDataSource ds;

    public MySQL(MomentoVelocityUtils plugin) {
        this.plugin = plugin;
        MySQLConfig config = plugin.getConfig().getMysql();

        HikariConfig hikari = new HikariConfig();
        hikari.setJdbcUrl("jdbc:mysql://" + config.getIp() + ":" + config.getPort() + "/" + config.getDb());
        hikari.setUsername(config.getUser());
        hikari.setPassword(config.getPass());
        hikari.addDataSourceProperty("cachePrepStmts", "true");
        hikari.addDataSourceProperty("prepStmtCacheSize", "250");
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikari.setMaximumPoolSize(config.getMaxConnections());

        ds = new HikariDataSource(hikari);
        createTables(config.getDb());
    }

    private void createTables(String db) {
        executeStatementAsync("");
    }

    public void getResultAsync(String stmt, FindResultCallback callback) {
        Async.schedule(plugin, new Runnable() {
            @Override
            public void run() {
                try (Connection con = ds.getConnection()) {
                    PreparedStatement pst = con.prepareStatement(stmt);
                    ResultSet rs = pst.executeQuery();
                    callback.onQueryDone(rs);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getResultSync(String stmt, FindResultCallback callback) {
        try (Connection con = ds.getConnection()) {
            PreparedStatement pst = con.prepareStatement(stmt);
            ResultSet rs = pst.executeQuery();
            callback.onQueryDone(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeStatementSync(String stmt) {
        try (Connection con = ds.getConnection()) {
            PreparedStatement pst = con.prepareStatement(stmt);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeStatementAsync(String stmt) {
        Async.schedule(plugin, new Runnable() {
            @Override
            public void run() {
                try (Connection con = ds.getConnection()) {
                    PreparedStatement pst = con.prepareStatement(stmt);
                    pst.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public HikariDataSource getDs() {
        return ds;
    }

    public void close() {
        ds.close();
    }
}