package de.doridian.yiffbukkit.util;

import org.bukkit.entity.Player;

import java.util.*;

public class Request implements Runnable {
	protected static final HashMap<Player, Stack<Request>> requests = new HashMap<Player, Stack<Request>>();
	
	public static Request getRequest(Player forPlayer) {
		return getRequest(forPlayer, null);
	}

	public static Request getRequest(Player forPlayer, Player byPlayer) {
		if(requests.containsKey(forPlayer)) {
			Stack<Request> reqs = requests.get(forPlayer);
			if(byPlayer == null) {
				return reqs.isEmpty() ? null : reqs.firstElement();
			} else {
				for(Request req : reqs) {
					if(req.byPlayer == byPlayer) {
						return req;
					}
				}
			}
		}
		return null;
	}
	
	public static void timeoutCheck(Player forPlayer) {
		Stack<Request> reqs = (Stack<Request>)requests.get(forPlayer).clone();
		for(Request req : reqs) {
			if(!req.isInTime()) {
				req.remove();
			}
		}
	}

	public static void timeoutCheck() {
		for(Player ply : requests.keySet()) {
			timeoutCheck(ply);
		}
	}
	
	public static void removeAllRequests(Player forPlayer) {
		requests.remove(forPlayer);
	}


	private final Player forPlayer;
	private final Player byPlayer;
	private final Runnable execute;
	private long timeout;
	
	public Request(Player forPlayer, Player byPlayer, Runnable run) {
		this.byPlayer = byPlayer;
		this.forPlayer = forPlayer;
		this.execute = run;
		this.timeout = 0;
	}

	@Override
	public void run() {
		try {
			execute.run();
		} catch(Exception e) { }
		remove();
	}

	public void remove() {
		requests.get(forPlayer).remove(this);
	}

	public void add(String msg) {
		Request req = getRequest(forPlayer, byPlayer);
		if(req != null) {
			req.remove();
		}
		this.timeout = System.currentTimeMillis() + 30000;
		if(!requests.containsKey(forPlayer)) {
			requests.put(forPlayer, new Stack<Request>());
		}
		requests.get(forPlayer).add(this);
		forPlayer.sendMessage(String.format(msg, forPlayer.getDisplayName(), byPlayer.getDisplayName()));
		forPlayer.sendMessage("Use /yes to accept or /no to decline");
	}

	public boolean isInTime() {
		return System.currentTimeMillis() <= timeout;
	}
}
