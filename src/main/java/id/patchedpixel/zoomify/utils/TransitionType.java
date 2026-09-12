package id.patchedpixel.zoomify.utils;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum TransitionType implements Transition, NameableEnum, StringRepresentable {
    INSTANT("zoomify.transition.instant") {
        @Override
        public double apply(double t) {
            return t;
        }
    },
    LINEAR("zoomify.transition.linear") {
        @Override
        public double apply(double t) {
            return t;
        }
    },
    EASE_IN_SINE("zoomify.transition.ease_in_sine") {
        @Override
        public double apply(double t) {
            return 1 - Math.cos((t * Math.PI) / 2);
        }

        @Override
        public double inverse(double x) {
            return Math.acos(-(x - 1)) * 2 / Math.PI;
        }
    },
    EASE_OUT_SINE("zoomify.transition.ease_out_sine") {
        @Override
        public double apply(double t) {
            return Math.sin((t * Math.PI) / 2);
        }

        @Override
        public double inverse(double x) {
            return Math.asin(x) * 2 / Math.PI;
        }
    },
    EASE_IN_OUT_SINE("zoomify.transition.ease_in_out_sine") {
        @Override
        public double apply(double t) {
            return -(Math.cos(Math.PI * t) - 1) / 2;
        }
    },
    EASE_IN_QUAD("zoomify.transition.ease_in_quad") {
        @Override
        public double apply(double t) {
            return t * t;
        }

        @Override
        public double inverse(double x) {
            return Math.sqrt(x);
        }
    },
    EASE_OUT_QUAD("zoomify.transition.ease_out_quad") {
        @Override
        public double apply(double t) {
            return 1 - (1 - t) * (1 - t);
        }

        @Override
        public double inverse(double x) {
            return -(Math.sqrt(-(x - 1)) - 1);
        }
    },
    EASE_IN_OUT_QUAD("zoomify.transition.ease_in_out_quad") {
        @Override
        public double apply(double t) {
            if (t < 0.5)
                return 2 * t * t;
            else
                return 1 - Math.pow(-2 * t + 2, 2) / 2;
        }
    },
    EASE_IN_CUBIC("zoomify.transition.ease_in_cubic") {
        @Override
        public double apply(double t) {
            return Math.pow(t, 3);
        }

        @Override
        public double inverse(double x) {
            return Math.pow(x, 1 / 3.0);
        }
    },
    EASE_OUT_CUBIC("zoomify.transition.ease_out_cubic") {
        @Override
        public double apply(double t) {
            return 1 - Math.pow(1 - t, 3);
        }

        @Override
        public double inverse(double x) {
            return -Math.pow(-x + 1, 1.0 / 3.0) + 1;
        }
    },
    EASE_IN_OUT_CUBIC("zoomify.transition.ease_in_out_cubic") {
        @Override
        public double apply(double t) {
            if (t < 0.5)
                return 4 * t * t * t;
            else
                return 1 - Math.pow(-2 * t + 2, 3) / 2;
        }
    },
    EASE_IN_EXP("zoomify.transition.ease_in_exp") {
        private final double c_log2_1023 = log2(1023.0);

        @Override
        public double apply(double t) {
            if (t == 0.0) return 0.0;
            if (t == 1.0) return 1.0;
            return Math.pow(2.0, 10.0 * t - c_log2_1023) - 1 / 1023.0;
        }

        @Override
        public double inverse(double x) {
            if (x == 0.0) return 0.0;
            if (x == 1.0) return 1.0;
            return Math.log(1023 * x + 1) / (10 * Math.log(2.0));
        }
    },
    EASE_OUT_EXP("zoomify.transition.ease_out_exp") {
        private final double c_log2_1023 = log2(1023.0);
        private final double c_10_ln2 = 10.0 * Math.log(2.0);
        private final double c_ln_1203 = Math.log(1023.0);

        @Override
        public double apply(double t) {
            if (t == 0.0) return 0.0;
            if (t == 1.0) return 1.0;
            return 1.0 - Math.pow(2.0, 10.0 - c_log2_1023 - 10.0 * t) + 1 / 1023.0;
        }

        @Override
        public double inverse(double x) {
            if (x == 0.0) return 0.0;
            if (x == 1.0) return 1.0;
            return -((Math.log(-((1023 * x - 1024) / 1023)) - c_10_ln2 + c_ln_1203) / c_10_ln2);
        }
    },
    EASE_IN_OUT_EXP("zoomify.transition.ease_in_out_exp") {
        private final double c_log2_1023 = log2(1023.0);

        @Override
        public double apply(double t) {
            if (t == 0.0) return 0.0;
            if (t == 1.0) return 1.0;
            if (t < 0.5) return Math.pow(2.0, 20.0 * t - c_log2_1023) - 1 / 1023.0;
            return 1.0 - Math.pow(2.0, 10.0 - c_log2_1023 - 10.0 * t) + 1 / 1023.0;
        }
    };

    private static double log2(double value) {
        return Math.log(value) / Math.log(2.0);
    }

    private final Component localisedName;

    TransitionType(String name) {
        this.localisedName = Component.translatable(name);
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }

    @Override
    public Component getDisplayName() {
        return localisedName;
    }

    public Component getLocalisedName() {
        return localisedName;
    }

    public TransitionType opposite() {
        switch (this) {
            case INSTANT: return INSTANT;
            case LINEAR: return LINEAR;
            case EASE_IN_SINE: return EASE_OUT_SINE;
            case EASE_OUT_SINE: return EASE_IN_SINE;
            case EASE_IN_OUT_SINE: return EASE_IN_OUT_SINE;
            case EASE_IN_QUAD: return EASE_OUT_QUAD;
            case EASE_OUT_QUAD: return EASE_IN_QUAD;
            case EASE_IN_OUT_QUAD: return EASE_IN_OUT_QUAD;
            case EASE_IN_CUBIC: return EASE_OUT_CUBIC;
            case EASE_OUT_CUBIC: return EASE_IN_CUBIC;
            case EASE_IN_OUT_CUBIC: return EASE_IN_OUT_CUBIC;
            case EASE_IN_EXP: return EASE_OUT_EXP;
            case EASE_OUT_EXP: return EASE_IN_EXP;
            case EASE_IN_OUT_EXP: return EASE_IN_OUT_EXP;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public static final EnumCodec<TransitionType> CODEC = StringRepresentable.fromEnum(TransitionType::values);
}
