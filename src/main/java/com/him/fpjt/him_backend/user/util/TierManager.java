package com.him.fpjt.him_backend.user.util;

import com.him.fpjt.him_backend.user.domain.Tier;

public class TierManager {
    public static Tier getNextTier(Tier currentTier) {
        switch (currentTier) {
            case IRON: return Tier.BRONZE;
            case BRONZE: return Tier.SILVER;
            case SILVER: return Tier.GOLD;
            case GOLD: return Tier.PLATINUM;
            case PLATINUM: return Tier.EMERALD;
            case EMERALD: return Tier.DIAMOND;
            case DIAMOND: return Tier.MASTER;
            case MASTER: return Tier.LEGEND;
            case LEGEND: return Tier.GOAT;
            default: return null;
        }
    }
    public static int getRequiredExpForNextTier(Tier currentTier) {
        switch (currentTier) {
            case IRON: return 1000;
            case BRONZE: return 2000;
            case SILVER: return 3000;
            case GOLD: return 4000;
            case PLATINUM: return 5000;
            case EMERALD: return 6000;
            case DIAMOND: return 8000;
            case MASTER: return 9000;
            case LEGEND: return 10000;
            default: return 0;
        }
    }
}
