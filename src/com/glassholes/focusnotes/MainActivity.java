package com.glassholes.focusnotes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.rtf.R;
import com.glassholes.jrtf.Rtf;
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Log.i("adsf", "adf");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	public void save(String input)
	{
		//File out = new File( "out.rtf" );

	    //RtfPara nextPar = RtfPara.p( "second paragraph" );
		//String input = ">Cactuses\n>>They're Spiky\n>>They're green\n>Your mom\n>>She's spiky\n>>She's not green\n>Chris\n>>He get's high\n>>>Especially when I'm trying to do things\n>>>Everybody loves Chris\n>>Lolcano\n>Octopus";
		String[] splitText = input.split("\n");
		Rtf.rtf().p( "Hello World" );
		for (String i: splitText)
		{
			String j = i.replace(">",  "\t").substring(1) + "\n";
			Rtf.rtf().p(j);
		}
	   // Rtf test = Rtf.section( p("hello")).out);
	    //rtf().section( p( "Hello World" ) ).out
	    //test.p("hello world \t\t \n Hello again");
		//Rtf test = Rtf.rtf().section( ).out( new FileWriter("out.rtf") );
	    
	    try {
	        FileOutputStream fos = openFileOutput("file_name"+".rtf",Context.MODE_PRIVATE);
	        Writer z = new OutputStreamWriter(fos);
	        for (String i: splitText)
			{
				String j = i.replace(">",  "\t") + "\n";
				z.write(j);
				//Rtf.rtf().p(j);
			}
	        
	        z.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    try {
	        FileInputStream fis = openFileInput("file_name"+".rtf");
	        BufferedReader r = new BufferedReader(new InputStreamReader(fis));
	        String line= r.readLine();
	        Log.i("Read", line);
	        r.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	        Log.i("TESTE", "FILE - false");
	    }
	    //String fn = "file_name"+".docx";
	    SendToCloud sync = new SendToCloud("file_name.rtf",this);
	    sync.execute();
	    
	    //SendToCloud sync = new SendToCloud("test.txt");
	    //sync.execute();
	    /*
	    FileOutputStream outStream = null;
	    try {
            outStream = openFileOutput("/Documents/test.docx", Context.MODE_WORLD_READABLE);
            outStream.write(test.out().toString().getBytes());
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
        	e.printStackTrace();
        }*/
        
	}
}
	/*
	public void sendToCloud(String fn, String file_path)
	{
		
		
		HttpClient hc = new DefaultHttpClient();

        // Prepare a request object

        HttpClient httpclient;
        HttpPost httppost = new HttpPost();
        ArrayList<NameValuePair> postParameters;
        
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("RCcYt_c8sb_64lhDuSVAUcNiDkT5gqwxXwZ4acRywjIK6gRC", "HDUITtCXbWXDr9EqU3jjkxqH5k0vX1q8_qx_Ob9QylTAkU0d");
        httppost.addHeader( BasicScheme.authenticate(creds,"US-ASCII",false) );
        
        httpclient = new DefaultHttpClient();
        httppost = new HttpPost("http://www.api.kloudless.com");


        postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("id", "30"));
        postParameters.add(new BasicNameValuePair("name", fn));
        postParameters.add(new BasicNameValuePair("file", "/" + fn));
        
        try{

        	httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        }
        catch (UnsupportedEncodingException e){
        	Log.i("FUCK", "FUCK");

        }
        
        Log.d("MAIN", "httpquery: " + httppost.getURI().toString());
        // Execute the request
        HttpResponse response;
        try {
            response = hc.execute(httppost);
            Log.i("MAIN", EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
        	e.printStackTrace();
//            Log.v("MAIN", e.getStackTrace());
        }
	}
        
        
		try {
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setDoOutput(true);
	        connection.setRequestProperty  ("Authorization", "Basic " + encoding);
	        connection.setRequestProperty("id", "30");
			connection.setRequestProperty("name", fn);
	        InputStream content = (InputStream)connection.getInputStream();
	        BufferedReader in   = 
	            new BufferedReader (new InputStreamReader (content));
	        String line;
	        while ((line = in.readLine()) != null) {
	        	Log.d("FUCK YEAH", line);
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
		
	{
		String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("id", "30");
		con.setRequestProperty("name", fn);
		
 
		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
	}


}
*/