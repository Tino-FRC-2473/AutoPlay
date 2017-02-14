package org.usfirst.frc.team4950.robot.autoplay.clients;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class ReceiverClient {
    public static void main(String[] args) {
        int port_number = 8080;
        File f;
        FileWriter writer;

        try (Socket s = new Socket("RoboRIO-4950-FRC.local", port_number)) {
            try (InputStream in = s.getInputStream()) {
                try (Scanner scan = new Scanner(in)) {
                    System.out.print("connected");
                    f = new File("./moments.txt");
                    writer = new FileWriter(f);

                    //Gets values from the robot and puts it into a map
                    while (scan.hasNextLine()) {
                        writer.write(scan.nextLine() + "\n");
                    }
                    writer.close();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}