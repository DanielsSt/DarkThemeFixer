package tk.navideju.darkthemefixer.activities;

import android.animation.LayoutTransition;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tk.navideju.darkthemefixer.R;
import tk.navideju.darkthemefixer.dao.ConfigDao;
import tk.navideju.darkthemefixer.domain.AttributeForChange;
import tk.navideju.darkthemefixer.domain.LayoutForChange;
import tk.navideju.darkthemefixer.domain.PackageForChange;
import tk.navideju.darkthemefixer.domain.ThingForChange;

public class MainActivity extends PreferenceActivity {

    private String selectedPackage;
    private String selectedLayout;
    PreferenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView();
        prefManager = getPreferenceManager();
//        prefManager.setStorageDeviceProtected();
        prefManager.setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        prefManager.setSharedPreferencesName("fix_states");
        Intent intent = getIntent();
        selectedPackage = intent.getStringExtra("selectedPackage");
        selectedLayout = intent.getStringExtra("selectedLayout");

        // nailed it :/
        AdapterView.OnItemClickListener itemListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        ThingForChange selectedThing = (ThingForChange) parent.getItemAtPosition(position);
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        if(selectedThing.getClass().equals(PackageForChange.class)) {
                            intent.putExtra("selectedPackage", selectedThing.getName());
                            intent.putExtra("selectedLayout", "");
                        } else if(selectedThing.getClass().equals(LayoutForChange.class)) {
                            intent.putExtra("selectedPackage", selectedPackage);
                            intent.putExtra("selectedLayout", selectedThing.getName());
                        }else {
                            CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox);
                            cb.setChecked(!cb.isChecked());
                            SharedPreferences pref = prefManager.getSharedPreferences();
//                            SharedPreferences pref = getSharedPreferences("fix_states", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean(selectedPackage+"."+selectedLayout+"."+selectedThing.getName(),cb.isChecked());
                            editor.apply();
                            return;
                        }
                        intent.putExtra("selectedThingType", selectedThing.getClass().toString());
                        startActivity(intent);
                    }
                };
        getListView().setOnItemClickListener(itemListener);

        if(selectedPackage == null || selectedPackage.isEmpty()) {
            setListAdapter(new FixesAdapter(new ConfigDao().getPackages()));
        }else if(selectedLayout == null || selectedLayout.isEmpty()){
            setListAdapter(new FixesAdapter(new ConfigDao().getByName(selectedPackage).getLayouts()));
        }else{
            setListAdapter(new FixesAdapter(new ConfigDao().getByName(selectedPackage).getByName(selectedLayout).getAttributes()));
        }
    }

    private Object getModel(int position) {
        return(((FixesAdapter)getListAdapter()).
            getItem(position));
    }


    public class FixesAdapter<T> extends ArrayAdapter<T> {
        private LayoutInflater mInflater;

        FixesAdapter(List<T> list) {
            super(MainActivity.this, R.layout.list_view, list);
            mInflater = LayoutInflater.from(MainActivity.this);
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View row = convertView;
            row = mInflater.inflate(R.layout.list_view, parent, false);
            holder = new FixesAdapter.ViewHolder();


            T thing = (T) getModel(position);

            if(thing instanceof ThingForChange){
                ThingForChange tmp = (ThingForChange) thing;

                if(tmp.getThingType().equals("attribute")){
                    CheckBox cb = (CheckBox) row.findViewById(R.id.checkBox);
                    SharedPreferences pref = prefManager.getSharedPreferences();
                    Boolean isChecked = pref.getBoolean(selectedPackage+"."+selectedLayout+"."+tmp.getName(), true);
                    cb.setChecked(isChecked);
                    cb.setFocusable(false);
                    View hide = (View) row.findViewById(R.id.text);
                    hide.setVisibility(View.GONE);
                    holder.text = cb;
                }else{
                    holder.text = (TextView) row.findViewById(R.id.text);
                    View hide = (View) row.findViewById(R.id.checkBox);
                    hide.setVisibility(View.GONE);
                }

                row.setTag(holder);
                holder.text.setText(tmp.getName());
            }


            return row;
        }

        class ViewHolder {
            public TextView text;
        }

    }

}

