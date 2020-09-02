package com.dnyferguson.momentovelocityutils.config;

public class Configuration {
    private boolean test;
    private MySQLConfig mysql;
    private KickingConfig kicking;
    private RejoinConfig rejoin;
    private SlashServerConfig slashserver;

    public Configuration() {}

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public MySQLConfig getMysql() {
        return mysql;
    }

    public void setMysql(MySQLConfig mysql) {
        this.mysql = mysql;
    }

    public KickingConfig getKicking() {
        return kicking;
    }

    public void setKicking(KickingConfig kicking) {
        this.kicking = kicking;
    }

    public RejoinConfig getRejoin() {
        return rejoin;
    }

    public void setRejoin(RejoinConfig rejoin) {
        this.rejoin = rejoin;
    }

    public SlashServerConfig getSlashserver() {
        return slashserver;
    }

    public void setSlashserver(SlashServerConfig slashserver) {
        this.slashserver = slashserver;
    }
}
