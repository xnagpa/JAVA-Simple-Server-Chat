import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Maxc on 25.12.2014.
 */
public class StableServer {

    private static Socket sock;
    private static ServerSocket serverSocket;
    private static CopyOnWriteArrayList<ProcessRequestThread> clientLst;
    private BufferedReader in;
    private PrintWriter out;

    public StableServer(int port)
    {
         clientLst = new CopyOnWriteArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
    //читаем аргументы командной строки
        if(args[0]!=null) {

            StableServer server = new StableServer(Integer.parseInt(args[0]));
            //запускаем сервер
            try {
                server.startServerAndWaitForRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // StableServer server = new StableServer(Integer.parseInt(args[0]));


    }

    public void startServerAndWaitForRequest() throws IOException {

     //   Socket sock =serverSocket.accept();

        System.out.println("Server is running....");
        while(true)
        {
            //принимаем подключение
            Socket sock =serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);

            //Извещаем всех о подключении
           if(!clientLst.isEmpty()) {
               for (ProcessRequestThread t : clientLst) {
                   out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(t.sock.getOutputStream())), true);
                   out.println(sock.getInetAddress().getHostAddress()+" "+sock.getInetAddress().getHostName() + " joined us! ");

               }
           }
            //Читаем запрос от клиента
            String command = in.readLine();

            //запускаем поток для выполнения запроса
            ProcessRequestThread thread = new ProcessRequestThread(clientLst,sock,command);
            clientLst.add(thread);

            thread.start();


        }



    }




}
