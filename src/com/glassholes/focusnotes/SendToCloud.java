package com.glassholes.focusnotes;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SendToCloud extends AsyncTask<Void, Void, Void> {
	private String fn;
	public Context co;
	public SendToCloud(String in, Context c) 
	{
		co = c;
		fn = in;
		Log.i("work", "work");
    }

	  protected void onPreExecute() {
		 // System.out.println("Pre-execute");
	    //activity.numAsyncTasks++;
	    //progressBar.setVisibility(View.VISIBLE);
		  //SettingsActivity.inc_task_count();
	  }

	  protected void onPostExecute() {
	 //   if (0 == --activity.numAsyncTasks) {
	   //   progressBar.setVisibility(View.GONE);
	    //}

		//  SettingsActivity.updatePatron(result);
		 // SettingsActivity.updateMonsieurOnServer();

		 // SettingsActivity.dec_task_count();
		  //Update Monsieur here
	  }

	@Override
	protected Void doInBackground(Void... params) {
		//org.apache.http.entity.mime.content.FileBody
		
		try
	    {
	    	HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("https://api.kloudless.com/v0/accounts/30/files/");
			httppost.addHeader("Authorization",  "ApiKey HDUITtCXbWXDr9EqU3jjkxqH5k0vX1q8_qx_Ob9QylTAkU0d");
			File file = new File(fn);
			Log.v("PAV", co.getFilesDir()+file.getAbsolutePath());
			
			FileBody bin = new FileBody(new File(co.getFilesDir()+file.getAbsolutePath()));
			//StringBody comment = new StringBody("Filename: " + fn);

			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("file", bin);
			
			//String temp = "{\"name\":\"" + fn + "\", \"parent_id\":\"root\"}";
			//ContentBody temp = new StringBody("{\"name\":\"" + fn + "\", \"parent_id\":\"root\"}");
			reqEntity.addPart("metadata", new StringBody("{\"name\":\"" + fn + "\", \"parent_id\":\"root\"}"));
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			String responseString = EntityUtils.toString(resEntity, "UTF-8");
	        Log.i("response", "maybe" + responseString.toString());
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    	Log.v("FAIL", "FAIL");
	    
	    }
		
		//EntityUtils.toString(response.getEntity()));
		/*
		Log.i("do in ", "backgroud");
		HttpClient hc = new DefaultHttpClient();

        // Prepare a request object

        HttpClient httpclient;
        HttpPost httppost = new HttpPost();
        ArrayList<NameValuePair> postParameters;
        
        //UsernamePasswordCredentials creds = new UsernamePasswordCredentials("RCcYt_c8sb_64lhDuSVAUcNiDkT5gqwxXwZ4acRywjIK6gRC", "HDUITtCXbWXDr9EqU3jjkxqH5k0vX1q8_qx_Ob9QylTAkU0d");
        //Credentials z = new Credentials()
        //httppost.addHeader("Authenticate", "Key HDUITtCXbWXDr9EqU3jjkxqH5k0vX1q8_qx_Ob9QylTAkU0d");
        httppost.addHeader("Content-Type", "application/json");
        
        
        //httppost.addHeader( BasicScheme.authenticate()(creds,"US-ASCII",false) );
        
        httpclient = new DefaultHttpClient();
        httppost = new HttpPost("http://api.kloudless.com/v0/accounts/30/folders/");


        postParameters = new ArrayList<NameValuePair>();
        //postParameters.add(new BasicNameValuePair("id", "30"));
        //postParameters.add(new BasicNameValuePair("name", fn.toString()));
        //postParameters.add(new BasicNameValuePair("file", "" + fn));
        postParameters.add(new BasicNameValuePair("parent_id", "root"));
        postParameters.add(new BasicNameValuePair("name", "fuckthis"));
        Log.i("in", "done filling");
        
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
        	Log.i("should see this", "huh");
            response = hc.execute(httppost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Log.i("response", "maybe" + responseString.toString());//EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            Log.v("failed response", ""+e.getStackTrace());
        	e.printStackTrace();
        }
        */
        return null;
	}


}
