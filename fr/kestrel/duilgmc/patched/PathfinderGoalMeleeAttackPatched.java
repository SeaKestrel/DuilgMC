package fr.kestrel.duilgmc.patched;

import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.level.pathfinder.PathEntity;

public class PathfinderGoalMeleeAttackPatched extends PathfinderGoalMeleeAttack {

    protected final EntityCreature a;
    private final double b;
    private final boolean c;
    private PathEntity d;
    private double e;
    private double f;
    private double g;
    private int h;
    private int i;
    private final int j = 20;
    private long k;
    private static final long l = 20L;

    public PathfinderGoalMeleeAttackPatched(EntityCreature var0, double var1, boolean var3) {
        super(var0, var1, var3);
        this.a = var0;
        this.b = var1;
        this.c = var3;
    }

    @Override
    public void e() {
        EntityLiving var0 = this.a.getGoalTarget();
        this.a.getControllerLook().a(this.a.getGoalTarget(), 30.0F, 30.0F);
        double var1 = this.a.h(this.a.getGoalTarget().locX(), this.a.getGoalTarget().locY(), this.a.getGoalTarget().locZ());
        this.h = Math.max(this.h - 1, 0);
        if ((this.c || this.a.getEntitySenses().a(this.a.getGoalTarget())) && this.h <= 0 && (this.e == 0.0D && this.f == 0.0D && this.g == 0.0D || this.a.getGoalTarget().h(this.e, this.f, this.g) >= 1.0D || this.a.getRandom().nextFloat() < 0.05F)) {
            this.e = this.a.getGoalTarget().locX();
            this.f = this.a.getGoalTarget().locY();
            this.g = this.a.getGoalTarget().locZ();
            this.h = 4 + this.a.getRandom().nextInt(7);
            if (var1 > 1024.0D) {
                this.h += 10;
            } else if (var1 > 256.0D) {
                this.h += 5;
            }

            if (!this.a.getNavigation().a(this.a.getGoalTarget(), this.b)) {
                this.h += 15;
            }
        }

        this.i = Math.max(this.i - 1, 0);
        this.a(this.a.getGoalTarget(), var1);
    }
}
