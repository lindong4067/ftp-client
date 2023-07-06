package com.lindong.ftp.client;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class App {
    public static void main(String[] args) {

        String server = "ftp.example.com";
        int port = 21;
        String username = "your-username";
        String password = "your-password";
        String localFilePath = "/path/to/local/file.zip";
        String remoteFilePath = "/path/to/remote/file.zip";
        boolean passive = true;

        if (args.length == 0) {
            System.out.println("Usage: java -jar ftp-client-3.0.jar -s ftp.example.com -P 21 -u your-username -p your-password -l /path/to/local/file.zip -r /path/to/remote/file.zip -a");
            System.exit(0);
        }

        for (int base = 0; base < args.length; base++) {
            if (args[base].equals("-s")) {
                server = args[++base];
            } else if (args[base].equals("-P")) {
                port = Integer.valueOf(args[++base]);
            } else if (args[base].equals("-u")) {
                username = args[++base];
            } else if (args[base].equals("-p")) {
                password = args[++base];
            } else if (args[base].equals("-l")) {
                localFilePath = args[++base];
            } else if (args[base].equals("-r")) {
                remoteFilePath = args[++base];
            } else if (args[base].equals("-a")) {
                passive = false;
            }
        }

        System.out.println("server: " + server + ", port: " + port + ", username: " + username + ", password: "
                + password + ", local: " + localFilePath + ", remote: " + remoteFilePath + ", passive: " + passive);

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("FTP server refused connection.");
                return;
            }

            boolean success = ftpClient.login(username, password);
            if (!success) {
                System.out.println("Unable to log into FTP server.");
                return;
            }

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(localFilePath);
            FileInputStream inputStream = new FileInputStream(localFile);
            if (passive) {
                ftpClient.enterLocalPassiveMode();
            }

            success = ftpClient.storeFile(remoteFilePath, inputStream);
            inputStream.close();

            if (success) {
                System.out.println("File uploaded successfully.");
            } else {
                replyCode = ftpClient.getReplyCode();
                String replyString = ftpClient.getReplyString();
                System.out.println("File upload failed, reply code: " + replyCode + ", " + replyString);
            }

            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
