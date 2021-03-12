package com.uni.ppg.image;

public class PixelProcessor {

    public static int yuvToRedSum(byte[] yuv, int width, int height) {
        int frameSize = width * height;
        int sum = 0;
        for (int h = 0, yp = 0; h < height; h++) {
            int uvp = frameSize + (h >> 1) * width, u = 0, v = 0;
            for (int w = 0; w < width; w++, yp++) {
                int y = (0xff & ((int) yuv[yp])) - 16;

                if (y < 0) {
                    y = 0;
                }

                if ((w & 1) == 0) {
                    v = (0xff & yuv[uvp++]) - 128;
                    u = (0xff & yuv[uvp++]) - 128;
                }

                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0) {
                    r = 0;
                } else if (r > 262143) {
                    r = 262143;
                }

                if (g < 0) {
                    g = 0;
                } else if (g > 262143) {
                    g = 262143;
                }

                if (b < 0) {
                    b = 0;
                } else if (b > 262143) {
                    b = 262143;
                }

                int pixel = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
                int red = (pixel >> 16) & 0xff;
                sum += red;
            }
        }
        return sum;
    }

}