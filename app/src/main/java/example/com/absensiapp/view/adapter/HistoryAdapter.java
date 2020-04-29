package example.com.absensiapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ListHistoryBinding;
import example.com.absensiapp.model.HistDataModel;
import lombok.SneakyThrows;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistDataModel> historyList = new ArrayList<>();
    private Context context;

    public HistoryAdapter(){}

    public void setHistoryList (List<HistDataModel> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ListHistoryBinding listBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_history, parent, false);
        return new ViewHolder(listBinding);
    }

    @SneakyThrows
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistDataModel histDataModel = historyList.get(position);
        holder.listBinding.setHistoryData(histDataModel);
        if(holder.listBinding.getHistoryData().getOutputDesc().equals(""))
            holder.listBinding.statusFrame.setBackgroundColor(context.getResources().getColor(R.color.green));
        else
            holder.listBinding.statusFrame.setBackgroundColor(context.getResources().getColor(R.color.red));

        if(holder.listBinding.getHistoryData().getOutputDesc().equals("Not 9 Hours Working Time"))
            holder.listBinding.statusFrame.setBackgroundColor(context.getResources().getColor(R.color.yellow));

        if(holder.listBinding.getHistoryData().getOutputTimeIn().equals(""))
            holder.listBinding.timeInFrame.setBackgroundColor(context.getResources().getColor(R.color.red));


        if(holder.listBinding.getHistoryData().getOutputTimeOut().equals(""))
            holder.listBinding.timeOutFrame.setBackgroundColor(context.getResources().getColor(R.color.red));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ListHistoryBinding listBinding;

        private ViewHolder( ListHistoryBinding listBinding) {
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
