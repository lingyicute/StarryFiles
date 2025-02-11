/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package com.google.android.material.shape;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.elevation.ElevationOverlayProvider;

public class MaterialShapeDrawableAccessor {
    private MaterialShapeDrawableAccessor() {}

    @SuppressLint("RestrictedApi")
    public static ElevationOverlayProvider getElevationOverlayProvider(
            @NonNull MaterialShapeDrawable drawable) {
        MaterialShapeDrawable.MaterialShapeDrawableState drawableState =
                (MaterialShapeDrawable.MaterialShapeDrawableState) drawable.getConstantState();
        return drawableState.elevationOverlayProvider;
    }

    @SuppressLint("RestrictedApi")
    public static void setElevationOverlayProvider(
            @NonNull MaterialShapeDrawable drawable,
            @Nullable ElevationOverlayProvider elevationOverlayProvider) {
        MaterialShapeDrawable.MaterialShapeDrawableState drawableState =
                (MaterialShapeDrawable.MaterialShapeDrawableState) drawable.getConstantState();
        drawableState.elevationOverlayProvider = elevationOverlayProvider;
    }

    public static void updateZ(@NonNull MaterialShapeDrawable drawable) {
        final float parentAbsoluteElevation = drawable.getParentAbsoluteElevation();
        drawable.setParentAbsoluteElevation(parentAbsoluteElevation + 1);
        drawable.setParentAbsoluteElevation(parentAbsoluteElevation);
    }
}
