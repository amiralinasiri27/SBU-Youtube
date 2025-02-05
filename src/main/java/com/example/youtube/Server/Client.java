package com.example.youtube.Server;

import com.example.youtube.Model.*;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import com.google.gson.Gson;

public class Client {
    private static final Gson gson = new Gson();
    private static final int SERVER_PORT = 3000;
    private final String SERVER_IP;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    public Client(String IP) throws IOException{
        SERVER_IP=IP;
        createImagesDirectory();
        createVideosDirectory();
        try {
            this.socket = new Socket(SERVER_IP, SERVER_PORT);
            // Create DataOutputStream for sending requests to the server
            this.out = new DataOutputStream(socket.getOutputStream());
            // Create DataInputStream for reading responses from the server
            this.in= new DataInputStream(socket.getInputStream());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void createImagesDirectory(){ //create folder for saving images
        String folderPath = "C:\\Users\\Asus\\Desktop\\YouTube\\YOUTUBE\\src\\main\\resources\\com\\example\\youtube\\clientImages";
        Path path = Paths.get(folderPath);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createVideosDirectory(){ //create folder for saving images
        String folderPath = "C:\\Users\\Asus\\Desktop\\YouTube\\YOUTUBE\\src\\main\\resources\\com\\example\\youtube\\clientVideos";
        Path path = Paths.get(folderPath);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void deleteFoldersOnExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            String folderPath = "C:\\Users\\Asus\\Desktop\\YouTube\\YOUTUBE\\src\\main\\resources\\com\\example\\youtube\\clientImages";
            Path imagePath=Paths.get(folderPath);
            try {
                Files.walk(imagePath)
                        .sorted((path1, path2) -> path2.compareTo(path1))
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                System.out.println("Folder and files deleted: " + imagePath.toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            folderPath = "C:\\Users\\Asus\\Desktop\\YouTube\\YOUTUBE\\src\\main\\resources\\com\\example\\youtube\\clientVideos";
            Path videoPath=Paths.get(folderPath);
            try {
                Files.walk(videoPath)
                        .sorted((path1, path2) -> path2.compareTo(path1))
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                System.out.println("Folder and files deleted: " + videoPath.toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
    public void closeConnection(){
        try {
            // Close input and output streams and the client socket when done
            System.out.println("in this is close connection in line 48 client ");
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean sendVideoBytes(String path) throws IOException {
        File videoFile=new File(path);
        if (!videoFile.exists()) {
            return false;
        }
        try(FileInputStream fileInputStream = new FileInputStream(videoFile);) {
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
            return true;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean sendImageBytes(String path) throws IOException {
        File imageFile=new File(path);
        if (!imageFile.exists()) {
            return false;
        }
        try(FileInputStream fileInputStream = new FileInputStream(imageFile)) {
            byte[] buffer = new byte[4 * 1024];
            int bytes;
            out.writeLong(imageFile.length());
            while ((bytes = fileInputStream.read(buffer)) != -1) {
                // Send the file to Server Socket
                out.write(buffer, 0, bytes);
                out.flush();
            }
            fileInputStream.close();
            return true;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean getVideoBytes(String videoID) throws IOException{
        //endpoint = 9
        out.writeUTF("9#"+videoID);
        FileOutputStream fos = new FileOutputStream("C:\\Users\\Asus\\Desktop\\YouTube\\YOUTUBE\\src\\main\\resources\\com\\example\\youtube\\clientVideos\\"+videoID +".mp4");
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
            return true;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }finally {
            fos.close();
        }
    }
    public boolean getImageBytes(String imageID) throws IOException, InterruptedException {
        //endpoint = 8
        FileOutputStream fos = new FileOutputStream("C:\\Users\\Asus\\Desktop\\YouTube\\YOUTUBE\\src\\main\\resources\\com\\example\\youtube\\clientImages\\" + imageID + ".jpg");

        try {
            long fileSize = in.readLong();
            byte[] buffer = new byte[4 * 1024];
            long totalBytesRead = 0;
            int bytesRead;
            while (totalBytesRead < fileSize && (bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }
            fos.flush();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            fos.close();
        }

    }
//        try { byte[] sizeAr = new byte[4];
//            in.read(sizeAr);
//            int size = ByteBuffer.wrap(sizeAr).getInt();
//
//            byte[] imageAr = new byte[size];
//            int totalBytesRead = 0;
//            while (totalBytesRead < size) {
//                int bytesRead = in.read(imageAr, totalBytesRead, size - totalBytesRead);
//                if (bytesRead == -1) {
//                    break;
//                }
//                totalBytesRead += bytesRead;
//            }
//
//            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
//            ImageIO.write(image, "jpg", new File("C:\\Users\\Asus\\Desktop\\YouTube\\YOUTUBE\\src\\main\\resources\\com\\example\\youtube\\clientImages\\"+imageID+ ".jpg"));
//            return true;
//        }catch (Exception e){
//            return false;}
//    }
    //send a request to get a User class and return response
    public User getUserRequest(String email, String passWord) throws IOException {
        try {
            String request = "1#11#"+email+"#"+passWord;
            out.writeUTF(request);
            String response = in.readUTF();
            return gson.fromJson(response,User.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    //send a request to get a Channel class and return response
    public Channel getChannelRequest(String identifier, int number) throws IOException {
        try {
            String request = "1#12#"+identifier+"#"+number;
            out.writeUTF(request);
            String response = in.readUTF();
            return gson.fromJson(response, Channel.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Comment> getVideoCommentsRequest(String videoID) throws IOException {
        try {
            String request = "1#13#"+videoID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Comment>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Comment> getChannelCommentsRequest(String channelID) throws IOException {
        try {
            String request = "1#14#"+channelID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Comment>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Comment> getUserCommentsRequest(String userID) throws IOException {
        try {
            String request = "1#15#"+userID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Comment>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<PlayList> getChannelPlaylistsRequest(String chennalID) throws IOException {
        try {
            String request = "1#16#"+chennalID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<PlayList>>() {}.getType();
            try {
                return gson.fromJson(response, listType);
            } catch (JsonSyntaxException e) {
                System.out.println("Error parsing JSON response: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Video> getChannelVideosRequest(String channelID) throws IOException {
        try {
            String request = "1#17#"+channelID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Video>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Video> getPlaylistVideosRequest(String plsylistID) throws IOException {
        try {
            String request = "1#18#"+plsylistID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Video>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Video> getVideosByCategoryRequest(String categoryID) throws IOException {
        try {
            String request = "1#19#"+categoryID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Video>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Video> getSavedVideosRequest(String userID) throws IOException {
        try {
            String request = "1#110#"+userID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Video>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<PlayList> getSavedPlaylistsRequest(String userID) throws IOException {
        try {
            String request = "1#111#"+userID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<PlayList>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Video> getVideoHistoryRequest(String userID) throws IOException {
        try {
            String request = "1#113#"+userID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Video>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Video> getVideoByRandomCategoryRequest(int numVideos,String userID) throws IOException {
        try {
            String request = "1#112#"+numVideos+"#"+userID;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Video>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Video> getSearchedVideosRequest(String word,String way) throws IOException {
        try {
            String request = "1#114#"+word+"#"+way;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Video>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<PlayList> getSearchedPlaylistsRequest(String word,String way) throws IOException {
        try {
            String request = "1#116#"+word+"#"+way;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<PlayList>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Channel> getSearchedChannelsRequest(String word,String way) throws IOException {
        try {
            String request = "1#115#"+word+"#"+way;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Channel>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Channel> countVideoLikeRequest(String videoID,String karmaType) throws IOException {
        try {
            String request = "1#117#"+videoID+"#"+karmaType;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Channel>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public ArrayList<Channel> countShortLikeRequest(String shortID,String karmaType) throws IOException {
        try {
            String request = "1#117#"+shortID+"#"+karmaType;
            out.writeUTF(request);
            String response = in.readUTF();
            Type listType = new TypeToken<ArrayList<Channel>>() {}.getType();
            return gson.fromJson(response, listType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //TODO get folowers




    //TODO get follwing


    /***
     *
     * @param user
     * @return
     * @throws IOException
     */
// this make a request to create a new user and return server response
public Boolean addUserRequest(User user) throws IOException{
    try {
        String request="2#21#"+gson.toJson(user);
        out.writeUTF(request);
        String response=in.readUTF();
        if(response.equals("1"))
            return true;
        else
            return false;
    }catch (IOException e){
        System.out.println(e.getMessage());
        return false;
    }
}
    // this make a request to create a new channel and return server response
    public boolean addChannelRequest(Channel channel) throws IOException{
        try {
            String request="2#22#"+gson.toJson(channel);
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    // this make a request to create a new comment and return server response
    public boolean addCommentRequest(Comment comment) throws IOException{
        try {
            String request="2#23#"+gson.toJson(comment);
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addPlaylistRequest(PlayList playList) throws IOException{
        try {
            String request="2#24#"+gson.toJson(playList);
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addVideoRequest(Video video) throws IOException{
        try {
            String request="2#25#"+gson.toJson(video);
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addVideoToHistoryRequest(String videoID, String userID) throws IOException{
        try {
            String request="2#26#"+videoID+"#"+userID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addFollowerOrFollowingRequest(String userID, String channelID,int identifier) throws IOException{
        try {
            String request="2#27#"+userID+"#"+channelID+"#"+identifier;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addKarmaRequest(int karma, String userID,String videoID) throws IOException{
        try {
            String request="2#28#"+karma+"#"+userID+"#"+videoID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addVideoToSavedRequest(String videoID,String userID) throws IOException{
        try {
            String request="2#29#"+videoID+"#"+userID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addPlaylistToSavedRequest(String playlistID,String userID) throws IOException{
        try {
            String request="2#210#"+playlistID+"#"+userID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /***
     * Delete request methods
     */

    public boolean deleteVideoRequest(String videoID,String channelID) throws IOException{
        try {
            String request="3#31#"+videoID+"#"+channelID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean deletePlaylistRequest(String playlistID) throws IOException{
        try {
            String request="3#32#"+playlistID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean deleteCommentRequest(String videoID,String commentID) throws IOException{
        try {
            String request="3#33#"+videoID+"#"+commentID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean unfollowRequest(String userID,String channelID,int identifier) throws IOException{
        try {
            String request="3#34#"+userID+"#"+channelID+"#"+identifier;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean deleteVideoCommentsRequest(String videoID) throws IOException{
        try {
            String request="3#35#"+videoID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    //TODO delete from history api and request

    /***
     * update requests methods
     */

    public boolean editPasswordRequest(String userID,String newPassword) throws IOException{
        try {
            String request="4#41#"+userID+"#"+newPassword;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean editUsernameRequest(String exUsername,String newUsername) throws IOException{
        try {
            String request="4#42#"+exUsername+"#"+newUsername;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean editPlaylistNameRequest(String name,String playlistID) throws IOException{
        try {
            String request="4#43#"+name+"#"+playlistID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean updateCommentLikeRequest(String commentID) throws IOException{
        try {
            String request="4#44#"+ commentID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean editCommentTextRequest(String commentID,String newText) throws IOException{
        try {
            String request="4#45#"+commentID+"#"+newText;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean updateDeslikeRequest(String commentID) throws IOException{
        try {
            String request="4#46#"+commentID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean editChannelNameRequest(String channelID,String newName) throws IOException{
        try {
            String request="4#47#"+channelID+"#"+newName;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean editChannelDescriptionRequest(String newDescription,String channelID) throws IOException{
        try {
            String request="4#48#"+newDescription+"#"+channelID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean editChannelLinksRequest(String newLinks,String channelID) throws IOException{
        try {
            String request="4#49#"+newLinks+"#"+channelID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean updateVideoViewsRequest(String videoID,String channelID) throws IOException{
        try {
            String request="4#410#"+videoID+"#"+channelID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean updateVideoLikesRequest(String videoID,String channelID) throws IOException{
        try {
            String request="4#411#"+videoID+"#"+channelID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean updateVideoDeslikesRequest(String videoID,String channelID) throws IOException{
        try {
            String request="4#412#"+videoID+"#"+channelID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    /***
     * checking request
     */
    public boolean checkingEmailRequest(String email) throws IOException{
        try {
            String request="5#51#"+email;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean checkingUsernameRequest(String username) throws IOException{
        try {
            String request="5#52#"+username;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean checkingUserExistsRequest(String email,String password) throws IOException{
        try {
            String request="5#53#"+email+"#"+password;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean checkingVideoSavingRequest(String videoID,String userID) throws IOException{
        try {
            String request="5#54#"+videoID+"#"+userID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean checkingPlaylistSavingRequest(String playlistID,String userID) throws IOException{
        try {
            String request="5#55#"+playlistID+"#"+userID;
            out.writeUTF(request);
            String response=in.readUTF();
            if(response.equals("1"))
                return true;
            else
                return false;
        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
