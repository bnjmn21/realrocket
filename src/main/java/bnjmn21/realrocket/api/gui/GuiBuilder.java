package bnjmn21.realrocket.api.gui;

import com.lowdragmc.lowdraglib.gui.editor.ColorPattern;
import com.lowdragmc.lowdraglib.gui.modular.IUIHolder;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.ResourceBorderTexture;
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.gui.widget.layout.Layout;
import com.lowdragmc.lowdraglib.utils.Size;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Consumer;

/**
 * A wrapper around ldlib to make gui creation easier
 */
@SuppressWarnings("UnusedReturnValue")
public class GuiBuilder {
    public WidgetGroup inner;

    public GuiBuilder(WidgetGroup inner) {
        this.inner = inner;
    }

    public static GuiBuilder create(int width, int height) {
        WidgetGroup inner = new WidgetGroup();
        inner.setSize(width, height);
        inner.setLayout(Layout.VERTICAL_LEFT);
        return new GuiBuilder(inner);
    }

    public WidgetGroup stack(Layout layout, Consumer<GuiBuilder> children) {
        WidgetGroup stack = new WidgetGroup();
        stack.setLayout(layout);
        stack.setDynamicSized(true);
        GuiBuilder builder = new GuiBuilder(stack);
        children.accept(builder);
        return this.add(builder.inner);
    }

    public WidgetGroup vStack(Consumer<GuiBuilder> children) {
        return stack(Layout.VERTICAL_LEFT, children);
    }

    public WidgetGroup hStack(Consumer<GuiBuilder> children) {
        return stack(Layout.HORIZONTAL_CENTER, children);
    }

    public LabelWidget label(Component text) {
        LabelWidget wgt = new LabelWidget();
        wgt.setComponent(text);
        return this.add(wgt);
    }

    public LabelWidget label(String text) {
        LabelWidget wgt = new LabelWidget();
        wgt.setText(text);
        return this.add(wgt);
    }

    public ButtonWidget button(Component text) {
        return button(text.toString());
    }

    public ButtonWidget button(String text) {
        ButtonWidget wgt = new ButtonWidget();
        TextTexture label = new TextTexture(text);
        ResourceTexture clickedTexture = ResourceBorderTexture.BUTTON_COMMON.copy().setColor(ColorPattern.CYAN.color);
        wgt.setButtonTexture(ResourceBorderTexture.BUTTON_COMMON, label);
        wgt.setClickedTexture(clickedTexture, label);
        return this.add(wgt);
    }

    public WidgetGroup pad() {
        return this.pad(5, 5);
    }

    public WidgetGroup pad(int vertical, int horizontal) {
        WidgetGroup stack = new WidgetGroup();
        stack.setSize(horizontal, vertical);
        return this.add(stack);
    }

    public SelectorWidget select(int width, List<String> candidates, String selected) {
        SelectorWidget wgt = new SelectorWidget();
        wgt.setSizeWidth(width);
        wgt.setButtonBackground(ColorPattern.GRAY.rectTexture());
        wgt.setCandidates(candidates);
        wgt.setValue(selected);
        return this.add(wgt);
    }

    public PhantomSlotWidget phantomSlot() {
        PhantomSlotWidget wgt = new PhantomSlotWidget();
        wgt.setCanPutItems(false);
        wgt.setCanTakeItems(false);
        wgt.setClearSlotOnRightClick(false);
        return this.add(wgt);
    }

    public <T extends Widget> T add(T widget) {
        this.inner.addWidget(widget);
        return widget;
    }

    public ModularUI build(IUIHolder holder, Player entityPlayer) {
        WidgetGroup wrapper = new WidgetGroup();
        Size size = this.inner.getSize();
        wrapper.setSize(size);
        this.inner.setSize(size.width - 10, size.height - 10);
        this.inner.setSelfPosition(5, 5);
        wrapper.setBackground(ResourceBorderTexture.BORDERED_BACKGROUND);
        wrapper.addWidget(this.inner);
        return new ModularUI(wrapper, holder, entityPlayer);
    }
}
