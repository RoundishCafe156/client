package cc.hyperium.cosmetics;

import cc.hyperium.Hyperium;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.PurchaseLoadEvent;
import cc.hyperium.event.WorldChangeEvent;
import cc.hyperium.mods.sk1ercommon.Multithreading;
import cc.hyperium.purchases.EnumPurchaseType;
import cc.hyperium.purchases.PurchaseApi;
import cc.hyperium.utils.UUIDUtil;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractCosmetic {
    private final EnumPurchaseType purchaseType;
    private final Map<UUID, Boolean> purchasedBy = new ConcurrentHashMap<>();
    private boolean selfUnlocked;

    public AbstractCosmetic(EnumPurchaseType purchaseType) {
        this.purchaseType = purchaseType;
        try {
            PurchaseApi.getInstance().getPackageAsync(UUIDUtil.getClientUUID(), hyperiumPurchase -> {
                if (Hyperium.INSTANCE.isDevEnv) return;
                if (hyperiumPurchase == null) return;
                selfUnlocked = hyperiumPurchase.hasPurchased(purchaseType);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @InvokeEvent
    public void worldSwitch(WorldChangeEvent changeEvent) {
        UUID id = UUIDUtil.getClientUUID();
        if (id == null) return;
        Boolean aBoolean = purchasedBy.get(id);
        purchasedBy.clear();
        if (aBoolean != null) purchasedBy.put(id, aBoolean);
    }

    @InvokeEvent
    public void purchaseLoadEvent(PurchaseLoadEvent event) {
        purchasedBy.put(event.getUuid(), event.getPurchase().hasPurchased(purchaseType));
    }

    public boolean isPurchasedBy(UUID uuid) {
        if (purchasedBy.containsKey(uuid)) {
            return purchasedBy.get(uuid);
        } else {
            purchasedBy.put(uuid, false);
            Multithreading.runAsync(() -> purchasedBy.put(uuid, PurchaseApi.getInstance().getPackageSync(uuid).hasPurchased(purchaseType)));
            return false;
        }
    }

    EnumPurchaseType getPurchaseType() {
        return purchaseType;
    }

    public boolean isSelfUnlocked() {
        return selfUnlocked;
    }

    float interpolate(final float yaw1, final float yaw2, final float percent) {
        float f = (yaw1 + (yaw2 - yaw1) * percent) % 360.0f;
        if (f < 0.0f)  f += 360.0f;
        return f;
    }
}
