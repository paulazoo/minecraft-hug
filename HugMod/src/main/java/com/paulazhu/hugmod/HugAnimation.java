package com.paulazhu.hugmod;

import com.paulazhu.hugmod.PlayerData;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class HugAnimation {
    public boolean isValid(AbstractClientPlayer entity, PlayerData data) {
        if (!entity.isCrouching()) {
            return false;
        }
        double d = 1;//range
        //Vec3 vec3 = entity.getEyePosition(0);
        //Vec3 vec32 = entity.getViewVector(1.0F);
        //Vec3 vec33 = vec3.add(vec32.x * d, vec32.y * d, vec32.z * d);
        //AABB aABB = entity.getBoundingBox().expandTowards(vec32.scale(d)).inflate(1.0D, 1.0D, 1.0D);
        //EntityHitResult entHit = ProjectileUtil.getEntityHitResult(entity, vec3, vec33, aABB,
        //       en -> (!en.isSpectator()), d);
        //if(entHit != null && (entHit.getEntity().getType() == EntityType.PLAYER)) {
        //   AbstractClientPlayer otherPlayer = (AbstractClientPlayer) entHit.getEntity();
        //  double dif = otherPlayer.getY() - entity.getY();
        //   if(otherPlayer.isCrouching() && Math.abs(dif) < 0.3) { // Making sure they are about on the same height
        //       return true;
        //   }
        //}
        return false;
    }
}
