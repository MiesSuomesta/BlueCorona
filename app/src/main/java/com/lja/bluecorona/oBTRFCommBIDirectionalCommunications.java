package com.lja.bluecorona;

import android.bluetooth.*;
import android.content.Context;
import android.os.*;
import android.provider.SyncStateContract;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import static com.lja.bluecorona.cBTRFCommConstants.*;
import static java.lang.Thread.sleep;

public class oBTRFCommBIDirectionalCommunications {
    public MainActivity                 mActivity = null;
    public BluetoothAdapter             mBTAdapter = null;
    public AcceptThread                 mServerAcceptThread = null;
    public ArrayList<ConnectThread>     mListOfServerConnectThreads = null;
    public ArrayList<ConnectedThread>   mListOfServerConnectedThreads = null;

    public oBTRFCommBIDirectionalCommunications(MainActivity pAct, BluetoothAdapter pBA) {
        mActivity = pAct;
        mBTAdapter = pBA;
        mListOfServerConnectThreads = new ArrayList<ConnectThread>();
        mListOfServerConnectedThreads = new ArrayList<ConnectedThread>();
        setUserSicknessLevel(cBTRFCommUserNotSet);
    }

    public synchronized int getUserSicknessLevel() {
        return mActivity.getUserSicknessLevel();
    }

    public synchronized int setUserSicknessLevel(int tmp) {
        mActivity.setUserSicknessLevel(tmp);
        return tmp;
    }

    public synchronized void stop() {

        for (ConnectThread tmp : this.mListOfServerConnectThreads)
        {
            tmp.cancel();
        }

        this.mListOfServerConnectThreads.clear();

        for (ConnectedThread tmp : this.mListOfServerConnectedThreads)
        {
            tmp.cancel();
        }

        this.mListOfServerConnectedThreads.clear();

        mServerAcceptThread.interrupt();
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void start() {
        stop();
        mServerAcceptThread = new AcceptThread();
    }

    public synchronized void connect(BluetoothDevice device) {

        ConnectThread tmp = new ConnectThread(device);
        mListOfServerConnectThreads.add(tmp);
        tmp.start();

    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {

        ConnectedThread tmp = new ConnectedThread(socket);
        mListOfServerConnectedThreads.add(tmp);
        tmp.start();

    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                tmp = device.createInsecureRfcommSocketToServiceRecord(cBTRFCommUID);
            } catch (IOException e) {
            }
            mmSocket = tmp;
        }

        public void run() {

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                }
                return;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    private class ConnectedThread extends Thread {
        private BluetoothSocket mmSocket;
        private InputStream mmInStream;
        private OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1];
            int bytes;

            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                mActivity.setUserSicknessLevel(buffer[0]);
                buffer[0] = (byte)mActivity.getUserSicknessLevel();
            } catch (IOException e) {

            };
            cancel();
        }

        public void cancel() {
            try {
                mmInStream.close();
                mmOutStream.close();
                mmSocket.close();
            } catch (IOException e2) {
            };
            mmSocket = null;
        }

    }

    private class AcceptThread extends Thread {
        // The local server socket https://github.com/googlearchive/android-BluetoothChat/blob/master/Application/src/main/java/com/example/android/bluetoothchat/BluetoothChatService.java
        private final BluetoothServerSocket mmServerSocket;
        private boolean mStateConnected = false;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            // Create a new listening server socket
            try {
                tmp = mBTAdapter.listenUsingInsecureRfcommWithServiceRecord(
                        cBTRFCommUIDString, cBTRFCommUID);
            } catch (IOException e) {
            }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;

            // Listen to the server socket if we're not connected
            boolean keeprunning = true;
            while (keeprunning) {
                keeprunning = ! isInterrupted();

                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    keeprunning = false;
                    break;
                }


                // If a connection was accepted
                if (socket != null) {
                    synchronized (oBTRFCommBIDirectionalCommunications.this) {
                            // Situation normal. Start the connected thread.
                            connected(socket, socket.getRemoteDevice());
                        }
                    }
                }
            } /* EO while */
        }
}
