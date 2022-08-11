package com.iangohy.harrywinnersclientapp.model;

import android.util.Log;

import java.util.Map;
import java.util.Objects;

public class Recommendation {
    private static final String TAG = "Recommendation";
    private String description;
    private String badgeDescription;
    private double currentValue;
    private double proposedValue;
    private boolean isManaged;
    private boolean toBuy;

    public Recommendation() {}
    public Recommendation(String description, boolean isManaged, double currentValue, double proposedValue, boolean toBuy) {
        this.description = description;
        this.isManaged = isManaged;
        this.currentValue = currentValue;
        this.proposedValue = proposedValue;
        this.toBuy = toBuy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBadgeDescription() {
        return badgeDescription;
    }

    public void setBadgeDescription(String badgeDescription) {
        this.badgeDescription = badgeDescription;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getProposedValue() {
        return proposedValue;
    }

    public void setProposedValue(double proposedValue) {
        this.proposedValue = proposedValue;
    }

    public boolean isManaged() {
        return isManaged;
    }

    public void setManaged(boolean managed) {
        isManaged = managed;
    }

    public static Recommendation convertDocumentToRecommendation(Map<String, Object> doc) {
        String description = doc.get("symbol") + ": " + doc.get("description");
        boolean isManaged = (boolean) doc.get("managed");
        double currentValue = (double) doc.get("quantity");
        // TODO: ACTION CAN BE OTHERS
        boolean toBuy = Objects.equals((String) doc.get("action"), "BUY");
        String input = (String) doc.get("input");
        double proposedValue = input == null || input.length() == 0 ? 0d : Double.parseDouble(input);
        return new Recommendation(description, isManaged, currentValue, proposedValue, toBuy);
    }

    public boolean isToBuy() {
        return toBuy;
    }

    public void setToBuy(boolean toBuy) {
        this.toBuy = toBuy;
    }

    public static class RecommendationBankerContact {
        private String name;
        private String email;
        private String officeNumber;
        private String phoneNumber;
        private String telegramHandle;

        public RecommendationBankerContact() {}
        public RecommendationBankerContact(Map<String, Object> doc) {
            Log.d("model", String.valueOf(doc));
            this.name = doc.get("name") == null ? "Jane Flyer" : (String) doc.get("name");
            this.email = (String) doc.get("email");
            this.officeNumber = String.valueOf(doc.get("office"));
            this.phoneNumber = String.valueOf(doc.get("phone"));
            this.telegramHandle = (String) doc.get("telegram");
        }

        public String getEmail() {
            return email;
        }

        public String getOfficeNumber() {
            return officeNumber;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getTelegramHandle() {
            return telegramHandle;
        }

        public String getName() {
            return name;
        }
    }
}
