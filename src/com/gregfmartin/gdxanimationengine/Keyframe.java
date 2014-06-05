package com.gregfmartin.gdxanimationengine;

/**
 * <p>
 * A critical component of a {@link com.gregfmartin.gdxanimationengine.Sequence}. A Keyframe can be placed
 * on an arbitrary point of time in a Sequence from 0 to n-1 where n is the total length of the container
 * Sequence. When a Sequence's Play Head encounters a Keyframe, it emits an event (or a signal). This event
 * will cause some action to occur (which is managed elsewhere - typically expected to be defined by any
 * client code).
 * </p>
 * <p>
 * Speaking to event emission, it specifically emits an {@link com.gregfmartin.gdxanimationengine.AnimationEvent}
 * which is, as noted above, expected to be defined by any client code. A Keyframe will only emit one event.
 * </p>
 *
 * @author Greg Martin
 */
public class Keyframe {
    /**
     * <p>
     * Defines the particular point in time in a containing Sequence where this Keyframe resides. The time signature
     * for all Sequences is measured in milliseconds and can have an length defined to be as long as the
     * compiler-supported size of the data type long. Thus this member is of a comparative type.
     * </p>
     */
    protected long mSeatedFrame;

    /**
     * <p>
     * The Event that will be emitted when the containing Sequence's Play Head encounters this Keyframe.
     * </p>
     */
    protected AnimationEvent mEvent;

    /**
     * <p>
     *     The constructor expected to be used. It takes an Object that has implemented
     *     {@link com.gregfmartin.gdxanimationengine.AnimationEvent}. That Object's particular
     *     implementation will act as the action to take when the Keyframe emits.
     * </p>
     *
     * @param animationEventImpl - An external implementation of {@link com.gregfmartin.gdxanimationengine.AnimationEvent}
     * @param seatedFrame - The point in time to place the Keyframe at in a containing {@link com.gregfmartin.gdxanimationengine.Sequence}
     */
    public Keyframe(AnimationEvent animationEventImpl, long seatedFrame) {
        mSeatedFrame = seatedFrame;
        mEvent = animationEventImpl;
    }
}
