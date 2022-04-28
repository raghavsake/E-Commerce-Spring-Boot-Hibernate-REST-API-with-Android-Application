package com.project.ecommerce.utils;

public class FlagsManager {

    private static FlagsManager instance = null;

    private boolean isActivityRunning = false;

    public static FlagsManager getInstance() {
        if (instance == null)
            instance = new FlagsManager();

        return instance;
    }


    public boolean isActivityRunning() {
        return isActivityRunning;
    }

    public void setActivityRunning(boolean activityRunning) {
        isActivityRunning = activityRunning;
    }
}
