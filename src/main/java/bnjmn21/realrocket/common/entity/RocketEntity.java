package bnjmn21.realrocket.common.entity;

import bnjmn21.realrocket.api.gui.EntityUIFactory;
import bnjmn21.realrocket.api.gui.GuiBuilder;
import bnjmn21.realrocket.common.data.RREntityDataSerializers;
import bnjmn21.realrocket.util.serialization.GetName;
import com.lowdragmc.lowdraglib.gui.modular.IUIHolder;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.ParametersAreNonnullByDefault;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RocketEntity extends Entity implements HasCustomInventoryScreen, IUIHolder, PlayerRideable, IEntityAdditionalSpawnData {
    public static final EntityDataAccessor<FlightPhase> FLIGHT_PHASE = SynchedEntityData.defineId(RocketEntity.class, RREntityDataSerializers.FLIGHT_PHASE);
    public static final EntityDataAccessor<BlockPos> AABB_START = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BLOCK_POS);
    public static final EntityDataAccessor<BlockPos> AABB_END = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BLOCK_POS);

    public RocketEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.entityData.get(FLIGHT_PHASE) == FlightPhase.LAUNCHING) {
            Vec3 mov = getDeltaMovement();
            setDeltaMovement(mov.x, Math.min(mov.y + 0.003, 4), mov.z);
        }
        this.move(MoverType.SELF, getDeltaMovement());
    }

    @Override
    protected AABB makeBoundingBox() {
        Vec3 pos = this.position();
        BlockPos start = this.entityData.get(AABB_START);
        BlockPos end = this.entityData.get(AABB_END);
        return new AABB(
                pos.x + start.getX(), pos.y + start.getY(), pos.z + start.getZ(),
                pos.x + end.getX(), pos.y + end.getY(), pos.z + end.getZ()
        );
    }

    @Override
    public void refreshDimensions() {
        Vec3 pos = this.position();
        super.refreshDimensions();
        this.setPos(pos);
    }


    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        super.interact(player, hand);
        InteractionResult result = InteractionResult.sidedSuccess(this.level().isClientSide);

        if (!this.level().isClientSide) {
            if (player.isSecondaryUseActive()) {
                this.openCustomInventoryScreen(player);
                return InteractionResult.CONSUME;
            }

//            player.startRiding(this);
            return result;
        }

        return result;
    }

    static {
        REGISTRATE.setLangPrefix("realrocket.rocket");
    }

    @Override
    public ModularUI createUI(Player player) {
        GuiBuilder ui = GuiBuilder.create(200, 200);
        ui.button("Launch!").setOnPressCallback(click -> {
            if (this.isRemote()) return;
            SynchedEntityData data = this.getEntityData();
            data.set(FLIGHT_PHASE, FlightPhase.LAUNCHING);
        });
        return ui.build(this, player);
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            EntityUIFactory.INSTANCE.openUI(this, serverPlayer);
        }
    }

    public enum FlightPhase implements GetName {
        STOPPED("stopped"),
        LAUNCHING("launching"),
        LANDING("landing");

        final String name;

        FlightPhase(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void push(Entity entity) {}

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(FLIGHT_PHASE, FlightPhase.STOPPED);
        this.entityData.define(AABB_START, new BlockPos(0, 0, 0));
        this.entityData.define(AABB_END, new BlockPos(1, 1, 1));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.entityData.set(FLIGHT_PHASE, GetName.fromName(FlightPhase.class, compound.getString("flightPhase")));
        this.entityData.set(AABB_START, NbtUtils.readBlockPos(compound.getCompound("aabbStart")));
        this.entityData.set(AABB_END, NbtUtils.readBlockPos(compound.getCompound("aabbEnd")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putString("flightPhase", this.entityData.get(FLIGHT_PHASE).getName());
        compound.put("aabbStart", NbtUtils.writeBlockPos(this.entityData.get(AABB_START)));
        compound.put("aabbEnd", NbtUtils.writeBlockPos(this.entityData.get(AABB_END)));
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf arg) {

    }

    @Override
    public void readSpawnData(FriendlyByteBuf arg) {

    }


    @Override
    public boolean isInvalid() {
        return this.isRemoved();
    }

    @Override
    public boolean isRemote() {
        return this.level().isClientSide();
    }

    @Override
    public void markAsDirty() {}
}
