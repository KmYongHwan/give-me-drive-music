public class translocalpoint {

	
	   public static final int NX = 149;
	   public static final int NY = 253;
	   
	   class lamc_parameter{
		   float Re;
		   float grid;
		   float slat1;
		   float slat2;
		   float olon;
		   float olat;
		   float xo;
		   float yo;
		   int first;  
	   }
	   
	    class grid_xy{
		   int x;
		   int y;
	   }
	   
	   public grid_xy transLocalpoint(double latitude, double longitude) {
		   float lon, lat, x, y;
		   lamc_parameter map = new lamc_parameter();
		   grid_xy ans = new grid_xy();
		   
		   map.Re = 6371.00877f;
		   map.grid = 5.0f;
		   map.slat1 = 30.0f;
		   map.slat2 = 60.0f;
		   map.olon = 126.0f;
		   map.olat = 38.0f;
		   map.xo = 210/map.grid;
		   map.yo = 675/map.grid;
		   map.first = 0;
		   
		   float lon1, lat1, x1, y1;
		   double PI, DEGRAD, RADDEG;
		   double re, olon, olat, sn, sf, ro;
		   double slat1, slat2, alon, alat, xn, yn, ra, theta;
		   
		   PI = Math.asin(1.0)*2.0;
		   DEGRAD = PI/180.0;
		   RADDEG = 180.0/PI;
		   
		   re = map.Re/map.grid;
		   slat1 = map.slat1 * DEGRAD;
		   slat2 = map.slat2 * DEGRAD;
		   olon = map.olon * DEGRAD;
		   olat = map.olat * DEGRAD;
		   
		   sn = Math.tan(PI * 0.25 + slat2*0.5)/Math.tan(PI*0.25 + slat1*0.5);
		   sn = Math.log(Math.cos(slat1)/Math.cos(slat2))/Math.log(sn);
		   sf = Math.tan(PI*0.25 + slat1*0.5);
		   sf = Math.pow(sf, sn)* Math.cos(slat1)/sn;
		   ro = Math.tan(PI*0.25 +olat*0.5);
		   ro = re*sf/Math.pow(ro, sn);
		   
		   map.first = 1;
		   
		   ra = Math.tan(PI * 0.25 + latitude * DEGRAD * 0.5);
		   ra = re*sf/Math.pow(ra,sn);
		   theta = longitude * DEGRAD - olon;
		   if (theta > PI) theta -= 2.0*PI;
		   if (theta < -PI) theta += 2.0*PI;
		   theta *= sn;
		   x1 = (float)(ra*Math.sin(theta)) + map.xo;
		   y1 = (float)(ro - ra*Math.cos(theta)) + map.yo;

		   ans.x = (int)(x1 + 1.5);
		   ans.y = (int)(y1 + 1.5);
		   
		   //System.out.println(ans.x);
		   //System.out.println(ans.y);
		   
		   return ans;
	   }
	   
	  
	}



