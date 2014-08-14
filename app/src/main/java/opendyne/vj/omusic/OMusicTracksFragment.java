package opendyne.vj.omusic;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * This takes care of fetching songs from SD card using @ReadingSongs class. which returns
 *  an array list of songs with songTitle, songPath, songDuration.
 *
 */

public class OMusicTracksFragment extends Fragment {
    ListView trackList;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    public OMusicTracksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.omusic_tracks_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();
        ReadingSongs rs = new ReadingSongs();
        songsList = rs.getPlayList();

        for(int i=0;i<songsList.size();i++) {
            HashMap<String, String> songs = songsList.get(i);
            songsListData.add(songs);

        }

        trackList = (ListView) getActivity().findViewById(R.id.trackListView);
        ListAdapter adapter = new SimpleAdapter(getActivity(), songsListData,
                R.layout.track_item, new String[] {"songTitle", "songDuration"}, new int[] {
                R.id.trackNameTextVew, R.id.trackTimetextView
        });
        trackList.setAdapter(adapter);

    }
}
