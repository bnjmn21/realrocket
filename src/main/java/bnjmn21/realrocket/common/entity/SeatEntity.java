package bnjmn21.realrocket.common.entity;

import bnjmn21.realrocket.common.block.SeatBlock;
import bnjmn21.realrocket.common.data.RREntities;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SeatEntity extends Entity implements IEntityAdditionalSpawnData {

    public static double RIDE_HEIGHT = 5.0 / 16.0;

    public SeatEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public SeatEntity(Level world) {
        this(RREntities.SEAT.get(), world);
        noPhysics = true;
    }

    @Override
    public void setPos(double x, double y, double z) {
        super.setPos(x, y, z);
        AABB bb = getBoundingBox();
        Vec3 diff = new Vec3(x, y, z).subtract(bb.getCenter());
        setBoundingBox(bb.move(diff));
    }

    @Override
    protected void positionRider(Entity pEntity, Entity.MoveFunction pCallback) {
        if (!this.hasPassenger(pEntity))
            return;
        double d0 = this.getY() + pEntity.getMyRidingOffset();
        pCallback.accept(pEntity, this.getX(), d0 + RIDE_HEIGHT - 0.25, this.getZ());
    }

    @Override
    public void setDeltaMovement(Vec3 p_213317_1_) {}

    @Override
    public void tick() {
        if (level().isClientSide)
            return;
        boolean blockPresent = level().getBlockState(blockPosition())
                .getBlock() instanceof SeatBlock;
        if (isVehicle() && blockPresent)
            return;
        this.discard();
    }

    @Override
    protected boolean canRide(Entity entity) {
        // Fake Players (tested with deployers) have a BUNCH of weird issues, don't let
        // them ride seats
        return !(entity instanceof FakePlayer);
    }

    @Override
    protected void removePassenger(Entity entity) {
        super.removePassenger(entity);
        if (entity instanceof TamableAnimal ta)
            ta.setInSittingPose(false);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        return super.getDismountLocationForPassenger(pLivingEntity).add(0, RIDE_HEIGHT, 0);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag p_70037_1_) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag p_213281_1_) {}

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static class Render extends EntityRenderer<SeatEntity> {

        public Render(EntityRendererProvider.Context context) {
            super(context);
        }

        @Override
        public boolean shouldRender(SeatEntity p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_,
                                    double p_225626_7_) {
            return false;
        }

        @Override
        public ResourceLocation getTextureLocation(SeatEntity p_110775_1_) {
            // noinspection DataFlowIssue
            return null;
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {}

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {}
}
