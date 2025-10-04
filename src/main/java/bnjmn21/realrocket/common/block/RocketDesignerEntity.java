package bnjmn21.realrocket.common.block;

import bnjmn21.realrocket.api.gui.GuiBuilder;
import com.lowdragmc.lowdraglib.gui.factory.BlockEntityUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.IUIHolder;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.syncdata.IManaged;
import com.lowdragmc.lowdraglib.syncdata.IManagedStorage;
import com.lowdragmc.lowdraglib.syncdata.blockentity.IAsyncAutoSyncBlockEntity;
import com.lowdragmc.lowdraglib.syncdata.blockentity.IAutoPersistBlockEntity;
import com.lowdragmc.lowdraglib.syncdata.field.FieldManagedStorage;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
    static final MutableComponent TO = REGISTRATE.addPrefixedLang("from", "To: ");

    @Override
    public ModularUI createUI(Player entityPlayer) {
        GuiBuilder ui = GuiBuilder.create(200, 200);

        ui.label(Component.translatable("block.realrocket.rocket_designer"));
        ui.pad();
        ui.hStack(stack -> {
            stack.label(FROM);
        });

        return ui.build(this, entityPlayer);
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
}
