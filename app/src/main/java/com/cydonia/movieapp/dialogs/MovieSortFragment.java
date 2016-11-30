package com.cydonia.movieapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.cydonia.movieapp.R;
import com.cydonia.movieapp.Sort;

public class MovieSortFragment extends DialogFragment {

    private MovieSortListener sortListener;
    private RadioGroup optionsGroup;
    public void setSortListener(MovieSortListener listener){
        sortListener = listener;
    }
    private Sort selectedSortFilter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_movie_sort, null);
        optionsGroup = (RadioGroup)view.findViewById(R.id.movie_sort_group);
        optionsGroup.check(PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getInt("selected_option", R.id.nowplaying_movies));
        builder.setView(view);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
                preferenceEditor.putInt("selected_option", optionsGroup.getCheckedRadioButtonId());
                preferenceEditor.apply();
                dialog.dismiss();
                sortListener.sortSelected(selectedSortFilter);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        addListeners();

        return builder.create();
    }

    private void addListeners() {
        optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.popular_movies : selectedSortFilter = Sort.POPULAR;
                        break;
                    case R.id.nowplaying_movies : selectedSortFilter = Sort.NOW_PLAYING;
                        break;
                    case R.id.toprated_movies : selectedSortFilter = Sort.TOP_RATED;
                        break;
                    case R.id.upcoming_movies : selectedSortFilter = Sort.UPCOMING;
                        break;
                    default:
                        break;
                }
            }
        });
    }


    public interface MovieSortListener {

        void sortSelected(Sort sortSelection);
    }
}
