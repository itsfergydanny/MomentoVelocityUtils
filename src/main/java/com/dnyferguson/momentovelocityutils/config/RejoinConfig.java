package com.dnyferguson.momentovelocityutils.config;

import java.util.List;

public class RejoinConfig {
    private boolean enabled;
    private List<String> serversExcluded;
    private int interval;
    private int attempts;

    public RejoinConfig() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getServersExcluded() {
        return serversExcluded;
    }

    public void setServersExcluded(List<String> serversExcluded) {
        this.serversExcluded = serversExcluded;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @Override
    public String toString() {
        return "RejoinConfig{" +
                "enabled=" + enabled +
                ", serversExcluded=" + serversExcluded +
                ", interval=" + interval +
                ", attempts=" + attempts +
                '}';
    }
}
