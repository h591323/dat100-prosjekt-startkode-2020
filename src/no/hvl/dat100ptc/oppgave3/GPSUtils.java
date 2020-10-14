package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import java.util.Locale;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		double [] ost= new double [gpspoints.length];
		int o = 0;
		for (GPSPoint i : gpspoints) {
			ost [o] = i.getLatitude();
			o++;
		}
		return ost;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double [] ost= new double [gpspoints.length];
		int o = 0;
		for (GPSPoint i : gpspoints) {
			ost [o] = i.getLongitude();
			o++;
		}
		return ost;
	
	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		
		latitude1 = toRadians(gpspoint1.getLatitude());
		latitude2 = toRadians(gpspoint2.getLatitude());
		longitude2 = toRadians(gpspoint2.getLongitude());
		longitude1 = toRadians(gpspoint1.getLongitude());
	
		
		double e = latitude2-latitude1;
		double f = longitude2-longitude1;
		
		
		double a = pow(sin(e/2), 2) + cos(latitude1) * cos(latitude2) * pow(sin(f/2), 2);
		
		double c = 2*(atan2(sqrt(a), sqrt(1-a)));
		
		d = R * c;
		
		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double secs;
		double speed;
		double distance = distance(gpspoint1, gpspoint2);
		
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		speed = distance/secs*3.6;
	
		return speed;
		

	}

	public static String formatTime(int secs) {

		String timestr = "";
		String TIMESEP = ":";

		int time = secs/3600;
		int timerest = secs%3600;
		int minut = timerest/60;
		int sek = timerest%60;
		
		String hhStr = String.format("%02d", time);
		String mmStr = String.format("%02d", minut);
		String ssStr = String.format("%02d", sek);
		
		
		timestr += hhStr + TIMESEP + mmStr + TIMESEP + ssStr;
		
		timestr = String.format("%10s", timestr);
		return timestr;

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;
		
	
		String St = String.format(Locale.US, "%.2f", d);
		
		str = String.format("%" + TEXTWIDTH + "s", St);
		
		return str;
	}
}
