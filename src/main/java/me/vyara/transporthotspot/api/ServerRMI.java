package me.vyara.transporthotspot.api;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import me.vyara.transporthotspot.entities.*;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

public class ServerRMI implements InterfaceRMI{
	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private StopRepository stopRepository;
	
	public ServerRMI() {}

	@Override
	public void updateLines(HashMap<String, String> map) {
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> current = it.next();
			System.out.println("Line with id " + current.getKey() + " has name " + current.getValue());
		}
	}
	
	@Override
	public void updateStops(Stop stop, List<Line> lineList) throws RemoteException {
		System.out.println(stop.toString());
		for (Iterator<Line> iterator = lineList.iterator(); iterator.hasNext();) {
			Line line = (Line) iterator.next();
			System.out.println(line.toString());
		}
		
	}
	
	@Override
	public void updateTimetableForStop(long stopCode, HashMap<String, List<LocalDateTime>> timeTable) throws RemoteException {
		System.out.println("Timetable for stop " + stopCode);
		Set<Entry<String,List<LocalDateTime>>> setList = timeTable.entrySet();
		Iterator it = setList.iterator();
		while(it.hasNext()) {
			Entry<String, List<LocalDateTime>> current = (Entry<String, List<LocalDateTime>>) it.next();
			System.out.println("Bus number " + current.getKey());
			System.out.println("Times of arrival: ");
			Iterator<LocalDateTime> timeIt = current.getValue().iterator();
			while(timeIt.hasNext()) {
				LocalDateTime currentTime = timeIt.next();
				System.out.println(currentTime);
			}
		}
		
	}
}
