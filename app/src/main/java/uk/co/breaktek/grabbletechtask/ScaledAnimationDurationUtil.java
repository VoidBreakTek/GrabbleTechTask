package uk.co.breaktek.grabbletechtask;

/**
 */
class ScaledAnimationDurationUtil {
    private static final int BASE_ANIM_SPEED = 250;

    static int getScaledAnimSpeed(float scale) {
        return (int) (scale * BASE_ANIM_SPEED);
    }
}
