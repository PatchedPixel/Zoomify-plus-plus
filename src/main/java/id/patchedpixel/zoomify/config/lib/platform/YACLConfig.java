package id.patchedpixel.zoomify.config.lib.platform;

import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigClassHandler;
import id.patchedpixel.zoomify.config.lib.config.v2.api.SerialEntry;
import id.patchedpixel.zoomify.config.lib.config.v2.api.serializer.GsonConfigSerializerBuilder;

public class YACLConfig {
    public static final ConfigClassHandler<YACLConfig> HANDLER = ConfigClassHandler.createBuilder(YACLConfig.class)
            .id(YACLPlatform.rl("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("yacl.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public boolean showColorPickerIndicator = true;
}
