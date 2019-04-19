package cc.hyperium.utils.staff;

import cc.hyperium.utils.ChatColor;
import cc.hyperium.utils.StaffUtils;

public class StaffSettings {
    private StaffUtils.DotColour color;

    public StaffSettings(StaffUtils.DotColour colour) {
        if (colour != null)
            this.color = colour;
        else
            this.color = new StaffUtils.DotColour(false, ChatColor.GREEN);
    }

    public StaffUtils.DotColour getColour() {
        return color;
    }
}
