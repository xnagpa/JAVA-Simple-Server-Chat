Запуск производится в последовательности: 
1. Запустить сервер
2. Запустить клиент

Пример вывода программы. Из-за того что клиент и сервер на одной машине, все они будут называться одинаково и иметь один адрес. 

Сервер запустился
Server is running....

Message from server:192.168.56.1 Maxc-VAIO Thread-0  I'm ONLINE! //Клиенты подключаются и получают сообщения от сервера о подключении.

Message from server:192.168.56.1 Maxc-VAIO Thread-1  I'm ONLINE! //Клиенты подключаются и получают сообщения от сервера о подключении.
Каждый клиент запрашивает сначала LIST - список потоков онлайн. 
Message from server:192.168.56.1 Maxc-VAIO   Thread-0   2 hosts is online //Клиент 1 выполняет LIST
Message from server:192.168.56.1 Maxc-VAIO   Thread-1   2 hosts is online //клиент 2 выполняет LIST
Message from server:192.168.56.1 Maxc-VAIO joined us!  // сервер сообщает, что хост подключился
Message from server:192.168.56.1 Maxc-VAIO joined us!  // сервер сообщает, что хост подключился
Message from server:192.168.56.1 Maxc-VAIO Thread-2  I'm ONLINE!  // клиент 3  подключился к серверу
Message from server:192.168.56.1 Removed! by Thread-2  192.168.56.1 Maxc-VAIO //клиент 3 запросил SHUTSOWN адреса 192.168.56.1
Message from server:192.168.56.1 Removed! by Thread-2  192.168.56.1 Maxc-VAIO //клиент 3 запросил SHUTSOWN адреса 192.168.56.1
Message from server:192.168.56.1 Removed! by Thread-2  192.168.56.1 Maxc-VAIO //клиент 3 запросил SHUTSOWN адреса 192.168.56.1
Message from server:192.168.56.1 Maxc-VAIO Thread-3  I'm ONLINE! // клиент 4  подключился к серверу
Message from server:192.168.56.1 Maxc-VAIO   Thread-3   1 hosts is online // клиент 4  выполняет LIST

Программа завершена. 


