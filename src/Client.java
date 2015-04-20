/**
 * Created by Maxc on 23.12.2014.
 */
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.Random;

public class Client extends Thread{

    private Socket sock;
    private InetAddress addr;
    private String ClientList;

    private String command;


     public static void main(String[] args) {

     /*    if (args[1].equals("SHUTDOWN")) {
             String command = "SHUTDOWN";
             String address = args[0];
             Client c1 = new Client(address);


         } else if (args[0].equals("LIST"))
         {
             Client c2 = new Client("LIST");
             c2.start();

         }*/




        //Скрипт для демонстрации - отправляем несколько команд LIST, и один запрос на выключение
         Client c1 = new Client("LIST");
         Client c2 = new Client("LIST");

         Client c3 = new Client("192.168.56.1");
         Client c4 = new Client("LIST");

         c1.start();
         c2.start();

         try {
             Thread.sleep(10000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         c3.start();
         try {
             Thread.sleep(5000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         c4.start();


         try {
             c1.join();
             c2.join();
             c3.join();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }


     }

    public void run()
    {
        Random rnd = new Random();
        //отправляем сообщение
        this.sendMessage(command);
        //И бесконечно ждем ответа
        while(true)
        {
            getMessage();
        }

    }

    public Client()
    {

    }
    public Client(String comm)
    {
        this.command=comm;
    }
    //Чтение сообщения
    public synchronized void getMessage()
    {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String inStr= in.readLine();
            System.out.println("Message from server:"+inStr);
        }
        catch(Exception exc)
        {
            System.err.println("EXCEPTION!!!");
        }

    }
    //Отправка сообщения
    public synchronized void  sendMessage(String message)
    {
        try {
             addr = InetAddress.getLocalHost();
             sock = new Socket(addr,8081);
             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
            out.println(message);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
