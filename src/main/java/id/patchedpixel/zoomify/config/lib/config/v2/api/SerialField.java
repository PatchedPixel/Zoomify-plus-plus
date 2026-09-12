package id.patchedpixel.zoomify.config.lib.config.v2.api;

import java.util.Optional;

/**
 * The backing interface for the {@link SerialEntry} annotation.
 */
public interface SerialField {
    String serialName();

    Optional<String> comment();

    boolean required();

    boolean nullable();
}
