package bnjmn21.realrocket.common.block;

import bnjmn21.realrocket.api.RRRegistrate;
import bnjmn21.realrocket.api.celestial_body.VirtualLevelKey;
import bnjmn21.realrocket.api.celestial_body.VirtualLevels;
import bnjmn21.realrocket.api.gui.GuiBuilder;
import bnjmn21.realrocket.api.rocket.FlightTarget;
import bnjmn21.realrocket.api.rocket.RocketLogic;
import com.lowdragmc.lowdraglib.gui.factory.BlockEntityUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.IUIHolder;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.SelectorWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.IManaged;
import com.lowdragmc.lowdraglib.syncdata.IManagedStorage;
import com.lowdragmc.lowdraglib.syncdata.blockentity.IAsyncAutoSyncBlockEntity;
import com.lowdragmc.lowdraglib.syncdata.blockentity.IAutoPersistBlockEntity;
import com.lowdragmc.lowdraglib.syncdata.field.FieldManagedStorage;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class RocketDesignerEntity extends BlockEntity implements IAsyncAutoSyncBlockEntity, IAutoPersistBlockEntity, IManaged, IUIHolder.BlockEntityUI {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(RocketDesignerEntity.class);
    private final FieldManagedStorage syncStorage = new FieldManagedStorage(this);

    public RocketDesignerEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    static {
        REGISTRATE.setLangPrefix("realrocket.rocket_designer");
    }

    static final MutableComponent FROM = REGISTRATE.addPrefixedLang("from", "From: ");
    static final MutableComponent TO = REGISTRATE.addPrefixedLang("to", "To: ");
    static final RRRegistrate.Translatable PLANET_TIER = REGISTRATE.addPrefixedLangTemplate("planet_tier", " (Planet Tier: %s)", c -> c.withStyle(ChatFormatting.GRAY));
    static final RRRegistrate.Translatable ROCKET_TIER = REGISTRATE.addPrefixedLangTemplate("rocket_tier", "Rocket Tier: %s", c -> c);
    static final MutableComponent TIER_DEBUFF = REGISTRATE.addPrefixedLang("tier_debuff", "Due to needing to travel through %s's atmosphere, the required rocket tier is increased by 1");
    static final MutableComponent SELECT_DIFF_DESTINATION = REGISTRATE.addPrefixedLang("select_diff_destination", "Select different destination").withStyle(ChatFormatting.RED);
    static final MutableComponent REQUIRED_ROCKET = REGISTRATE.addPrefixedLang("required_rocket", "Required rocket:");

    @Override
    public ModularUI createUI(Player player) {
        GuiBuilder ui = GuiBuilder.create(200, 200);

        final AtomicReference<VirtualLevelKey> from = new AtomicReference<>(defaultLevel(player));
        final AtomicReference<VirtualLevelKey> to = new AtomicReference<>(defaultLevel(player));
        final AtomicReference<WidgetGroup> resultGui = new AtomicReference<>(null);

        ui.label(Component.translatable("block.realrocket.rocket_designer"));
        ui.pad();
        ui.hStack(stack -> {
            stack.label(FROM);
            levelSelector(stack, player, from.get(), newLvl -> {
                from.set(newLvl);
                rerenderResultGui(player.level(), resultGui.get(), from.get(), to.get());
            });
        });
        ui.pad();
        ui.hStack(stack -> {
            stack.label(TO);
            levelSelector(stack, player, to.get(), newLvl -> {
                to.set(newLvl);
                rerenderResultGui(player.level(), resultGui.get(), from.get(), to.get());
            });
        });
        ui.pad();
        resultGui.set(ui.vStack(stack -> {}));
        rerenderResultGui(player.level(), resultGui.get(), from.get(), to.get());

        return ui.build(this, player);
    }

    static void rerenderResultGui(Level level, WidgetGroup resultGui, VirtualLevelKey from, VirtualLevelKey to) {
        resultGui.clearAllWidgets();
        GuiBuilder ui = new GuiBuilder(resultGui);
        FlightTarget target = new FlightTarget.VirtualLevel(to);
        int tier = RocketLogic.requiredRocketTier(level, from, target);
        if (tier == 0) {
            ui.label(SELECT_DIFF_DESTINATION);
            return;
        }
        if (RocketLogic.hasTierDebuff(level, from)) {
            ui.hStack(stack -> {
                ui.label(ROCKET_TIER.apply(tier));
                ui.label(PLANET_TIER.apply(from.name()));
            }).appendHoverTooltips(TIER_DEBUFF);
        } else {
            ui.label(ROCKET_TIER.apply(tier));
        }

        ui.label(ROCKET_TIER.apply(tier));
        ui.pad();
        ui.hStack(stack -> {
            ui.label(REQUIRED_ROCKET);
            ui.pad();
            ui.phantomSlot();
        });
    }

    static void levelSelector(GuiBuilder ui, Player player, VirtualLevelKey selected, Consumer<VirtualLevelKey> update) {
        Set<VirtualLevelKey> discoveredLevels = VirtualLevels.discoveredLevels(player);
        SelectorWidget selectorWidget = ui.select(
                100,
                discoveredLevels.stream().map(lvl -> lvl.name().getString()).toList(),
                selected.name().getString()
        );
        selectorWidget.setOnChanged(newStr -> {
            VirtualLevelKey result = discoveredLevels.stream()
                    .filter(lvl -> lvl.name().getString().equals(newStr))
                    .findAny().orElseThrow();
            update.accept(result);
        });
    }

    static VirtualLevelKey defaultLevel(Player player) {
        return VirtualLevels.getLevelAt(player).orElseGet(VirtualLevels::overworld);
    }

    public void onPlayerUse(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            BlockEntityUIFactory.INSTANCE.openUI(this, serverPlayer);
        }
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public IManagedStorage getSyncStorage() {
        return syncStorage;
    }

    @Override
    public void onChanged() {
        setChanged();
    }

    @Override
    public IManagedStorage getRootStorage() {
        return getSyncStorage();
    }

    public static void init() {}
}
