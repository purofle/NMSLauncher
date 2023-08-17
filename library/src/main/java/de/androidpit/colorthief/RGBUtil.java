/*
 * Java Color Thief
 * by Sven Woltmann, Fonpit AG
 *
 * https://www.androidpit.com
 * https://www.androidpit.de
 *
 * License
 * -------
 * Creative Commons Attribution 2.5 License:
 * http://creativecommons.org/licenses/by/2.5/
 *
 * Thanks
 * ------
 * Lokesh Dhakar - for the original Color Thief JavaScript version
 * available at http://lokeshdhakar.com/projects/color-thief/
 */

package de.androidpit.colorthief;

public class RGBUtil {

    /**
     * Packs the RGB array into one int for storing into db or sending via network.
     *
     * @param rgb
     *            array containing red, green and blue values of the color to encode
     * @return packed into int RGB value
     * @throws IllegalArgumentException
     *             if rgb has not exactly 3 elements
     */
    public static int packRGB(int[] rgb) {
        if (rgb.length != 3) {
            throw new IllegalArgumentException("RGB array should contain exactly 3 values.");
        }
        return rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

}
