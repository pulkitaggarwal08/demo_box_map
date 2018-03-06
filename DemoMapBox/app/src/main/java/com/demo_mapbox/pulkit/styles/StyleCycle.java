package com.demo_mapbox.pulkit.styles;

import com.mapbox.mapboxsdk.constants.Style;

/**
 * Created by pulkit on 5/12/17.
 */

public class StyleCycle {
    private static final String[] STYLES = new String[] {
            Style.MAPBOX_STREETS,
            Style.OUTDOORS,
            Style.LIGHT,
            Style.DARK,
            Style.SATELLITE_STREETS
    };

    private int index;

    private String getNextStyle() {
        index++;
        if (index == STYLES.length) {
            index = 0;
        }
        return getStyle();
    }

    private String getStyle() {
        return STYLES[index];
    }
}