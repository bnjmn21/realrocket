package bnjmn21.realrocket.common.data;

import bnjmn21.realrocket.client.entity_renderer.RocketEntityRenderer;
import bnjmn21.realrocket.common.entity.RocketEntity;
import bnjmn21.realrocket.common.entity.SeatEntity;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.world.entity.MobCategory;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class RREntities {
    public static EntityEntry<SeatEntity> SEAT = REGISTRATE.<SeatEntity>entity("seat", SeatEntity::new, MobCategory.MISC)
            .properties(b -> b.setTrackingRange(5)
                    .setUpdateInterval(Integer.MAX_VALUE)
                    .setShouldReceiveVelocityUpdates(false)
                    .fireImmune()
                    .sized(0.25f, 0.35f)
            )
            .renderer(() -> SeatEntity.Render::new)
            .lang("Seat")
            .register();

    public static EntityEntry<RocketEntity> ROCKET = REGISTRATE.entity("rocket", RocketEntity::new, MobCategory.MISC)
            .properties(b -> b.fireImmune()
                    .clientTrackingRange(8)
                    .updateInterval(3)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(1, 1))
            .renderer(() -> RocketEntityRenderer::new)
            .lang("Rocket")
            .register();

    public static void init() {}
}
