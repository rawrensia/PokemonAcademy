package com.example.pokemonacademy.Control;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
/**
 * The RecycleViewAdapter is used by the Leaderboard to create and display
 * the list of users along with their ranking.
 *
 * @author  Lawrann
 * @since   2019-11-01
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecycleViewAdapter";

    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<Integer> userTimeList;
    private ArrayList<String> userGradesList;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<Integer> userTimeList, ArrayList<String> userGradesList, ArrayList<User> userList, Context mContext) {
        this.userList = userList;
        this.userTimeList = userTimeList;
        this.userGradesList = userGradesList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i){
        Log.d(TAG, "onBindViewHolder: called." +i);
        Log.d(TAG, "" + userList.get(i).getName());
        Log.d(TAG, "" + userGradesList.get(i));
        Log.d(TAG, "" + userTimeList.get(i));

        viewHolder.name.setText(userList.get(i).getName());
        viewHolder.grades.setText(userGradesList.get(i));
        int time = userTimeList.get(i);
        int s = time%60;
        int m = time/60;
        if (s<10 & m<10){
            viewHolder.timing.setText("0" + m + ":0" + s);
        } else if (s<10){
            viewHolder.timing.setText(m + ":0" + s);
        } else if (m<10){
            viewHolder.timing.setText("0" + m + ":" + s);
        } else {
            viewHolder.timing.setText(m + ":" + s);
        }

        if (userList.get(i).getCharId() == 0) {
            viewHolder.image.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.char3, null));
        } else if (userList.get(i).getCharId() == 1) {
            viewHolder.image.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.char1, null));
        } else if (userList.get(i).getCharId() == 2) {
            viewHolder.image.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.char5, null));
        } else if (userList.get(i).getCharId() == 3) {
            viewHolder.image.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.char2, null));
        }
        viewHolder.rank.setText("#"+(i+1));
    }

    @Override
    public int getItemCount() {
        return userList.size(); //set the demo items
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView name;
        TextView grades;
        TextView timing;
        TextView rank;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            grades = itemView.findViewById(R.id.grades);
            timing = itemView.findViewById(R.id.timing);
            rank = itemView.findViewById(R.id.rank);
        }
    }

}
