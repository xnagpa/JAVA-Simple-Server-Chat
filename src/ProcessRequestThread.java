import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by Maxc on 25.12.2014.
 */
public class ProcessRequestThread extends Thread {

    Socket sock;
    List<ProcessRequestThread> listThreads;
    private BufferedReader in;
    private PrintWriter out;
    private String command;

    public ProcessRequestThread(List<ProcessRequestThread> list,Socket socket,String command)
   {

       //список потоков передается в качестве пареметра
       listThreads = list;
       this.command=command;
       sock=socket;
       try {

           in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
           out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);

       } catch (IOException e) {
           e.printStackTrace();
       }

   }

    @Override
    public void run() {
        out.println(sock.getInetAddress().getHostAddress()+" "+sock.getInetAddress().getHostName()+" "+Thread.currentThread().getName()+"  I'm ONLINE! ");
        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Пытаемся прочитать сообщение от клиента;
        //Это либо LIST либо адрес которые надо удалить
        //Ниже решаем что есть что и оповещаем клиентов в цикле.


        try{
            synchronized (listThreads) {
                if (command.equals("LIST")) {
                    for (ProcessRequestThread t : listThreads) {
                        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(t.sock.getOutputStream())), true);
                      //  out.println("Who is online:  ");
                        //out.println(t.sock.getInetAddress().getHostAddress() + "is online");
                        out.println(sock.getInetAddress().getHostAddress()+" "+sock.getInetAddress().getHostName()+"   "+Thread.currentThread().getName()+"   "+listThreads.size() + " hosts is online");
                    }
                }
                else
                {
                    try {
                        for (ProcessRequestThread t : listThreads) {
                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(t.sock.getOutputStream())), true);
                            if(command.equals(t.sock.getInetAddress().getHostAddress())) {
                                out.println(t.sock.getInetAddress().getHostAddress() + " Removed! by " + Thread.currentThread().getName()+"  "+sock.getInetAddress().getHostAddress()+" "+sock.getInetAddress().getHostName());
                                listThreads.remove(t);
                            }
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }


       /* synchronized (listThreads) {
            try {
                for (ProcessRequestThread t : listThreads) {
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(t.sock.getOutputStream())), true);
                    out.println(sock.getInetAddress().getHostAddress() + "Removed! "+Thread.currentThread().getName() );
                }
                listThreads.remove(this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }*/
      //  }





    }

}
