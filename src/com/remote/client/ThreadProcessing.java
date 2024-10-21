package com.remote.client;

import java.awt.image.BufferedImage;

public class ThreadProcessing extends Thread {
    public JoinedImage joinedImage;
    public BufferedImage partImage;
    public int id;
    
    public ThreadProcessing(BufferedImage partImage, int id, JoinedImage joinedImage) {
        this.partImage = partImage;
        this.id = id;
        this.joinedImage = joinedImage;
    }

    public BufferedImage processImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage processedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int grayscale = convertToGrayscale(pixel);
                processedImage.setRGB(x, y, grayscale);
            }
        }

        return processedImage;
    }

    public int convertToGrayscale(int pixel) {
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;
        int grayscale = (red + green + blue) / 3;
        int newPixel = (grayscale << 16) | (grayscale << 8) | grayscale;
        return newPixel;
    }

    @Override
    public void run() {
        BufferedImage processedImage = processImage(partImage);
        this.joinedImage.receiveImagePart(processedImage, id + 1);
    }

}
