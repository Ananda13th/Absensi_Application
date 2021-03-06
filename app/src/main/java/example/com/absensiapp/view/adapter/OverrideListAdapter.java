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
import example.com.absensiapp.databinding.ListOverrideBinding;
import example.com.absensiapp.model.OverrideRespModel;
import example.com.absensiapp.view.listener.OverrideRecycleListener;

public class OverrideListAdapter extends RecyclerView.Adapter<OverrideListAdapter.ViewHolder> implements OverrideRecycleListener {

    private List<OverrideRespModel> overrideList = new ArrayList<>();
    private OverrideRecycleListener overrideRecycleListener;

    public void setOnClick(OverrideRecycleListener listener) {
        this.overrideRecycleListener = listener;
    }

    @Override
    public void onClickAcceptButton(OverrideRespModel overrideRespModel) {
        overrideRecycleListener.onClickAcceptButton(overrideRespModel);
    }

    @Override
    public void onClickRejectButton(OverrideRespModel overrideRespModel) {
        overrideRecycleListener.onClickRejectButton(overrideRespModel);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ListOverrideBinding overrideListBinding;

        private ViewHolder(ListOverrideBinding overrideListBinding) {
            super(overrideListBinding.getRoot());
            this.overrideListBinding = overrideListBinding;
        }
    }

    public void setOverrideList(List<OverrideRespModel> overrideList) {
        this.overrideList = overrideList;
        for(Iterator<OverrideRespModel> iterator = overrideList.iterator(); iterator.hasNext();) {
            OverrideRespModel overrideHistoryRespModel = iterator.next();
            if(!overrideHistoryRespModel.getStatus().equals("Diproses")) {
                iterator.remove();
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OverrideListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListOverrideBinding overrideBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_override, parent, false);
        overrideBinding.setOnClick(this);
        return new ViewHolder(overrideBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OverrideListAdapter.ViewHolder holder, int position) {
        OverrideRespModel overrideRespModel = overrideList.get(position);
        holder.overrideListBinding.setOverrideItem(overrideRespModel);

    }

    @Override
    public int getItemCount() {
        if(overrideList != null)
            return overrideList.size();
        else
            return 0;
    }
}
