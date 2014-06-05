Animation Engine Documentation
Gregory Martin
==============================

==============================
PREFACE
==============================
As I was writing this, it became beyond evident that a glossary of terms needed to be defined so that I could keep track of how I was slowly constructing a schema of identifiers for all sorts of things. Consider it a way to help organize the collective.

==============================
GLOSSARY
==============================
Animation - A sequence of Frames that together form a cohesive visual element. The references to "Frames" and "visual element" in both this definition and document are entirely abstract. The engine that I describe in the following lines does not, and is not, intended to be married to anything specific (2D/3D texture, "sprite", etc...). The terms will be used regardless.

Sequence - An ordered collection of Frames. This is the primary component of an Animation. It is possible for an Animation to contain multiple Sequences. A Sequence has a definite start and end point, however it can be iterated over forward and backward and can be looped.

Frame - A specific point in time in a Sequence.

Frame Zero - The absolute starting point for a Sequence. This special Frame is used to initialize a Sequence and can be used as a constant reference point.

Duration - The overall length of a Sequence, measured in Frames. This value is always defined by the client. This can be considered to be the "end" of a Sequence.

Step - Can also be referred to as Frame Skip. This value governs how many Frames a Sequence will advance per iteration. It is possible for the value of Step to be negative.

Current Frame - This is effectively the "play head." Step will affect this value directly. The value of Current Frame is bound between Frame Zero and Duration and cannot exceed either of them under any circumstances.

Keyframe - A special Frame that will trigger an Event when encountered. There are several caveats with Keyframes that are dependent upon other aspects. It is possible for Keyframes to be assigned to Frames that would be skipped due to a condition where a future multiple of Step doesn't coincide with that of a multiple of a Frame that a Keyframe is assigned to. Execution of Keyframes is serial unless multiple Keyframes are assigned to a single Frame (in which case the Keyframes will be executed in parallel).

Event - An action to occur. The specific action to be taken is to be defined entirely by the client. The Animation engine makes no assumption about visceral things like Objects or abstract entities and their respective properties that could potentially be augmented by the Animation.


==============================
BACKGROUND
==============================
libgdx doesn't appear to have an animation framework for Scene2D. More specifically, while there is an Animation class for 2D graphics (container of TextureRegions) and one for 3D meshes, I require one of a little more of a generic nature. While generics aren't necessarily friendly to embedded devices, there is something attractive to being able to just instantiate an object and have it perform the work that I need done.

The example that I'm going to work with here is being able to queue multiple operations on a Camera and have them execute in serial and parallel.

The example that I'm going to use is one where I want the Camera to do the following things:

1. Translate along the X axis until a certain calculated value has been reached.
2. Zoom out. In parallel, adjust the dimensions of the Viewport to the size of the Splash image that's being displayed on the screen.

That being said, I believe that it's first pertinent to gather some basic concepts about an Animation.

==============================
ENGINE SPECIFICS
==============================
The base component of an Animation is the Sequence. As defined above, a Sequence is an ordered collection of Frames. Frames themselves do not correlate to a specific entity in the engine but rather refer to "one unit of measure" in between Frame Zero and Duration. However they're tied to Step as well. Step acts as a divisor of Duration. For example, if Duration is 100 and Step is 10, there would be 10 Frames in the Sequence.

An Animation has a Duration. That is to say that any Animation has a start and end point and the time in between those two points (inclusive if you're measuring it in Frames) is the Duration. Relative to the Duration would be the Step. The Step could be considered to be the Frame Skip or how many Frames are incremented in the Animation per iteration. The Current Frame indicates the current position of the "play head" in the Animation Sequence.

At the start of an Animation Sequence, Current Frame is guaranteed to start at Frame Zero. The Duration can be set to whatever value the client wants to set it at. Therefore, it's possible, although redundant, to have an Animation whose Duration is zero (0). This Animation will not have the concept of elapsed time and will therefore only apply changes to those objects involved that were set from the initial starting point (frame 0). Moreover, with reference to a concept introducted later in the document, Keyframes not set to frame 0 will be dropped.

Per iteration, the value of Current Frame will be either incremented or decremented by the value of Step. This will ultimately control the speed of the Animation sequence. As noted above, the ability to either increment or decrement will provide the caller the ability to have bi-directional animation. However, regardless of which direction the Step moves in, it is locked to the bounds of zero (0) (the relative start position of the Animation) and Duration (user-specified end frame for the Animation). In other words, Step can only augment Current Frame so long as Current Frame does not exceed zero (0) or Duration.

The concept of bi-directional animation can raise questions about easing even though it's more specifically tied to the relationship between Frame 0, Duration and Step. This will be something that's addressed at a later point in the document..

That being said, Animations are tied to Keyframes. Keyframes will launch Events.

Keyframes are tied explicitly to user-specified frames in an Animation. The frame that a Keyframe belongs to must be within the boundaries of the Animation sequence (previously defined as the range between zero (0) and Duration). If a Keyframe is assigned to a frame that does not belong within those boundaries, it will be dropped (more specifically, it will be removed from the list of monitored Keyframes for that Animation). Keyframes can overlap each other. For example, let's say that you have an Animation that has a Duration of 500 frames. You specify a Keyframe at frame 46. You want to run another Animation in parallel and you want it to start at the same time as the previous one. You can also add an additional Keyframe at frame 46. The Animation will handle the parallel execution of those frames. That being said, Keyframe execution will be treated in serial unless a condition like this is met where multiple Keyframes are defined in the same frame.

The default execution rate is one frame per second.
