package com.snebot.fbmoll.util;

import org.jeasy.random.EasyRandom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for object interaction.
 * Currently only used in tests.
 *
 * @author Serafi Nebot Ginard
 */
public class DummyUtils {
    public DummyUtils() {
    }

    /**
     * Generate a list of objects of a given size.
     *
     * @param classType Object type.
     * @param size      List size.
     * @param <T>       Object to generate.
     * @return List of objects.
     */
    public <T extends Serializable> List<T> generateObjects(Class<T> classType, int size) {
        ArrayList<T> objects = new ArrayList<>();
        EasyRandom generator = new EasyRandom();
        for (int i = 0; i < size; i++) objects.add(generator.nextObject(classType));
        return objects;
    }
}