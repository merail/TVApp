package com.example.tvapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapp.adapters.ChannelsRecyclerViewAdapter;
import com.example.tvapp.interfaces.OnChannelClickListener;
import com.example.tvapp.server.Channel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChannelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChannelsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Channel[] channels;
    private RecyclerView channelsRecyclerView;
    private OnChannelClickListener onChannelClickListener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChannelsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param channels Parameter 1.
     * @return A new instance of fragment ChannelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChannelsFragment newInstance(Channel[] channels, OnChannelClickListener onChannelClickListener) {
        ChannelsFragment fragment = new ChannelsFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(ARG_PARAM1, channels);
        args.putParcelable(ARG_PARAM2, onChannelClickListener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            channels = (Channel[]) getArguments().getParcelableArray(ARG_PARAM1);
            onChannelClickListener = getArguments().getParcelable(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_channels, container, false);

        channelsRecyclerView = v.findViewById(R.id.channelsRecyclerView);
        channelsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));
        channelsRecyclerView.setAdapter(new ChannelsRecyclerViewAdapter(getContext(),
                channels, onChannelClickListener));

        return v;
    }
}