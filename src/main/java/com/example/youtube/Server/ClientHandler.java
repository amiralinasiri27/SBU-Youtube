package com.example.youtube.Server;

import com.example.youtube.Server.API.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
public class ClientHandler implements Runnable {
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
    }

    public void send(String response) throws IOException{
        out.writeUTF(response);
    }
    @Override
    public void run() {
            try {
                while (true) {

                    String[] request = in.readUTF().split("#", 2);
                    String response = "";
                    switch (request[0]) {
                        case "1":
                            response = getApiService.handleRequest(request[1]);
                            break;
                        case "2":
                            response = addApiService.handleRequest(request[1]);
                            break;
                        case "3":
                            response = deleteApiService.handleRequest(request[1]);
                            break;
                        case "4":
                            response = updateApiService.handleRequest(request[1]);
                            break;
                        case "5":
                            response = checkApiService.handleRequest(request[1]);
                            break;
                        case "6":
                            getImageBytes(request[1]);
                            break;
                        case "7":
                            getVideoBytes(request[1]);
                            break;
                        case "8":
                            sendImageBytes(request[1]);
                        case "9":
                            sendVideoBytes(request[1]);
                        default:
                            response = "0";
                            break;
                    }

                    send(response);
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }finally {
                try {
                    // Close input and output streams and the client socket when done
                    in.close();
                    out.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    public void sendVideoBytes(String videoID) throws IOException {
        File videoFile=new File("C:\\Users\\Asus\\Desktop\\videos\\"+videoID+".mp4");
        if (!videoFile.exists()) {
            out.writeInt(0);
            out.flush();
            return;
        }
        try(FileInputStream fileInputStream = new FileInputStream(videoFile)) {
            //Divide the video into 1024*4 byte arrays and send them to client.
            byte[] buffer = new byte[4 * 1024];
            int bytes;
            out.writeLong(videoFile.length());
            while ((bytes = fileInputStream.read(buffer))
                    != -1) {
                // Send the file to Server Socket
                out.write(buffer, 0, bytes);
                out.flush();
            }
            fileInputStream.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
    public void sendImageBytes(String imageID) throws IOException {
//        File imageFile=new File("C:\\Users\\ASUS\\Desktop\\images\\"+imageID+".jpg"); //TODO
//        if (!imageFile.exists()){
//            // if file doesn't exist
//            out.writeInt(0);
//            out.flush();
//            return;
//        }
//        try(FileInputStream fileInputStream = new FileInputStream(imageFile)) {
//            //Divide the image into 1024*4 byte arrays and send them to client.
//            byte[] buffer = new byte[4 * 1024];
//            int bytes;
//            out.writeLong(imageFile.length());
//            while ((bytes = fileInputStream.read(buffer)) != -1) {
//                // Send the file to Server Socket
//                out.write(buffer, 0, bytes);
//                out.flush();
//            }
//            fileInputStream.close();
//        }
//        catch (IOException e){
//            System.out.println(e.getMessage());
//        }
        try {
            System.out.println("image is here");
            BufferedImage image = ImageIO.read(new File("C:\\Users\\ASUS\\Desktop\\images\\"+imageID+".jpg"));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);

            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            out.write(size);
            out.write(byteArrayOutputStream.toByteArray());
            out.flush();
            Thread.sleep(1000); // برای تست تأخیر
        }catch (Exception e){
            System.out.println(e.getMessage());}
    }
    //The video is received from the client in byte arrays of length 4*1024
    public void getVideoBytes(String videoID) throws IOException{

        FileOutputStream fos = new FileOutputStream("C:\\Users\\ASUS\\Desktop\\videos\\"+videoID + ".mp4");
        try {
            long fileSize = in.readLong();
            byte[] buffer = new byte[4*1024];
            long totalBytesRead = 0;
            int bytesRead;
            while (totalBytesRead < fileSize && (bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }
            fos.flush();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            fos.close();
        }
    }
    //The image is received from the client in byte arrays of length 4*1024
    public void getImageBytes(String imageID) throws IOException{
        FileOutputStream fos = new FileOutputStream("C:\\Users\\ASUS\\Desktop\\images\\"+imageID + ".jpg");
        try {
            long fileSize = in.readLong();
            byte[] buffer = new byte[4*1024];
            long totalBytesRead = 0;
            int bytesRead;
            while (totalBytesRead < fileSize && (bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }
            fos.flush();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }finally {
            fos.close();
        }
    }
}
