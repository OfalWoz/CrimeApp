package pl.ofalwoz.crimeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.CrimeViewHolder> {

    private final CrimeLab mCrimes;
    private final LayoutInflater inflater;

    public static final String EXTRA_MESSAGE = "pl.edu.uwr.pum.recyclerviewwordlistjava.MESSAGE";

    public WordListAdapter(Context context){
        inflater = LayoutInflater.from(context);
        mCrimes = CrimeLab.get(context);
    }

    class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView crimeText;
        private ImageView crimeImage;
        private TextView date;
        final WordListAdapter adapter;
        public final Context context;

        public CrimeViewHolder(@NonNull View itemView, WordListAdapter adapter) {
            super(itemView);
            context = itemView.getContext();
            crimeText = itemView.findViewById(R.id.word);
            crimeImage = itemView.findViewById(R.id.solved_image);
            date = itemView.findViewById(R.id.date);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Crime currentCrime = mCrimes.getCrime(position);
            Intent intent = new Intent(context, CrimeActivity.class);
            intent.putExtra(EXTRA_MESSAGE, position);
            intent.putExtra("title", currentCrime.getTitle());
            intent.putExtra("id", position);
            intent.putExtra("date", currentCrime.getDate());
            intent.putExtra("solve", currentCrime.getSolved());
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public WordListAdapter.CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.word_list_item, parent, false);
        return new CrimeViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.CrimeViewHolder holder, int position) {
        Crime currentCrime = mCrimes.getCrime(position);
        holder.crimeText.setText(currentCrime.getTitle());
        holder.date.setText(currentCrime.getDate().toString());
        if (currentCrime.getSolved()){
            holder.crimeImage.setVisibility(View.VISIBLE);
        }
        else{
            holder.crimeImage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mCrimes.getSize();
    }
}