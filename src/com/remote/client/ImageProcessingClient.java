package com.remote.client;

import com.remote.server.InterfaceServer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageProcessingClient extends UnicastRemoteObject implements InterfaceClient {
    private final InterfaceServer server;
    private final String name;
    public ImageIcon filteredImage;
    public ImageIcon partImage;
    private PropertyChangeSupport propertyChangeSupport;
        
    public ImageProcessingClient(String name, InterfaceServer server) throws RemoteException {
        this.name = name;
        this.server = server;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        server.addClient(this);
    }

   
    @Override
    public String getName() throws RemoteException {
        return name;
    }
    
    @Override
    public void setfilteredImage(ImageIcon icon) throws RemoteException {
        ImageIcon oldIcon = this.filteredImage;
        this.filteredImage = icon;
        propertyChangeSupport.firePropertyChange("filteredImage", oldIcon, icon);
    }

    @Override
    public void setpartImage(ImageIcon icon) throws RemoteException {
        ImageIcon oldIcon = this.partImage;
        this.partImage = icon;
        propertyChangeSupport.firePropertyChange("partImage", oldIcon, icon);
    }
    
    @Override
    public ImageIcon getfilteredImage() throws RemoteException {
        return filteredImage;
    }
    
    @Override
    public ImageIcon getpartImage() throws RemoteException {
        return partImage;
    }
    
    @Override
    public BufferedImage loadImage(String imagePath) throws RemoteException {
        try {
            // Cargar la imagen remota utilizando ImageIO
            URL imageUrl = new URL(imagePath);
            BufferedImage image = ImageIO.read(imageUrl);

            return image;
        } catch (IOException e) {
            throw new RemoteException("Error al cargar la imagen remota: " + e.getMessage());
        }
    }

    @Override
    public BufferedImage applyFilter(BufferedImage image, String filterName) throws RemoteException {
        try {
            // Aplicar un filtro a la imagen utilizando Java 2D API
            BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = filteredImage.createGraphics();
            
            // Aplicar el filtro deseado
            if (filterName.equals("Blur")) {
                float[] blurKernel = {
                        0.0625f, 0.125f, 0.0625f,
                        0.125f,  0.25f,  0.125f,
                        0.0625f, 0.125f, 0.0625f
                };
                Kernel blur = new Kernel(3, 3, blurKernel);
                ConvolveOp blurOp = new ConvolveOp(blur);
                g2d.drawImage(image, blurOp, 0, 0);
            }
            // Otros filtros

            g2d.dispose();

            return filteredImage;
        } catch (Exception e) {
            throw new RemoteException("Error al aplicar el filtro: " + e.getMessage());
        }
    }
    @Override
    public BufferedImage splitImage(BufferedImage image, boolean isFirstHalf) throws RemoteException {
        try {
            int width = image.getWidth();
            int height = image.getHeight();
            int splitPoint = width / 2;

            BufferedImage splitImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = splitImage.createGraphics();

            if (isFirstHalf) {
                g2d.drawImage(image, 0, 0, splitPoint, height, 0, 0, splitPoint, height, null);
            } else {
                g2d.drawImage(image, splitPoint, 0, width, height, splitPoint, 0, width, height, null);
            }

            g2d.dispose();

            return splitImage;
        } catch (Exception e) {
            throw new RemoteException("Error al dividir la imagen: " + e.getMessage());
        }
    }
    @Override
    public void receiveImagePart(ImageIcon icon, int partIndex) throws RemoteException {
        BufferedImage image = convertImageIconToBufferedImage(icon);
        System.out.println("Se recibio la imagen correctamente");
        try { 
            BufferedImage procesedImage = processImagetoGrayScale(image);
            ImageIcon imageIcon = new ImageIcon(procesedImage);
            server.receiveImagePart(imageIcon, name, partIndex);
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessingClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BufferedImage processImagetoGrayScale(BufferedImage image) {
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
    
    public static BufferedImage convertImageIconToBufferedImage(ImageIcon icon) {
        // Obtener el ancho y alto del ImageIcon
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();

        // Crear un BufferedImage con el mismo ancho y alto del ImageIcon
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Obtener el Graphics2D del BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();

        // Dibujar el ImageIcon en el BufferedImage
        g2d.drawImage(icon.getImage(), 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }
    
    public int convertToGrayscale(int pixel) {
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;
        int grayscale = (red + green + blue) / 3;
        int newPixel = (grayscale << 16) | (grayscale << 8) | grayscale;
        return newPixel;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
