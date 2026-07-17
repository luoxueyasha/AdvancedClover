package com.roseyasa.advanced_clover.event;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.registry.ComponentRegister;
import com.roseyasa.advanced_clover.utils.MagicCloverHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityProcessor;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.roseyasa.advanced_clover.Main.MODID;
import static com.roseyasa.advanced_clover.registry.SoundRegister.SOUND_CLOVER_FAIL_ID;
import static com.roseyasa.advanced_clover.utils.MagicCloverConfig.MOB_SPAWN_CHANCE;
import static com.roseyasa.advanced_clover.utils.MagicCloverHandler.*;
import static net.minecraft.world.item.Items.ENDER_PEARL;

public class MagicCloverEvent extends Event implements ICancellableEvent {
    private final @Nullable Player player;
    private final Level level;
    private boolean isSuccess = false;
    private final ItemStack cloverStack;
    public static final int MAX_CHANCE = 1000;

    public MagicCloverEvent(@Nullable Player player, Level level, ItemStack cloverStack){
        super();
        this.player = player;
        this.level = level;
        this.cloverStack = cloverStack;
        execute();
    }

    public void execute() {
        if (player == null) {
            this.isSuccess = false;
            return;
        }

        if(level.isClientSide()) {
            return;
        }
        // @debug, todo: 对物品的component解析（或许不用解析，直接套上去）

        // 如果设置了itemstack独立的chance且大于0，则不应用全局chance抽生物；只随机抽设置好的生物
        int mob_chance = cloverStack.get(ComponentRegister.ENTITY_TYPE).chance();
        List<String> mob_type_list = cloverStack.get(ComponentRegister.ENTITY_TYPE).entity_list();
        EntityType entityType;
        CompoundTag compoundTag = null;
        if(mob_type_list != null ) {
            String selected_mob = mob_type_list.get(level.getRandom().nextInt(mob_type_list.size()));
            entityType = parseEntityType(selected_mob);
            compoundTag = parseNbtFromString(selected_mob);
        } else {
            entityType = getRandomEntity(level, cloverStack);
        }

        if(mob_chance < 0) {
            // fallback到全局chance
            mob_chance = MOB_SPAWN_CHANCE.get();
        }

        if (mob_chance == MAX_CHANCE || mob_chance > level.getRandom().nextInt(MAX_CHANCE)) {
            if (entityType == null) {
                failOnExecute(player);
                return;
            }
            boolean success = createRandomEntity(level, player, entityType, compoundTag);
            if (!success) {
                failOnExecute(player);
            }
            return;
        }

        // 如果没抽到怪物，则做正常的生成物品
        ItemStack itemStack = generateRandomItem(level, cloverStack);
        if(itemStack == null){
            failOnExecute(player);
            return;
        } else {
            this.isSuccess = true;
        }
        player.drop(itemStack, false);

    }

    private void failOnExecute(Player player){
        this.isSuccess = false;
        ((ServerPlayer) player).connection.send(
            new ClientboundCustomPayloadPacket(new SoundEffectPayload(SOUND_CLOVER_FAIL_ID))
        );
        ItemStack itemStack = RandomFallback();
        player.drop(itemStack, false);

    }

    public record SoundEffectPayload(String id) implements CustomPacketPayload {
        public static final Type<SoundEffectPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(MODID, "sound_effect"));

        public static final StreamCodec<ByteBuf, SoundEffectPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SoundEffectPayload::id,
            SoundEffectPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    private static final Pattern MOB_STRING_PATTERN = Pattern.compile("^([a-zA-Z0-9_.:]+)(\\{.*\\})?$");

    public static EntityType<?> parseEntityType(String input) {
        Matcher m = MOB_STRING_PATTERN.matcher(input);
        if (!m.matches()) return null;
        String idStr = m.group(1);
        return BuiltInRegistries.ENTITY_TYPE.getOptional(Identifier.tryParse(idStr)).orElse(null);
    }

    public static CompoundTag parseNbtFromString(String input) {
        int braceStart = input.indexOf('{');
        if (braceStart == -1) return null;
        String nbtPart = input.substring(braceStart);
        try {
            return TagParser.parseCompoundFully(nbtPart);
        } catch (CommandSyntaxException e) {
            return null;
        }
    }

    private boolean createRandomEntity(Level level, Player player, EntityType entityType, @Nullable CompoundTag nbt){

        // 先判末影珍珠，@debug：末影珍珠暂时不接受nbt
        if(entityType.equals(EntityType.ENDER_PEARL)) {
            level.playSound((Entity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            ItemStack itemStack = new ItemStack(ENDER_PEARL);
            Projectile.spawnProjectileFromRotation(ThrownEnderpearl::new, (ServerLevel) level, itemStack, player, 0.0F, 1.5F, 1.0F);
        }

        Entity entity = entityType.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
        if(entity == null){
            failOnExecute(player);
            return false;
        }
        if(player == null){
            return false;
        }
        if (nbt != null) {
            ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(Main.LOGGER);
            entity.load(TagValueInput.create(reporter, entity.registryAccess(), nbt));
        }
        Vec3 pos = player.position();

        // 船/矿车放置在前方
        Vec3 spawnPos;
       if(entity instanceof Boat || entity instanceof Minecart){
           float yaw = player.getYRot();
           float pitch = player.getXRot();
           Vec3 forward = Vec3.directionFromRotation(pitch, yaw).multiply(1.0, 0.0, 1.0).normalize();
           double offsetX = forward.x * 1.1;
           double offsetZ = forward.z * 1.1;
           spawnPos = new Vec3(pos.x + offsetX, pos.y, pos.z + offsetZ);
           entity.setPos(spawnPos);
           entity.setYRot(yaw);
        } else {
           spawnPos = new Vec3(pos.x, pos.y + 0.2, pos.z);
           entity.setPos(spawnPos);
        }
        entity.xo = spawnPos.x;
        entity.yo = spawnPos.y;
        entity.zo = spawnPos.z;

        level.addFreshEntity(entity);
        this.isSuccess = true;
        return true;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isSuccess() {
        return isSuccess;
    }



    public @Nullable Player getPlayer() {
        return player;
    }

    public void setSuccessful(boolean success) {
        this.isSuccess = success;
        this.setCanceled(true);
    }

    @Override
    public void setCanceled(boolean canceled) {
        ICancellableEvent.super.setCanceled(canceled);
    }

    public boolean isSuccessful() {
        return this.isSuccess;
    }
}
