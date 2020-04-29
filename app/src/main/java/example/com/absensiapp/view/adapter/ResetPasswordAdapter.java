package example.com.absensiapp.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ListResetPasswordBinding;
import example.com.absensiapp.model.ResetPasswordRespModel;
import example.com.absensiapp.view.listener.ResetPasswordListener;

public class ResetPasswordAdapter extends RecyclerView.Adapter<ResetPasswordAdapter.ViewHolder> implements ResetPasswordListener {

    private List<ResetPasswordRespModel> resetList = new ArrayList<>();
    private ResetPasswordListener resetPasswordListener;

    public void setResetList(List<ResetPasswordRespModel> resetList) {
        this.resetList = resetList;
        notifyDataSetChanged();
    }

    public void setOnClick(ResetPasswordListener listener) {
        this.resetPasswordListener = listener;
    }

    @Override
    public void onClickCardview(ResetPasswordRespModel respModel) {
        resetPasswordListener.onClickCardview(respModel);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ListResetPasswordBinding listBinding;

        private ViewHolder (ListResetPasswordBinding listBinding) {
            super(listBinding.getRoot());
            this.listBinding = listBinding;
        }
    }

    @NonNull
    @Override
    public ResetPasswordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListResetPasswordBinding listBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_reset_password, parent, false);
        return new ViewHolder(listBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResetPasswordAdapter.ViewHolder holder, int position) {
        ResetPasswordRespModel resetModel = resetList.get(position);
        holder.listBinding.setResetItem(resetModel);
        holder.listBinding.setOnClick(this);

    }

    @Override
    public int getItemCount() {
        if(resetList!= null)
            return resetList.size();
        else
            return 0;
    }

}
