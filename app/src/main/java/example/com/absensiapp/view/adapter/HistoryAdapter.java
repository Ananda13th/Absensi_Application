package example.com.absensiapp.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.HistoryListBinding;
import example.com.absensiapp.model.HistDataModel;
import lombok.SneakyThrows;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistDataModel> historyList = new ArrayList<>();

    public HistoryAdapter(){}

    public void setHistoryList (List<HistDataModel> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryListBinding listBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.history_list, parent, false);
        return new ViewHolder(listBinding);
    }

    @SneakyThrows
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistDataModel histDataModel = historyList.get(position);
        holder.listBinding.setHistoryData(histDataModel);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        HistoryListBinding listBinding;

        private ViewHolder( HistoryListBinding listBinding) {
            super(listBinding.getRoot());
            this.listBinding = listBinding;
        }
    }

    @Override
    public int getItemCount() {
        if(historyList != null)
            return historyList.size();
        else
            return 0;
    }
}
