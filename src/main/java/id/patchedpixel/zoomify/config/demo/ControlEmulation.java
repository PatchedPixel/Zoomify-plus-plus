package id.patchedpixel.zoomify.config.demo;

@FunctionalInterface
public interface ControlEmulation {
    void tick(ZoomDemoImageRenderer imageRenderer);

    default void pause(ZoomDemoImageRenderer imageRenderer) {}

    default void setup(ZoomDemoImageRenderer imageRenderer) {}

    final class InitialOnly implements ControlEmulation {
        public static final InitialOnly INSTANCE = new InitialOnly();

        private int switchPauseTicks = 0;

        private InitialOnly() {}

        @Override
        public void tick(ZoomDemoImageRenderer imageRenderer) {
            // if the previous state is identical to the current state, pause for a bit, then inverse what we just did
            if (switchPauseTicks > 0) {
                switchPauseTicks--;
                if (switchPauseTicks == 0) {
                    imageRenderer.setKeyDown(!imageRenderer.getKeyDown());
                }
            } else if (imageRenderer.getZoomHelper().getZoomDivisor(1f) == imageRenderer.getZoomHelper().getZoomDivisor(0f)) {
                switchPauseTicks = 20;
            }
        }

        @Override
        public void pause(ZoomDemoImageRenderer imageRenderer) {
            imageRenderer.setKeyDown(false);
            switchPauseTicks = 20;
        }

        @Override
        public void setup(ZoomDemoImageRenderer imageRenderer) {
            imageRenderer.setKeyDown(true);
        }
    }

    final class ScrollOnly implements ControlEmulation {
        public static final ScrollOnly INSTANCE = new ScrollOnly();

        private int scrollPauseTicks = 0;
        private boolean reverse = false;

        private ScrollOnly() {}

        @Override
        public void tick(ZoomDemoImageRenderer imageRenderer) {
            if (imageRenderer.getZoomHelper().maxScrollTiers() == 0) {
                imageRenderer.getZoomHelper().setToZero(false, true);
                imageRenderer.setScrollTiers(0);
                return;
            }

            if (scrollPauseTicks > 0) {
                scrollPauseTicks--;
            } else {
                scrollPauseTicks = 3;

                if (!reverse) {
                    imageRenderer.setScrollTiers(imageRenderer.getScrollTiers() + 1);
                    if (imageRenderer.getScrollTiers() >= imageRenderer.getZoomHelper().maxScrollTiers()) {
                        reverse = true;
                        scrollPauseTicks = 20;
                    }
                } else {
                    imageRenderer.setScrollTiers(imageRenderer.getScrollTiers() - 1);
                    if (imageRenderer.getScrollTiers() <= 0) {
                        reverse = false;
                        scrollPauseTicks = 20;
                    }
                }
            }
        }

        @Override
        public void setup(ZoomDemoImageRenderer imageRenderer) {
            imageRenderer.setKeyDown(true);
            imageRenderer.getZoomHelper().skipInitial();
        }

        @Override
        public void pause(ZoomDemoImageRenderer imageRenderer) {
            scrollPauseTicks = 20;
            reverse = false;
            imageRenderer.setScrollTiers(0);
            imageRenderer.getZoomHelper().skipInitial();
        }
    }
}
