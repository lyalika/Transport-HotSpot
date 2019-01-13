package me.vyara.transporthotspot.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.vyara.transporthotspot.entities.Line;
import me.vyara.transporthotspot.entities.Stop;

public interface InterfaceRMI extends Remote{
	void updateLines(List<Line> lineList) throws RemoteException;
	void updateStops(Stop stop, List<Line> lineList) throws RemoteException;
	void updateTimetableForStop(long stopCode, HashMap<String, List<LocalDateTime>> timeTable) throws RemoteException;
}
