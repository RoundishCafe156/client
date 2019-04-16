package cc.hyperium.purchases.packages;

import cc.hyperium.purchases.AbstractHyperiumPurchase;
import cc.hyperium.purchases.EnumPurchaseType;
import cc.hyperium.utils.JsonHolder;

public class DabOnKill extends AbstractHyperiumPurchase {
    public DabOnKill(EnumPurchaseType type, JsonHolder data) {
        super(type, data);
    }

    public int getDuration() {
        return getData().optInt("duration", 3);
    }
}

