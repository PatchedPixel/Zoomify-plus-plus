package id.patchedpixel.zoomify.config.lib.gui.controllers.string.number;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ValueFormatter;
import id.patchedpixel.zoomify.config.lib.api.utils.Dimension;
import id.patchedpixel.zoomify.config.lib.gui.AbstractWidget;
import id.patchedpixel.zoomify.config.lib.gui.YACLScreen;
import id.patchedpixel.zoomify.config.lib.gui.controllers.slider.ISliderController;
import id.patchedpixel.zoomify.config.lib.gui.controllers.string.IStringController;
import id.patchedpixel.zoomify.config.lib.gui.controllers.string.StringControllerElement;
import id.patchedpixel.zoomify.config.lib.impl.utils.YACLConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.function.Function;

/**
 * Controller that allows you to enter in numbers using a text field.
 *
 * @param <T> number type
 */
public abstract class NumberFieldController<T extends Number> implements ISliderController<T>, IStringController<T> {
    protected static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();
    private static final DecimalFormatSymbols DECIMAL_FORMAT_SYMBOLS = DecimalFormatSymbols.getInstance();

    private final Option<T> option;
    private final ValueFormatter<T> displayFormatter;

    public NumberFieldController(Option<T> option, Function<T, Component> displayFormatter) {
        this.option = option;
        this.displayFormatter = displayFormatter::apply;
    }

    @Override
    public Option<T> option() {
        return this.option;
    }

    @Override
    public void setFromString(String value) {
        try {
            String transformed = transformInput(value);
            setPendingValue(Mth.clamp(NUMBER_FORMAT.parse(transformed).doubleValue(), min(), max()));
        } catch (ParseException ignore) {
            YACLConstants.LOGGER.warn("Failed to parse number: {}", value);
        }
    }

    @Override
    public double pendingValue() {
        return option().pendingValue().doubleValue();
    }

    @Override
    public boolean isInputValid(String input) {
        input = transformInput(input);

        ParsePosition parsePosition = new ParsePosition(0);
        NUMBER_FORMAT.parse(input, parsePosition);
        return parsePosition.getIndex() == input.length();
    }

    @Override
    public Component formatValue() {
        return displayFormatter.format(option().pendingValue());
    }

    @Override
    public AbstractWidget provideWidget(YACLScreen screen, Dimension<Integer> widgetDimension) {
        return new StringControllerElement(this, screen, widgetDimension, false);
    }

    @Override
    public double interval() {
        return -1;
    }

    protected String transformInput(String input) {
        if (input.isEmpty()) input = "0";
        if (input.equals("-")) input = "-0";

        return input.replace(DECIMAL_FORMAT_SYMBOLS.getGroupingSeparator() + "", "");
    }
}
