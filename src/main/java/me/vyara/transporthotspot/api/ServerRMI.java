package me.vyara.transporthotspot.api;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import me.vyara.transporthotspot.entities.*;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

public class ServerRMI implements InterfaceRMI{
	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private StopRepository stopRepository;
	
	@Autowired
	private ArrivalRepository arrivalRepository;
	
	public ServerRMI(StopRepository stopR, LineRepository lineR, ArrivalRepository arrivalRepository) {
		this.lineRepository = lineR;
		this.stopRepository = stopR;
		this.arrivalRepository = arrivalRepository;
	}

	@Override
	public void updateLines(List<Line> lineList) {
//		Iterator<Entry<String, String>> it = map.entrySet().iterator();
//		while(it.hasNext()) {
//			Entry<String, String> current = it.next();
//			System.out.println("Line with id " + current.getKey() + " has name " + current.getValue());
//		}
		Iterator<Line> it = lineList.iterator();
		while(it.hasNext()) {
			Line current = it.next();
			lineRepository.save(current);
		}
	}
	
	@Override
	@Transactional
	public void updateStops(Stop stop, List<Line> lineList) throws RemoteException {
		Iterator<Line> it = lineList.iterator();
		Set<Line> set = new HashSet<>();
		while(it.hasNext()) {
			Line current = it.next();
			set.add(current);
		}
		stop.setLines(set);
		stopRepository.save(stop);
		
	}
	
	@Override
	public void updateTimetableForStop(long stopCode, HashMap<String, List<LocalDateTime>> timeTable) throws RemoteException {
		Stop stop= stopRepository.findByNumber(stopCode);
		
		Set<Entry<String, List<LocalDateTime>>> set = timeTable.entrySet();
		Iterator it = set.iterator();
		while(it.hasNext()) {
			Entry<String, List<LocalDateTime>> entry = (Entry<String, List<LocalDateTime>>) it.next();
			String vehicleName = entry.getKey();
			List<LocalDateTime> timeList = entry.getValue();
			Iterator<LocalDateTime> it2 = timeList.iterator();
			while(it2.hasNext()) {
				LocalDateTime currentTime = it2.next();
				Line currentLine = lineRepository.findByName(vehicleName);
				arrivalRepository.save(new Arrival(stop, currentLine, currentTime));
			}
		}
		
	}
}
