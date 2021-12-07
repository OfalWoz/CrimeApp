package pl.ofalwoz.crimeapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CrimeActivityAdapter extends RecyclerView.Adapter<CrimeActivityAdapter.CrimeViewHolder> {

    private List<Crime> crimeList;
    private LayoutInflater inflater;

    public CrimeActivityAdapter(CrimeActivity context) {
        inflater = LayoutInflater.from(context);
        this.crimeList = crimeList;
    }

    class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView Title;
        private TextView crimeSolvedText;
        private TextView crimeDateText;

        final CrimeActivityAdapter adapter;

        public CrimeViewHolder(@NonNull View itemView, CrimeActivityAdapter adapter) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            crimeSolvedText = itemView.findViewById(R.id.solved);
            crimeDateText = itemView.findViewById(R.id.date);
            this.adapter = adapter;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final Intent intent;

            int position = getLayoutPosition();
            Crime element = crimeList.get(position);
            crimeList.set(position, element);
            adapter.notifyItemChanged(position);

            intent = new Intent(view.getContext(), Crime.class);
            intent.putExtra("position", getLayoutPosition());
            view.getContext().startActivity(intent);
        }

    }


    @NonNull
    @Override
    public CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.word_list_item, parent, false);
        return new CrimeViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeViewHolder holder, int position) {
        Crime current = crimeList.get(position);
        holder.Title.setText(current.getTitle());
        holder.crimeSolvedText.setText("Solved:" + String.valueOf(current.getSolved()));
        holder.crimeDateText.setText(current.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }


}