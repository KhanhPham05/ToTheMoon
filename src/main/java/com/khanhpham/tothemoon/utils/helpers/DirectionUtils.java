package com.khanhpham.tothemoon.utils.helpers;

import net.minecraft.core.Direction;

public class DirectionUtils {
    public static Direction getBlockDirectionLeft(Direction facing) {
        return switch(facing) {
            case NORTH -> Direction.WEST;
            case EAST -> Direction.NORTH;
            case SOUTH -> Direction.EAST;
            case WEST -> Direction.SOUTH;
            default -> throw new IllegalStateException("Block facing should not be UP or DOWN");
        };
    }

    public static Direction getBlockDirectionRight(Direction facing) {
        return switch(facing) {
            case NORTH -> Direction.EAST;
            case EAST -> Direction.SOUTH;
            case SOUTH -> Direction.WEST;
            case WEST -> Direction.NORTH;
            default -> throw new IllegalStateException("Block facing should not be UP or DOWN");
        };
    }
}
