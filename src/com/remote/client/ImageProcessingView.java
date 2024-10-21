package com.remote.client;

import com.remote.server.InterfaceServer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



public class ImageProcessingView extends javax.swing.JFrame implements Runnable {

    private ImageProcessingClient client;
    private InterfaceServer server;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private Vector<String> listClients;
    private String name;
    private int numHilos;

    public ImageProcessingView(String name, InterfaceServer server) {
        initComponents();
        this.server = server;
        this.name = name;
        this.numHilos = 1;
        btnReiniciarSecuencial.setEnabled(false);
        btnReiniciarConcurrente.setEnabled(false);
        btnReiniciarParalelo.setEnabled(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Chat (" + name + ")");

        listClients = new Vector<>();
        listConnect.setListData(listClients);
        try {
            client = new ImageProcessingClient(name, server);
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        client.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("filteredImage".equals(evt.getPropertyName()) || "partImage".equals(evt.getPropertyName())) {
                    try {
                        ImageIcon newFilteredImage = client.getfilteredImage();
                        ImageIcon newPartImage = client.getpartImage();
                        updateUI(newFilteredImage, newPartImage);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ImageProcessingView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        Timer minuteur = new Timer();
        TimerTask tache = new TimerTask() {
            @Override
            public void run() {
                try {
                    int[] indices = listConnect.getSelectedIndices();
                    model.clear();
                    listClients = server.getListClients();
                    int i = 0;
                    while (i < listClients.size()) {
                        model.addElement(listClients.get(i));
                        i++;
                    }
                    listConnect.setModel(model);
                    listConnect.setSelectedIndices(indices);
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        };
        minuteur.schedule(tache, 0, 20000);
    }
    
    public void divideAndSendImage(BufferedImage image) {
        int partWidth = image.getWidth() / numHilos;
        int totalParts = numHilos;
        JoinedImage joinedImage = new JoinedImage(ImagenFiltradaConcurrente, totalParts);
        ProcesamientoHilo[] hilos = new ProcesamientoHilo[numHilos];
        for (int i = 0; i < numHilos; i++) {
            int startX = i * partWidth;
            int endX = (i + 1) * partWidth;
            if (i == numHilos - 1) {
                endX = image.getWidth();
            }
            BufferedImage imagePart = image.getSubimage(startX, 0, endX - startX, image.getHeight());
            hilos[i] = new ProcesamientoHilo(imagePart, i, joinedImage);
            hilos[i].start();
        }
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
                try {
                    Thread.sleep(1); // Pausa de 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
    
    public void updateUI(ImageIcon filteredImage, ImageIcon partImage) {
        ImagenFiltradaParalelo.setIcon(filteredImage);
        ImagenPartidaParalelo.setIcon(partImage);
    }
    
    public SerializableImage convertToSerializableImage(BufferedImage image) {
        return new SerializableImage(image);
    }
    
    public BufferedImage loadImage(String imagePath){
        File imageFile = new File(imagePath);
        try {
            return ImageIO.read(imageFile);
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessingView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Cliente = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listConnect = new javax.swing.JList<>();
        btnReiniciarParalelo = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnIniciarParalelo = new javax.swing.JButton();
        btnReiniciarSecuencial = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnIniciarConcurrente = new javax.swing.JButton();
        btnReiniciarConcurrente = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnIniciarSecuencial = new javax.swing.JButton();
        Secuencial = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        TiempoSecuencial = new javax.swing.JLabel();
        btnSeleccionarImagenSecuencial = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        ImagenFiltradaSecuencial = new javax.swing.JLabel();
        ImagenSecuencial = new javax.swing.JLabel();
        Concurrente = new javax.swing.JPanel();
        ImagenConcurrente = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        ImagenFiltradaConcurrente = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Hilos = new javax.swing.JLabel();
        btnSeleccionarImagenConcurrente = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        TiempoConcurrente = new javax.swing.JLabel();
        btnIncrementarHilos = new javax.swing.JButton();
        btnDecrementarHilos = new javax.swing.JButton();
        Paralelo = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ImagenParalelo = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        ImagenFiltradaParalelo = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        ImagenPartidaParalelo = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        TiempoParalelo = new javax.swing.JLabel();
        btnSeleccionarImagenParalelo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Cliente.setBackground(new java.awt.Color(155, 0, 242));
        Cliente.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(153, 153, 153)));
        Cliente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Paralelo:");
        Cliente.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 100, -1));

        listConnect.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        listConnect.setForeground(new java.awt.Color(0, 255, 0));
        listConnect.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listConnect.setToolTipText("");
        jScrollPane1.setViewportView(listConnect);

        Cliente.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 230, 122));

        btnReiniciarParalelo.setBackground(new java.awt.Color(116, 235, 242));
        btnReiniciarParalelo.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnReiniciarParalelo.setForeground(new java.awt.Color(255, 255, 255));
        btnReiniciarParalelo.setText("Reiniciar");
        btnReiniciarParalelo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReiniciarParalelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarParaleloActionPerformed(evt);
            }
        });
        Cliente.add(btnReiniciarParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 230, -1));

        jLabel6.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Secuencial:");
        Cliente.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 130, -1));

        btnIniciarParalelo.setBackground(new java.awt.Color(116, 235, 242));
        btnIniciarParalelo.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnIniciarParalelo.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciarParalelo.setText("Iniciar");
        btnIniciarParalelo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnIniciarParalelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarParaleloActionPerformed(evt);
            }
        });
        Cliente.add(btnIniciarParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 230, -1));

        btnReiniciarSecuencial.setBackground(new java.awt.Color(116, 132, 242));
        btnReiniciarSecuencial.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnReiniciarSecuencial.setForeground(new java.awt.Color(255, 255, 255));
        btnReiniciarSecuencial.setText("Reiniciar");
        btnReiniciarSecuencial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReiniciarSecuencial.setFocusPainted(false);
        btnReiniciarSecuencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarSecuencialActionPerformed(evt);
            }
        });
        Cliente.add(btnReiniciarSecuencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 230, -1));

        jLabel7.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Concurrente:");
        Cliente.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 140, -1));

        btnIniciarConcurrente.setBackground(new java.awt.Color(116, 194, 242));
        btnIniciarConcurrente.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnIniciarConcurrente.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciarConcurrente.setText("Iniciar");
        btnIniciarConcurrente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnIniciarConcurrente.setFocusPainted(false);
        btnIniciarConcurrente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarConcurrenteActionPerformed(evt);
            }
        });
        Cliente.add(btnIniciarConcurrente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 230, -1));

        btnReiniciarConcurrente.setBackground(new java.awt.Color(116, 194, 242));
        btnReiniciarConcurrente.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnReiniciarConcurrente.setForeground(new java.awt.Color(255, 255, 255));
        btnReiniciarConcurrente.setText("Reiniciar");
        btnReiniciarConcurrente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReiniciarConcurrente.setFocusPainted(false);
        btnReiniciarConcurrente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarConcurrenteActionPerformed(evt);
            }
        });
        Cliente.add(btnReiniciarConcurrente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 230, -1));

        jLabel8.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("CLIENTE");
        Cliente.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 100, -1));

        jLabel9.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Usuarios Conectados");
        Cliente.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 210, -1));

        btnIniciarSecuencial.setBackground(new java.awt.Color(116, 132, 242));
        btnIniciarSecuencial.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnIniciarSecuencial.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciarSecuencial.setText("Iniciar");
        btnIniciarSecuencial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnIniciarSecuencial.setFocusPainted(false);
        btnIniciarSecuencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSecuencialActionPerformed(evt);
            }
        });
        Cliente.add(btnIniciarSecuencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 230, -1));

        getContentPane().add(Cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 710));

        Secuencial.setBackground(new java.awt.Color(116, 132, 242));
        Secuencial.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Secuencial.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Imagen Actual:");
        Secuencial.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 160, -1));

        jLabel14.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("SECUENCIAL");
        Secuencial.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 150, -1));

        jLabel16.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Tiempo:");
        Secuencial.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 80, -1));

        TiempoSecuencial.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        TiempoSecuencial.setForeground(new java.awt.Color(255, 255, 255));
        TiempoSecuencial.setText("00:00 ms");
        Secuencial.add(TiempoSecuencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 620, 190, -1));

        btnSeleccionarImagenSecuencial.setBackground(new java.awt.Color(116, 132, 242));
        btnSeleccionarImagenSecuencial.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnSeleccionarImagenSecuencial.setForeground(new java.awt.Color(255, 255, 255));
        btnSeleccionarImagenSecuencial.setText("Seleccionar Imagen");
        btnSeleccionarImagenSecuencial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSeleccionarImagenSecuencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarImagenSecuencialActionPerformed(evt);
            }
        });
        Secuencial.add(btnSeleccionarImagenSecuencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 650, 350, 40));

        jLabel18.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Imagen con filtro:");
        Secuencial.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 200, -1));

        ImagenFiltradaSecuencial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Secuencial.add(ImagenFiltradaSecuencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 350, 230));

        ImagenSecuencial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Secuencial.add(ImagenSecuencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 350, 230));

        getContentPane().add(Secuencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 370, 710));

        Concurrente.setBackground(new java.awt.Color(116, 194, 242));
        Concurrente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Concurrente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ImagenConcurrente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Concurrente.add(ImagenConcurrente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 350, 230));

        jLabel15.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("CONCURRENTE");
        Concurrente.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 160, -1));

        jLabel19.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Imagen Actual:");
        Concurrente.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 160, -1));

        jLabel20.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Imagen con filtro:");
        Concurrente.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 200, -1));

        ImagenFiltradaConcurrente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Concurrente.add(ImagenFiltradaConcurrente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 350, 200));

        jLabel22.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Hilos:");
        Concurrente.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 590, 70, -1));

        Hilos.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        Hilos.setForeground(new java.awt.Color(255, 255, 255));
        Hilos.setText(" 1");
        Concurrente.add(Hilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 590, 30, 30));

        btnSeleccionarImagenConcurrente.setBackground(new java.awt.Color(116, 194, 242));
        btnSeleccionarImagenConcurrente.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnSeleccionarImagenConcurrente.setForeground(new java.awt.Color(255, 255, 255));
        btnSeleccionarImagenConcurrente.setText("Seleccionar Imagen");
        btnSeleccionarImagenConcurrente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSeleccionarImagenConcurrente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarImagenConcurrenteActionPerformed(evt);
            }
        });
        Concurrente.add(btnSeleccionarImagenConcurrente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 650, 350, 40));

        jLabel24.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Tiempo:");
        Concurrente.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 80, -1));

        TiempoConcurrente.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        TiempoConcurrente.setForeground(new java.awt.Color(255, 255, 255));
        TiempoConcurrente.setText("00:00 ms");
        Concurrente.add(TiempoConcurrente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 620, 160, -1));

        btnIncrementarHilos.setBackground(new java.awt.Color(116, 194, 242));
        btnIncrementarHilos.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnIncrementarHilos.setForeground(new java.awt.Color(255, 255, 255));
        btnIncrementarHilos.setText("+");
        btnIncrementarHilos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnIncrementarHilos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncrementarHilosActionPerformed(evt);
            }
        });
        Concurrente.add(btnIncrementarHilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 590, 50, -1));

        btnDecrementarHilos.setBackground(new java.awt.Color(116, 194, 242));
        btnDecrementarHilos.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnDecrementarHilos.setForeground(new java.awt.Color(255, 255, 255));
        btnDecrementarHilos.setText("-");
        btnDecrementarHilos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDecrementarHilos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDecrementarHilosActionPerformed(evt);
            }
        });
        Concurrente.add(btnDecrementarHilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 590, 50, -1));

        getContentPane().add(Concurrente, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, 372, 710));

        Paralelo.setBackground(new java.awt.Color(116, 235, 242));
        Paralelo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Paralelo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PARALELO");
        Paralelo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 120, -1));

        jLabel10.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Imagen Actual:");
        Paralelo.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 160, -1));

        ImagenParalelo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Paralelo.add(ImagenParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 360, 170));

        jLabel11.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Imagen con filtro:");
        Paralelo.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 200, -1));

        ImagenFiltradaParalelo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Paralelo.add(ImagenFiltradaParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 360, 130));

        jLabel21.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Parte de la Imagen:");
        Paralelo.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 210, -1));

        ImagenPartidaParalelo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Paralelo.add(ImagenPartidaParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 360, 170));

        jLabel12.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Tiempo:");
        Paralelo.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 80, -1));

        TiempoParalelo.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        TiempoParalelo.setForeground(new java.awt.Color(255, 255, 255));
        TiempoParalelo.setText("00:00 ms");
        Paralelo.add(TiempoParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 630, 140, -1));

        btnSeleccionarImagenParalelo.setBackground(new java.awt.Color(116, 235, 242));
        btnSeleccionarImagenParalelo.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        btnSeleccionarImagenParalelo.setForeground(new java.awt.Color(255, 255, 255));
        btnSeleccionarImagenParalelo.setText("Seleccionar Imagen");
        btnSeleccionarImagenParalelo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSeleccionarImagenParalelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarImagenParaleloActionPerformed(evt);
            }
        });
        Paralelo.add(btnSeleccionarImagenParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 650, 360, 40));

        getContentPane().add(Paralelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 0, 380, 710));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarSecuencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSecuencialActionPerformed
        // TODO add your handling code here:
        Icon icon = ImagenSecuencial.getIcon();
        if (icon != null) {
            String imageUrl = icon.toString();
            try {
                long startTime = System.currentTimeMillis();
                BufferedImage loadedImage = loadImage(imageUrl);
                BufferedImage filteredImage = processImage(loadedImage);
                ImageIcon filteredIcon = new ImageIcon(filteredImage);
                ImagenFiltradaSecuencial.setIcon(filteredIcon);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                long segundos = elapsedTime / 1000;
                long minutos = segundos / 60;
                segundos = segundos % 60;
                String tiempoFormateado = String.format("%d min %d seg", minutos, segundos);
                TiempoSecuencial.setText(tiempoFormateado);
                btnIniciarSecuencial.setEnabled(false);
                btnReiniciarSecuencial.setEnabled(true);
            } catch (Exception e) {
                Logger.getLogger(ImageProcessingView.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"No hay imagen seleccionada en Secuencial","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnIniciarSecuencialActionPerformed

    private void btnReiniciarSecuencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarSecuencialActionPerformed
        // TODO add your handling code here:
        ImagenSecuencial.setIcon(null);
        ImagenFiltradaSecuencial.setIcon(null);
        btnIniciarSecuencial.setEnabled(true);
        btnReiniciarSecuencial.setEnabled(false);
        TiempoSecuencial.setText("");
    }//GEN-LAST:event_btnReiniciarSecuencialActionPerformed

    private void btnSeleccionarImagenSecuencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarImagenSecuencialActionPerformed
        // TODO add your handling code here:
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showOpenDialog(this);
      if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
          ImagenSecuencial.setIcon(imageIcon);
      }
    }//GEN-LAST:event_btnSeleccionarImagenSecuencialActionPerformed

    private void btnSeleccionarImagenConcurrenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarImagenConcurrenteActionPerformed
        // TODO add your handling code here:
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showOpenDialog(this);
      if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
          ImagenConcurrente.setIcon(imageIcon);
      }
    }//GEN-LAST:event_btnSeleccionarImagenConcurrenteActionPerformed

    private void btnDecrementarHilosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDecrementarHilosActionPerformed
        // TODO add your handling code here:
        if(numHilos > 1){
            numHilos = numHilos - 1;
            Hilos.setText(Integer.toString(numHilos));
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"Minimo debe haber un hilo","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDecrementarHilosActionPerformed

    private void btnIncrementarHilosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncrementarHilosActionPerformed
        // TODO add your handling code here:
        if(numHilos < 5){
            numHilos = numHilos + 1;
            Hilos.setText(Integer.toString(numHilos));
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"Maximo puede haber 5 hilos","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnIncrementarHilosActionPerformed

    private void btnIniciarConcurrenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarConcurrenteActionPerformed
        // TODO add your handling code here:
        Icon icon = ImagenConcurrente.getIcon();
        if (icon != null) {
            String imageUrl = icon.toString();
            try {
                long startTime = System.currentTimeMillis();
                BufferedImage loadedImage = loadImage(imageUrl);
                BufferedImage filteredImage = processImage(loadedImage);
                divideAndSendImage(filteredImage);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                long segundos = elapsedTime / 1000;
                long minutos = segundos / 60;
                segundos = segundos % 60;
                String tiempoFormateado = String.format("%d min %d seg", minutos, segundos);
                TiempoConcurrente.setText(tiempoFormateado);
                btnIniciarConcurrente.setEnabled(false);
                btnReiniciarConcurrente.setEnabled(true);
            } catch (Exception e) {
                Logger.getLogger(ImageProcessingView.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"No hay imagen seleccionada en Concurrente","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnIniciarConcurrenteActionPerformed

    private void btnReiniciarConcurrenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarConcurrenteActionPerformed
        ImagenConcurrente.setIcon(null);
        ImagenFiltradaConcurrente.setIcon(null);
        btnIniciarConcurrente.setEnabled(true);
        btnReiniciarConcurrente.setEnabled(false);
        TiempoConcurrente.setText("");
    }//GEN-LAST:event_btnReiniciarConcurrenteActionPerformed

    private void btnSeleccionarImagenParaleloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarImagenParaleloActionPerformed
        // TODO add your handling code here:
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showOpenDialog(this);
      if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
          ImagenParalelo.setIcon(imageIcon);
      }
    }//GEN-LAST:event_btnSeleccionarImagenParaleloActionPerformed

    private void btnIniciarParaleloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarParaleloActionPerformed
        
        Icon icon = ImagenParalelo.getIcon();
        if (icon != null) {
            String imageUrl = icon.toString();
            try {
                long startTime = System.currentTimeMillis();
                BufferedImage loadedImage = loadImage(imageUrl);
                // Guardar la imagen en el sistema de archivos del servidor
                String serverImagePath = "src/prueba.jpg";  // Ruta relativa al lado del servidor
                File imageFile = new File(serverImagePath);
                ImageIO.write(loadedImage, "jpg", imageFile);
                server.divideAndSendImage((ImageIcon) icon);
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                long segundos = elapsedTime / 1000;
                long minutos = segundos / 60;
                segundos = segundos % 60;
                String tiempoFormateado = String.format("%d min %d seg", minutos, segundos);
                TiempoParalelo.setText(tiempoFormateado);
                btnIniciarParalelo.setEnabled(false);
                btnReiniciarParalelo.setEnabled(true);
            } catch (Exception e) {
                Logger.getLogger(ImageProcessingView.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"No hay imagen seleccionada en Concurrente","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnIniciarParaleloActionPerformed

    private void btnReiniciarParaleloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarParaleloActionPerformed
                
    ImagenParalelo.setIcon(null);
    ImagenPartidaParalelo.setIcon(null);
    ImagenFiltradaParalelo.setIcon(null);
    btnIniciarParalelo.setEnabled(true);
    btnReiniciarParalelo.setEnabled(false);
    TiempoParalelo.setText("");
    }//GEN-LAST:event_btnReiniciarParaleloActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cliente;
    private javax.swing.JPanel Concurrente;
    private javax.swing.JLabel Hilos;
    private javax.swing.JLabel ImagenConcurrente;
    private javax.swing.JLabel ImagenFiltradaConcurrente;
    private javax.swing.JLabel ImagenFiltradaParalelo;
    private javax.swing.JLabel ImagenFiltradaSecuencial;
    private javax.swing.JLabel ImagenParalelo;
    private javax.swing.JLabel ImagenPartidaParalelo;
    private javax.swing.JLabel ImagenSecuencial;
    private javax.swing.JPanel Paralelo;
    private javax.swing.JPanel Secuencial;
    private javax.swing.JLabel TiempoConcurrente;
    private javax.swing.JLabel TiempoParalelo;
    private javax.swing.JLabel TiempoSecuencial;
    private javax.swing.JButton btnDecrementarHilos;
    private javax.swing.JButton btnIncrementarHilos;
    private javax.swing.JButton btnIniciarConcurrente;
    private javax.swing.JButton btnIniciarParalelo;
    private javax.swing.JButton btnIniciarSecuencial;
    private javax.swing.JButton btnReiniciarConcurrente;
    private javax.swing.JButton btnReiniciarParalelo;
    private javax.swing.JButton btnReiniciarSecuencial;
    private javax.swing.JButton btnSeleccionarImagenConcurrente;
    private javax.swing.JButton btnSeleccionarImagenParalelo;
    private javax.swing.JButton btnSeleccionarImagenSecuencial;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listConnect;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        try {
            model.clear();
            listClients = server.getListClients();
            int i = 0;
            while (i < listClients.size()) {
                model.addElement(listClients.get(i));
                i++;
            }
            listConnect.setModel(model);
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
