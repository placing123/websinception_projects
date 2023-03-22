
package com.mw.fantasy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("TotalRecords")
    @Expose
    private String totalRecords;
    @SerializedName("SubscriptionsStatus")
    @Expose
    private Boolean subscriptionsStatus;
    @SerializedName("subscription")
    @Expose
    private Subscription subscription;
    @SerializedName("SubscriptionEnd")
    @Expose
    private String subscriptionEnd;

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Boolean getSubscriptionsStatus() {
        return subscriptionsStatus;
    }

    public void setSubscriptionsStatus(Boolean subscriptionsStatus) {
        this.subscriptionsStatus = subscriptionsStatus;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public String getSubscriptionEnd() {
        return subscriptionEnd;
    }

    public void setSubscriptionEnd(String subscriptionEnd) {
        this.subscriptionEnd = subscriptionEnd;
    }

}
