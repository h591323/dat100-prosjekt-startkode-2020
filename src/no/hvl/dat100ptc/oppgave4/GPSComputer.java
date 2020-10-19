package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i<gpspoints.length-1; i++) {
		distance +=	GPSUtils.distance(gpspoints[i], gpspoints[i+1]);
			
		}

		return distance;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		

		for (int i = 1; i < gpspoints.length; i++) {
			
			if (gpspoints[i].getElevation() > gpspoints[i - 1].getElevation()) {
				elevation += gpspoints[i].getElevation() - gpspoints[i - 1].getElevation();
			}
		}
		
		return elevation;

		
		

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int	x = gpspoints[0].getTime();
		int	y = gpspoints[gpspoints.length-1].getTime();
		
		x = y-x;
		return x;
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		double [] tab = new double [gpspoints.length-1];
		
		for (int i = 0; i<gpspoints.length-1; i++) {
			tab [i] = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
		}
		
		return tab;
	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		double [] tab = speeds();
		for (int i = 0; i<gpspoints.length-1; i++) {
			if (tab[0]<tab[i]) {
				maxspeed = tab[i];
			} 
		}
		return maxspeed;
	}

	public double averageSpeed() {

		double average = 0;
		
		double distance = totalDistance();
		int time = totalTime();
		
		average = distance/time*3.6;
		
		return average;
		
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		
		double met = 0;		
		double speedmph = speed * MS;

		
		
		double time = (secs)/3600.0;
		
		if (speedmph < 10) {
			met = 4.0;
		}
		else if (speedmph < 12) {
			met = 6.0;
		}
		else if (speedmph < 14) {
			met = 8.0;
		}
		else if (speedmph < 16) {
			met = 10.0;
		}
		else if (speedmph <= 20) {
			met = 12.0;
		}
		else if (speedmph > 20) {
			met = 16.0;
		}
		
		
		kcal = met*weight*time;
		
		return kcal;
		
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		double averageSpeed = averageSpeed();
		int totalTime = totalTime();
		
		totalkcal = kcal(weight, totalTime, averageSpeed);
		
		return totalkcal;
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");

		int totalTime = totalTime();
		double totalDistance = totalDistance() / 1000.0;
		double totalElevation = totalElevation();
		double maxSpeed = maxSpeed();
		double averageSpeed = averageSpeed();
		double totalKcal = totalKcal(WEIGHT);
		
		System.out.println("Total Time     :" + GPSUtils.formatTime(totalTime));
		System.out.println("Total distance :" + GPSUtils.formatDouble(totalDistance) + " km");
		System.out.println("Total elevation:" + GPSUtils.formatDouble(totalElevation) + " m");
		System.out.println("Max speed      :" + GPSUtils.formatDouble(maxSpeed) + " km/t");
		System.out.println("Average speed  :" + GPSUtils.formatDouble(averageSpeed) + " km/t");
		System.out.println("Energy         :" + GPSUtils.formatDouble(totalKcal) + " kcal");
		
		System.out.println("==============================================");
	}

}
