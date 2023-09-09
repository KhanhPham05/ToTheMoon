package com.khanhtypo.tothemoon.client.chat;

import net.minecraft.network.chat.*;

import java.text.NumberFormat;
import java.util.Optional;

public class FormattedTextUnit implements ComponentContents {
    private static final NumberFormat intFormat = NumberFormat.getIntegerInstance();
    private final int value;

    private FormattedTextUnit(int value) {
        this.value = value;
    }

    private String formatValue() {
        return intFormat.format(this.value);
    }

    @Override
    public <T> Optional<T> visit(FormattedText.ContentConsumer<T> pContentConsumer) {
        return pContentConsumer.accept(this.formatValue());
    }

    @Override
    public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> pStyledContentConsumer, Style pStyle) {
        return pStyledContentConsumer.accept(pStyle, this.formatValue());
    }

    public static MutableComponent create(int value) {
        return MutableComponent.create(new FormattedTextUnit(value));
    }
}
