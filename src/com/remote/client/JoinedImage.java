package com.remote.client;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class JoinedImage {
    public javax.swing.JLabel label;
    private final Map<Integer, BufferedImage> receivedParts;
    private final int totalParts;

    public JoinedImage(javax.swing.JLabel label, int totalParts) {
        this.label = label;
        this.receivedParts = new HashMap<>();
        this.totalParts = totalParts;
    }

    public BufferedImage combineImageParts() {
        int totalWidth = 0;
        int maxHeight = 0;
        for (BufferedImage part : receivedParts.values()) {
            totalWidth += part.getWidth();
            maxHeight = Math.max(maxHeight, part.getHeight());
        }

        BufferedImage joinedImage = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = joinedImage.createGraphics();
        int currentX = 0;
        for (BufferedImage part : receivedParts.values()) {
            g2d.drawImage(part, currentX, 0, null);
            currentX += part.getWidth();
        }

        g2d.dispose();

        return joinedImage;
    }

    public synchronized void receiveImagePart(BufferedImage receivedPart, int partIndex) {
        System.out.println("Recibiste la parte número: " + partIndex);
        System.out.println("Partes Recibidas: " + receivedParts.size());
        System.out.println("Partes Totales: " + totalParts);
        receivedParts.put(partIndex, receivedPart);

        if (receivedParts.size() == totalParts) {
            BufferedImage joinedImage = combineImageParts();
            ImageIcon filteredIcon = new ImageIcon(joinedImage);
            label.setIcon(filteredIcon);
            receivedParts.clear();
        }
    }
}

