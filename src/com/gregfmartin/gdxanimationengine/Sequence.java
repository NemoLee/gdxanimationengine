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

    /**
     * <p>
     * Add a Keyframe to the Sequence in a direct fashion. May be the most unused method since direct client
     * instantiation of Keyframe is prohibited (instantiation is preferred via obtain pattern). Exists as a
     * viable contextual option.
     * </p>
     *
     * @param keyframe The Keyframe to add to the Sequence
     */
    public void addKeyframe(final Keyframe keyframe) {
        HashMap<Long, Keyframe> hashMap = new HashMap<Long, Keyframe>();
        hashMap.put(keyframe.mSeatedFrame, keyframe);
        mKeyframes.add(hashMap);
    }

    /**
     * <p>
     *     Add a properly instantiated HashMap containing the Keyframe to the Sequence. This method assumes
     *     the most about the prior work by client code as it assumes the client has built the HashMap against
     *     the requirements of Keyframe Injection into the Sequence.
     * </p>
     *
     * @param map A HashMap containing the Keyframe to add to the Sequence
     */
    public void addKeyframe(final HashMap<Long, Keyframe> map) {
        mKeyframes.add(map);
    }

    /**
     * <p>
     *     Adds a Keyframe to the Sequence via raw Keyframe data defined by the client. The values asked for
     *     by this method are the very same used to construct a Keyframe. This method does the most work to
     *     inject a Keyframe but it may be the most friendly to the client.
     * </p>
     *
     * @param frame The "frame" to seat the Keyframe on (in milliseconds)
     * @param animationEventImpl An object reference that implements AnimationEvent
     */
    public void addKeyframe(final long frame, final AnimationEvent animationEventImpl) {
        HashMap<Long, Keyframe> hashMap = new HashMap<Long, Keyframe>();
        Keyframe keyframe = Keyframe.obtain(animationEventImpl, frame);

        hashMap.put(keyframe.mSeatedFrame, keyframe);
        mKeyframes.add(hashMap);
    }

    /**
     * <p>
     *     Defines the playback mode of the Sequence. Following are the definitions of the playback modes.
     * </p>
     * <ul>
     *     <li>NONLOOP_FORWARD - Runs the Play Head through the Sequence once from frame 0 to frame n-1</li>
     *     <li>NONLOOP_REVERSE - Runs the Play Head through the Sequence once from frame n-1 to frame 0</li>
     *     <li>LOOP_FORWARD_X - Runs the Play Head through the Sequence x number of times from frame 0 to frame n-1, restarting at frame 0 each time</li>
     *     <li>LOOP_REVERSE_X - Runs the Play Head through the Sequence x number of times from frame n-1 to frame 0, restarting at frame n-1 each time</li>
     *     <li>LOOP_PINGPONG - Runs the Play Head through the Sequence a total of two times, once forward (from frame 0 to frame n-1) and then once backward (from frame n-1 to frame 0)</li>
     *     <li>LOOP_PONGPING - Runs the Play Head through the Sequence a total of two times, once reverse (from frame n-1 to frame 0) and then once forward (from frame 0 to frame n-1)</li>
     *     <li>LOOP_PINGPONG_X - Runs the Play Head through the Sequence x number of times in a PingPong fashion (hence one iteration in this mode is actually two passes through the Sequence)</li>
     *     <li>LOOP_PONGPING_X - Runs the Play Head through the Sequence x number of times in a PongPing fashion (hence one iteration in this mode is actually two passes through the Sequence)</li>
     * </ul>
     */
    public enum SequencePlayMode {
        NONLOOP_FORWARD,
        NONLOOP_REVERSE,
        LOOP_FORWARD_X,
        LOOP_REVERSE_X,
        NONLOOP_PINGPONG,
        NONLOOP_PONGPING,
        LOOP_PINGPONG_X,
        LOOP_PONGPING_X
    }
}
