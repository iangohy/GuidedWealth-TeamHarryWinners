package com.iangohy.harrywinnersclientapp.model;

import java.util.Random;

public class Holding {
    private String description;
    private String symbol;
    private Double unitCost;
    private Double quantity;
    private Double unrealisedPnl;
    private Double currentYield;
    private Boolean managed;

    private String assetType = "";
    private final double delta = -10.0 + 20.0 * new Random().nextDouble();

    public Holding() {}

    public Holding(
            String description,
            String symbol,
//            Double unitCost,
            Double quantity,
//            Double unrealisedPnl,
//            Double currentYield,
            Boolean managed
    ) {
        this.description = description;
        this.symbol = symbol;
        this.unitCost = 0.0;
        this.quantity = quantity;
        this.unrealisedPnl = 0.0;
        this.currentYield = 0.0;
        this.managed = managed;
    }

    public String getDescription() {
        return description;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getUnrealisedPnl() {
        return unrealisedPnl;
    }

    public Double getCurrentYield() {
        return currentYield;
    }

    public Boolean getManaged() {
        return managed;
    }

    public String getAssetType() {
        return assetType;
    }

    public double getDelta() {
        return delta;
    }
}
