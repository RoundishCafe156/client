package cc.hyperium.handlers.handlers.animation;

public class AnimationFrame {
    public String name;
    private long time;
    private BodyPart leftUpperArm = new BodyPart();
    private BodyPart leftLowerArm = new BodyPart();
    private BodyPart rightUpperArm = new BodyPart();
    private BodyPart rightLowerArm = new BodyPart();
    private BodyPart chest = new BodyPart();
    private BodyPart head = new BodyPart();
    private BodyPart leftUpperLeg = new BodyPart();
    private BodyPart leftLowerLeg = new BodyPart();
    private BodyPart rightUpperLeg = new BodyPart();
    private BodyPart rightLowerLeg = new BodyPart();

    public AnimationFrame(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AnimationFrame{" +
            "time=" + time +
            ", leftUpperArm=" + leftUpperArm +
            ", leftLowerArm=" + leftLowerArm +
            ", rightUpperArm=" + rightUpperArm +
            ", rightLowerArm=" + rightLowerArm +
            ", chest=" + chest +
            ", head=" + head +
            ", leftUpperLeg=" + leftUpperLeg +
            ", leftLowerLeg=" + leftLowerLeg +
            ", rightUpperLeg=" + rightUpperLeg +
            ", rightLowerLeg=" + rightLowerLeg +
            ", name='" + name + '\'' +
            '}';
    }

    public long getTime() {
        return time;
    }

    BodyPart getLeftUpperArm() {
        return leftUpperArm;
    }

    BodyPart getLeftLowerArm() {
        return leftLowerArm;
    }

    BodyPart getRightUpperArm() {
        return rightUpperArm;
    }

    BodyPart getRightLowerArm() {
        return rightLowerArm;
    }

    BodyPart getChest() {
        return chest;
    }

    BodyPart getHead() {
        return head;
    }

    BodyPart getLeftUpperLeg() {
        return leftUpperLeg;
    }

    BodyPart getLeftLowerLeg() {
        return leftLowerLeg;
    }

    BodyPart getRightUpperLeg() {
        return rightUpperLeg;
    }

    BodyPart getRightLowerLeg() {
        return rightLowerLeg;
    }
}
