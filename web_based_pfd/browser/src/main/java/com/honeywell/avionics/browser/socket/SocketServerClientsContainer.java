package com.honeywell.avionics.browser.socket;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.honeywell.avionics.browser.socket.SocketServer;

import java.util.Map;
import java.util.Set;

public class SocketServerClientsContainer {

    private static final Map<String, Set<SocketServer>> clientMap = Maps.newHashMap();

    public synchronized static void add(String flightId, SocketServer socket) {
        if (clientMap.containsKey(flightId)) {
            clientMap.get(flightId).add(socket);
        } else {
            Set<SocketServer> socketList = Sets.newHashSet();
            socketList.add(socket);
            clientMap.put(flightId, socketList);
        }
    }

    public static void remove(String flightId, SocketServer socket) {
        if (clientMap.containsKey(flightId)) {
            clientMap.get(flightId).remove(socket);
        }
    }

    public static Set<SocketServer> get(String flightId) {
        return clientMap.containsKey(flightId) ? clientMap.get(flightId) : Sets.newHashSet();
    }

    public static void broadcast(String flightId, String flightData) {
        for (SocketServer socketServer : SocketServerClientsContainer.get(flightId)) {
            socketServer.sendMessage(flightData);
        }
    }
}
