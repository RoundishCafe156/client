package cc.hyperium.mods.autogg.config;

import cc.hyperium.Hyperium;
import cc.hyperium.config.Category;
import cc.hyperium.config.ConfigOpt;
import cc.hyperium.config.SliderSetting;
import cc.hyperium.config.ToggleSetting;
import static cc.hyperium.config.Category.AUTO_GG;

public class AutoGGConfig {
    @ConfigOpt
    @ToggleSetting(name = "Hide GG's at end of game", mods = true, category = AUTO_GG)
    public boolean ANTI_GG = false;
    @ConfigOpt
    @SliderSetting(name = "Delay", min = 0, max = 5, round = true, isInt = true, category = Category.AUTO_GG, mods = true)
    public int delay = 1;
    @ConfigOpt
    @ToggleSetting(name = "Enable", mods = true, category = Category.AUTO_GG)
    public boolean toggled = true;
    @ConfigOpt
    @ToggleSetting(name = "Say Good Game instead of GG", mods = true, category = AUTO_GG)
    public boolean sayGoodGameInsteadOfGG = false;
    @ConfigOpt
    @ToggleSetting(name = "Say Lowercase", mods = true, category = AUTO_GG)
    public boolean lowercase = false;

    public AutoGGConfig() {
        Hyperium.CONFIG.register(this);
    }

    public int getDelay() {
        return this.delay < 0 ? 1 : this.delay > 5 ? 1 : this.delay;
    }

    public void setDelay(int delay) {
        if (delay >= 0 && this.delay <= 5) {
            this.delay = delay;
        }
    }

    public void flipToggle() {
        this.toggled = !this.toggled;
    }

    public boolean isToggled() {
        return this.toggled;
    }
}
