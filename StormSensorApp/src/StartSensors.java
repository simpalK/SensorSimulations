import java.util.*;
public class StartSensors {

	public static void main(String[] args) {
		SensorDataSet sensorStart = new SensorDataSet (1);
	     sensorStart.start ();

	     // Create DataGetter and tell it to obtain
	     // 100 sensor readings for first sensor.
	     SensorDataGet sensorDataget = new SensorDataGet (10, sensorStart);
	     sensorDataget.start ();
	     
	     SensorDataSet sensorStart2 = new SensorDataSet (2);
	     sensorStart2.start ();

	     // Create DataGetter and tell it to obtain
	     // 100 sensor readings for second sensor.
	     SensorDataGet sensorDataget2 = new SensorDataGet (10, sensorStart2);
	     sensorDataget2.start ();
	     SensorDataSet sensorStart3 = new SensorDataSet (3);
	     sensorStart3.start ();

	     // Create DataGetter and tell it to obtain
	     // 100 sensor readings for 3rd sensor.
	     SensorDataGet sensorDataget3 = new SensorDataGet (10, sensorStart3);
	     sensorDataget3.start ();
	}

}
