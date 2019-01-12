package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import entities.Line;
import entities.Stop;

public interface InterfaceRMI extends Remote{
	void updateLines(HashMap<String, String> map) throws RemoteException;
	void updateStops(Stop stop, List<Line> lineList) throws RemoteException;
	void updateTimetableForStop(long stopCode, HashMap<String, List<LocalDateTime>> timeTable) throws RemoteException;
}
