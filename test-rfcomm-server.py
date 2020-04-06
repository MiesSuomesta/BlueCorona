import bluetooth
from threading import Thread

uuid = "a18e5a7b-a086-4fc2-a789-92308302204c"


def thread_rf_comm_server(threadName):
    global uuid
    server_sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
    server_sock.bind(("", bluetooth.PORT_ANY))
    server_sock.listen(1)

    port = server_sock.getsockname()[1]

    bluetooth.advertise_service(server_sock, "BtCoronaServer", service_id=uuid,
                                service_classes=[uuid, bluetooth.SERIAL_PORT_CLASS],
                                profiles=[bluetooth.SERIAL_PORT_PROFILE],
                                )

    print("Waiting for connection on RFCOMM channel", port)
    while(true):
        client_sock, client_info = server_sock.accept()
        print("Accepted connection from", client_info)

        try:
            while True:
                data = client_sock.recv(1024)
                if not data:
                    break
                print("{}: Received: {}\n" % (threadName, data))
        except OSError:
            pass

    print("Disconnected.")
    client_sock.close()
    server_sock.close()
    print("Server closed, All done.")


def thread_rf_comm_client(threadName):
    global uuid
    service_matches = bluetooth.find_service(uuid=uuid, address=None)
    first_match = service_matches[0]
    port = first_match["port"]
    name = first_match["name"]
    host = first_match["host"]

    print("Connecting to \"{}\" on {}".format(name, host))

    # Create the client socket
    sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
    sock.connect((host, port))

    print("Connected. Type something...")
    while True:
        data = input()
        sock.send(data)
        print("{}: Sent: {}\n" % (threadName, data))
        if not data:
            break

    sock.close()

app = Thread( target=thread_rf_comm_server, args = ("RFComm server",) )
app.start()