package com.example.chun.whefe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CafemenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafemenu);
    }
    /*Context context;
    int[] images;
    String[] menunames;

    public CafemenuActivity(Context context,int[] images, String[] menunames){
        this.context = context;
        this.images = images;
        this.menunames = menunames;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return menunames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.cafemenu,parent,false);

        ImageView menuImage = (ImageView)convertView.findViewById(R.id.MenuImage);
        TextView menuName = (TextView)convertView.findViewById(R.id.MenuNameView);

        Spinner optionSpinner = (Spinner)convertView.findViewById(R.id.OptionSpinner);
        ArrayAdapter Adapter = ArrayAdapter.createFromResource(this,
                R.array.option_array, android.R.layout.simple_spinner_item);
        Spinner.setAdapter(Adapter);


        return null;
    }*/
}
