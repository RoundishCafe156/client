package cc.hyperium.gui;

import cc.hyperium.Hyperium;
import cc.hyperium.config.Settings;
import cc.hyperium.event.GuiClickEvent;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.RenderHUDEvent;
import cc.hyperium.event.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import javax.annotation.Nullable;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static cc.hyperium.gui.HyperiumGui.clamp;
import static cc.hyperium.gui.HyperiumGui.easeOut;
import static cc.hyperium.gui.HyperiumGui.trimString;

public class NotificationCenter extends Gui {
    private final Queue<Notification> notifications = new LinkedList<>();
    private Notification currentNotification;
    private FontRenderer fontRenderer;

    public NotificationCenter() {}

    @InvokeEvent
    private void tick(TickEvent ev) {
        if (currentNotification == null || currentNotification.tick()) currentNotification = notifications.poll();
    }

    @InvokeEvent
    private void onRenderTick(RenderHUDEvent e) {
        if (currentNotification != null) currentNotification.render();
    }

    @InvokeEvent
    private void onClick(GuiClickEvent event) {
        if (currentNotification != null && currentNotification.clickedCallback != null) {
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final int left = currentNotification.getX(sr);
            final int top = currentNotification.getY(sr);
            final int right = left + currentNotification.width;
            final int bottom = top + currentNotification.height;
            final int mouseX = event.getMouseX();
            final int mouseY = event.getMouseY();

            if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom) currentNotification.clickedCallback.run();
        }
    }

    public Notification display(String title, String description, float seconds) {
        return this.display(title, description, seconds, null, null, null);
    }

    public Notification display(String title, String description, float seconds, @Nullable BufferedImage img, @Nullable Runnable callback, @Nullable Color highlightColor) {
        final Notification notif = new Notification(title, description, (int) (seconds * 20), img, callback, highlightColor);

        try {
            notifications.add(notif);
            return notif;
        } catch (Exception e) {
            Hyperium.LOGGER.error("Can't display notification!", e);
        }

        return null;
    }

    private void setDefaultFontRenderer() {
        if (fontRenderer == null) fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    }

    public class Notification {
        final int width = 175;
        final int bottomMargins = 15;
        final int rightMargins = 5;
        final int topPadding = 5;
        private final int imgSize = 256;
        private final int maxDescriptionLines = 4;
        private final int highlightBarWidth = 5;
        private final int highlightBarMargins = 5;
        private final int lineSpacing = 1;
        int height = 40;
        private String title;
        private String description;
        private int ticksLeft;
        private float percentComplete;
        private int topThreshold;
        private int lowerThreshold;
        private Runnable clickedCallback = null;
        private DynamicTexture img = null;
        private double imgScale = 0.125;
        private Color highlightColor = new Color(149, 201, 144);
        private Color descriptionColor = new Color(80, 80, 80);

        Notification(String title, String description, int ticks, BufferedImage img, Runnable clickedCallback, Color highlightColor) {
            if (title == null) throw new IllegalArgumentException("Title cannot be null!");
            if (description == null) throw new IllegalArgumentException("Description cannot be null!");
            if (ticks <= 0) throw new IllegalArgumentException("Ticks cannot be less than or equal to 0!");

            this.ticksLeft = ticks;

            int fifth = ticks / 5;
            this.topThreshold = ticks - fifth;
            this.lowerThreshold = fifth;
            this.percentComplete = 0.0F;

            setClickedCallback(clickedCallback)
                .setTitle(title)
                .setDescriptionText(description)
                .setHighlightColor(highlightColor)
                .setImage(img);
        }

        Notification setImage(BufferedImage img) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                this.img = img != null ? new DynamicTexture(img) : null;
            });
            return this;
        }

        Notification setClickedCallback(Runnable runnable) {
            this.clickedCallback = runnable;
            return this;
        }

        Notification setTitle(String title) {
            this.title = title;
            return this;
        }

        Notification setHighlightColor(Color highlightColor) {
            this.highlightColor = (highlightColor != null ? highlightColor : new Color(149, 201, 144));
            return this;
        }

        Notification setDescriptionText(String description) {
            this.description = description;
            adjustHeight();
            return this;
        }

        void adjustHeight() {
            setDefaultFontRenderer();
            final int lineCount = fontRenderer.listFormattedStringToWidth(description, getWrapWidth()).size();
            final int totalHeight = (fontRenderer.FONT_HEIGHT + lineSpacing) * (Math.min(maxDescriptionLines, lineCount) + 1) + topPadding;
            if (totalHeight > height) height = totalHeight;
        }

        private int getWrapWidth() {
            final int descRightMargins = (img == null ? rightMargins : rightMargins * 2); // Double right margins if image is there
            // Width that text is permitted to stretch across before wrapping/stopping
            return (int) (width - descRightMargins - imgSize * imgScale - highlightBarMargins - highlightBarWidth);
        }

        boolean tick() {
            this.ticksLeft--;

            return ticksLeft <= 0;
        }

        float updatePercentage() {
            return this.percentComplete = clamp(
                easeOut(
                    this.percentComplete,
                    this.ticksLeft < lowerThreshold ? 0.0f : this.ticksLeft > topThreshold ? 1.0f : ticksLeft,
                    0.01f,
                    8f
                ),
                0.0f,
                1.0f
            );
        }

        int getX(ScaledResolution sr) {
            return (int) (sr.getScaledWidth() - (width * updatePercentage()));
        }

        int getY(ScaledResolution sr) {
            return sr.getScaledHeight() - height - bottomMargins;
        }

        void render() {
            int imgTopMargins = 5;
            if (ticksLeft <= 0)
                return;
            if (!Settings.SHOW_INGAME_NOTIFICATION_CENTER)
                return;
            setDefaultFontRenderer();

            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

            // Update percentage -- Called in getX()
            final int x = getX(sr);
            final int y = getY(sr);

            final int alpha = (int) clamp(this.percentComplete * 255, 127, 255);

            // Background
            Gui.drawRect(x, y, x + width, y + height,
                new Color(30, 30, 30, alpha).getRGB());
            GlStateManager.enableBlend();

            // Highlight color
            setHighlightColor(highlightColor); // Anti-NPE
            drawRect(x, y, x + highlightBarWidth, y + height, highlightColor.getRGB() | alpha << 24);
            GlStateManager.enableBlend();

            // Title Text
            fontRenderer.drawString(trimString(String.valueOf(title), width - rightMargins,
                null, true), x + highlightBarWidth + highlightBarMargins, y + topPadding, 0xFFFFFF | alpha << 24);

            // Description text
            if (descriptionColor == null) descriptionColor = new Color(80, 80, 80);
            // Don't draw if no lines
            final int wrapWidth = getWrapWidth();
            // Trim & split into multiple lines
            List<String> lines = fontRenderer.listFormattedStringToWidth(String.valueOf(description), wrapWidth);
            if (lines.size() > maxDescriptionLines) { // Trim size & last line if overflow
                final String nextLine = lines.get(maxDescriptionLines); // The line that would appear after the last one
                lines = lines.subList(0, maxDescriptionLines);
                // Next line is appended to guarantee three ellipsis on the end of the string
                lines.set(lines.size() - 1, trimString(lines.get(lines.size() - 1) + " " + nextLine, wrapWidth, null, true));
            }

            // Draw lines
            int currentLine = 0;
            for (final String line : lines) {
                fontRenderer.drawString(String.valueOf(line),
                    x + highlightBarWidth + highlightBarMargins,
                    y + topPadding + fontRenderer.FONT_HEIGHT + lineSpacing + fontRenderer.FONT_HEIGHT * currentLine,
                    descriptionColor.getRGB() | alpha << 24);

                if (++currentLine >= maxDescriptionLines) break; // stop if too many lines have gone by
            }

            // Notification Image
            if (img != null) {
                imgScale = (double) (height - topPadding - fontRenderer.FONT_HEIGHT - imgTopMargins) / imgSize;
                if (imgScale * imgSize > (double) width / 4) imgScale = ((double) width / 4) / imgSize; // Limit to 25% of width
                GlStateManager.color(1, 1, 1, 1);
                GlStateManager.scale(imgScale, imgScale, imgScale);
                GlStateManager.bindTexture(img.getGlTextureId());
                GlStateManager.enableTexture2D();
                drawTexturedModalRect(
                    (float) ((x + width - rightMargins) / imgScale - imgSize),
                    (float) (y / imgScale + (((height + fontRenderer.FONT_HEIGHT) / imgScale) - imgSize) / 2),
                    0, 0, imgSize, imgSize);
                GlStateManager.scale(1 / imgScale, 1 / imgScale, 1 / imgScale);
            } else {
                imgScale = 0;
            }
        }
    }
}
