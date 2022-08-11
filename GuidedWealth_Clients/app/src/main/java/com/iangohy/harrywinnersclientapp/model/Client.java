package com.iangohy.harrywinnersclientapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client {

    private static final Random random = new Random();
    enum ClientType { GOLD, PLATINUM };

    private Double randDoubleRange(Double min, Double max) {
        return min + (max - min) * random.nextDouble();
    }

    private String name;
    private Integer contactNumber = 9000000;
    private String telegramHandle;

    private ClientType clientType = ClientType.GOLD;
    private String bankerAttached;

    private Double recurringIncome = randDoubleRange(100000.0, 200000.0);
    private Double overallPnl = randDoubleRange(-10000.0, 10000.0);
    private Double totalAssetIncome = randDoubleRange(50000.0, 90000.0);
    private Double totalIncome = randDoubleRange(5000.0, 12000.0);
    private Double capital = randDoubleRange(50000.0, 75000.0);

    public Client() {}

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getContactNumber() {
        return contactNumber;
    }

    public String getTelegramHandle() {
        return telegramHandle;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public String getBankerAttached() {
        return bankerAttached;
    }

    public Double getRecurringIncome() {
        return recurringIncome;
    }

    public Double getOverallPnl() {
        return overallPnl;
    }

    public Double getTotalAssetIncome() {
        return totalAssetIncome;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public Double getCapital() {
        return capital;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", contactNumber=" + contactNumber +
                ", telegramHandle='" + telegramHandle + '\'' +
                ", clientType=" + clientType +
                ", bankerAttached=" + bankerAttached +
                ", recurringIncome=" + recurringIncome +
                ", overallPnl=" + overallPnl +
                ", totalAssetIncome=" + totalAssetIncome +
                ", totalIncome=" + totalIncome +
                '}';
    }
}
