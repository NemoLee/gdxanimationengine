package com.gregfmartin.gdxanimationengine;

/**
 * <p>
 * The type of event that will be emitted from a {@link com.gregfmartin.gdxanimationengine.Keyframe}
 * when it is encountered by a Sequence's Play Head. The method declared in this interface is truly
 * agnostic of what it will be used for.
 * </p>
 *
 * @author Greg Martin
 */
public interface AnimationEvent {
    public void onAnimationEventEmitted();
}
