package example.com.absensiapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ListOverrideHistoryBinding;
import example.com.absensiapp.model.OverrideHistoryRespModel;
import example.com.absensiapp.view.listener.OverrideHistoryListener;
import example.com.absensiapp.view.utils.UtilsFormatter;

public class OverrideHistoryAdapter extends RecyclerView.Adapter<OverrideHistoryAdapter.ViewHolder> implements OverrideHistoryListener {

    private String status;
    private List<OverrideHistoryRespModel> overideHistoryList = new ArrayList<>();
    private OverrideHistoryListener overrideHistoryListener;
    private UtilsFormatter utilsFormatter = new UtilsFormatter();

    public OverrideHistoryAdapter(String status) {
        this.status = status;
    }

    public void setOnClick(OverrideHistoryListener listener) {
        this.overrideHistoryListener = listener;
    }

    @Override
    public void onClickDeleteButton(String overrideId) {
        overrideHistoryListener.onClickDeleteButton(overrideId);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ListOverrideHistoryBinding overrideHistoryListBinding;

        private ViewHolder(ListOverrideHistoryBinding listBinding) {
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
        ListOverrideHistoryBinding overrideHistoryListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_override_history, parent, false);
        return new ViewHolder(overrideHistoryListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OverrideHistoryAdapter.ViewHolder holder, int position) {
        OverrideHistoryRespModel overrideHistoryResp = overideHistoryList.get(position);
        holder.overrideHistoryListBinding.setOnClick(this);
        holder.overrideHistoryListBinding.setOverrideHistory(overrideHistoryResp);
        if(!holder.overrideHistoryListBinding.getOverrideHistory().getStatus().matches("Diproses")) {
            holder.overrideHistoryListBinding.btnDelete.setVisibility(View.INVISIBLE);
            holder.overrideHistoryListBinding.btnDelete.setEnabled(false);
        }
        //overrideHistoryResp.setAction(utilsFormatter.ActionOutputFormatter(overrideHistoryResp.getAction()));
    }

    @Override
    public int getItemCount() {
        if(overideHistoryList != null)
            return overideHistoryList.size();
        else
            return 0;
    }
}
