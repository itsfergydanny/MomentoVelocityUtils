package com.dnyferguson.momentovelocityutils.config;

public class MySQLConfig {
    private String ip;
    private String port;
    private String user;
    private String pass;
    private String db;
    private int maxConnections;

    public MySQLConfig() {}

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    @Override
    public String toString() {
        return "MySQLConfig{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                ", db='" + db + '\'' +
                ", maxConnections=" + maxConnections +
                '}';
    }
}
