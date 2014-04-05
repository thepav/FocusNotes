package com.glassholes.focusnotes;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class Notes extends Activity implements TextWatcher,OnKeyListener{
	EditText note;
	EditText parent;
	int enter;
	boolean shift;
	int currentCars;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_notes);
		currentCars = 0;
		parent = (EditText)findViewById(R.id.parent);
		note = (EditText)findViewById(R.id.note);
		note.requestFocus();
		note.addTextChangedListener(this);
		enter = 0;
		shift = false;
		note.setSelection(2);
		note.setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				int thisline = findLastLine(note.getEditableText(),note.getSelectionEnd());
				thisline+=1;
				
				String[] noteLines = note.getEditableText().toString().split("\n");
				int linecount = 0;
				for(int i = note.getSelectionEnd();i<note.getEditableText().length();i++){
					if(note.getText().charAt(i)=='\n'){
						linecount++;
					}
				}
				int index = noteLines.length-1-linecount;
				int numCarrotsCurrent = numCarrot(noteLines[index]);
				currentCars = numCarrotsCurrent;
				
				
				
				Log.v("KeyCode", arg2.getKeyCode()+"");
				if (arg2.getKeyCode()==60 && arg2.getAction()==KeyEvent.ACTION_DOWN){
					shift = true;
				}
				if(arg2.getKeyCode()==60 && arg2.getAction()==KeyEvent.ACTION_UP){
					shift = false;
				}
				if(shift &&arg2.getKeyCode()==61){
					tabBack(note.getEditableText());
				}
				
				//Tab Over
				if(arg2.getKeyCode()==61 && arg2.getAction()==KeyEvent.ACTION_UP){
					tabOver(note.getEditableText());
				}
				
				if(arg2.getKeyCode()==66 && arg2.getAction()==KeyEvent.ACTION_UP){
					enter++;
					if(enter==2){
						note.getEditableText().append("\n");
						enter=0;
					}
				}
				if(arg2.getKeyCode()==67 && arg2.getAction()==KeyEvent.ACTION_DOWN){
					note.getEditableText().delete(note.getEditableText().length()-1, note.getEditableText().length());
				}
				//Move cursor left
				if(arg2.getKeyCode()==21 && arg2.getAction()==KeyEvent.ACTION_DOWN){
					try{
						note.setSelection(note.getSelectionEnd()-1);
					}catch(Exception e){}
				}
				//Move cursor right
				if(arg2.getKeyCode()==22 && arg2.getAction()==KeyEvent.ACTION_DOWN){
					try{
						note.setSelection(note.getSelectionEnd()+1);
					}catch(Exception e){}
				}
				//Move cursor up
				if(arg2.getKeyCode()==19 && arg2.getAction()==KeyEvent.ACTION_DOWN){
					note.setSelection(findLastLine(note.getEditableText(),note.getSelectionEnd()));
				}
				if(arg2.getKeyCode()==20 && arg2.getAction()==KeyEvent.ACTION_DOWN){
					note.setSelection(findNextLine(note.getEditableText(),note.getSelectionEnd()));
				}
				thisline = findLastLine(note.getEditableText(),note.getSelectionEnd());
				thisline+=1;
				
				noteLines = note.getEditableText().toString().split("\n");
				linecount = 0;
				for(int i = note.getSelectionEnd();i<note.getEditableText().length();i++){
					if(note.getText().charAt(i)=='\n'){
						linecount++;
					}
				}
				index = noteLines.length-1-linecount;
				numCarrotsCurrent = numCarrot(noteLines[index]);
				if (numCarrotsCurrent==1){
					parent.setText("...");
					return false;
				}
				for (int i=index;i>0;i--){
					if(numCarrot(noteLines[i])==numCarrotsCurrent-1){
						if(noteLines[i].length()<15){
							parent.setText(noteLines[i].toString()+"...");
						}else{
							parent.setText(noteLines[i].toString().substring(0, 15)+"...");
						}
						break;
					}
				}

				return false;
			}
			public int numCarrot(String s){
				boolean done = false;
				int i = 0;
				while (!done){
					
					if(i==s.length() ||s.charAt(i)!='>'){
						done =true;
					}
					i++;
				}
				i--;
				
				return i;
			}
			public int findLastLine(Editable e,int end){
				int index = -1;
				for (int i =0;i<end;i++){
					if(e.charAt(i)=='\n'){
						index = i;
//						Log.v("lastline", e.charAt(i-1)+":"+i);
					}
				}
				if( index == -1){
					index = 0;
				}
				return index;
			}
			public int findNextLine(Editable e,int start){
				int index = -1;
				for (int i =start;i<e.length();i++){
					if(e.charAt(i)=='\n'){
						index = i;
//						Log.v("lastline", e.charAt(i-1)+":"+i);
					}
				}
				if( index == -1){
					index = e.length()-1;
				}
				
				return index;
			}
			public void tabOver(Editable e){
				
				int index = -1;
				for (int i =0;i<e.length();i++){
					if(e.charAt(i)=='\n'){
						index = i;
						Log.v("Tabing", e.charAt(i)+":"+i);
					}
				}
				//e.delete(e.length()-1, e.length());
				String c = "";
				
				e.insert(index+1,">");
			}
			public void tabBack(Editable e){
				
				int index = -1;
				for (int i =0;i<e.length();i++){
					if(e.charAt(i)=='\n'){
						index = i+1	;
						Log.v("Tabing", e.charAt(i)+":"+i);
					}
				}
				//e.delete(e.length()-1, e.length());
				e.replace(index,index+1,"");
			}	
			
		});
		note.setText(">\n>");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}

	@Override
	public void afterTextChanged(Editable e) {
		// TODO Auto-generated method stub
		if (e.charAt(e.length()-1)=='\n'){
			String c ="";
			for (int i =0;i<currentCars;i++){
				c+=">";
			}
			e.append(c+" ");
		}
		
	}
	

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
