package com.gregfmartin.gdxanimationengine;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     An ordered collection of {@link com.gregfmartin.gdxanimationengine.Keyframe}(s) that span a finite
 *     client-specified range of time. To be more accurate, a Sequence always starts at zero while the client
 *     will define the overall length. Time is measured and expressed in milliseconds.
 * </p>
 * <p>
 *     Every Sequence has a Play Head. The Play Head is simply a reference point to the current "frame" that's
 *     being processed. When the Play Head encounters a "frame" that contains Keyframe(s), the events in those
 *     Keyframe(s) are emitted. It is possible for a single "frame" to contain multiple Keyframes. If this is the
 *     case, each of those Keyframes will emit their events in parallel. Since each Keyframe has a duration of
 *     their own, it's possible for Keyframes to execute in parallel if multiples are encountered on a single
 *     "frame" and can overlap in serial if a previous Keyframe has been executed and is still running while
 *     a new one is encountered by the Play Head. These facilities provide for a more robust animation
 *     experience.
 * </p>
 *
 * @author Greg Martin
 */
public class Sequence {
    /**
     * <p>
     *     The length of time in which the Sequence operates. The time is measured in milliseconds.
     * </p>
     */
    private long mTotalTime;

    /**
     * <p>
     *     The current "frame" in the Sequence that's being looked at. More specifically, this is the current
     *     millisecond in the Sequence.
     * </p>
     */
    private long mPlayHead;

    /**
     * <p>
     *     All of the {@link com.gregfmartin.gdxanimationengine.Keyframe}s that are found in this Sequence.
     *     The List that contains all of these holds a Map instance that ties a Keyframe to a specific "frame."
     * </p>
     */
    private List<HashMap<Long, Keyframe>> mKeyframes;

    public void addKeyframe(Keyframe keyframe) {
        HashMap<Long, Keyframe> hashMap = new HashMap<Long, Keyframe>();
        hashMap.put(keyframe.mSeatedFrame, keyframe);
        mKeyframes.add(hashMap);
    }

    public void addKeyframe(HashMap<Long, Keyframe> map) {
        mKeyframes.add(map);
    }

    public void addKeyframe(long frame, AnimationEvent animationEventImpl) {
        HashMap<Long, Keyframe> hashMap = new HashMap<Long, Keyframe>();
        Keyframe keyframe = Keyframe.obtain(animationEventImpl, frame);

        hashMap.put(keyframe.mSeatedFrame, keyframe);
        mKeyframes.add(hashMap);
    }
}
