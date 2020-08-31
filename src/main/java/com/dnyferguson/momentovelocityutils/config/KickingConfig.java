package com.dnyferguson.momentovelocityutils.config;

import java.util.List;

public class KickingConfig {
    private List<String> reasons;

    public KickingConfig() {
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public String listReasons() {
        StringBuilder str = new StringBuilder("Reasons: ");
        for (String reason : reasons) {
            str.append(reason).append(", ");
        }
        return str.toString();
    }
}
