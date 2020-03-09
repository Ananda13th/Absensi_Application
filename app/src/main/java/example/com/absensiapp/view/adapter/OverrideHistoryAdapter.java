package example.com.absensiapp.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.OverrideHistoryListBinding;
import example.com.absensiapp.model.OverrideHistoryRespModel;

public class OverrideHistoryAdapter extends RecyclerView.Adapter<OverrideHistoryAdapter.ViewHolder>  {

    private String status;
    private List<OverrideHistoryRespModel> overideHistoryList = new ArrayList<>();

    public OverrideHistoryAdapter(String status) {
        this.status = status;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        OverrideHistoryListBinding overrideHistoryListBinding;

        private ViewHolder(OverrideHistoryListBinding listBinding) {
            super(listBinding.getRoot());
            this.overrideHistoryListBinding = listBinding;
        }
    }

    public void setOverrideHistory(List<OverrideHistoryRespModel> overrideHistoryRespModelList) {
        this.overideHistoryList = overrideHistoryRespModelList;

        for(Iterator<OverrideHistoryRespModel> iterator = overrideHistoryRespModelList.iterator(); iterator.hasNext();) {
            OverrideHistoryRespModel overrideHistoryRespModel = iterator.next();
            if(!overrideHistoryRespModel.getStatus().equals(status)) {
                iterator.remove();
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OverrideHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OverrideHistoryListBinding overrideHistoryListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.override_history_list, parent, false);
        return new ViewHolder(overrideHistoryListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OverrideHistoryAdapter.ViewHolder holder, int position) {
        OverrideHistoryRespModel overrideHistoryResp = overideHistoryList.get(position);
        holder.overrideHistoryListBinding.setOverideHistory(overrideHistoryResp);

    }

    @Override
    public int getItemCount() {
        if(overideHistoryList != null)
            return overideHistoryList.size();
        else
            return 0;
    }
}
