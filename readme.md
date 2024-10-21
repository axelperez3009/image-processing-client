# Distributed Image Processing Application

## Overview

This Java-based application demonstrates a distributed approach to image processing using Remote Method Invocation (RMI). It allows multiple clients to connect to a server, select images, and apply various image processing techniques using sequential, concurrent, and parallel processing methods.

## Features

- Client-server architecture using Java RMI
- Three processing modes: sequential, concurrent, and parallel
- Grayscale filter application
- Adjustable number of threads for concurrent processing
- Performance comparison between different processing modes
- Client management system on the server

## Requirements

- Java Development Kit (JDK) 8 or higher
- Java Runtime Environment (JRE)

## Project Structure

The project consists of the following main components:

1. `SerializableImage`: A wrapper for BufferedImage to make it serializable for RMI
2. `InterfaceServer`: Remote interface for server-side methods
3. `ThreadProcessing`: Thread class for parallel image processing
4. `LoginView`: GUI for client login and server connection
5. `JoinedImage`: Class for combining processed image parts
6. `InterfaceClient`: Remote interface for client-side methods
7. `ImageProcessingView`: Main GUI for image processing operations
8. `ImageProcessingClient`: Implementation of client-side logic

## Setup and Running

1. Compile all Java files in the project.
2. Start the RMI registry:
   ```
   rmiregistry
   ```
3. Start the server:
   ```
   java ServerMain
   ```
4. Start the client:
   ```
   java ClientMain
   ```

## Usage

1. Launch the client application.
2. Enter the server IP address and a unique username to connect.
3. Select an image file to process.
4. Choose a processing mode (sequential, concurrent, or parallel).
5. Adjust the number of threads for concurrent processing (if applicable).
6. Click the "Process" button to apply the grayscale filter.
7. View the processed image and performance metrics.

## Performance Comparison

The application allows users to compare the performance of different processing modes:

- Sequential: Processes the entire image on a single thread.
- Concurrent: Divides the image into parts and processes them using multiple threads on the client.
- Parallel: Distributes image parts to multiple connected clients for processing.

Time measurements for each mode are displayed, allowing users to evaluate the efficiency of each approach.

