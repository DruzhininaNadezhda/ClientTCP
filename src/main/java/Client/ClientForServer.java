package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientForServer {
    final String host = "localhost";
    final int port = 22222;
    private String message = "";
    public void message() { // для записи из консоли
        Scanner in = new Scanner(System.in);
        message = in.nextLine();
    }
    public void start() {
        while (true) {
            message(); // запись из консоли
            if (!message.equals("")) {
                try (Socket socket = new Socket(host, port))//  открывается подключение
                {
                    OutputStreamWriter writerOut = new OutputStreamWriter(socket.getOutputStream()); //ВЫходной поток
                    writerOut.write(message); //запись сообщения в поток
                    writerOut.flush(); // очистка буфера
                    socket.shutdownOutput(); // закрывается выходной поток

                    InputStreamReader readerIn = new InputStreamReader(socket.getInputStream()); // Входной поток
                    char[] buffer = new char[1024];
                    StringBuilder sb = new StringBuilder();
                    for (int i; (i = readerIn.read(buffer, 0, buffer.length)) > 0; ) { //получение сообщения
                        sb.append(buffer, 0, i);
                    }
                    socket.shutdownInput();// закрытие потока
                    System.out.println(sb);
                } catch (IOException e) {
                    System.out.println("Что-то с клиентом");
                    e.printStackTrace();
                }
            }
        }
    }
}

